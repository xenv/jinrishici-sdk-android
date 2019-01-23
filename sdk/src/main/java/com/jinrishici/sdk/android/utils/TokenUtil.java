package com.jinrishici.sdk.android.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jinrishici.sdk.android.config.Constant;
import com.jinrishici.sdk.android.factory.ExceptionFactory;

public final class TokenUtil {
	private SharedPreferences sharedPreferences;

	private TokenUtil() {
	}

	private static final class Singleton {
		private static final TokenUtil INSTANCE = new TokenUtil();
	}

	public void initSharedPreference(Context context) {
		sharedPreferences = context.getSharedPreferences(Constant.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
	}

	public boolean isInit() {
		return sharedPreferences != null;
	}

	public static TokenUtil getInstance() {
		return Singleton.INSTANCE;
	}

	public void setToken(@NonNull String token) {
		if (sharedPreferences == null)
			throw ExceptionFactory.throwByCode(ExceptionFactory.Code.ERROR_INIT);
		sharedPreferences.edit()
				.putString(Constant.KEY_TOKEN, token)
				.apply();
	}

	@Nullable
	public String getToken() {
		if (sharedPreferences == null)
			throw ExceptionFactory.throwByCode(ExceptionFactory.Code.ERROR_INIT);
		return sharedPreferences.getString(Constant.KEY_TOKEN, null);
	}
}
