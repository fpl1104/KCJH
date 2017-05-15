package com.mr.http.mime;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.util.ByteArrayBuffer;

import com.mr.http.mime.content.MR_ContentBody;

public class MR_HttpMultipart {
	private static final ByteArrayBuffer FIELD_SEP = encode(MR_MIME.DEFAULT_CHARSET, ": ");
	private static final ByteArrayBuffer CR_LF = encode(MR_MIME.DEFAULT_CHARSET, "\r\n");
	private static final ByteArrayBuffer TWO_DASHES = encode(MR_MIME.DEFAULT_CHARSET, "--");
	private final String subType;
	private final Charset charset;
	private final String boundary;
	private final List<MR_FormBodyPart> parts;
	private final MR_HttpMultipartMode mode;

	private static ByteArrayBuffer encode(Charset charset, String string) {
		ByteBuffer encoded = charset.encode(CharBuffer.wrap(string));
		ByteArrayBuffer bab = new ByteArrayBuffer(encoded.remaining());
		bab.append(encoded.array(), encoded.position(), encoded.remaining());
		return bab;
	}

	private static void writeBytes(ByteArrayBuffer b, OutputStream out) throws IOException {
		out.write(b.buffer(), 0, b.length());
	}

	private static void writeBytes(String s, Charset charset, OutputStream out) throws IOException {
		ByteArrayBuffer b = encode(charset, s);
		writeBytes(b, out);
	}

	private static void writeBytes(String s, OutputStream out) throws IOException {
		ByteArrayBuffer b = encode(MR_MIME.DEFAULT_CHARSET, s);
		writeBytes(b, out);
	}

	private static void writeField(MR_MinimalField field, OutputStream out) throws IOException {
		writeBytes(field.getName(), out);
		writeBytes(FIELD_SEP, out);
		writeBytes(field.getBody(), out);
		writeBytes(CR_LF, out);
	}

	private static void writeField(MR_MinimalField field, Charset charset, OutputStream out) throws IOException {
		writeBytes(field.getName(), charset, out);
		writeBytes(FIELD_SEP, out);
		writeBytes(field.getBody(), charset, out);
		writeBytes(CR_LF, out);
	}

	public MR_HttpMultipart(String subType, Charset charset, String boundary, MR_HttpMultipartMode mode) {
		if (subType == null) {
			throw new IllegalArgumentException("Multipart subtype may not be null");
		}
		if (boundary == null) {
			throw new IllegalArgumentException("Multipart boundary may not be null");
		}
		this.subType = subType;
		this.charset = ((charset != null) ? charset : MR_MIME.DEFAULT_CHARSET);
		this.boundary = boundary;
		this.parts = new ArrayList<MR_FormBodyPart>();
		this.mode = mode;
	}

	public MR_HttpMultipart(String subType, Charset charset, String boundary) {
		this(subType, charset, boundary, MR_HttpMultipartMode.STRICT);
	}

	public MR_HttpMultipart(String subType, String boundary) {
		this(subType, null, boundary);
	}

	public String getSubType() {
		return this.subType;
	}

	public Charset getCharset() {
		return this.charset;
	}

	public MR_HttpMultipartMode getMode() {
		return this.mode;
	}

	public List<MR_FormBodyPart> getBodyParts() {
		return this.parts;
	}

	public void addBodyPart(MR_FormBodyPart part) {
		if (part == null) {
			return;
		}
		this.parts.add(part);
	}

	public String getBoundary() {
		return this.boundary;
	}

	private void doWriteTo(MR_HttpMultipartMode mode, OutputStream out, boolean writeContent) throws IOException {
		ByteArrayBuffer boundary = encode(this.charset, getBoundary());
		for (MR_FormBodyPart part : this.parts) {
			writeBytes(TWO_DASHES, out);
			writeBytes(boundary, out);
			writeBytes(CR_LF, out);

			MR_Header header = part.getHeader();

			switch (mode) {
			case STRICT:
				for (MR_MinimalField field : header) {
					writeField(field, out);
				}
				break;
			case BROWSER_COMPATIBLE:
				MR_MinimalField cd = part.getHeader().getField("Content-Disposition");
				writeField(cd, this.charset, out);
				String filename = part.getBody().getFilename();
				if (filename != null) {
					MR_MinimalField ct = part.getHeader().getField("Content-Type");
					writeField(ct, this.charset, out);
				}
			}

			writeBytes(CR_LF, out);

			if (writeContent) {
				part.getBody().writeTo(out);
			}
			writeBytes(CR_LF, out);
		}
		writeBytes(TWO_DASHES, out);
		writeBytes(boundary, out);
		writeBytes(TWO_DASHES, out);
		writeBytes(CR_LF, out);
	}

	public void writeTo(OutputStream out) throws IOException {
		doWriteTo(this.mode, out, true);
	}

	public long getTotalLength() {
		long contentLen = 0L;
		for (MR_FormBodyPart part : this.parts) {
			MR_ContentBody body = part.getBody();
			long len = body.getContentLength();
			if (len >= 0L)
				contentLen += len;
			else {
				return -1L;
			}
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			doWriteTo(this.mode, out, false);
			byte[] extra = out.toByteArray();
			return (contentLen + extra.length);
		} catch (IOException ex) {
		}
		return -1L;
	}
}