package com.mr.http.mime;

public class MR_MinimalField {
	private final String name;
	private final String value;

	MR_MinimalField(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return this.name;
	}

	public String getBody() {
		return this.value;
	}

	public String toString() {
		StringBuilder buffer = new StringBuilder();
		buffer.append(this.name);
		buffer.append(": ");
		buffer.append(this.value);
		return buffer.toString();
	}
}