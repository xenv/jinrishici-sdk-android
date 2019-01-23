package com.jinrishici.sdk.android.model;

public final class JinrishiciRuntimeException extends RuntimeException {
	private int code;
	private String message;

	public JinrishiciRuntimeException(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public JinrishiciRuntimeException(Throwable e) {
		this.code = -1;
		this.message = e.getMessage();
	}

	public int getCode() {
		return code;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return "JinrishiciRuntimeException{" +
				"code=" + code +
				", message='" + message + '\'' +
				'}';
	}
}
