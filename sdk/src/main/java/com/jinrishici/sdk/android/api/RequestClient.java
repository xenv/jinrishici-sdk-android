package com.jinrishici.sdk.android.api;

import com.jinrishici.sdk.android.model.PoetySentence;
import com.jinrishici.sdk.android.model.PoetyToken;

public interface RequestClient {
    PoetyToken generateToken(String httpMethod,
                             String httpUrl);

    PoetySentence getPoetySentence(String httpMethod,
                                   String httpUrl,
                                   String httpHeaderName,
                                   String httpHeaderValue);
}
