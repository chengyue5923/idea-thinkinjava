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
import android.widget.TextView;

import com.base.framwork.net.lisener.ViewNetCallBack;
import com.base.platform.utils.java.DateTools;
import com.fhzc.app.android.R;
import com.fhzc.app.android.android.ui.view.widget.EmptyLayout;
import com.fhzc.app.android.controller.ActivityController;
import com.fhzc.app.android.models.ActivityModel;
import com.fhzc.app.android.models.ActivityModels;
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
public class RightActivityListNewFragment extends Fragment implements ViewNetCallBack {


    @Bind(R.id.rightActivityImage)
    View rightActivityImage;
    @Bind(R.id.item_gallery)
    LinearLayout itemGallery;
    @Bind(R.id.rootLayout)
    LinearLayout rootLayout;
    SwipeRefreshLayout swipeLayout;
    private LayoutInflater mInflater;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_right_activity_list, container, false);
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
    }

    private void initData() {
        ActivityController.getInstance().activityList(this);
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
        ActivityModels models = (ActivityModels) result;
        swipeLayout.setRefreshing(false);
        for (int i = 0; i < models.getItems().size(); i++) {
            final ActivityModel model =models.getItems().get(i);
            Logger.e("dddd11" + model.getCover());
            View view = mInflater.inflate(R.layout.view_activity_new_item,
                    itemGallery, false);
            view.setTag(i);
            ImageView backImg = (ImageView) view
                    .findViewById(R.id.activity_item_image);
            ImageView statuImg = (ImageView) view
                    .findViewById(R.id.activity_item_status);
            TextView textViewtime=(TextView)view.findViewById(R.id.activity_item_time);
            TextView textViewaddress=(TextView)view.findViewById(R.id.activity_item_address);
            if (!String.valueOf(model.getStatus()).equals("")) {
                if(model.getStatus()==0){
//                ("查看详情");
                    statuImg.setImageResource(R.drawable.activity_notstrart);
                }else if(model.getStatus()==1){
//                 ("立即参加");
                    statuImg.setImageResource(R.drawable.activity_underway);
                }else if(model.getStatus()==2){
//                   ("报名结束");
                    statuImg.setImageResource(R.drawable.complete);

                }else if(model.getStatus()==3){
//                   ("活动结束");
                    statuImg.setImageResource(R.drawable.complete);
                }
            }
            ImageLoader.getInstance(getActivity(), R.drawable.default_error).displayImage(model.getCover(), backImg);
            textViewtime.setText("报名截止日期："+DateTools.formatDateWithSecondSince1970(DateTools.DATE_FORMAT_STYLE_5, model.getApplyEndTime()));
            textViewaddress.setText("活动地点："+model.getAddress());
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
}
