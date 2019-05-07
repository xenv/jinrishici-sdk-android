package com.jinrishici.sdk.android.factory;

import com.jinrishici.sdk.android.model.JinrishiciRuntimeException;

public final class ExceptionFactory {
	public static JinrishiciRuntimeException throwByCode(Code code) {
		return new JinrishiciRuntimeException(code.code, code.message);
	}

	public enum Code {
		INLINE_ERROR(-2, "内部错误"),
		UNDEFINED_CODE(-1, "错误码未定义"),
		DONE(0, "请求成功"),
		ERROR(1, "未知错误"),
		ERROR_INIT(2, "未初始化，请先调用init方法进行初始化"),
		ERROR_REQUEST_TOKEN(3, "请求token出错"),
		ERROR_REQUEST_TOKEN_EMPTY(4, "token返回数据为空"),
		ERROR_TOKEN_EMPTY(5, "token为空"),
		ERROR_REQUEST_JRSC(6, "请求今日诗词出错"),
		ERROR_REQUEST_JRSC_EMPTY(7, "今日诗词返回数据为空");
		public int code;
		public String message;

		Code(int code, String message) {
			this.code = code;
			this.message = message;
		}
	}
}
