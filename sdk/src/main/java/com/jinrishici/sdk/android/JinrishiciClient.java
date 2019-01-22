package com.jinrishici.sdk.android;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jinrishici.sdk.android.api.JinrishiciAPI;
import com.jinrishici.sdk.android.factory.ExceptionFactory;
import com.jinrishici.sdk.android.factory.RetrofitFactory;
import com.jinrishici.sdk.android.model.JinrishiciRuntimeException;
import com.jinrishici.sdk.android.model.PoetySentence;
import com.jinrishici.sdk.android.model.PoetyToken;
import com.jinrishici.sdk.android.utils.TokenUtil;

import java.io.IOException;

import okhttp3.OkHttpClient;
import retrofit2.Response;

public class JinrishiciClient {
	@Nullable
	public PoetySentence getOneSentence(OkHttpClient client) {
		RetrofitFactory.getInstance().setClient(client);
		if (TokenUtil.getInstance().getToken() == null)
			generateToken();
		if (TokenUtil.getInstance().getToken() == null)
			throw ExceptionFactory.throwByCode(ExceptionFactory.Code.ERROR_TOKEN_EMPTY);
		return getSentence();
	}

	private void generateToken() {
		try {
			Response<PoetyToken> response = RetrofitFactory.getInstance().getRetrofit()
					.create(JinrishiciAPI.class)
					.getToken()
					.execute();
			if (response.isSuccessful()) {
				PoetyToken body = response.body();
				if (body != null && !body.getToken().equals(""))
					TokenUtil.getInstance().setToken(body.getToken());
				else
					throw ExceptionFactory.throwByCode(ExceptionFactory.Code.ERROR_REQUEST_TOKEN_EMPTY);
			} else
				throw ExceptionFactory.throwByCode(ExceptionFactory.Code.ERROR_REQUEST_TOKEN);
		} catch (IOException e) {
			throw new JinrishiciRuntimeException(e);
		}
	}

	@NonNull
	private PoetySentence getSentence() {
		try {
			Response<PoetySentence> response = RetrofitFactory.getInstance().getRetrofit()
					.create(JinrishiciAPI.class)
					.getSentence()
					.execute();
			if (response.isSuccessful()) {
				PoetySentence body = response.body();
				if (body != null)
					return body;
				else
					throw ExceptionFactory.throwByCode(ExceptionFactory.Code.ERROR_REQUEST_JRSC_EMPTY);
			} else
				throw ExceptionFactory.throwByCode(ExceptionFactory.Code.ERROR_REQUEST_JRSC);
		} catch (IOException e) {
			throw new JinrishiciRuntimeException(e);
		}
	}
}
