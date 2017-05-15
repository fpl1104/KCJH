package com.mr.http.mime.content;

import java.io.IOException;
import java.io.OutputStream;

public class MR_ByteArrayBody extends MR_AbstractContentBody {
	private final byte[] data;
	private final String filename;

	public MR_ByteArrayBody(byte[] data, String mimeType, String filename) {
		super(mimeType);
		if (data == null) {
			throw new IllegalArgumentException("byte[] may not be null");
		}
		this.data = data;
		this.filename = filename;
	}

	public MR_ByteArrayBody(byte[] data, String filename) {
		this(data, "application/octet-stream", filename);
	}

	public String getFilename() {
		return this.filename;
	}

	public void writeTo(OutputStream out) throws IOException {
		out.write(this.data);
	}

	public String getCharset() {
		return null;
	}

	public String getTransferEncoding() {
		return "binary";
	}

	public long getContentLength() {
		return this.data.length;
	}
}