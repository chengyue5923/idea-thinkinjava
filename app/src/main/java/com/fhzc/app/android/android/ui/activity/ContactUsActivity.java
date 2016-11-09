package com.fhzc.app.android.android.ui.activity;

import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fhzc.app.android.R;
import com.fhzc.app.android.android.base.BaseActivity;
import com.fhzc.app.android.android.ui.view.widget.CommonToolBar;
import com.fhzc.app.android.controller.UserController;
import com.fhzc.app.android.models.ContactUsModel;
import com.fhzc.app.android.utils.android.Logger;
import com.fhzc.app.android.utils.im.CommonUtil;

import java.io.Serializable;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by yanbo on 2016/8/3.
 */
public class ContactUsActivity extends BaseActivity implements View.OnClickListener{
    @Bind(R.id.systemMessageToolBar)
    CommonToolBar systemMessageToolBar;
    @Bind(R.id.phontcontact)
    WebView phontcontact;


    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initEvent() {
    }

    @Override
    protected void initData() {
        UserController.getInstance().aboutUs(this);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_contact_us;
    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o) {
        ContactUsModel model=(ContactUsModel)result;
        Logger.e("model.to" + model.toString());
//        phontcontact.setMovementMethod(ScrollingMovementMethod.getInstance());//滚动
//        phontcontact.setText(Html.fromHtml(model.getIntroduction()));
        phontcontact.loadDataWithBaseURL(null, model.getIntroduction(), "text/html", "utf-8", null);
        phontcontact.getSettings().setJavaScriptEnabled(true);
        phontcontact.setWebChromeClient(new WebChromeClient());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.phontcontact:

                break;
        }
    }
}
