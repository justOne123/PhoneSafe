package com.example.phonesafe1.entity;

public class ContractsInfo {
	String name;
	String num;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	@Override
	public String toString() {
		return "ContractsInfo [name=" + name + ", num=" + num + "]";
	}
	

}
