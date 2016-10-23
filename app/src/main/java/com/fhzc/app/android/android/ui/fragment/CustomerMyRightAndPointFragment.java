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
import com.fhzc.app.android.R;
import com.fhzc.app.android.configer.enums.CustomerType;
import com.fhzc.app.android.controller.UserController;
import com.fhzc.app.android.db.UserPreference;
import com.fhzc.app.android.models.PointModel;
import com.fhzc.app.android.utils.android.IntentTools;

import java.io.Serializable;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by User on 2016/7/21.
 */
public class CustomerMyRightAndPointFragment extends Fragment implements View.OnClickListener, ViewNetCallBack {


    @Bind(R.id.totalPoint)
    TextView totalPoint;
    @Bind(R.id.availablePoint)
    TextView availablePoint;
    @Bind(R.id.frozenPoint)
    TextView frozenPoint;
    @Bind(R.id.willExpiredPoint)
    TextView willExpiredPoint;
    @Bind(R.id.pointDetailLayout)
    LinearLayout pointDetailLayout;
    @Bind(R.id.levelImage)
    ImageView levelImage;
    @Bind(R.id.orderRightLayout)
    LinearLayout orderRightLayout;
    @Bind(R.id.pointRecordLayout)
    LinearLayout pointRecordLayout;
    @Bind(R.id.sr_randp_rootlayout)
    SwipeRefreshLayout swipeRefreshLayout;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_right_point, container, false);
        ButterKnife.bind(this, view);
        swipeRefreshLayout.setColorSchemeResources(R.color.appColorBlue);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                UserController.getInstance().personalScore(CustomerMyRightAndPointFragment.this);
            }
        });
        orderRightLayout.setOnClickListener(this);
        pointRecordLayout.setOnClickListener(this);
        levelImage.setImageResource(CustomerType.getCustomerByType(UserPreference.getLevel()).getPointBg());
        UserController.getInstance().personalScore(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.orderRightLayout:
                IntentTools.startOrderRight(getActivity(),UserPreference.getRoleId());
                break;
            case R.id.pointRecordLayout:
                IntentTools.startPointRecord(getActivity());
                break;
        }
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
        swipeRefreshLayout.setRefreshing(false);
        PointModel model = (PointModel) result;
        totalPoint.setText(String.valueOf(model.getYours()));
        willExpiredPoint.setText(String.valueOf(model.getWillExpired()));
        frozenPoint.setText(String.valueOf(model.getFrozen()));
        availablePoint.setText(String.valueOf(model.getAvailable()));
    }
}
