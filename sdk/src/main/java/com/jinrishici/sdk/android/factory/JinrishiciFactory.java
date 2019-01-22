package com.jinrishici.sdk.android.factory;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jinrishici.sdk.android.utils.TokenUtil;

public class JinrishiciFactory {
	public static void init(@NonNull Context context) {
		TokenUtil.getInstance().initSharedPreference(context.getApplicationContext());
	}
}
