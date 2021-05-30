# 今日诗词安卓 SDK

README: [English](https://github.com/xenv/jinrishici-sdk-android/blob/master/README_EN.md "English") | [中文](https://github.com/xenv/jinrishici-sdk-android/blob/master/README.md "中文")

![](https://img.shields.io/github/last-commit/xenv/jinrishici-sdk-android.svg) ![](https://img.shields.io/github/release-date/xenv/jinrishici-sdk-android.svg) ![](https://img.shields.io/github/license/xenv/jinrishici-sdk-android.svg) ![](https://img.shields.io/github/stars/xenv/jinrishici-sdk-android.svg?label=Stars&style=social) ![](https://img.shields.io/github/forks/xenv/jinrishici-sdk-android.svg?label=Fork&style=social)

## 安装
![](https://img.shields.io/github/release/xenv/jinrishici-sdk-android.svg)

通过Gradle集成：

	implementation 'com.jinrishici:android-sdk:{release-version}'
通过Maven集成：

	<dependency>
		<groupId>com.jinrishici</groupId>
		<artifactId>android-sdk</artifactId>
		<version>{release-version}</version>
		<type>pom</type>
	</dependency>

## 如何使用

### 初始化

在调用获取今日诗词的接口之前任何地方调用初始化方法：
```java
//以下两种初始化方式任选一种即可
JinrishiciFactory.init(getContext());
JinrishiciClient.getInstance().init(getContext());
```
**这一步为了初始化 `SharedPreference` 的存储，让今日诗词的 `token` 能够存储到设备上，避免重复请求 `token` 给服务端带来不必要的开销。**

### 使用
在任何你想要的地方调用函数进行请求：
```java
//异步方法
JinrishiciClient client = JinrishiciClient.getInstance();
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
PoetySentence poetySentence = JinrishiciClient.getInstance().getOneSentence();
```

### 使用项目中的HTTP请求框架发起请求（1.5版本以后提供）

如果不喜欢使用 `HttpsURLConnection` 发起请求，或者希望使用项目中统一的HTTP请求框架，可以通过以下方法指定请求的具体实现。

默认使用 `HttpsURLConnection` 实现。

```java
client.setRequestClient(new RequestClient() {
            @Override
            public PoetyToken generateToken(String httpMethod, String httpUrl) {
              	//使用项目中使用的Http框架发起请求，例如OkHttp或者Retrofit
              	//向给定的httpUrl发送指定httpMethod的请求，然后将返回结果反序列化成PoetyToken返回即可
              	//该方法为同步调用，无需在此处以异步方式返回结果
                return null;
            }

            @Override
            public PoetySentence getPoetySentence(String httpMethod, String httpUrl, String httpHeaderName, String httpHeaderValue) {
              	//使用项目中使用的Http框架发起请求，例如OkHttp或者Retrofit
              	//向给定的httpUrl发送指定httpMethod的请求，添加指定的HttpHeaderName和HttpHeaderValue到请求Header中，然后将返回结果反序列化成PoetySentence返回即可
              	//该方法为同步调用，无需在此处以异步方式返回结果
                return null;
            }
        });
```



### 自定义控件

同样，sdk也提供自定义控件，你只需要将控件添加进布局中，无需处理相关逻辑，控件会自动请求数据并展示到控件上
**如果使用自定义控件的方式，那么不用手动调用初始化的方法，控件会自动调用**
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


## 混淆配置
**1.3版本之后已经在sdk中自动添加了混淆代码，不用再手动添加混淆配置**
**如果存在问题请手动添加以下配置**

```
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature
# For using GSON @Expose annotation
-keepattributes *Annotation*
# Gson specific classes
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
# Application classes that will be serialized/deserialized over Gson
-keep class com.jinrishici.sdk.android.model.** { *; }
```

## License

BSD 3-Clause "New" or "Revised" License

