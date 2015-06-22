package com.happy.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable{
	private String name;
	private String feedback;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFeedback() {
		return feedback;
	}
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeString(feedback);
	}
	
	public static Parcelable.Creator<User> CREATOR = new Creator<User>() {
		
		@Override
		public User[] newArray(int size) {
			// TODO Auto-generated method stub
			return new User[size];
		}
		
		@Override
		public User createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			User user = new User();
			user.name = source.readString();
			user.feedback = source.readString();
			return user;
			
		}
	};
	
	
}
