package com.vgearen.webdavcaiyundrive.store;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

import java.io.IOException;
import java.io.InputStream;

public class InputStreamRequestBody extends RequestBody {
    private final InputStream inputStream;
    private final MediaType mediaType;
    private final long contentLength;
    private final ProgressListener listener;

    public InputStreamRequestBody(InputStream inputStream, MediaType mediaType, long contentLength, ProgressListener listener) {
        this.inputStream = inputStream;
        this.mediaType = mediaType;
        this.contentLength = contentLength;
        this.listener = listener;
    }

    @Override
    public MediaType contentType() {
        return mediaType;
    }

    @Override
    public long contentLength() throws IOException {
        return contentLength;
    }

    @Override
    public void writeTo(BufferedSink bufferedSink) throws IOException {
        byte[] buffer = new byte[8192];
        long uploaded = 0;
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            bufferedSink.write(buffer, 0, bytesRead);
            uploaded += bytesRead;
            listener.onProgress(uploaded, contentLength);
        }
        bufferedSink.close();
    }

    public interface ProgressListener {
        void onProgress(long byteWritten, long contentLength);
    }
}
