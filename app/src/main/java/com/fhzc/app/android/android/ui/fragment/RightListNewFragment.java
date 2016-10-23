package com.fhzc.app.android.android.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.framwork.net.lisener.ViewNetCallBack;
import com.base.platform.utils.java.DateTools;
import com.fhzc.app.android.R;
import com.fhzc.app.android.controller.RightController;
import com.fhzc.app.android.db.UserPreference;
import com.fhzc.app.android.models.RightModel;
import com.fhzc.app.android.models.RightModels;
import com.fhzc.app.android.utils.android.IntentTools;
import com.fhzc.app.android.utils.android.Logger;
import com.fhzc.app.android.utils.im.ImageLoader;

import java.io.Serializable;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by yanbo on 2016/8/1.
 */
public class RightListNewFragment extends Fragment implements ViewNetCallBack,View.OnClickListener {


    @Bind(R.id.healthImageView)
    RelativeLayout healthImageView;
    @Bind(R.id.tourismImageView)
    RelativeLayout tourismImageView;
    @Bind(R.id.artImageView)
    RelativeLayout artImageView;
    @Bind(R.id.busTourismImageView)
    RelativeLayout busTourismImageView;
    @Bind(R.id.liveImageView)
    RelativeLayout liveImageView;
    @Bind(R.id.sportImageView)
    RelativeLayout sportImageView;
    @Bind(R.id.collegeImageView)
    RelativeLayout collegeImageView;
    @Bind(R.id.cubImageView)
    RelativeLayout cubImageView;
    @Bind(R.id.rightLayout)
    RelativeLayout rightLayout;
    @Bind(R.id.item_gallery)
    LinearLayout itemGallery;
    @Bind(R.id.rootLayout)
    LinearLayout rootLayout;
    @Bind(R.id.leavelandpointtext)
    TextView leavelandpointtext;
    @Bind(R.id.selfLayout)
    RelativeLayout selfLayout;
    @Bind(R.id.clubImage)
    ImageView clubImage;
    SwipeRefreshLayout swipeLayout;
    private LayoutInflater mInflater;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_right_list, container, false);
        ButterKnife.bind(this, view);
        mInflater = LayoutInflater.from(getActivity());
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        swipeLayout.setRefreshing(false);
//                    }
//                }, 5000);

                initData();
            }
        });
        swipeLayout.setColorSchemeResources(R.color.color_bule2, R.color.color_bule, R.color.color_bule2, R.color.color_bule3);

        initData();
        initEvent();
        return view;
    }

    private void initEvent() {
        healthImageView.setOnClickListener(this);
        tourismImageView.setOnClickListener(this);
        artImageView.setOnClickListener(this);
        busTourismImageView.setOnClickListener(this);
        liveImageView.setOnClickListener(this);
        sportImageView.setOnClickListener(this);
        collegeImageView.setOnClickListener(this);
        cubImageView.setOnClickListener(this);
    }

    private void initData() {
        RightController.getInstance().selectRight(this);
        leavelandpointtext.setText(UserPreference.getLevel());
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onConnectStart() {

    }

    @Override
    public void onConnectEnd() {

    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o) {
        swipeLayout.setRefreshing(false);
        final List<RightModel> list = ((RightModels) result).getItems();
        for (int i = 0; i < list.size(); i++) {
            final RightModel model = list.get(i);
            Logger.e("dddd11" + model.getCover());
            View view = mInflater.inflate(R.layout.view_my_order_right_item,
                    itemGallery, false);
            view.setTag(i);
            ImageView img = (ImageView) view
                    .findViewById(R.id.order_right_image);
            TextView time = (TextView) view
                    .findViewById(R.id.order_right_time);
            ImageView levelImage = (ImageView) view.findViewById(R.id.levelImageas);
            if (model.getLevel() + "" != null) {
                Logger.e("level" + model.getLevel());
                switch (model.getLevel() - 1) {
                    case 0:
                        levelImage.setBackgroundResource(R.drawable.clientlist_investor);
                        break;
                    case 1:
                        levelImage.setBackgroundResource(R.drawable.clientlist_associate);
                        break;
                    case 2:
                        levelImage.setBackgroundResource(R.drawable.clientlist_silvervip);
                        break;
                    case 3:
                        levelImage.setBackgroundResource(R.drawable.clientlist_goldvip);
                        break;
                    case 4:
                        levelImage.setBackgroundResource(R.drawable.clientlist_balckvip);
                        break;
                }
            }
            ImageLoader.getInstance(getActivity(), R.drawable.default_error).displayImage(model.getCover(), img);
            time.setText(DateTools.formatDateWithSecondSince1970(DateTools.DATE_FORMAT_STYLE_5, model.getCtime()));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    IntentTools.startNewRightDetail(getActivity(), model.getId());
                }
            });
            itemGallery.addView(view);
        }
    }

    @Override
    public void onFail(Exception e) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.healthImageView:
                IntentTools.startRightList(getActivity(), "健康管理", 1);
                break;
            case R.id.tourismImageView:
                IntentTools.startRightList(getActivity(), "旅行管家", 2);
                break;
            case R.id.artImageView:
                IntentTools.startRightList(getActivity(), "艺术管家", 3);
                break;
            case R.id.busTourismImageView:
                IntentTools.startRightList(getActivity(), "商旅通", 4);
                break;
            case R.id.liveImageView:
                IntentTools.startRightList(getActivity(), "奢生活", 5);
                break;
            case R.id.sportImageView:
                IntentTools.startRightList(getActivity(), "爱体育", 6);
                break;
            case R.id.collegeImageView:
                IntentTools.startRightList(getActivity(), "商学院", 7);
                break;
            case R.id.cubImageView:
                IntentTools.startRightList(getActivity(), "俱乐部", 8);
                break;
        }

    }
}
