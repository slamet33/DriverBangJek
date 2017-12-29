package com.iu33.driverbangjek.ResponeServer;

import com.google.gson.annotations.SerializedName;

public class ResponseTarif{

	@SerializedName("result")
	private String result;

	@SerializedName("msg")
	private String msg;

	@SerializedName("id_booking")
	private int idBooking;

	@SerializedName("tarif")
	private boolean tarif;

	@SerializedName("waktu")
	private String waktu;

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

	public void setIdBooking(int idBooking){
		this.idBooking = idBooking;
	}

	public int getIdBooking(){
		return idBooking;
	}

	public void setTarif(boolean tarif){
		this.tarif = tarif;
	}

	public boolean isTarif(){
		return tarif;
	}

	public void setWaktu(String waktu){
		this.waktu = waktu;
	}

	public String getWaktu(){
		return waktu;
	}

	@Override
 	public String toString(){
		return 
			"ResponseTarif{" + 
			"result = '" + result + '\'' + 
			",msg = '" + msg + '\'' + 
			",id_booking = '" + idBooking + '\'' + 
			",tarif = '" + tarif + '\'' + 
			",waktu = '" + waktu + '\'' + 
			"}";
		}
}