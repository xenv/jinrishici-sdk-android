package com.jinrishici.sdk.android;

import android.util.Log;

import com.google.gson.Gson;
import com.jinrishici.sdk.android.api.RequestClient;
import com.jinrishici.sdk.android.factory.ExceptionFactory;
import com.jinrishici.sdk.android.model.JinrishiciRuntimeException;
import com.jinrishici.sdk.android.model.PoetySentence;
import com.jinrishici.sdk.android.model.PoetyToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class DefaultRequestClient implements RequestClient {
    private static final String TAG = "DefaultRequestClient";
    private static final Gson gson = new Gson();

    @Override
    public PoetyToken generateToken(String httpMethod, String httpUrl) {
        HttpsURLConnection connection = null;
        InputStream inputStream = null;
        try {
            URL url = new URL(httpUrl);
            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod(httpMethod.toUpperCase());
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(10000);
            connection.connect();
            inputStream = connection.getInputStream();
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null)
                    response.append(line);
                return gson.fromJson(response.toString(), PoetyToken.class);
            } else {
                throw ExceptionFactory.throwByCode(ExceptionFactory.Code.ERROR_REQUEST_TOKEN);
            }
        } catch (IOException e) {
            throw new JinrishiciRuntimeException(e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    Log.w(TAG, "close input stream failed", e);
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    @Override
    public PoetySentence getPoetySentence(String httpMethod, String httpUrl, String httpHeaderName, String httpHeaderValue) {
        HttpsURLConnection connection = null;
        InputStream inputStream = null;
        try {
            URL url = new URL(httpUrl);
            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod(httpMethod);
            connection.setRequestProperty(httpHeaderName, httpHeaderValue);
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(10000);
            connection.connect();
            inputStream = connection.getInputStream();
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null)
                    response.append(line);
                return gson.fromJson(response.toString(), PoetySentence.class);
            } else {
                throw ExceptionFactory.throwByCode(ExceptionFactory.Code.ERROR_REQUEST_JRSC);
            }
        } catch (IOException e) {
            Log.w(TAG, "getSentence: ", e);
            throw new JinrishiciRuntimeException(e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    Log.w(TAG, "close input stream failed", e);
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
