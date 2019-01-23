# 今日诗词安卓 SDK

README: [English](https://github.com/xenv/jinrishici-sdk-android/blob/master/README_EN.md "English") | [中文](https://github.com/xenv/jinrishici-sdk-android/blob/master/README.md "中文")

![](https://img.shields.io/github/last-commit/xenv/jinrishici-sdk-android.svg) ![](https://img.shields.io/github/release-date/xenv/jinrishici-sdk-android.svg)

## 安装
![](https://img.shields.io/github/release/xenv/jinrishici-sdk-android.svg)

通过Gradle集成：

	TODO
通过Maven集成：

	TODO

## 如何使用

### 初始化

在你的项目的 `Application` 的 `onCreate` 中加入以下代码：
```java
JinrishiciFactory.init(this);
```
**这一步为了初始化 `SharedPreference` 的存储，让今日诗词的 `token` 能够存储到设备上，避免重复请求 `token` 给服务端带来不必要的开销。**

### 使用
在任何你想要的地方调用函数进行请求：
```java
//异步方法
JinrishiciClient client = new JinrishiciClient();
client.getOneSentenceBackground(new JinrishiciCallback() {
	@Override
	public void done(PoetySentence poetySentence) {
		//TODO do something
		//在这里进行你的逻辑处理
	}

	@Override
	public void error(JinrishiciRuntimeException e) {
		Log.w(TAG, "error: code = " + e.getCode() + " message = " + e.getMessage());
		//TODO do something else
	}
});

//同步方法，当请求出现错误时会抛出 JinrishiciRuntimeException ，请自行捕获进行处理
PoetySentence poetySentence = new JinrishiciClient().getOneSentence();
```

### 自定义控件
同样，sdk也提供自定义控件，你只需要将控件添加进布局中，无需处理相关逻辑，控件会自动请求数据并展示到控件上
```xml
<com.jinrishici.sdk.android.view.JinrishiciTextView
	android:id="@+id/jinrisiciTextView"
	android:layout_width="wrap_content"
	android:layout_height="wrap_content"
	android:textColor="@color/colorAccent"
	android:textSize="18sp" />
<!--你可以像使用TextView那样对JinrishiciTextView进行设置-->
<!--如示例代码中的textColor和textSize属性-->
```
#### 自定义控件支持的自定义属性

|属性名|支持数据类型|备注|
| :------------ | :------------ | :------------ |
|jrsc_refresh_on_click|boolean|点击TextView时是否刷新|
|jrsc_show_error|boolean|当请求出现错误时，是否直接将错误信息显示到TextView上|
|jrsc_show_loading_text|boolean|是否在加载数据时显示加载文本|
|jrsc_text_loading|string|加载数据时显示的文本|
|jrsc_text_error|string|加载失败时显示的文本，当jrsc_show_error为false时有效|

#### 自定义显示数据格式
```java
JinrishiciTextView jinrishiciTextView = findViewById(R.id.jinrisiciTextView);
jinrishiciTextView.setDataFormat(new JinrishiciTextView.DataFormatListener() {
	@Override
	public String set(PoetySentence poetySentence) {
		//TODO return String by yourself
		return "ip:" + poetySentence.getIpAddress() + "content:" + poetySentence.getData().getContent();
	}
});
```
**设置新的格式后会自动刷新当前显示的格式，数据不会改变**

## 参考代码
[Sample](https://github.com/xenv/jinrishici-sdk-android/blob/master/app/src/main/java/com/jinrishici/sdk/android/demo/MainActivity.java "Sample")

## License
开源协议
