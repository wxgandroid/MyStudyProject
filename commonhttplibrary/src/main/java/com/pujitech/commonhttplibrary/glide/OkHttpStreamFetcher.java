package com.pujitech.commonhttplibrary.glide;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.HttpException;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.GlideUrl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by WangXuguang on 2017/9/28.
 */

public class OkHttpStreamFetcher
        implements DataFetcher<InputStream>
{

    private static final String TAG = "OkHttpFetcher";
    private final Call.Factory client;
    private final GlideUrl url;
    InputStream stream;
    ResponseBody responseBody;
    private volatile Call call;


    public OkHttpStreamFetcher(Call.Factory client, GlideUrl url) {
        this.client = client;
        this.url = url;
    }


    @Override
    public void loadData(Priority priority, final DataCallback<? super InputStream> callback) {
        Request.Builder requestBuilder = (new Request.Builder()).url(this.url.toStringUrl());
        Iterator request = this.url.getHeaders().entrySet().iterator();

        while (request.hasNext()) {
            Map.Entry headerEntry = (Map.Entry) request.next();
            String key = (String) headerEntry.getKey();
            requestBuilder.addHeader(key, (String) headerEntry.getValue());
        }

        Request request1 = requestBuilder.build();
        this.call = this.client.newCall(request1);
        this.call.enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {
                callback.onLoadFailed(e);
            }

            public void onResponse(Call call, Response response) throws IOException {
                OkHttpStreamFetcher.this.responseBody = response.body();
                if (response.isSuccessful()) {
                    long contentLength = OkHttpStreamFetcher.this.responseBody.contentLength();

                    OkHttpStreamFetcher.this.stream = OkHttpProgressContentLengthInputStream.obtain(OkHttpStreamFetcher.this.responseBody.byteStream(), contentLength,url.toStringUrl());
                    callback.onDataReady(OkHttpStreamFetcher.this.stream);

                } else {
                    callback.onLoadFailed(new HttpException(response.message(), response.code()));
                }

            }
        });
    }

    @Override
    public void cleanup() {
        try {
            if (this.stream != null) {
                this.stream.close();
            }
        } catch (IOException var2) {

        }

        if (this.responseBody != null) {
            this.responseBody.close();
        }

    }

    @Override
    public void cancel() {
        Call local = this.call;
        if (local != null) {
            local.cancel();
        }

    }

    @Override
    public Class<InputStream> getDataClass() {
        return InputStream.class;
    }

    @Override
    public DataSource getDataSource() {
        return DataSource.REMOTE;
    }
}
