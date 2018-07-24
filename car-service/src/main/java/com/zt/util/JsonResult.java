package com.zt.util;

public class JsonResult {

	public static final String JSON_STATE_SUCCESS = "success";
	public static final String JSON_STATE_ERROR = "error";
	
	private String state;
	private Object data;
	private String message;
	
	public JsonResult() {
		
	}
	
	public static JsonResult success() {
		JsonResult jsonResult = new JsonResult();
		jsonResult.setState(jsonResult.JSON_STATE_SUCCESS);
		return jsonResult;
	}
	
	public static JsonResult success(Object data) {
		JsonResult jsonResult = new JsonResult();
		jsonResult.setState(jsonResult.JSON_STATE_SUCCESS);
		jsonResult.setData(data);
		return jsonResult;
	}
	
	public static JsonResult error(String message) {
		JsonResult jsonResult = new JsonResult();
		jsonResult.setState(jsonResult.JSON_STATE_ERROR);
		jsonResult.setMessage(message);
		return jsonResult;
	}
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
