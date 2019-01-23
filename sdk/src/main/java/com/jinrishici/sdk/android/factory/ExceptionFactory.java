package com.jinrishici.sdk.android.factory;

import com.jinrishici.sdk.android.model.JinrishiciRuntimeException;
import com.jinrishici.sdk.android.utils.annotation.DeclareMessage;

import java.lang.reflect.Field;

public final class ExceptionFactory {
	private static final Code instance = new Code();
	private static final Class clazz = Code.class;

	public static JinrishiciRuntimeException throwByCode(int code) {
		Field[] fields = clazz.getDeclaredFields();
		Field codeField = null;
		try {
			for (Field field : fields) {
				if (!field.getType().toString().toLowerCase().equals("int"))
					continue;
				if ((int) (field.get(instance)) == code) {
					codeField = field;
					break;
				}
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		if (codeField == null)
			return throwByCode(Code.UNDEFINED_CODE);
		if (!codeField.isAnnotationPresent(DeclareMessage.class))
			return throwByCode(Code.INLINE_ERROR);
		return new JinrishiciRuntimeException(code, codeField.getAnnotation(DeclareMessage.class)
				.message());
	}

	public static class Code {
		@DeclareMessage(message = "内部错误")
		private static final int INLINE_ERROR = -2;
		@DeclareMessage(message = "错误码未定义")
		private static final int UNDEFINED_CODE = -1;
		@DeclareMessage(message = "请求成功")
		public static final int DONE = 0;
		@DeclareMessage(message = "未知错误")
		public static final int ERROR = 1;
		@DeclareMessage(message = "未初始化，请先调用init方法进行初始化")
		public static final int ERROR_INIT = 2;
		@DeclareMessage(message = "请求token出错")
		public static final int ERROR_REQUEST_TOKEN = 3;
		@DeclareMessage(message = "token返回数据为空")
		public static final int ERROR_REQUEST_TOKEN_EMPTY = 4;
		@DeclareMessage(message = "token为空")
		public static final int ERROR_TOKEN_EMPTY = 5;
		@DeclareMessage(message = "请求今日诗词出错")
		public static final int ERROR_REQUEST_JRSC = 6;
		@DeclareMessage(message = "今日诗词返回数据为空")
		public static final int ERROR_REQUEST_JRSC_EMPTY = 7;
	}
}
