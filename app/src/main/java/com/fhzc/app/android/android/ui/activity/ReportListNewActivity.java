package com.fhzc.app.android.android.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.fhzc.app.android.R;
import com.fhzc.app.android.android.base.BaseActivity;
import com.fhzc.app.android.android.ui.view.adapter.ReportNewListAdapter;
import com.fhzc.app.android.android.ui.view.widget.CommonToolBar;
import com.fhzc.app.android.android.ui.view.widget.EmptyLayout;
import com.fhzc.app.android.android.ui.view.widget.ImagePagerViewPointRight;
import com.fhzc.app.android.android.ui.view.widget.ScrollViewExtend;
import com.fhzc.app.android.configer.UrlRes;
import com.fhzc.app.android.configer.enums.HttpConfig;
import com.fhzc.app.android.controller.EventManager;
import com.fhzc.app.android.controller.LoginController;
import com.fhzc.app.android.controller.ReportController;
import com.fhzc.app.android.dao.MessageDao;
import com.fhzc.app.android.event.IMEvent;
import com.fhzc.app.android.models.BannerModel;
import com.fhzc.app.android.models.IndexMapModel;
import com.fhzc.app.android.models.ReportTypeModel;
import com.fhzc.app.android.utils.android.IntentTools;
import com.fhzc.app.android.utils.android.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by yanbo on 2016/7/20.
 */
public class ReportListNewActivity extends BaseActivity implements View.OnClickListener, CommonToolBar.ClickRightListener {
    @Bind(R.id.reportnewListToolBar)
    CommonToolBar reportnewListToolBar;

//    EmptyLayout emptyLayout;
    ReportNewListAdapter ListAdapter;
    @Bind(R.id.ImagePagerView)
    ImagePagerViewPointRight ImagePagerView;
    @Bind(R.id.textViewtitle)
    TextView textViewtitle;
    @Bind(R.id.reportTabLayout)
    TabLayout reportTabLayout;
    @Bind(R.id.reportpager)
    ViewPager reportpager;
//    @Bind(R.id.mScrollView)
//    ScrollViewExtend mScrollView;
    private List<ReportTypeModel> model;

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        if (!EventManager.getInstance().isRegister(this)) {
            EventManager.getInstance().register(this);
        }
//        String[] strings = getResources().getStringArray(R.array.report_tab);
//        ListAdapter = new ReportNewListAdapter(getSupportFragmentManager(), strings);
//        reportpager.setAdapter(ListAdapter);
//        reportTabLayout.setupWithViewPager(reportpager);
//        reportTabLayout.setTabsFromPagerAdapter(ListAdapter);
//        reportpager.setOffscreenPageLimit(20);
//        emptyLayout = new EmptyLayout(this, mScrollView);
    }

    @Override
    protected void initEvent() {
        reportnewListToolBar.setClickRightListener(this);
    }

    @Override
    public void onClick(View v) {
    }

    public void onEventMainThread(IMEvent event) {
        if (null != event) {
            reportnewListToolBar.setRedImage(new MessageDao().getUnReadCount());
        }
    }

    @Override
    protected void initData() {
        reportnewListToolBar.setRedImage(new MessageDao().getUnReadCount());
        ReportController.getInstance().reportTypeListDetail(this);
        LoginController.getInstance().index(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_report_new_list;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        if (EventManager.getInstance().isRegister(this)) {
            EventManager.getInstance().unregister(this);
        }
    }

    @Override
    public void onConnectStart() {
        super.onConnectStart();
//        emptyLayout.showLoading();
    }

    @Override
    public void onFail(Exception e) {
        super.onFail(e);
//        emptyLayout.showError();
    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o) {
        if (flag == HttpConfig.getReportType.getType()) {
//            emptyLayout.showSuccess(false);
            model = (List<ReportTypeModel>) result;
            Logger.e("modelddds++" + result);
            String[] strings = getResources().getStringArray(R.array.report_tab);
//            ListAdapter.setModel(model);
            ListAdapter = new ReportNewListAdapter(getSupportFragmentManager(), strings,model);
            reportpager.setAdapter(ListAdapter);
            reportTabLayout.setupWithViewPager(reportpager);
            reportTabLayout.setTabsFromPagerAdapter(ListAdapter);
            reportpager.setOffscreenPageLimit(20);
        }
        if (flag == HttpConfig.index.getType()) {
//            emptyLayout.showSuccess(false);
            IndexMapModel mapModel = (IndexMapModel) result;
            List<BannerModel> banner_pic = mapModel.getBanner_pic();
            List<String> paths = new ArrayList<>();
            paths.clear();
            if (banner_pic != null) {
                for (BannerModel m : banner_pic) {
                    paths.add(UrlRes.getInstance().getPictureUrl() + m.getCover());
                }
            }
            ImagePagerView.initData(paths);
        }
    }

    @Override
    public void OnClickRight(View view) {
        IntentTools.startChatList(this);
    }

}
