package com.mr.http.mime;

import com.mr.http.mime.content.MR_ContentBody;

public class MR_FormBodyPart {
	private final String name;
	private final MR_Header header;
	private final MR_ContentBody body;

	public MR_FormBodyPart(String name, MR_ContentBody body) {
		if (name == null) {
			throw new IllegalArgumentException("Name may not be null");
		}
		if (body == null) {
			throw new IllegalArgumentException("Body may not be null");
		}
		this.name = name;
		this.body = body;
		this.header = new MR_Header();

		generateContentDisp(body);
		generateContentType(body);
		generateTransferEncoding(body);
	}

	public String getName() {
		return this.name;
	}

	public MR_ContentBody getBody() {
		return this.body;
	}

	public MR_Header getHeader() {
		return this.header;
	}

	public void addField(String name, String value) {
		if (name == null) {
			throw new IllegalArgumentException("Field name may not be null");
		}
		this.header.addField(new MR_MinimalField(name, value));
	}

	protected void generateContentDisp(MR_ContentBody body) {
		StringBuilder buffer = new StringBuilder();
		buffer.append("form-data; name=\"");
		buffer.append(getName());
		buffer.append("\"");
		if (body.getFilename() != null) {
			buffer.append("; filename=\"");
			buffer.append(body.getFilename());
			buffer.append("\"");
		}
		addField("Content-Disposition", buffer.toString());
	}

	protected void generateContentType(MR_ContentBody body) {
		StringBuilder buffer = new StringBuilder();
		buffer.append(body.getMimeType());
		if (body.getCharset() != null) {
			buffer.append("; charset=");
			buffer.append(body.getCharset());
		}
		addField("Content-Type", buffer.toString());
	}

	protected void generateTransferEncoding(MR_ContentBody body) {
		addField("Content-Transfer-Encoding", body.getTransferEncoding());
	}
}