package com.example.phonesafe1.entity;

public class BlackNumInfo {
	private String num;
	private int mode;
	public BlackNumInfo(){
		
	}
	public BlackNumInfo(String num, int mode) {
		super();
		this.num = num;
		this.mode = mode;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public int getMode() {
		return mode;
	}
	public void setMode(int mode) {
		this.mode = mode;
	}
	@Override
	public String toString() {
		return "BlackNumInfo [num=" + num + ", mode=" + mode + "]";
	}
	

}
