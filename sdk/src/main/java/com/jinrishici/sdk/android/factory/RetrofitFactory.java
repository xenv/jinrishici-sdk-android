package com.jinrishici.sdk.android.factory;

import com.jinrishici.sdk.android.config.Constant;
import com.jinrishici.sdk.android.interceptor.JinrishiciInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class RetrofitFactory {
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
			client = new OkHttpClient.Builder()
					.addInterceptor(new JinrishiciInterceptor())
					.build();
		return client;
	}

	public void setClient(OkHttpClient.Builder builder) {
		this.client = builder.addInterceptor(new JinrishiciInterceptor())
				.build();
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
