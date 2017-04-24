package com.myardina.buckeyes.myardina.DTO;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Tyler on 11/5/2016.
 */
public class DoctorDTO extends UserDTO {

    private String location;
    private String requesterPhoneNumber;
    private String visitWith;
    private int totalRatingPoints;
    private int ratingCount;
    private boolean available;
    private boolean verifiedDoctor;
    private boolean requested;
    private boolean videoRequested;
    private boolean chatRequested;

    public String getLocation() {
        return location;
    }

    public boolean getVideoRequested() { return videoRequested;}

    public void setVideoRequested(boolean videoRequested) { this.videoRequested = videoRequested;}

    public boolean getChatRequested() { return chatRequested;}

    public void setChatRequested(boolean chatRequested) { this.chatRequested = chatRequested;}

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRequesterPhoneNumber() {
        return requesterPhoneNumber;
    }

    public void setRequesterPhoneNumber(String requesterPhoneNumber) { this.requesterPhoneNumber = requesterPhoneNumber; }

    public boolean isAvailable() { return available; }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean isVerifiedDoctor() {
        return verifiedDoctor;
    }

    public void setVerifiedDoctor(boolean verifiedDoctor) {
        this.verifiedDoctor = verifiedDoctor;
    }

    public boolean isRequested() {
        return requested;
    }

    public void setRequested(boolean requested) {
        this.requested = requested;
    }

    public String getVisitWith(){
        return visitWith;
    }

    public void setVisitWith(String visitWith){
        this.visitWith = visitWith;
    }

    public void setTotalRatingPoints(int totalRatingPoints){ this.totalRatingPoints = totalRatingPoints;}

    public int getTotalRatingPoints(){ return totalRatingPoints; }

    public void setRatingCount(int ratingCount) { this.ratingCount = ratingCount;}

    public int getRatingCount(){ return ratingCount; }

    // PARCEL OBJECT

    public DoctorDTO() { super(); }

    public DoctorDTO(Parcel in){
        super(in);
        this.location = in.readString();
        this.requesterPhoneNumber = in.readString();
        this.available = in.readByte() != 0;
        this.verifiedDoctor = in.readByte() != 0;
        this.requested = in.readByte() != 0;
        this.videoRequested = in.readByte() != 0;
        this.chatRequested = in.readByte() != 0;
        this.visitWith = in.readString();
        this.ratingCount = in.readInt();
        this.totalRatingPoints = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(location);
        dest.writeString(requesterPhoneNumber);
        dest.writeByte((byte) (available ? 1 : 0));
        dest.writeByte((byte) (verifiedDoctor ? 1 : 0));
        dest.writeByte((byte) (requested ? 1 : 0));
        dest.writeByte((byte)(videoRequested ? 1 : 0));
        dest.writeByte((byte)(chatRequested ? 1 : 0));
        dest.writeString(visitWith);
        dest.writeInt(ratingCount);
        dest.writeInt(totalRatingPoints);

    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public DoctorDTO createFromParcel(Parcel in) {
            return new DoctorDTO(in);
        }

        public DoctorDTO[] newArray(int size) {
            return new DoctorDTO[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
}
