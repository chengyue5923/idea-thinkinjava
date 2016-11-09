package com.fhzc.app.android.android.ui.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.base.platform.utils.android.Logger;
import com.fhzc.app.android.R;
import com.fhzc.app.android.android.base.BaseActivity;
import com.fhzc.app.android.android.ui.fragment.CustomerMineFragment;
import com.fhzc.app.android.android.ui.fragment.PlannerMyFragment;
import com.fhzc.app.android.android.ui.fragment.SelectedFragment;
import com.fhzc.app.android.android.ui.fragment.ServiceFragement1;
import com.fhzc.app.android.android.ui.fragment.ServiceFragment;
import com.fhzc.app.android.controller.LoginController;
import com.fhzc.app.android.controller.SystemController;
import com.fhzc.app.android.db.UserPreference;
import com.fhzc.app.android.utils.android.IntentTools;
import com.fhzc.app.android.utils.im.CommonUtil;
import com.umeng.message.UmengRegistrar;

import java.io.Serializable;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by yanbo on 16-7-4.
 */
public class MainTabActivity extends BaseActivity {

    @Bind(R.id.realtabcontent)
    FrameLayout realtabcontent;
    @Bind(R.id.tab_menu_selected_text_CN)
    TextView tabMenuSelectedTextCN;
    @Bind(R.id.tab_menu_selected_EN)
    TextView tabMenuSelectedEN;
    @Bind(R.id.main_tab_selected_layout)
    RelativeLayout mainTabSelectedLayout;
    @Bind(R.id.tab_menu_service_iv)
    ImageView tabMenuServiceIv;
    @Bind(R.id.tab_menu_wealth_text_CN)
    TextView tabMenuWealthTextCN;
    @Bind(R.id.tab_menu_wealth_text_EN)
    TextView tabMenuWealthTextEN;
    @Bind(R.id.main_tab_wealth_layout)
    LinearLayout mainTabWealthLayout;
    @Bind(R.id.main_tab_layout)
    RelativeLayout mainTabLayout;
    @Bind(R.id.drawer_layout)
    LinearLayout drawerLayout;
    private Fragment curFragment;
    private SelectedFragment selectedFragment;
    private ServiceFragment serviceFragment;
    private CustomerMineFragment customerMineFragment;
    private PlannerMyFragment plannerMyFragment;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                try {
                    if (curFragment instanceof ServiceFragment) {
                        CommonUtil.FlymeSetStatusBarLightMode(getWindow(), true);
                        CommonUtil.MIUISetStatusBarLightMode(getWindow(), true);
                    } else if ((curFragment instanceof SelectedFragment)) {
                        CommonUtil.FlymeSetStatusBarLightMode(getWindow(), false);
                        CommonUtil.MIUISetStatusBarLightMode(getWindow(), false);
                    } else if (curFragment instanceof CustomerMineFragment) {
                        CommonUtil.FlymeSetStatusBarLightMode(getWindow(), true);
                        CommonUtil.MIUISetStatusBarLightMode(getWindow(), true);
                    } else if (curFragment instanceof PlannerMyFragment) {
                        CommonUtil.FlymeSetStatusBarLightMode(getWindow(), true);
                        CommonUtil.MIUISetStatusBarLightMode(getWindow(), true);
                    }
                } catch (Exception e) {
                }
            }
        }, 889);


    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        boolean needAnim = getIntent().getBooleanExtra("needAnim",true);
        if(needAnim){
            IntentTools.startLoadingAnim(this,false);
            new CountDownTimer(1000,1000){

                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
//                    toSelectedFragment();
                    findViewById(R.id.ll_transition).setVisibility(View.GONE);
                }
            }.start();

        }
        else{
//            toSelectedFragment();
            findViewById(R.id.ll_transition).setVisibility(View.GONE);
        }
        toSelectedFragment();
