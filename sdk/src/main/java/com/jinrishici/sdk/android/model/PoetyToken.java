package com.jinrishici.sdk.android.model;

import com.google.gson.annotations.SerializedName;

public class PoetyToken {
	/**
	 * status : success
	 * data : RgU1rBKtLym/MhhYIXs42WNoqLyZeXY3EkAcDNrcfKkzj8ILIsAP1Hx0NGhdOO1I
	 */

	private String status;
	@SerializedName("data")
	private String token;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
