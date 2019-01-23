package com.jinrishici.sdk.android.model;

import com.google.gson.annotations.SerializedName;

public class PoetyToken {
	@SerializedName("data")
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
