package com.fhzc.app.android.configer.enums;

import com.base.framwork.utils.GsonTool;
import com.base.platform.android.application.BaseApplication;
import com.base.platform.utils.android.Logger;
import com.fhzc.app.android.R;
import com.fhzc.app.android.models.HttpConfigBean;


import java.util.HashMap;
import java.util.List;




/**
 *  对 url 配置 从内存里面 操作
 */
public class HttpManager {

    HashMap<Integer, HttpConfigBean> has;

    public static HttpManager getInstance() {

        return SingleClearCach.instance;
    }

    /**
     * 实例化  内存中的 url参数
     */
    public void init() {
        has = new HashMap();
        try {
            String url = BaseApplication.getInstance().getString(R.string.url);
            List<HttpConfigBean> models= GsonTool.jsonToArrayEntity(url, HttpConfigBean.class);
            for (HttpConfigBean m:models){
                has.put(m.getId(),m);
            }
        } catch (Exception e) {
            Logger.e(e.getLocalizedMessage(), e);

        }
    }

    public HttpConfigBean getConifgById(HttpConfig config) {
        if (has == null || has.size() == 0) {
            init();
        }
        return has.get(config.getType());

    }

    static class SingleClearCach {
        static HttpManager instance = new HttpManager();
    }
    public void clear(){
        has.clear();
        has=null;
    }

}
