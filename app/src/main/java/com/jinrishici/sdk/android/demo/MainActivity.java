package com.jinrishici.sdk.android.demo;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.jinrishici.sdk.android.JinrishiciClient;
import com.jinrishici.sdk.android.listener.JinrishiciCallback;
import com.jinrishici.sdk.android.model.JinrishiciRuntimeException;
import com.jinrishici.sdk.android.model.PoetySentence;
import com.jinrishici.sdk.android.view.JinrishiciTextView;

public class MainActivity extends AppCompatActivity {
	private static final String TAG = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		JinrishiciTextView jinrishiciTextView = findViewById(R.id.jinrisici);
		jinrishiciTextView.setDataFormat(new JinrishiciTextView.DataFormatListener() {
			@Override
			public String set(PoetySentence poetySentence) {
				return "ip:" + poetySentence.getIpAddress() + "content:" + poetySentence.getData()
						.getContent();
			}
		});
		new JinrishiciClient()
				.getOneSentenceBackground(new JinrishiciCallback() {
					@Override
					public void done(PoetySentence poetySentence) {
						Log.i(TAG, "done: " + poetySentence.getData().getContent());
						((TextView) (findViewById(R.id.textView))).setText(poetySentence.getData()
								.getContent());
					}

					@Override
					public void error(JinrishiciRuntimeException e) {
						Log.w(TAG, "error: code = " + e.getCode() + " message = " + e.getMessage());
						((TextView) (findViewById(R.id.textView))).setText(e.getMessage());
					}
				});

//		PoetySentence poetySentence=new JinrishiciClient().getOneSentence();
	}
}
