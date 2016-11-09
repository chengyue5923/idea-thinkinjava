package com.fhzc.app.android.android.ui.activity.fuhuaservice;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.platform.utils.android.ToastTool;
import com.base.platform.utils.java.DateTools;
import com.base.platform.utils.java.StringTools;
import com.fhzc.app.android.R;
import com.fhzc.app.android.android.base.BaseActivity;
import com.fhzc.app.android.android.ui.view.dialog.ActivityPopUpDialog;
import com.fhzc.app.android.android.ui.view.dialog.ActivitySignUpCancelDialog;
import com.fhzc.app.android.android.ui.view.widget.CommonToolBar;
import com.fhzc.app.android.android.ui.view.widget.ScrollViewExtend;
import com.fhzc.app.android.configer.UrlRes;
import com.fhzc.app.android.configer.constants.Constant;
import com.fhzc.app.android.configer.enums.HttpConfig;
import com.fhzc.app.android.controller.ActivityController;
import com.fhzc.app.android.controller.EventManager;
import com.fhzc.app.android.db.UserPreference;
import com.fhzc.app.android.event.CancelCollectEvent;
import com.fhzc.app.android.models.ActivityModel;
import com.fhzc.app.android.models.CollectActivityModel;
import com.fhzc.app.android.utils.IntentTool;
import com.fhzc.app.android.utils.android.Logger;
import com.fhzc.app.android.utils.im.ImageLoader;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 私行活动详情
 * Created by lenovo on 2016/7/11.
 */
public class FhNewActivityDetailActivity extends BaseActivity implements View.OnClickListener, CommonToolBar.ClickRightListener {


    private static final int VIDEO_CONTENT_DESC_MAX_LINE = 3;// 默认展示最大行数3行
    private static final int SHOW_CONTENT_NONE_STATE = 0;// 扩充
    private static final int SHRINK_UP_STATE = 1;// 收起状态
    private static final int SPREAD_STATE = 2;// 展开状态
    private static int mState = SHRINK_UP_STATE;//默认收起状态
    @Bind(R.id.activityDetailToolbar)
    CommonToolBar activityDetailToolbar;
    @Bind(R.id.signUpPlannerActivityTextView)
    TextView signUpPlannerActivityTextView;
    @Bind(R.id.plannerLayout)
    LinearLayout plannerLayout;
    @Bind(R.id.signUpForOtherActivityTextView)
    TextView signUpForOtherActivityTextView;
    @Bind(R.id.signUpActivityTextView)
    TextView signUpActivityTextView;
    @Bind(R.id.singupLayout)
    LinearLayout singupLayout;
    @Bind(R.id.bottomlayout)
    LinearLayout bottomlayout;
    @Bind(R.id.activityImageView)
    ImageView activityImageView;
    @Bind(R.id.activityTextView)
    TextView activityTextView;
    @Bind(R.id.activitySingUpview)
    ImageView activitySingUpview;
    @Bind(R.id.time)
    TextView time;
    @Bind(R.id.signUpTimeText)
    TextView signUpTimeText;
    @Bind(R.id.activitytime)
    TextView activitytime;
    @Bind(R.id.activityTimeTextView)
    TextView activityTimeTextView;
    @Bind(R.id.activityPlaceText)
    TextView activityPlaceText;
    @Bind(R.id.activityAddressText)
    TextView activityAddressText;
    @Bind(R.id.activityDesc)
    TextView activityDesc;
    @Bind(R.id.activityContextTextView)
    TextView activityContextTextView;
    @Bind(R.id.moretextview)
    TextView moretextview;
    @Bind(R.id.spread)
    ImageView spread;
    @Bind(R.id.shrink_up)
    ImageView shrinkUp;
    @Bind(R.id.show_more)
    RelativeLayout showMore;
    @Bind(R.id.shouurl)
    WebView shouurl;
    @Bind(R.id.activityContextText)
    TextView activityContextText;
    @Bind(R.id.scrollview)
    ScrollViewExtend scrollview;
    @Bind(R.id.signUpCancelTextView)
    TextView signUpCancelTextView;
    @Bind(R.id.cancelSingUpLayout)
    LinearLayout cancelSingUpLayout;
    @Bind(R.id.activityDescss)
    TextView activityDescss;
    private int activityId;
    private int position;
    private Handler mHandler;
    private Thread t;

    private ActivityModel model;

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        cancelSingUpLayout.setVisibility(View.GONE);
        if (!UserPreference.isCustomer()) {
            plannerLayout.setVisibility(View.VISIBLE);
            singupLayout.setVisibility(View.GONE);
        } else {
            plannerLayout.setVisibility(View.GONE);
            singupLayout.setVisibility(View.VISIBLE);
        }
        mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == 0x105) {
                    if (msg.obj != null)
                        activityContextText.setText((CharSequence) msg.obj);
                }
                return false;
            }
        });
        WebSettings wv_setttig = shouurl.getSettings();
        wv_setttig.setDefaultTextEncodingName("UTF-8");
        shouurl.setWebViewClient(webviewClient);
        wv_setttig.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
