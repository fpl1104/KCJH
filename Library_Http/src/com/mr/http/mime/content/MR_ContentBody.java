package com.mr.http.mime.content;

import java.io.IOException;
import java.io.OutputStream;

public abstract interface MR_ContentBody extends MR_ContentDescriptor {
	public abstract String getFilename();

	public abstract void writeTo(OutputStream paramOutputStream) throws IOException;
}