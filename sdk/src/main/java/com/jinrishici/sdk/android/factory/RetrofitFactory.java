package com.jinrishici.sdk.android.factory;

import com.jinrishici.sdk.android.config.Constant;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory {
	private RetrofitFactory() {
	}

	private static final class Singleton {
		private static final RetrofitFactory INSTANCE = new RetrofitFactory();
	}

	public static RetrofitFactory getInstance() {
		return Singleton.INSTANCE;
	}

	private OkHttpClient client = null;

	private OkHttpClient getClient() {
		if (client == null)
			client = new OkHttpClient.Builder().build();
		return client;
	}

	public void setClient(OkHttpClient client) {
		this.client = client;
	}

	private Retrofit retrofit = null;

	public Retrofit getRetrofit() {
		if (retrofit == null)
			retrofit = new Retrofit.Builder()
					.baseUrl(Constant.DOMAIN)
					.client(getClient())
					.addConverterFactory(GsonConverterFactory.create())
					.build();
		return retrofit;
	}
}
