package com.hms.utils;

public class JsonView {
	public boolean isSuccess;
	public String errorMessage = "";
	private Object data;

	public JsonView() {
	}

	public JsonView(boolean isSuccess, Object data, String errorMessage) {
		this.isSuccess = isSuccess;
		this.data = data;
		this.errorMessage = errorMessage;
	}

	public JsonView(boolean isSuccess, String errorMessage) {
		this.isSuccess = isSuccess;
		this.errorMessage = errorMessage;
		data = "";
	}

	public JsonView(boolean isSuccess, Object data) {
		this.isSuccess = isSuccess;
		this.data = data;
	}

	public JsonView(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public JsonView(String data) {
		this.isSuccess = true;
		this.data = data;
	}

	public boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