//        CommonUtil.FlymeSetStatusBarLightMode(getWindow(), true);
//        CommonUtil.MIUISetStatusBarLightMode(getWindow(), true);

        initBar();
    }

    @Override
    protected void initEvent() {
        mainTabSelectedLayout.setOnClickListener(layoutClickListener);
        tabMenuServiceIv.setOnClickListener(layoutClickListener);
        mainTabWealthLayout.setOnClickListener(layoutClickListener);
    }

    @Override
    protected void initData() {
        LoginController.getInstance().bindDevice(MainTabActivity.this, UmengRegistrar.getRegistrationId(MainTabActivity.this));
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        float width=dm.widthPixels*dm.density;
        float height=dm.heightPixels*dm.density;
        Logger.e("手机型号: "+ android.os.Build.MODEL +",\n系统版本:" + android.os.Build.VERSION.RELEASE+"---"+width*height);
        SystemController.getInstance().DeviceInfo(MainTabActivity.this,android.os.Build.MODEL,android.os.Build.VERSION.RELEASE,width+"*"+height);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    View.OnClickListener layoutClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.main_tab_selected_layout:
                    toSelectedFragment();
                    break;
                case R.id.tab_menu_service_iv:
                    toServiceFragment();
                    break;
                case R.id.main_tab_wealth_layout:

                    if (UserPreference.isCustomer())
                        toCustomerMyFragment();
                    else
                        toPlannerMyFragment();

//                    CommonUtil.FlymeSetStatusBarLightMode(getWindow(), true);
//                    CommonUtil.MIUISetStatusBarLightMode(getWindow(), true);
//                    clearAllMenu();
//
//                    tabMenuWealthTextCN.setSelected(true);
//                    tabMenuWealthTextEN.setSelected(true);
//                    ServiceFragement1 serviceFragement1 = (ServiceFragement1) getSupportFragmentManager().findFragmentByTag(ServiceFragement1.class.getName());
//                    if (null == serviceFragement1) {
//                        serviceFragement1 = ServiceFragement1.newInstance();
//                    }
//                    switchContent(R.id.realtabcontent, curFragment, serviceFragement1,
//                            ServiceFragement1.class.getName());
                    break;
            }
        }
    };


    @Override
    public void onWindowAttributesChanged(WindowManager.LayoutParams params) {
        super.onWindowAttributesChanged(params);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Logger.e("MaintabActivity----onWindowFocusChanged");

    }

    private void toServiceFragment() {
        CommonUtil.FlymeSetStatusBarLightMode(getWindow(), true);
        CommonUtil.MIUISetStatusBarLightMode(getWindow(), true);
        clearAllMenu();
        tabMenuServiceIv.setSelected(true);
        serviceFragment = (ServiceFragment) getSupportFragmentManager()
                .findFragmentByTag(ServiceFragment.class.getName());
        if (null == serviceFragment) {
            serviceFragment = ServiceFragment.newInstance();
        }
        switchContent(R.id.realtabcontent, curFragment, serviceFragment,
                ServiceFragment.class.getName());
    }

    private void toPlannerMyFragment() {
        CommonUtil.FlymeSetStatusBarLightMode(getWindow(), true);
        CommonUtil.MIUISetStatusBarLightMode(getWindow(), true);
        clearAllMenu();

        tabMenuWealthTextCN.setSelected(true);
        tabMenuWealthTextEN.setSelected(true);
        plannerMyFragment = (PlannerMyFragment) getSupportFragmentManager()
                .findFragmentByTag(PlannerMyFragment.class.getName());
        if (null == plannerMyFragment) {
            plannerMyFragment = PlannerMyFragment.newInstance();
        }
        switchContent(R.id.realtabcontent, curFragment, plannerMyFragment,
                PlannerMyFragment.class.getName());
    }

    private void toCustomerMyFragment() {
        CommonUtil.FlymeSetStatusBarLightMode(getWindow(), true);
        CommonUtil.MIUISetStatusBarLightMode(getWindow(), true);
        clearAllMenu();

        tabMenuWealthTextCN.setSelected(true);
        tabMenuWealthTextEN.setSelected(true);
        customerMineFragment = (CustomerMineFragment) getSupportFragmentManager()
                .findFragmentByTag(CustomerMineFragment.class.getName());
        if (null == customerMineFragment) {
            customerMineFragment = CustomerMineFragment.newInstance();
        }
        switchContent(R.id.realtabcontent, curFragment, customerMineFragment,
                CustomerMineFragment.class.getName());
    }

    private void toSelectedFragment() {
        CommonUtil.FlymeSetStatusBarLightMode(getWindow(), false);
        CommonUtil.MIUISetStatusBarLightMode(getWindow(), false);
        clearAllMenu();
        tabMenuSelectedEN.setSelected(true);
        tabMenuSelectedTextCN.setSelected(true);
        selectedFragment = (SelectedFragment) getSupportFragmentManager()
                .findFragmentByTag(SelectedFragment.class.getName());
        if (null == selectedFragment) {
            selectedFragment = SelectedFragment.newInstance();
        }
        switchContent(R.id.realtabcontent, curFragment, selectedFragment,
                SelectedFragment.class.getName());
    }

    public void switchContent(int id, Fragment from, Fragment to, String tag) {
        if (from != to) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (!to.isAdded()) {    // 先判断是否被add过
                if (null == from) {
                    transaction.add(id, to, tag).commitAllowingStateLoss(); // 隐藏当前的fragment，add下一个到Activity中
                } else {
                    transaction.hide(from).add(id, to, tag).commitAllowingStateLoss(); // 隐藏当前的fragment，add下一个到Activity中
                }
            } else {
                if (null == from) {
                    transaction.show(to).commitAllowingStateLoss(); // 隐藏当前的fragment，显示下一个
                } else {
                    transaction.hide(from).show(to).commitAllowingStateLoss(); // 隐藏当前的fragment，显示下一个
                }
            }
            curFragment = to;
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        if (null != curFragment) {
            outState.putString("tag", curFragment.getTag());
            getSupportFragmentManager().putFragment(outState, curFragment.getTag(),
                    curFragment);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String tag = savedInstanceState.getString("tag");
        Fragment fragment = getSupportFragmentManager().getFragment(
                savedInstanceState, tag);
        curFragment = fragment;
        if (SelectedFragment.class.getName().equalsIgnoreCase(tag)) {
            toSelectedFragment();
        } else if (ServiceFragment.class.getName().equalsIgnoreCase(tag)) {
            toServiceFragment();
        } else if (CustomerMineFragment.class.getName().equalsIgnoreCase(tag)) {
            toCustomerMyFragment();
        } else if (PlannerMyFragment.class.getName().equalsIgnoreCase(tag)) {
            toPlannerMyFragment();
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        for (Fragment f : getSupportFragmentManager().getFragments()) {
            if (null == f) {
                continue;
            }
            if (!f.getTag().equalsIgnoreCase(tag)) {
                ft.hide(f);
            }
            /*else {
                ft.show(f);
			}*/
        }
        ft.commit();
    }

    /****
     * 清除按钮的选中状态
     */
    private void clearAllMenu() {
        tabMenuSelectedTextCN.setSelected(false);
        tabMenuServiceIv.setSelected(false);
        tabMenuWealthTextCN.setSelected(false);
        tabMenuSelectedEN.setSelected(false);
        tabMenuWealthTextEN.setSelected(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o) {

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ExitApp();
        }
        return false;
    }
    /****
     * 再按一次退出程序
     */
    private long exitTime = 0;

    public void ExitApp() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, "再按一次退出复华私行", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            this.finish();
        }
    }


}
