package com.jinrishici.sdk.android;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.jinrishici.sdk.android.api.RequestClient;
import com.jinrishici.sdk.android.factory.ExceptionFactory;
import com.jinrishici.sdk.android.factory.JinrishiciFactory;
import com.jinrishici.sdk.android.listener.JinrishiciCallback;
import com.jinrishici.sdk.android.model.JinrishiciRuntimeException;
import com.jinrishici.sdk.android.model.PoetySentence;
import com.jinrishici.sdk.android.model.PoetyToken;
import com.jinrishici.sdk.android.utils.TokenUtil;

public final class JinrishiciClient {
    private static final Object lock = new Object();
    private RequestClient requestClient = new DefaultRequestClient();

    private JinrishiciClient() {
    }

    private static final class Singleton {
        private static final JinrishiciClient INSTANCE = new JinrishiciClient();
    }

    public static JinrishiciClient getInstance() {
        return Singleton.INSTANCE;
    }

    public JinrishiciClient init(@NonNull Context context) {
        if (!JinrishiciFactory.isInit())
            JinrishiciFactory.init(context);
        return this;
    }

    public JinrishiciClient setRequestClient(RequestClient requestClient) {
        this.requestClient = requestClient;
        return this;
    }

    @NonNull
    public PoetySentence getOneSentence() throws JinrishiciRuntimeException {
        getToken();
        return getSentence();
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
        private final JinrishiciCallback listener;

        JinrishiciHandler(JinrishiciCallback listener) {
            this.listener = listener;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    listener.done((PoetySentence) msg.obj);
                    break;
                case 1:
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
        handler = new JinrishiciHandler(listener);
        new Thread(() -> {
            try {
                PoetySentence sentence = getOneSentence();
                Message message = Message.obtain();
                message.obj = sentence;
                message.what = ExceptionFactory.Code.DONE.code;
                handler.sendMessage(message);
            } catch (Exception e) {
                Message message = Message.obtain();
                message.obj = e;
                message.what = ExceptionFactory.Code.ERROR.code;
                handler.sendMessage(message);
            }
        }).start();
    }

    private void generateToken() {
        PoetyToken poetyToken = requestClient.generateToken("GET", "https://v2.jinrishici.com/token");
        if (poetyToken == null || TextUtils.isEmpty(poetyToken.getToken()))
            throw ExceptionFactory.throwByCode(ExceptionFactory.Code.ERROR_REQUEST_TOKEN_EMPTY);
        TokenUtil.getInstance().setToken(poetyToken.getToken());
    }

    @NonNull
    private PoetySentence getSentence() {
        PoetySentence poetySentence = requestClient.getPoetySentence("GET", "https://v2.jinrishici.com/one.json?client=android-sdk/" + BuildConfig.LIB_VERSION_NAME, "X-User-Token", TokenUtil.getInstance().getToken());
        if (poetySentence == null)
            throw ExceptionFactory.throwByCode(ExceptionFactory.Code.ERROR_REQUEST_JRSC_EMPTY);
        return poetySentence;
    }
}
