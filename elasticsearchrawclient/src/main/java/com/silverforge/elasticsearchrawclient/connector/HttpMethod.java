package com.silverforge.elasticsearchrawclient.connector;

public enum HttpMethod {

	POST("POST"),
	GET("GET"),
	PUT("PUT"),
	DELETE("DELETE");

	private String methodName;

	HttpMethod(String methodName) {

		this.methodName = methodName;
	}

	@Override
	public String toString() {
		return methodName;
	}
}