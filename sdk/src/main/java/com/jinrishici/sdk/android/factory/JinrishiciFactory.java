package com.jinrishici.sdk.android.factory;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jinrishici.sdk.android.utils.TokenUtil;

public final class JinrishiciFactory {
	public static void init(@NonNull Context context) {
		TokenUtil.getInstance().initSharedPreference(context.getApplicationContext());
	}

	public static boolean isInit(){
		return TokenUtil.getInstance().isInit();
	}
}
