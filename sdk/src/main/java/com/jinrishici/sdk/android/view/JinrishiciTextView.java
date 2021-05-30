package com.jinrishici.sdk.android.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.jinrishici.sdk.android.JinrishiciClient;
import com.jinrishici.sdk.android.R;
import com.jinrishici.sdk.android.listener.JinrishiciCallback;
import com.jinrishici.sdk.android.model.JinrishiciRuntimeException;
import com.jinrishici.sdk.android.model.PoetySentence;

public class JinrishiciTextView extends AppCompatTextView {
	private static final String TAG = "JinrishiciTextView";
    private final JinrishiciClient client = JinrishiciClient.getInstance();
    private final JinrishiciTextViewConfig config = new JinrishiciTextViewConfig();
    private DataFormatListener dataFormatListener = null;//格式化方法
    private PoetySentence nowPoetySentence = null;//现在正在展示的诗词数据的对象

    public JinrishiciTextView(Context context) {
        this(context, null);
    }

    public JinrishiciTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JinrishiciTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        client.init(context);
        initAttrs(context, attrs, defStyleAttr);
        request();
    }

    private void initAttrs(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.JinrishiciTextView, defStyleAttr, 0);
        if (typedArray.hasValue(R.styleable.JinrishiciTextView_jrsc_refresh_on_click))
            config.setRefreshWhenClick(typedArray.getBoolean(R.styleable.JinrishiciTextView_jrsc_refresh_on_click, config
                    .isRefreshWhenClick()));
        if (typedArray.hasValue(R.styleable.JinrishiciTextView_jrsc_show_error))
            config.setShowErrorOnTextView(typedArray.getBoolean(R.styleable.JinrishiciTextView_jrsc_show_error, config
                    .isShowErrorOnTextView()));
        if (typedArray.hasValue(R.styleable.JinrishiciTextView_jrsc_show_loading_text))
            config.setShowLoadingText(typedArray.getBoolean(R.styleable.JinrishiciTextView_jrsc_show_loading_text, config
                    .isShowLoadingText()));
        if (typedArray.hasValue(R.styleable.JinrishiciTextView_jrsc_text_loading))
            config.setLoadingText(typedArray.getString(R.styleable.JinrishiciTextView_jrsc_text_loading));
        if (typedArray.hasValue(R.styleable.JinrishiciTextView_jrsc_text_error))
            config.setCustomErrorText(typedArray.getString(R.styleable.JinrishiciTextView_jrsc_text_error));
        typedArray.recycle();
        initConfig();
    }

    private void initConfig() {
        if (config.isRefreshWhenClick())
            setOnClickListener(v -> request());
        else
            setOnClickListener(null);
    }

    public void setConfig(JinrishiciTextViewConfig config) {
        this.config.copy(config);
        initConfig();
    }

    /**
     * 发起请求
     * 异步方法，请求成功后将诗词数据显示到TextView上
     */
    private void request() {
        if (config.isShowLoadingText())
            setText(config.getLoadingText());
        client.getOneSentenceBackground(new JinrishiciCallback() {
            @Override
            public void done(PoetySentence poetySentence) {
                setText(formatPoetySentence(poetySentence));
            }

            @Override
            public void error(JinrishiciRuntimeException e) {
                setText(config.isShowErrorOnTextView() ? e.getMessage() : config.getCustomErrorText());
            }
        });
    }

    public void setDataFormat(DataFormatListener listener) {
        this.dataFormatListener = listener;//存储格式化的操作，便于在设置的时候回调
        formatPoetySentence(nowPoetySentence);//刷新当前已有的数据
    }

    public interface DataFormatListener {
        String set(PoetySentence poetySentence);
    }

    private String formatPoetySentence(PoetySentence poetySentence) {
        if (poetySentence == null)
            //如果存储数据为空，第一次请求还未完成，这个时候跳过这次刷新数据的请求，
            // TextView显示的文本交由开发者定义
            return config.getLoadingText();
        nowPoetySentence = poetySentence;//存储
        if (dataFormatListener == null)//如果没有设置格式化操作，则生成默认的操作————只显示诗词内容
            dataFormatListener = poetySentence1 -> poetySentence1.getData().getContent();
        return dataFormatListener.set(poetySentence);
    }
}
