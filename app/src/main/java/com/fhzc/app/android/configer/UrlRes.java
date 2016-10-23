package com.fhzc.app.android.configer;

import android.content.Context;

import com.fhzc.app.android.configer.constants.Constant;


/**
 * 从资源中获取的rul 的res
 */
public class UrlRes {


    Context context;


    public static UrlRes getInstance() {

        return SingleClearCach.instance;

    }


    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }


    public String getUrl() {
        return Constant.APP_URL;
    }


    public String getPictureUrl() {
        return Constant.APP_PIC_URL;
    }

    static class SingleClearCach {
        static UrlRes instance = new UrlRes();
    }
}
