package com.pujitech.commonhttplibrary.glide;

import android.text.TextUtils;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by WangXuguang on 2017/9/28.
 */

public class OkHttpProgressContentLengthInputStream extends FilterInputStream {
    private static final String TAG = "OkHttpProgressContentLengthInputStream";
    private static final int UNKNOWN = -1;
    private final long contentLength;
    private int readSoFar;
    private static String mUrl;

    public static InputStream obtain(InputStream other, String contentLengthHeader) {
        return obtain(other, parseContentLength(contentLengthHeader), mUrl);
    }

    public static InputStream obtain(InputStream other, long contentLength, String url) {
        OkHttpProgressContentLengthInputStream.mUrl = url;
        return new OkHttpProgressContentLengthInputStream(other, contentLength);
    }

    private static int parseContentLength(String contentLengthHeader) {
        int result = UNKNOWN;
        if (!TextUtils.isEmpty(contentLengthHeader)) {
            try {
                result = Integer.parseInt(contentLengthHeader);
            } catch (NumberFormatException e) {

            }
        }
        return result;
    }

    OkHttpProgressContentLengthInputStream(InputStream in, long contentLength) {
        super(in);
        this.contentLength = contentLength;
    }

    @Override
    public synchronized int available() throws IOException {
        return (int) Math.max(contentLength - readSoFar, in.available());
    }

    @Override
    public synchronized int read() throws IOException {
        int value = super.read();
        checkReadSoFarOrThrow(value >= 0 ? 1 : -1);
        return value;
    }

    @Override
    public int read(byte[] buffer) throws IOException {
        return read(buffer, 0 /*byteOffset*/, buffer.length /*byteCount*/);
    }

    @Override
    public synchronized int read(byte[] buffer, int byteOffset, int byteCount) throws IOException {
        return checkReadSoFarOrThrow(super.read(buffer, byteOffset, byteCount));
    }

    private int checkReadSoFarOrThrow(int read) throws IOException {
        if (read >= 0) {
            readSoFar += read;
            //添加进度的监听    readSoFar/contentLength
            ProgressManager.setGlideProgress(mUrl, (int) (readSoFar * 100 / contentLength));

        } else if (contentLength - readSoFar > 0) {
            throw new IOException("Failed to read all expected data"
                    + ", expected: " + contentLength
                    + ", but read: " + readSoFar);
        }
        return read;
    }
}
