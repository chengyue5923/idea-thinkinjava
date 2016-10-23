package com.fhzc.app.android.android.ui.activity;

import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.platform.utils.android.ToastTool;
import com.base.platform.utils.java.DateTools;
import com.base.platform.utils.java.StringTools;
import com.fhzc.app.android.R;
import com.fhzc.app.android.android.base.BaseActivity;
import com.fhzc.app.android.android.ui.view.widget.CommonToolBar;
import com.fhzc.app.android.models.MyProductModel;
import com.fhzc.app.android.utils.android.IntentTools;
import com.fhzc.app.android.utils.android.Logger;

import java.io.Serializable;
import java.text.DecimalFormat;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 产品动态
 * Created by yanbo on 2016/7/25.
 */
public class ProductDynamicDetail extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.productDynamicTitle)
    CommonToolBar productDynamicTitle;
    @Bind(R.id.AnnualIncomeTv)
    TextView AnnualIncomeTv;
    @Bind(R.id.interestNumber)
    TextView interestNumber;
    @Bind(R.id.timeText)
    TextView timeText;
    @Bind(R.id.startPriceText)
    TextView startPriceText;
    @Bind(R.id.openDayLayout)
    LinearLayout openDayLayout;
    @Bind(R.id.productName)
    TextView productName;
    @Bind(R.id.productStatus)
    TextView productStatus;
    @Bind(R.id.foundDay)
    TextView foundDay;
    @Bind(R.id.dividendDayLayout)
    LinearLayout dividendDayLayout;
    @Bind(R.id.redeemDayLayout)
    LinearLayout redeemDayLayout;
    @Bind(R.id.deadDate)
    TextView deadDate;
    @Bind(R.id.subscriptionTime)
    TextView subscriptionTime;
    @Bind(R.id.paymentDate)
    TextView paymentDate;
    @Bind(R.id.productAnnouncementLayout)
    LinearLayout productAnnouncementLayout;
    @Bind(R.id.recordProofLayout)
    LinearLayout recordProofLayout;
    private boolean isHistory;
    private MyProductModel productModels;

    @Override
    protected void initView() {
        ButterKnife.bind(this);

    }

    @Override
    protected void initEvent() {
        recordProofLayout.setOnClickListener(this);
        productAnnouncementLayout.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        isHistory = getIntent().getBooleanExtra("isHistory", false);
        productModels = (MyProductModel) getIntent().getSerializableExtra("productModels");
        Logger.e("productModels" + productModels.toString());
        productDynamicTitle.setTitle(isHistory ? "产品历史详情" : "产品动态详情");
        setData();
    }

    private void setData() {
        try{
            productName.setText(productModels.getName());
            startPriceText.setText(((float) productModels.getAmount() / 10000) + "万");
            foundDay.setText(DateTools.formatDateWithSecondSince1970(DateTools.DATE_FORMAT_STYLE_3, productModels.getFoundDay()));
            long daoQiRi = 0l;
            String annualInterval = productModels.getAnnualInterval();
            if(StringTools.isNullOrEmpty(annualInterval)){
                annualInterval = "";
            }
            String annualOldYield = productModels.getAnnualOldYield();
            if(StringTools.isNullOrEmpty(annualOldYield)){
                annualOldYield = "";
            }else{
                annualOldYield = new DecimalFormat("0.00").format(Float.parseFloat(annualOldYield)*100f)+"%";
            }
            if(isHistory){
                interestNumber.setText(annualOldYield);
                daoQiRi = productModels.getDeadDate();
            }else{
                interestNumber.setText(annualInterval);
                daoQiRi = productModels.getExpireDay();
            }
            Logger.e("fuwen  "+daoQiRi+"  "+DateTools.formatDateWithSecondSince1970(DateTools.DATE_FORMAT_STYLE_3, daoQiRi));
            deadDate.setText(DateTools.formatDateWithSecondSince1970(DateTools.DATE_FORMAT_STYLE_3, daoQiRi));
            paymentDate.setText(DateTools.formatDateWithSecondSince1970(DateTools.DATE_FORMAT_STYLE_3, daoQiRi));
//        interestNumber.setText(productModels.getAnnualYield());
            subscriptionTime.setText(DateTools.formatDateWithSecondSince1970(DateTools.DATE_FORMAT_STYLE_3, productModels.getBuyTime()));
            //派息日
            String dividends = productModels.getDividendDay();
            if (null != dividends) {
                String[] strings = dividends.split(",");
                for (int i = 0; i < strings.length; i++) {
                    TextView view = new TextView(this);
                    view.setText(strings[i]);
                    view.setTextSize(16);
                    view.setTextColor(Color.parseColor("#46464B"));
                    view.setPadding(0, 0, 0, getResources().getDimensionPixelSize(R.dimen.tenDp));
                    dividendDayLayout.addView(view);
                }
            }
            String redeem = productModels.getDividendDay();
            //赎回日
            if (null != redeem) {
                String[] strings = redeem.split(",");
                for (int i = 0; i < strings.length; i++) {
                    TextView view = new TextView(this);
                    view.setText(strings[i]);
                    view.setTextSize(16);
                    view.setTextColor(Color.parseColor("#46464B"));
                    view.setPadding(0, 0, 0, getResources().getDimensionPixelSize(R.dimen.tenDp));
                    redeemDayLayout.addView(view);
                }
            }
            //开放日
            String open = productModels.getBuyDay();
            if (null != redeem) {
                String[] strings = open.split(",");
                for (int i = 0; i < strings.length; i++) {
                    TextView view = new TextView(this);
                    view.setText(strings[i]);
                    view.setTextSize(16);
                    view.setTextColor(Color.parseColor("#46464B"));
                    view.setPadding(0, 0, 0, getResources().getDimensionPixelSize(R.dimen.tenDp));
                    openDayLayout.addView(view);
                }
            }

        }catch (Exception e){
            Logger.e("Exceptionrrrer"+e.getMessage());
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_product_dynamic_detail;
    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o) {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.productAnnouncementLayout:
                if (StringTools.isNullOrEmpty(productModels.getNotice())) {
                    ToastTool.show("暂无成立公告");
                    return;
                }
                IntentTools.startScreenFullActivity(this, productModels.getNotice());
                break;
            case R.id.recordProofLayout:
                if (StringTools.isNullOrEmpty(productModels.getProveUrl())) {
                    ToastTool.show("暂无备案证明");
                    return;
                }
                IntentTools.startScreenFullActivity(this, productModels.getProveUrl());
                break;
        }
    }
}
