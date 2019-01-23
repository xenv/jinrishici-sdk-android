package com.jinrishici.sdk.android.listener;

import com.jinrishici.sdk.android.model.JinrishiciRuntimeException;
import com.jinrishici.sdk.android.model.PoetySentence;

public interface JinrishiciCallback {
	void done(PoetySentence poetySentence);

	void error(JinrishiciRuntimeException e);
}
