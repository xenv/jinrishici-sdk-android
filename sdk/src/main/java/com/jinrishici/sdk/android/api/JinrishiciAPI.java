package com.jinrishici.sdk.android.api;

import com.jinrishici.sdk.android.model.PoetySentence;
import com.jinrishici.sdk.android.model.PoetyToken;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JinrishiciAPI {
	@GET("/token")
	Call<PoetyToken> getToken();

	@GET("/one.json")
	Call<PoetySentence> getSentence(@Query("client") String client);
}
