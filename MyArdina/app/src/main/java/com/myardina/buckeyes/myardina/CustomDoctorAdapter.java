package com.myardina.buckeyes.myardina;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.myardina.buckeyes.myardina.DTO.DoctorDTO;

import java.util.ArrayList;

/**
 * Created by drewgallagher on 4/7/17.
 */

public class CustomDoctorAdapter extends BaseAdapter {

    Context context;
    ArrayList<DoctorDTO> data;
    private static LayoutInflater inflater = null;

    public CustomDoctorAdapter(Context context, ArrayList<DoctorDTO> data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    /**
     * Get star image resource id
     * @param doctorDTO
     * @return int that corresponds ot image resource id
     */
    private int getStarImage(DoctorDTO doctorDTO){
        int id = 0;
        double ratingAverage = (doctorDTO.getRatingCount() == 0) ? 0 : doctorDTO.getTotalRatingPoints() / (double) doctorDTO.getRatingCount();

        if(ratingAverage >= 0 && ratingAverage < 0.5){
            id = R.drawable.no_stars;
        }
        else if(ratingAverage >= 0.5 && ratingAverage < 1){
            id = R.drawable.star_half;
        }
        else if(ratingAverage >= 1.0 && ratingAverage < 1.5){
            id = R.drawable.star_1;
        }
        else if(ratingAverage >= 1.5 && ratingAverage < 2.0){
            id = R.drawable.stars_1_half;
        }
        else if(ratingAverage >= 2.0 && ratingAverage < 2.5){
            id = R.drawable.stars_2;
        }
        else if(ratingAverage >= 2.5 && ratingAverage < 3) {
            id = R.drawable.stars_2_half;
        }
        else if(ratingAverage >= 3.0 && ratingAverage < 3.5){
            id = R.drawable.stars_3;
        }
        else if(ratingAverage >= 3.5 && ratingAverage < 4.0){
            id = R.drawable.stars_3_half;
        }
        else if(ratingAverage >= 4.0 && ratingAverage < 4.5){
            id = R.drawable.stars_4;
        }
        else if(ratingAverage >= 4.5 && ratingAverage < 5.0){
            id = R.drawable.stars_4_half;
        }
        else if(ratingAverage == 5.0){
            id = R.drawable.stars_5;
        }

        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.custom_doctor_available_row, null);
        TextView text = (TextView) vi.findViewById(R.id.text);
        ImageView starImage = (ImageView)vi.findViewById(R.id.image);
        starImage.setImageResource(getStarImage(data.get(position)));
        StringBuilder sb = new StringBuilder();
        sb.append(data.get(position).getFirstName());
        sb.append(" ");
        sb.append(data.get(position).getLastName());
        text.setText(sb.toString());
        return vi;
    }
}
