package com.myardina.buckeyes.myardina.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.myardina.buckeyes.myardina.Common.CommonConstants;
import com.myardina.buckeyes.myardina.DTO.DoctorDTO;
import com.myardina.buckeyes.myardina.DTO.PatientDTO;
import com.myardina.buckeyes.myardina.R;
import com.myardina.buckeyes.myardina.Common.Helper;
import com.sendbird.android.AdminMessage;
import com.sendbird.android.BaseChannel;
import com.sendbird.android.BaseMessage;
import com.sendbird.android.FileMessage;
import com.sendbird.android.OpenChannel;
import com.sendbird.android.PreviousMessageListQuery;
import com.sendbird.android.SendBird;
import com.sendbird.android.SendBirdException;
import com.sendbird.android.User;
import com.sendbird.android.UserMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private static final String LOG_TAG = "Chat Activity";
    private static final String APP_ID = "27D7EF86-47BE-4756-92E9-339C2803515F";

    public static String sUserId;
    private String mNickname;

    private SendBirdChatFragment mSendBirdChatFragment;
    private View mTopBarContainer;
    private String mChannelUrl = "ardina_bmmas";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //sUserId = getPreferences(Context.MODE_PRIVATE).getString("user_id", "");

        //patient
        if(getIntent().hasExtra(CommonConstants.PATIENT_DTO)){
            PatientDTO patient = (PatientDTO) getIntent().getExtras().get(CommonConstants.PATIENT_DTO);
            if(patient != null){
                if(patient.getFirstName().length() == 0){
                    sUserId = "Patient";
                    mNickname = "Patient";
                }
                else{
                    sUserId = patient.getFirstName();
                    mNickname = patient.getFirstName();
                }
            }


        }
        //doctor
        else{
            DoctorDTO doctor = (DoctorDTO) getIntent().getExtras().get(CommonConstants.DOCTOR_DTO);
            if(doctor != null){
                if(doctor.getFirstName().length() == 0){
                    sUserId = "Doctor";
                    mNickname = "Doctor";
                }
                else{
                    sUserId = doctor.getFirstName();
                    mNickname = doctor.getFirstName();
                }
            } 
            
        }

        SendBird.init(APP_ID, this);
        connect();




    }

    @Override
    protected void onDestroy() {
        SendBird.disconnect(new SendBird.DisconnectHandler() {
            @Override
            public void onDisconnected() {}
        });
        super.onDestroy();
    }

    private void initFragment() {
        mSendBirdChatFragment = new SendBirdChatFragment();
        Bundle args = new Bundle();
        args.putString("channel_url", mChannelUrl);
        mSendBirdChatFragment.setArguments(args);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, mSendBirdChatFragment)
                .commit();
    }

    private void initUIComponents() {
        mTopBarContainer = findViewById(R.id.top_bar_container);

        //Resize the menubar:
        ViewGroup.LayoutParams lp = mTopBarContainer.getLayoutParams();
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            lp.height = (int) (28 * getResources().getDisplayMetrics().density);
        } else {
            lp.height = (int) (48 * getResources().getDisplayMetrics().density);
        }
        mTopBarContainer.setLayoutParams(lp);
    }

    private void connect() {
        SendBird.connect(sUserId, new SendBird.ConnectHandler() {
            @Override
            public void onConnected(User user, SendBirdException e) {
                if (e != null) {
                    Toast.makeText(ChatActivity.this, "" + e.getCode() + ":" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    //disconnected
                    return;
                }


                String nickname = mNickname;

                SendBird.updateCurrentUserInfo(nickname, null, new SendBird.UserInfoUpdateHandler() {
                    @Override
                    public void onUpdated(SendBirdException e) {
                        if (e != null) {
                            Toast.makeText(ChatActivity.this, "" + e.getCode() + ":" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            //disconnected
                            return;
                        }

                        SharedPreferences.Editor editor = getPreferences(Context.MODE_PRIVATE).edit();
                        editor.putString("user_id", sUserId);
                        editor.putString("nickname", mNickname);
                        editor.commit();
                        //now connected
                    }
                });

                initFragment();
                initUIComponents();




                if (FirebaseInstanceId.getInstance().getToken() == null) return;

//                SendBird.registerPushTokenForCurrentUser(FirebaseInstanceId.getInstance().getToken(), true, new SendBird.RegisterPushTokenWithStatusHandler() {
//                    @Override
//                    public void onRegistered(SendBird.PushTokenRegistrationStatus pushTokenRegistrationStatus, SendBirdException e) {
//                        if (e != null) {
//                            Toast.makeText(ChatActivity.this, "" + e.getCode() + ":" + e.getMessage(), Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//                    }
//                });
            }
        });

    }


    /**
     * The fragment where all chatting takes place
     */
    public static class SendBirdChatFragment extends Fragment {
        private static final int REQUEST_PICK_IMAGE = 100;
        private static final String identifier = "SendBirdOpenChat";

        private ListView mListView;
        private SendBirdChatAdapter mAdapter;
        private EditText mEtxtMessage;
        private Button mBtnSend;
        private String mChannelUrl;
        private OpenChannel mOpenChannel;
        private PreviousMessageListQuery mPrevMessageListQuery;
        private boolean mIsUploading;

        public SendBirdChatFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_chat, container, false);

            mChannelUrl = getArguments().getString("channel_url");

            initUIComponents(rootView);

            enterChannel(mChannelUrl);

            return rootView;
        }

        @Override
        public void onPause() {
            super.onPause();
            if (!mIsUploading) {
                SendBird.removeChannelHandler(identifier);
            }
        }

        @Override
        public void onResume() {
            super.onResume();
            if (!mIsUploading) {
                SendBird.addChannelHandler(identifier, new SendBird.ChannelHandler() {
                    @Override
                    public void onMessageReceived(BaseChannel baseChannel, BaseMessage baseMessage) {
                        if (baseChannel.getUrl().equals(mChannelUrl)) {
                            mAdapter.appendMessage(baseMessage);
                            mAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onMessageDeleted(BaseChannel channel, long msgId) {
                        if (channel.getUrl().equals(mChannelUrl)) {
                            boolean deleteMsg = false;

                            for (int i = 0; i < mAdapter.getCount(); i++) {
                                BaseMessage msg = (BaseMessage) mAdapter.getItem(i);
                                if (msg.getMessageId() == msgId) {
                                    mAdapter.delete(msg);
                                    deleteMsg = true;
                                    break;
                                }
                            }

                            if (deleteMsg) {
                                mAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });

                refreshChannel();
                loadPrevMessages(false);
            } else {
                mIsUploading = false;

                /**
                 * Set this as true to restart auto-background detection,
                 * when your Activity is shown again after the external Activity is finished.
                 */
                SendBird.setAutoBackgroundDetection(true);
            }
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
        }

        private void loadPrevMessages(final boolean refresh) {
            if (mOpenChannel == null) {
                return;
            }

            if (refresh || mPrevMessageListQuery == null) {
                mPrevMessageListQuery = mOpenChannel.createPreviousMessageListQuery();
            }

            if (mPrevMessageListQuery.isLoading()) {
                return;
            }

            if (!mPrevMessageListQuery.hasMore()) {
                return;
            }

            mPrevMessageListQuery.load(30, true, new PreviousMessageListQuery.MessageListQueryResult() {
                @Override
                public void onResult(List<BaseMessage> list, SendBirdException e) {
                    if (e != null) {
                        Toast.makeText(getActivity(), "" + e.getCode() + ":" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (refresh) {
                        mAdapter.clear();
                    }

                    for (BaseMessage message : list) {
                        mAdapter.insertMessage(message);
                    }
                    mAdapter.notifyDataSetChanged();
                    mListView.setSelection(list.size());
                }
            });
        }

        private void enterChannel(String channelUrl) {
            OpenChannel.getChannel(channelUrl, new OpenChannel.OpenChannelGetHandler() {
                @Override
                public void onResult(final OpenChannel openChannel, SendBirdException e) {
                    if (e != null) {
                        Toast.makeText(getActivity(), "" + e.getCode() + ":" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        return;
                    }

                    openChannel.enter(new OpenChannel.OpenChannelEnterHandler() {
                        @Override
                        public void onResult(SendBirdException e) {
                            if (e != null) {
                                Toast.makeText(getActivity(), "" + e.getCode() + ":" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                return;
                            }

                            mOpenChannel = openChannel;
                            ((TextView) getActivity().findViewById(R.id.txt_channel_name)).setText(openChannel.getName());

                            //loadPrevMessages(false);
                        }
                    });
                }
            });
        }

        private void refreshChannel() {
            if (mOpenChannel != null) {
                mOpenChannel.refresh(null);
            }
        }

        private void initUIComponents(View rootView) {
            mAdapter = new SendBirdChatAdapter(getActivity());

            mListView = (ListView) rootView.findViewById(R.id.list);
            turnOffListViewDecoration(mListView);
            mListView.setAdapter(mAdapter);

            mBtnSend = (Button) rootView.findViewById(R.id.btn_send);
            mEtxtMessage = (EditText) rootView.findViewById(R.id.etxt_message);

            mBtnSend.setEnabled(false);
            mBtnSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    send();
                }
            });

            mEtxtMessage.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_ENTER) {
                        if (event.getAction() == KeyEvent.ACTION_DOWN) {
                            send();
                        }
                        return true; // Do not hide keyboard.
                    }
                    return false;
                }
            });
            mEtxtMessage.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
            mEtxtMessage.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    mBtnSend.setEnabled(s.length() > 0);
                }
            });

            mListView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    Helper.hideKeyboard(getActivity());
                    return false;
                }
            });
            mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                    if (scrollState == SCROLL_STATE_IDLE) {
                        if (view.getFirstVisiblePosition() == 0 && view.getChildCount() > 0 && view.getChildAt(0).getTop() == 0) {
                            loadPrevMessages(true);
                        }
                    }
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                }
            });
            mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Select")
                            .setItems(new String[]{"Delete Message", "Block User"}, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case 0:
                                            final BaseMessage msg0 = (BaseMessage) mAdapter.getItem(position);
                                            mOpenChannel.deleteMessage(msg0, new BaseChannel.DeleteMessageHandler() {
                                                @Override
                                                public void onResult(SendBirdException e) {
                                                    if (e != null) {
                                                        Toast.makeText(getActivity(), "" + e.getCode() + ":" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                        return;
                                                    }

                                                    Toast.makeText(getActivity(), "Message deleted.", Toast.LENGTH_SHORT).show();
                                                    // Message will be deleted at ChannelHandler.
                                                }
                                            });
                                            break;

                                        case 1:
                                            BaseMessage msg1 = (BaseMessage) mAdapter.getItem(position);
                                            User target = null;

                                            if (msg1 instanceof AdminMessage) {
                                                Toast.makeText(getActivity(), "Admin message can not be deleted.", Toast.LENGTH_SHORT).show();
                                                return;
                                            } else if (msg1 instanceof UserMessage) {
                                                target = ((UserMessage) msg1).getSender();
                                            } else if (msg1 instanceof FileMessage) {
                                                target = ((FileMessage) msg1).getSender();
                                            }

                                            SendBird.blockUser(target, new SendBird.UserBlockHandler() {
                                                @Override
                                                public void onBlocked(User user, SendBirdException e) {
                                                    if (e != null) {
                                                        Toast.makeText(getActivity(), "" + e.getCode() + ":" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                        return;
                                                    }

                                                    Toast.makeText(getActivity(), user.getNickname() + " is blocked.", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                            break;
                                    }
                                }
                            })
                            .setNegativeButton("Cancel", null).create().show();

                    return true;
                }
            });
        }

        private void turnOffListViewDecoration(ListView listView) {
            listView.setDivider(null);
            listView.setDividerHeight(0);
            listView.setHorizontalFadingEdgeEnabled(false);
            listView.setVerticalFadingEdgeEnabled(false);
            listView.setHorizontalScrollBarEnabled(false);
            listView.setVerticalScrollBarEnabled(true);
            listView.setSelector(new ColorDrawable(0x00ffffff));
            listView.setCacheColorHint(0x00000000); // For Gingerbread scrolling bug fix
        }

        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == Activity.RESULT_OK) {
                if (requestCode == REQUEST_PICK_IMAGE && data != null && data.getData() != null) {
                    //upload(data.getData());
                    Log.d(LOG_TAG, "Upload"); //TODO: remove upload feature
                }
            }
        }

        private void send() {
            mOpenChannel.sendUserMessage(mEtxtMessage.getText().toString(), new BaseChannel.SendUserMessageHandler() {
                @Override
                public void onSent(UserMessage userMessage, SendBirdException e) {
                    if (e != null) {
                        Toast.makeText(getActivity(), "" + e.getCode() + ":" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        return;
                    }

                    mAdapter.appendMessage(userMessage);
                    mAdapter.notifyDataSetChanged();

                    mEtxtMessage.setText("");
                }
            });

            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                Helper.hideKeyboard(getActivity());
            }
        }
    }



    public static class SendBirdChatAdapter extends BaseAdapter {
        private static final int TYPE_UNSUPPORTED = 0;
        private static final int TYPE_USER_MESSAGE = 1;
        private static final int TYPE_FILE_MESSAGE = 2;
        private static final int TYPE_ADMIN_MESSAGE = 3;

        private final Context mContext;
        private final LayoutInflater mInflater;
        private final ArrayList<Object> mItemList;

        public SendBirdChatAdapter(Context context) {
            mContext = context;
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mItemList = new ArrayList<>();
        }

        public void delete(Object message) {
            mItemList.remove(message);
        }

        @Override
        public int getCount() {
            return mItemList.size();
        }

        @Override
        public Object getItem(int position) {
            return mItemList.get(position);
        }

        public void clear() {
            mItemList.clear();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public void insertMessage(BaseMessage message) {
            mItemList.add(0, message);
        }

        public void appendMessage(BaseMessage message) {
            mItemList.add(message);
        }

        @Override
        public int getItemViewType(int position) {
            Object item = mItemList.get(position);
            if (item instanceof UserMessage) {
                return TYPE_USER_MESSAGE;
            } else if (item instanceof FileMessage) {
                return TYPE_FILE_MESSAGE;
            } else if (item instanceof AdminMessage) {
                return TYPE_ADMIN_MESSAGE;
            }

            return TYPE_UNSUPPORTED;
        }

        @Override
        public int getViewTypeCount() {
            return 4;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            final Object item = getItem(position);

            if (convertView == null || ((ViewHolder) convertView.getTag()).getViewType() != getItemViewType(position)) {
                viewHolder = new ViewHolder();
                viewHolder.setViewType(getItemViewType(position));

                switch (getItemViewType(position)) {
                    case TYPE_UNSUPPORTED:
                        convertView = new View(mInflater.getContext());
                        convertView.setTag(viewHolder);
                        break;
                    case TYPE_USER_MESSAGE: {
                        TextView tv;

                        convertView = mInflater.inflate(R.layout.sendbird_view_open_user_message, parent, false);
                        tv = (TextView) convertView.findViewById(R.id.txt_message);
                        viewHolder.setView("message", tv);
                        convertView.setTag(viewHolder);
                        break;
                    }
                    case TYPE_ADMIN_MESSAGE: {
                        convertView = mInflater.inflate(R.layout.sendbird_view_admin_message, parent, false);
                        viewHolder.setView("message", convertView.findViewById(R.id.txt_message));
                        convertView.setTag(viewHolder);
                        break;
                    }
                    case TYPE_FILE_MESSAGE: {
                        TextView tv;

                        convertView = mInflater.inflate(R.layout.sendbird_view_open_file_message, parent, false);
                        tv = (TextView) convertView.findViewById(R.id.txt_sender_name);
                        viewHolder.setView("txt_sender_name", tv);

                        viewHolder.setView("img_file_container", convertView.findViewById(R.id.img_file_container));

                        viewHolder.setView("image_container", convertView.findViewById(R.id.image_container));
                        viewHolder.setView("img_thumbnail", convertView.findViewById(R.id.img_thumbnail));
                        viewHolder.setView("txt_image_name", convertView.findViewById(R.id.txt_image_name));
                        viewHolder.setView("txt_image_size", convertView.findViewById(R.id.txt_image_size));

                        viewHolder.setView("file_container", convertView.findViewById(R.id.file_container));
                        viewHolder.setView("txt_file_name", convertView.findViewById(R.id.txt_file_name));
                        viewHolder.setView("txt_file_size", convertView.findViewById(R.id.txt_file_size));

                        convertView.setTag(viewHolder);

                        break;
                    }
                }
            }

            viewHolder = (ViewHolder) convertView.getTag();
            switch (getItemViewType(position)) {
                case TYPE_UNSUPPORTED:
                    break;
                case TYPE_USER_MESSAGE:
                    final UserMessage message = (UserMessage) item;
                    viewHolder.getView("message", TextView.class).setText(Html.fromHtml("<font color='#824096'><b>" + message.getSender().getNickname() + "</b></font>: " + message.getMessage()));
                    break;
                case TYPE_ADMIN_MESSAGE:
                    AdminMessage adminMessage = (AdminMessage) item;
                    viewHolder.getView("message", TextView.class).setText(Html.fromHtml(adminMessage.getMessage()));
                    break;
                case TYPE_FILE_MESSAGE:
                    final FileMessage fileLink = (FileMessage) item;

                    viewHolder.getView("txt_sender_name", TextView.class).setText(Html.fromHtml("<font color='#824096'><b>" + fileLink.getSender().getNickname() + "</b></font>: "));

                    if (fileLink.getType().toLowerCase().startsWith("image")) {
                        // Get thumbnails from filemessage
                        ArrayList<FileMessage.Thumbnail> thumbnails = (ArrayList<FileMessage.Thumbnail>) fileLink.getThumbnails();

                        viewHolder.getView("file_container").setVisibility(View.GONE);

                        viewHolder.getView("image_container").setVisibility(View.VISIBLE);
                        viewHolder.getView("txt_image_name", TextView.class).setText(fileLink.getName());
                        viewHolder.getView("txt_image_size", TextView.class).setText(Helper.readableFileSize(fileLink.getSize()));

                        // If thumbnails exist, get smallest (first) thumbnail and display it in the message
                        if (thumbnails.size() > 0) {
                            Helper.displayUrlImage(viewHolder.getView("img_thumbnail", ImageView.class), thumbnails.get(0).getUrl());
                        } else {
                            Helper.displayUrlImage(viewHolder.getView("img_thumbnail", ImageView.class), fileLink.getUrl());
                        }

                    } else {
                        viewHolder.getView("image_container").setVisibility(View.GONE);

                        viewHolder.getView("file_container").setVisibility(View.VISIBLE);
                        viewHolder.getView("txt_file_name", TextView.class).setText(fileLink.getName());
                        viewHolder.getView("txt_file_size", TextView.class).setText(Helper.readableFileSize(fileLink.getSize()));
                    }
                    viewHolder.getView("img_file_container").setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new AlertDialog.Builder(mContext)
                                    .setTitle("SendBird")
                                    .setMessage("Do you want to download this file?")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            try {
                                                Helper.downloadUrl(fileLink.getUrl(), fileLink.getName(), mContext);
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    })
                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    })
                                    .create()
                                    .show();
                        }
                    });
                    break;
            }

            return convertView;
        }

        private class ViewHolder {
            private Hashtable<String, View> holder = new Hashtable<>();
            private int type;

            public int getViewType() {
                return this.type;
            }

            public void setViewType(int type) {
                this.type = type;
            }

            public void setView(String k, View v) {
                holder.put(k, v);
            }

            public View getView(String k) {
                return holder.get(k);
            }

            public <T> T getView(String k, Class<T> type) {
                return type.cast(getView(k));
            }
        }
    }
}
