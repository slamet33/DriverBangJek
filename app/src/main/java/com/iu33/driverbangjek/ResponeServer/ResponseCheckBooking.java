package com.iu33.driverbangjek.ResponeServer;

import com.google.gson.annotations.SerializedName;

public class ResponseCheckBooking{

	@SerializedName("result")
	private String result;

	@SerializedName("msg")
	private String msg;

	@SerializedName("data")
	private String data;

	public void setResult(String result){
		this.result = result;
	}

	public String getResult(){
		return result;
	}

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return msg;
	}

	public void setData(String data){
		this.data = data;
	}

	public String getData(){
		return data;
	}

	@Override
 	public String toString(){
		return 
			"ResponseCheckBooking{" + 
			"result = '" + result + '\'' + 
			",msg = '" + msg + '\'' + 
			",data = '" + data + '\'' + 
			"}";
		}
}