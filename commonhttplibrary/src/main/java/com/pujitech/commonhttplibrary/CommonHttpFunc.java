package com.pujitech.commonhttplibrary;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by WangXuguang on 2017/9/27.
 */

public class CommonHttpFunc<T> implements Function<CommonHttpResult<T>, T> {


    @Override
    public T apply(@NonNull CommonHttpResult<T> httpResult) {
        if (httpResult.getCode() != HttpStatus.HTTP_SUCCESS) {
            //联网失败

        }
        return httpResult.getData();
    }

}
