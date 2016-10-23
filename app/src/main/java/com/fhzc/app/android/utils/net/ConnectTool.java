package com.fhzc.app.android.utils.net;

import com.base.framwork.net.factory.HttpRequestFactory;
import com.base.framwork.net.lisener.HttpNetCallBack;
import com.base.framwork.net.lisener.ViewNetCallBack;
import com.base.platform.utils.android.Logger;
import com.fhzc.app.android.configer.UrlRes;
import com.fhzc.app.android.configer.enums.HttpConfig;
import com.fhzc.app.android.configer.enums.HttpManager;
import com.fhzc.app.android.models.HttpConfigBean;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * 连接底层的 http工具类
 */
public class ConnectTool {

    /**
     * http request
     *
     * @param configs  配置的枚举
     * @param param    hm
     * @param listener 监听的
     * @param cl       lass
     * @throws Exception
     */
    public static void httpRequest(HttpConfig configs, Map<String, Object> param, ViewNetCallBack listener,
                                   Class cl) throws Exception {

        HttpConfigBean config = HttpManager.getInstance().getConifgById(configs);
        String url = UrlRes.getInstance().getUrl() + config.getActioin() + "?";
        Logger.e("NetRP-url-" + url);
        HttpRequestFactory until = HttpRequestFactory.getInstance();
        until.setNetType(HttpRequestFactory.HTTPCLIENT);
        HttpNetCallBack callBack = until.getHttpRequst();
        BaseNetImpl implLinener = new BaseNetImpl(listener, cl, configs);
        HashMap<String, String> header = new HashMap<>();
        implLinener.setParam(param);
        if (param.size() == 0) {
            Logger.e("request=param is null");
        } else {
            Iterator iter = param.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                String key = entry.getKey().toString();
                Object val = entry.getValue();
                Logger.e("request=param  key=" + key + "   value=" + val);
            }
        }
        switch (config.getShowEnumsMethod()) {
            case GET:
                if (config.isHeader()) {
                    callBack.getWithHeader(url, param, header, config.getTimeout(), config.isCach(), implLinener);
                    return;
                }
                callBack.get(url, param, config.getTimeout(), config.isCach(), implLinener);
                return;
            case POST:
                if (config.isHeader()) {
                    callBack.postWithHeader(url, param, header, config.getTimeout(), config.isCach(), implLinener);
                    return;
                }
                callBack.post(url, param, config.getTimeout(), config.isCach(), implLinener);
                return;
            case DELETE:
                callBack.delete(url, param, header, config.getTimeout(), config.isCach(), implLinener);
                return;
            default:
                break;
        }
    }


}
