package edu.asupoly.ser422.info;

import java.util.Arrays;

public class UserInfo {
	
	String fname;
	String lname;
	String [] languages;
	String [] daysOfWeek;
	String hairColor;
	
	
	public UserInfo(String fname, String lname, String[] languages, String[] daysOfWeek, String hairColor) {
		super();
		this.fname = fname;
		this.lname = lname;
		this.languages = languages;
		this.daysOfWeek = daysOfWeek;
		this.hairColor = hairColor;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String[] getLanguages() {
		return languages;
	}
	public void setLanguages(String[] languages) {
		this.languages = languages;
	}
	public String[] getDaysOfWeek() {
		return daysOfWeek;
	}
	public void setDaysOfWeek(String[] daysOfWeek) {
		this.daysOfWeek = daysOfWeek;
	}
	public String getHairColor() {
		return hairColor;
	}
	public void setHairColor(String hairColor) {
		this.hairColor = hairColor;
	}
	@Override
	public String toString() {
		return "UserInfo [fname=" + fname + ", lname=" + lname + ", languages=" + Arrays.toString(languages)
				+ ", daysOfWeek=" + Arrays.toString(daysOfWeek) + ", hairColor=" + hairColor + "]";
	}
	

}
