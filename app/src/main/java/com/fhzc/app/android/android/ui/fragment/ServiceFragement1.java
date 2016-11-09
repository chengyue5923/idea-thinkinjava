package com.fhzc.app.android.android.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.base.framwork.net.lisener.ViewNetCallBack;
import com.base.platform.utils.android.Logger;
import com.fhzc.app.android.R;
import com.fhzc.app.android.android.ui.view.adapter.CommodityDetailPagerAdapter;
import com.fhzc.app.android.android.ui.view.widget.AutoScrollViewPager;
import com.fhzc.app.android.android.ui.view.widget.CirclePageIndicator;
import com.fhzc.app.android.android.ui.view.widget.CommonToolBar;
import com.fhzc.app.android.android.ui.view.widget.EmptyLayout;
import com.fhzc.app.android.android.ui.view.widget.RoundRectBackImageView;
import com.fhzc.app.android.android.ui.view.widget.RoundRectBackRightImageView;
import com.fhzc.app.android.android.ui.view.widget.ScrollViewExtend;
import com.fhzc.app.android.controller.EventManager;
import com.fhzc.app.android.controller.LoginController;
import com.fhzc.app.android.dao.MessageDao;
import com.fhzc.app.android.event.IMEvent;
import com.fhzc.app.android.models.BannerModel;
import com.fhzc.app.android.models.ProductModel;
import com.fhzc.app.android.models.ReportModel;
import com.fhzc.app.android.models.RightModel;
import com.fhzc.app.android.utils.im.CommonUtil;

import java.io.Serializable;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by apple on 16/9/7.
 */

public class ServiceFragement1 extends Fragment implements View.OnClickListener, CommonToolBar.ClickRightListener, ViewNetCallBack, CommodityDetailPagerAdapter.ClickListener  {
    @Bind(R.id.title)
    CommonToolBar title;


    @Bind(R.id.scrollview)
    ScrollViewExtend scrollViewExtend;
    @Bind(R.id.rootLayout)
    LinearLayout rootLayout;
    @Bind(R.id.srLayout)
    SwipeRefreshLayout swipeRefreshrLayout;

    View vessel;
    private LinearLayout mGallery;
    private RightModel rightModel;
    private ReportModel reportModel;
    private ProductModel productModel;
    private LayoutInflater mInflater;
    private EmptyLayout emptyLayout;


    public static ServiceFragement1 newInstance() {
        ServiceFragement1 squareFragmentV2 = new ServiceFragement1();
        return squareFragmentV2;
    }

    public void onEventMainThread(IMEvent event) {
        if (null != event) {
            title.setRedImage(new MessageDao().getUnReadCount());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //透明状态栏
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        vessel = inflater.inflate(R.layout.fragment_service, null);
        ButterKnife.bind(this, vessel);
        if (!EventManager.getInstance().isRegister(this)) {
            EventManager.getInstance().register(this);
        }
        mInflater = LayoutInflater.from(getActivity());
//        mGallery = (LinearLayout) vessel.findViewById(R.id.id_gallery);
        swipeRefreshrLayout.setColorSchemeResources(R.color.appColorBlue);
        swipeRefreshrLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
        initData();
        initView();

        return vessel;
    }

    public void initData() {
        title.setRedImage(new MessageDao().getUnReadCount());
        LoginController.getInstance().myBank(this);
//        mGallery.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Logger.e("glary" + view.getId() + "Tag" + view.getTag());
////                mGallery.getChildAt(Integer.parseInt(view.getTag().toString()));
//            }
//        });
        title.setClickRightListener(this);
    }

    public void initView() {

        if (CommonUtil.isUpperKK()) {
            View view = new View(getActivity());
            view.setBackgroundColor(getResources().getColor(R.color.common_title));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, CommonUtil.getStatusBarHeight(getActivity()));
            view.setLayoutParams(layoutParams);
            rootLayout.addView(view, 0);
        }

//        emptyLayout = new EmptyLayout(getActivity(), dataRootLayout);
//        foundationLayout.setOnClickListener(this);
//        rightLayout.setOnClickListener(this);
//        activityLayout.setOnClickListener(this);
//        reportLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onConnectStart() {

    }

    @Override
    public void onConnectEnd() {

    }

    @Override
    public void onFail(Exception e) {

    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o) {

    }

    @Override
    public void click(BannerModel model) {

    }

    @Override
    public void OnClickRight(View view) {

    }
}
