package com.jinrishici.sdk.android.interceptor;

import com.jinrishici.sdk.android.config.Constant;
import com.jinrishici.sdk.android.factory.ExceptionFactory;
import com.jinrishici.sdk.android.utils.TokenUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public final class JinrishiciInterceptor implements Interceptor {
	@Override
	public Response intercept(Chain chain) throws IOException {
		Request request = chain.request();
		Request.Builder builder = request.newBuilder();
		if (request.method().toLowerCase().equals("get") &&//get请求
				request.url().toString().contains(Constant.DOMAIN)) {//表示是对今日诗词的请求
			if (request.url().toString().contains("one.json")) {//请求今日诗词的接口
				String token = TokenUtil.getInstance().getToken();
				if (token == null)
					throw ExceptionFactory.throwByCode(ExceptionFactory.Code.ERROR_TOKEN_EMPTY);
				else
					builder.addHeader("X-User-Token", TokenUtil.getInstance().getToken());
			}
		}
		return chain.proceed(builder.build());
	}
}
