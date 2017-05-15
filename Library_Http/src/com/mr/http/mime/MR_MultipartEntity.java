package com.mr.http.mime;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Random;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.message.BasicHeader;

import com.mr.http.mime.content.MR_ContentBody;

public class MR_MultipartEntity implements HttpEntity {
	private static final char[] MULTIPART_CHARS = "-_1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
			.toCharArray();
	private final MR_HttpMultipart multipart;
	private final Header contentType;
	private long length;
	private volatile boolean dirty;

	public MR_MultipartEntity(MR_HttpMultipartMode mode, String boundary, Charset charset) {
		if (boundary == null) {
			boundary = generateBoundary();
		}
		if (mode == null) {
			mode = MR_HttpMultipartMode.STRICT;
		}
		this.multipart = new MR_HttpMultipart("form-data", charset, boundary, mode);
		this.contentType = new BasicHeader("Content-Type", generateContentType(boundary, charset));

		this.dirty = true;
	}

	public MR_MultipartEntity(MR_HttpMultipartMode mode) {
		this(mode, null, null);
	}

	public MR_MultipartEntity() {
		this(MR_HttpMultipartMode.STRICT, null, null);
	}

	protected String generateContentType(String boundary, Charset charset) {
		StringBuilder buffer = new StringBuilder();
		buffer.append("multipart/form-data; boundary=");
		buffer.append(boundary);
		if (charset != null) {
			buffer.append("; charset=");
			buffer.append(charset.name());
		}
		return buffer.toString();
	}

	protected String generateBoundary() {
		StringBuilder buffer = new StringBuilder();
		Random rand = new Random();
		int count = rand.nextInt(11) + 30;
		for (int i = 0; i < count; ++i) {
			buffer.append(MULTIPART_CHARS[rand.nextInt(MULTIPART_CHARS.length)]);
		}
		return buffer.toString();
	}

	public void addPart(MR_FormBodyPart bodyPart) {
		this.multipart.addBodyPart(bodyPart);
		this.dirty = true;
	}

	public void addPart(String name, MR_ContentBody contentBody) {
		addPart(new MR_FormBodyPart(name, contentBody));
	}

	public boolean isRepeatable() {
		for (MR_FormBodyPart part : this.multipart.getBodyParts()) {
			MR_ContentBody body = part.getBody();
			if (body.getContentLength() < 0L) {
				return false;
			}
		}
		return true;
	}

	public boolean isChunked() {
		return (!(isRepeatable()));
	}

	public boolean isStreaming() {
		return (!(isRepeatable()));
	}

	public long getContentLength() {
		if (this.dirty) {
			this.length = this.multipart.getTotalLength();
			this.dirty = false;
		}
		return this.length;
	}

	public Header getContentType() {
		return this.contentType;
	}

	public Header getContentEncoding() {
		return null;
	}

	public void consumeContent() throws IOException, UnsupportedOperationException {
		if (isStreaming())
			throw new UnsupportedOperationException("Streaming entity does not implement #consumeContent()");
	}

	public InputStream getContent() throws IOException, UnsupportedOperationException {
		throw new UnsupportedOperationException("Multipart form entity does not implement #getContent()");
	}

	public void writeTo(OutputStream outstream) throws IOException {
		this.multipart.writeTo(outstream);
	}
}