//        wv_setttig.setUseWideViewPort(true);
//        wv_setttig.setBuiltInZoomControls(false);
//        wv_setttig.setSupportZoom(false);
//        wv_setttig.setDisplayZoomControls(false);
        wv_setttig.setJavaScriptEnabled(true);
    }

    private WebViewClient webviewClient = new WebViewClient() {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    };

    @Override
    protected void initEvent() {
        cancelSingUpLayout.setOnClickListener(this);
        activityDetailToolbar.setClickRightListener(this);
        showMore.setOnClickListener(this);
        plannerLayout.setOnClickListener(this);
        signUpForOtherActivityTextView.setOnClickListener(this);
        signUpActivityTextView.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        activityId = getIntent().getIntExtra("activityId", -1);
        position = getIntent().getIntExtra("position", -1);
        if (activityId == -1)
            return;
        ActivityController.getInstance().focusStatus(this, activityId);
        ActivityController.getInstance().activityDetail(this, activityId);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_detail_activity;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.show_more: {
                if (mState == SPREAD_STATE) {
                    activityContextText.setMaxLines(VIDEO_CONTENT_DESC_MAX_LINE);
                    activityContextText.requestLayout();
                    shrinkUp.setVisibility(View.GONE);
                    spread.setVisibility(View.VISIBLE);
                    mState = SHRINK_UP_STATE;
                } else if (mState == SHRINK_UP_STATE) {
                    activityContextText.setMaxLines(Integer.MAX_VALUE);
                    activityContextText.requestLayout();
                    shrinkUp.setVisibility(View.VISIBLE);
                    spread.setVisibility(View.GONE);
                    mState = SPREAD_STATE;
                }
                break;
            }
            case R.id.signUpActivityTextView:
                if (UserPreference.isCustomer()) {
                    if (model.getStatus() == 2) {
                        ToastTool.show("报名结束");
                    } else if (model.getStatus() == 3) {
                        ToastTool.show("活动结束");
                    } else {
                        PopUpDialog();
                    }
                }
                break;
            case R.id.signUpForOtherActivityTextView:
                if (UserPreference.getPlannerUid() == 0) {
                    ToastTool.show("您没有理财师");
                    return;
                }
                IntentTool.chat(FhNewActivityDetailActivity.this, null, UserPreference.getPlannerUid());
                break;

            case R.id.cancelSingUpLayout:
                AccountCancelPopUpDialog();

                break;
            default: {
                break;
            }
        }
    }

    boolean dialogFlag;
    ActivityPopUpDialog PopUpDialog;


    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o) {
        if (flag == HttpConfig.activityDetail.getType()) {
            model = (ActivityModel) result;
            ImageLoader.getInstance(this, R.drawable.default_error_long).displayImage(model.getCover(), activityImageView);
            signUpTimeText.setText(DateTools.formatDateWithSecondSince1970(DateTools.DATE_FORMAT_STYLE_5, model.getBeginTime()) + "至" + DateTools.formatDateWithSecondSince1970(DateTools.DATE_FORMAT_STYLE_5, model.getEndTime()));
            activityAddressText.setText(model.getAddress());
            activityTimeTextView.setText(DateTools.formatDateWithSecondSince1970(DateTools.DATE_FORMAT_STYLE_5, model.getApplyBeginTime()) + "至" + DateTools.formatDateWithSecondSince1970(DateTools.DATE_FORMAT_STYLE_5, model.getApplyEndTime()));
            activityTextView.setText(model.getName());
            Object status = model.getFocusStatus();
            if (StringTools.isNullOrEmpty(status.toString()) || status.toString().equals("0.0")) {
                activityDetailToolbar.setCollect(false);
            } else {
                activityDetailToolbar.setCollect(true);
            }
            activityContextTextView.setText("暂无简介");
            if (model.getContent().equals("")) {
                activityContextText.setVisibility(View.GONE);
                shouurl.setVisibility(View.VISIBLE);
                shouurl.loadUrl(model.getUrl());
            } else {
                activityContextText.setVisibility(View.VISIBLE);
                shouurl.setVisibility(View.GONE);
                try {
                    t = new Thread(new Runnable() {
                        Message msg = Message.obtain();

                        @Override
                        public void run() {
                            Html.ImageGetter imageGetter = new Html.ImageGetter() {
                                @Override
                                public Drawable getDrawable(String source) {
                                    WindowManager wm = FhNewActivityDetailActivity.this.getWindowManager();
                                    int width = wm.getDefaultDisplay().getWidth();
                                    int height = wm.getDefaultDisplay().getHeight();
                                    Drawable drawable = getImageFromNetwork(source);
                                    drawable.setBounds(0, 0, width, drawable.getIntrinsicHeight() * 4);
                                    return drawable;
                                }
                            };
                            CharSequence charSequence = Html.fromHtml(model.getContent(), imageGetter, null);
                            msg.what = 0x105;
                            msg.obj = charSequence;
                            mHandler.sendMessage(msg);
                        }
                    });
                    t.start();
                } catch (Exception e) {
                    Logger.e("e.getmessasge" + e.getMessage());
                }
//                    activityContextText.setText(Html.fromHtml(model.getContent()));
            }
        } else if (flag == HttpConfig.focusStatus.getType()) {
            int status = (int) result;
            if (status == 0) {
                activityDetailToolbar.setCollect(false);
            } else {
                activityDetailToolbar.setCollect(true);
            }
        } else if (flag == HttpConfig.focus.getType()) {
            if (activityDetailToolbar.isCollect()) {
                activityDetailToolbar.setCollect(false);
                EventManager.getInstance().post(new CancelCollectEvent(Constant.COLLECTTYPE_ACTIVITY, position, false, null));
                ToastTool.show("取消关注");
            } else {
                activityDetailToolbar.setCollect(true);
                EventManager.getInstance().post(new CancelCollectEvent(Constant.COLLECTTYPE_ACTIVITY, position, true, CollectActivityModel.getModel(model)));
                ToastTool.show("已关注");
            }
        } else if (flag == HttpConfig.joinActivity.getType()) {
            try {
                JSONObject object = new JSONObject(o.toString());
                if (object.getInt("code") == 200) {
                    if (object.getJSONObject("map").getString("type").equals("self")) {
                        PopUpDialog.dismiss();
                        cancelSingUpLayout.setVisibility(View.VISIBLE);
                        ToastTool.show("报名成功");
                    }else{
                        ToastTool.show(object.getString("message"));
                    }
                } else {
                    ToastTool.show(object.getString("message"));
                }
            } catch (Exception e) {
                ToastTool.show("报名失败");
            }

        }else if (flag == HttpConfig.cancelJoinActivity.getType()) {
            try {
                JSONObject object = new JSONObject(o.toString());
                if (object.getInt("code") == 200) {
                    PopUpOtherCancelDialog.dismiss();
                    ToastTool.show("取消报名成功");
                    cancelSingUpLayout.setVisibility(View.GONE);
                } else {
                    ToastTool.show(object.getString("message"));
                }

            } catch (Exception e) {
                ToastTool.show("取消报名失败");
            }
        }
    }

    Drawable getImageFromNetwork(String imageUrl) {
        URL myFileUrl = null;
        Drawable drawable = null;
        try {
            if (imageUrl.contains("/opt/fhzc/system")) {
                myFileUrl = new URL(UrlRes.getInstance().getPictureUrl() + imageUrl);
            } else {
                myFileUrl = new URL(imageUrl);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            drawable = Drawable.createFromStream(is, null);
            if (is != null) {
                is.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return drawable;
    }
    ActivitySignUpCancelDialog PopUpOtherCancelDialog;

    public void AccountCancelPopUpDialog() {
        PopUpOtherCancelDialog = new ActivitySignUpCancelDialog(this);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        PopUpOtherCancelDialog.setDialogListener(new ActivitySignUpCancelDialog.DialogListener() {
            @Override
            public void onclickConfirm() {
                ActivityController.getInstance().cancelJoinActivity(FhNewActivityDetailActivity.this, model.getApplyId());
            }
        });
        PopUpOtherCancelDialog.show(fragmentTransaction, "ActivityAccountPopUpDialog");
    }

    public void PopUpDialog() {
        PopUpDialog = new ActivityPopUpDialog(this, model);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        PopUpDialog.setDialogListener(new ActivityPopUpDialog.DialogListener() {
            @Override
            public void onclickConfirm(int personnumber) {
                Logger.e("number" + personnumber);
                dialogFlag = true;
                ActivityController.getInstance().joinActivity(FhNewActivityDetailActivity.this, activityId, personnumber);
            }

            @Override
            public void onclickOtner() {
                PopUpDialog.dismiss();
//                AccountPopUpDialog();
            }
        });
        PopUpDialog.show(fragmentTransaction, "ActivityPopUpDialog");
    }

    @Override
    public void OnClickRight(View view) {
        if (activityDetailToolbar.isCollect()) {
            ActivityController.getInstance().cancelFocusRight(this, activityId);
        } else {
            ActivityController.getInstance().focusRight(this, activityId);
        }
    }

}
