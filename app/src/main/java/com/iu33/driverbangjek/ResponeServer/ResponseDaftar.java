package com.iu33.driverbangjek.ResponeServer;

import com.google.gson.annotations.SerializedName;

public class ResponseDaftar{

	@SerializedName("result")
	private String result;

	@SerializedName("msg")
	private String msg;

	@SerializedName("iduser")
	private int iduser;

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

	public void setIduser(int iduser){
		this.iduser = iduser;
	}

	public int getIduser(){
		return iduser;
	}

	@Override
 	public String toString(){
		return 
			"ResponseDaftar{" + 
			"result = '" + result + '\'' + 
			",msg = '" + msg + '\'' + 
			",iduser = '" + iduser + '\'' + 
			"}";
		}
}