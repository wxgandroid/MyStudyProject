package com.pujitech.commonhttplibrary;


import com.pujitech.merchant.network.BaseCommonObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangXuguang on 2017/9/26.
 */

public interface RxLifeRecycle {

    List<BaseCommonObserver> mObservers = new ArrayList<>();

    void setRegisterObject(BaseCommonObserver observer);

}
