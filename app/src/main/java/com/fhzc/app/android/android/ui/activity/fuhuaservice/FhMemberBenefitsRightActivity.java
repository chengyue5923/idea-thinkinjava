package com.fhzc.app.android.android.ui.activity.fuhuaservice;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fhzc.app.android.R;
import com.fhzc.app.android.android.base.BaseActivity;
import com.fhzc.app.android.android.ui.view.adapter.RightAndPointListAdapter;
import com.fhzc.app.android.controller.EventManager;
import com.fhzc.app.android.dao.MessageDao;
import com.fhzc.app.android.event.IMEvent;
import com.fhzc.app.android.utils.android.IntentTools;
import com.fhzc.app.android.utils.android.Logger;

import java.io.Serializable;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 新会员权益滑动列表
 * Created by lenovo on 2016/7/11.
 */
public class FhMemberBenefitsRightActivity extends BaseActivity implements View.OnClickListener {


    @Bind(R.id.backImageRight)
    ImageView backImageRight;
    @Bind(R.id.messageImage)
    ImageView messageImage;
    @Bind(R.id.TabLayout)
    TabLayout TabLayout;
    @Bind(R.id.viewPager)
    ViewPager viewPager;
    RightAndPointListAdapter ListAdapter;
    @Bind(R.id.messageRedImage)
    TextView messageRedImage;

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        if (!EventManager.getInstance().isRegister(this)) {
            EventManager.getInstance().register(this);
        }
        String[] strings = getResources().getStringArray(R.array.righttab);
        ListAdapter = new RightAndPointListAdapter(getSupportFragmentManager(), strings);
        viewPager.setAdapter(ListAdapter);
        TabLayout.setupWithViewPager(viewPager);
        TabLayout.setTabsFromPagerAdapter(ListAdapter);
        viewPager.setOffscreenPageLimit(20);
    }

    @Override
    protected void initEvent() {
        backImageRight.setOnClickListener(this);
        messageImage.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        int count = new MessageDao().getUnReadCount();
        messageRedImage.setVisibility(count > 0 ? View.VISIBLE : View.GONE);
        messageRedImage.setText(String.valueOf(count));
    }

    @Override
    protected void initData() {
        int count = new MessageDao().getUnReadCount();
        messageRedImage.setVisibility(count > 0 ? View.VISIBLE : View.GONE);
        messageRedImage.setText(String.valueOf(count));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_member_benefit_rights;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.messageImage:
                IntentTools.startChatList(this);
                break;
            case R.id.backImageRight:
                finish();
                break;
        }
    }
    public void onEventMainThread(IMEvent event) {
        try{
            if (null != event) {
                int count = new MessageDao().getUnReadCount();
                messageRedImage.setVisibility(count > 0 ? View.VISIBLE : View.GONE);
                messageRedImage.setText(String.valueOf(count));
            }
        }catch (Exception e){
            Logger.e("Exceptions"+e.getMessage());
        }
    }
    @Override
    public void onConnectStart() {
        super.onConnectStart();
    }

    @Override
    public void onFail(Exception e) {
        super.onFail(e);
    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventManager.getInstance().isRegister(this)) {
            EventManager.getInstance().unregister(this);
        }
    }
}
