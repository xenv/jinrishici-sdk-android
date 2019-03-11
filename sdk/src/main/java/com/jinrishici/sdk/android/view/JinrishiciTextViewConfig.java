package com.jinrishici.sdk.android.view;

public final class JinrishiciTextViewConfig {
	private boolean isRefreshWhenClick = true;//点击时刷新
	private boolean isShowErrorOnTextView = true;//是否直接显示错误信息到TextView上
	private boolean isShowLoadingText = true;//是否在加载数据时显示加载文本
	private String loadingText = "loading...";//加载时显示的文本
	private String customErrorText = "Request Error";//加载失败时显示的文本

	public boolean isRefreshWhenClick() {
		return isRefreshWhenClick;
	}

	public boolean isShowErrorOnTextView() {
		return isShowErrorOnTextView;
	}

	public boolean isShowLoadingText() {
		return isShowLoadingText;
	}

	public String getLoadingText() {
		return loadingText;
	}

	public String getCustomErrorText() {
		return customErrorText;
	}

	public JinrishiciTextViewConfig setRefreshWhenClick(boolean refreshWhenClick) {
		isRefreshWhenClick = refreshWhenClick;
		return this;
	}

	public JinrishiciTextViewConfig setShowErrorOnTextView(boolean showErrorOnTextView) {
		isShowErrorOnTextView = showErrorOnTextView;
		return this;
	}

	public JinrishiciTextViewConfig setShowLoadingText(boolean showLoadingText) {
		isShowLoadingText = showLoadingText;
		return this;
	}

	public JinrishiciTextViewConfig setLoadingText(String loadingText) {
		this.loadingText = loadingText;
		return this;
	}

	public JinrishiciTextViewConfig setCustomErrorText(String customErrorText) {
		this.customErrorText = customErrorText;
		return this;
	}

	void copy(JinrishiciTextViewConfig config) {
		isRefreshWhenClick = config.isRefreshWhenClick;
		isShowErrorOnTextView = config.isShowErrorOnTextView;
		isShowLoadingText = config.isShowLoadingText;
		loadingText = config.loadingText;
		customErrorText = config.customErrorText;
	}
}
