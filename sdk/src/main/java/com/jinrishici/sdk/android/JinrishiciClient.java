package com.jinrishici.sdk.android;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;

import com.jinrishici.sdk.android.api.JinrishiciAPI;
import com.jinrishici.sdk.android.config.Constant;
import com.jinrishici.sdk.android.factory.ExceptionFactory;
import com.jinrishici.sdk.android.factory.RetrofitFactory;
import com.jinrishici.sdk.android.listener.JinrishiciCallback;
import com.jinrishici.sdk.android.model.JinrishiciRuntimeException;
import com.jinrishici.sdk.android.model.PoetySentence;
import com.jinrishici.sdk.android.model.PoetyToken;
import com.jinrishici.sdk.android.utils.TokenUtil;

import java.io.IOException;

import okhttp3.OkHttpClient;
import retrofit2.Response;

public final class JinrishiciClient {
	private static final String TAG = "jinrishici";
	private static final Object lock = new Object();

	@NonNull
	public PoetySentence getOneSentence() throws JinrishiciRuntimeException {
		return getOneSentence(null);
	}

	@NonNull
	public PoetySentence getOneSentence(OkHttpClient.Builder builder) throws JinrishiciRuntimeException {
		try {
			if (builder != null)
				RetrofitFactory.getInstance().setClient(builder);
			getToken();
			return getSentence();
		} catch (Exception e) {
			if (e instanceof JinrishiciRuntimeException)
				throw e;
			else {
				Log.w(TAG, "getOneSentence: ", e);
				throw ExceptionFactory.throwByCode(ExceptionFactory.Code.ERROR);
			}
		}
	}

	private void getToken() {
		if (TokenUtil.getInstance().getToken() != null)
			return;
		synchronized (lock) {
			String savedToken = TokenUtil.getInstance().getToken();
			if (savedToken != null)
				return;
			generateToken();
			String token = TokenUtil.getInstance().getToken();
			if (token == null)
				throw ExceptionFactory.throwByCode(ExceptionFactory.Code.ERROR_REQUEST_TOKEN_EMPTY);
		}
	}

	private static class JinrishiciHandler extends Handler {
		private JinrishiciCallback listener;

		JinrishiciHandler(JinrishiciCallback listener) {
			this.listener = listener;
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
				case ExceptionFactory.Code.DONE:
					listener.done((PoetySentence) msg.obj);
					break;
				case ExceptionFactory.Code.ERROR:
					if (msg.obj instanceof JinrishiciRuntimeException)
						listener.error((JinrishiciRuntimeException) msg.obj);
					else
						listener.error(new JinrishiciRuntimeException((Throwable) msg.obj));
					break;
			}
		}
	}

	private JinrishiciHandler handler;

	public void getOneSentenceBackground(JinrishiciCallback listener) {
		getOneSentenceBackground(null, listener);
	}

	public void getOneSentenceBackground(final OkHttpClient.Builder builder, JinrishiciCallback listener) {
		try {
			handler = new JinrishiciHandler(listener);
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						PoetySentence sentence = getOneSentence(builder);
						Message message = Message.obtain();
						message.obj = sentence;
						message.what = ExceptionFactory.Code.DONE;
						handler.sendMessage(message);
					} catch (Exception e) {
						Message message = Message.obtain();
						message.obj = e;
						message.what = ExceptionFactory.Code.ERROR;
						handler.sendMessage(message);
					}
				}
			}).start();
		} catch (Exception e) {
			Log.w(TAG, "getOneSentence: ", e);
			listener.error(ExceptionFactory.throwByCode(ExceptionFactory.Code.ERROR));
		}
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
					.getSentence(Constant.CLIENT)
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
