package com.pujitech.commonhttplibrary;

import com.pujitech.commonhttplibrary.utils.LogUtils;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by WangXuguang on 2017/9/20.
 */

public class RetrofitUtils {

    private static final String TAG = "RetrofitUtils";
    private static long TIME_OUT = 15000;

    /**
     * RetrofitUtils的单例模式
     */
    private static Retrofit.Builder mRetrofit = new Retrofit.Builder()
            .addConverterFactory(FastJsonConverterFactory.create())     //添加Retrofit对fastjson的支持
            .addConverterFactory(ScalarsConverterFactory.create())      //添加Retrofit对String类型的支持
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create());  //添加Retrofit对Rxjava的支持


    private static ConcurrentHashMap<String, Object> mRetrofitMap = new ConcurrentHashMap<>();

    private static OkHttpClient.Builder mOkhttpClient = new OkHttpClient.Builder();


    private RetrofitUtils() {

    }


    public static <T extends Object> T getInstance(Class<T> t, String baseUrl) {
        if (mRetrofitMap.containsKey(baseUrl)) {
            return (T) mRetrofitMap.get(baseUrl);
        }
        return initRetrofit(t, baseUrl);
    }

    /**
     * 初始化retrofit的方法
     */
    private static <T extends Object> T initRetrofit(Class<T> t, String baseUrl) {
        T t1 = mRetrofit.client(mOkhttpClient.addNetworkInterceptor(new LogInterceptor()).connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS).build()).baseUrl(baseUrl).build().create(t);
        mRetrofitMap.put(baseUrl, t1);
        return t1;
    }

    private static void initOkhttpClient() {
        mOkhttpClient.connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS);
    }

    public static void setTimeOut(long timeOut) {
        TIME_OUT = timeOut;
        initOkhttpClient();
    }

    private static class LogInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            long t1 = System.nanoTime();
            LogUtils.d(TAG, "HttpHelper" + String.format("Sending request %s on %s%n%s",
                    request.url(), chain.connection(), request.headers()));

            Response response = chain.proceed(request);
            long t2 = System.nanoTime();

            LogUtils.d(TAG, "HttpHelper" + String.format("Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));
            return response;

        }
    }

//    private class CacheInterceptor implements Interceptor {
//
//        // 有网络时 设置缓存超时时间1个小时
//        int maxAge = 0;
//        // 无网络时，设置超时为1个月
//        int maxStale = 60 * 60 * 24 * 30;
//
//        @Override
//        public Response intercept(Chain chain) throws IOException {
//
//            Request request = chain.request();
//            if (!NetworkUtils.isNetworkAvailable(mContext)) {
//                request = request.newBuilder()
//                        .cacheControl(CacheControl.FORCE_CACHE)
//                        .build();
//            }
//
//            Response response = chain.proceed(request);
//            if (NetworkUtils.isNetworkAvailable(mContext)) {
//                response.newBuilder()
//                        .removeHeader("Pragma")
//                        .header("Cache-Control", "public, max-age=" + maxAge)
//                        .build();
//            } else {
//                response.newBuilder()
//                        .removeHeader("Pragma")
//                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
//                        .build();
//            }
//            return response;
//        }
//    }
}
