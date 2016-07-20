package com.example.phonesafe1.entity;

public class SmsBody {
	private String address;
	private String date;
	private String type;
	private String body;
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	@Override
	public String toString() {
		return "SmsBody [address=" + address + ", data=" + date + ", type="
				+ type + ", body=" + body + "]";
	}
	

}
