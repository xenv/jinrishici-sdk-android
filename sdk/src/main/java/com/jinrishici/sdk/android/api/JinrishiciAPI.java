package com.jinrishici.sdk.android.api;

import com.jinrishici.sdk.android.model.PoetySentence;
import com.jinrishici.sdk.android.model.PoetyToken;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JinrishiciAPI {
	@GET("/token")
	public Call<PoetyToken> getToken();

	@GET("/one.json")
	public Call<PoetySentence> getSentence();
}
