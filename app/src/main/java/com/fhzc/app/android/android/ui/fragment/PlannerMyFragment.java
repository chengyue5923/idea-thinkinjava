package com.fhzc.app.android.android.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.fhzc.app.android.R;
import com.fhzc.app.android.android.ui.view.widget.CommonToolBar;
import com.fhzc.app.android.controller.EventManager;
import com.fhzc.app.android.dao.MessageDao;
import com.fhzc.app.android.event.IMEvent;
import com.fhzc.app.android.utils.android.IntentTools;
import com.fhzc.app.android.utils.im.CommonUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by yanbo on 2016/8/1.
 */
public class PlannerMyFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener, CommonToolBar.ClickRightListener {

    @Bind(R.id.plannerMyToolBar)
    CommonToolBar plannerMyToolBar;
    @Bind(R.id.plannerMyClientLayout)
    LinearLayout plannerMyClientLayout;
    @Bind(R.id.plannerMyWorkLayout)
    LinearLayout plannerMyWorkLayout;
    @Bind(R.id.plannerMyCollectLayout)
    LinearLayout plannerMyCollectLayout;
    @Bind(R.id.rootLayout)
    LinearLayout rootLayout;

    public static PlannerMyFragment newInstance() {
        PlannerMyFragment plannerMyFragment = new PlannerMyFragment();
        return plannerMyFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_planner_my, container, false);

        if (!EventManager.getInstance().isRegister(this)) {
            EventManager.getInstance().register(this);
        }
        ButterKnife.bind(this, view);
        initView();
        initData();
        initEvent();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void onEventMainThread(IMEvent event) {
        if (null != event) {
            plannerMyToolBar.setRedImage(new MessageDao().getUnReadCount());
        }
    }

    private void initData() {
        plannerMyToolBar.setRedImage(new MessageDao().getUnReadCount());
    }

    private void initView() {
        if (CommonUtil.isUpperKK()) {
            View view = new View(getActivity());
            view.setBackgroundColor(getResources().getColor(R.color.common_title));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, CommonUtil.getStatusBarHeight(getActivity()));
            view.setLayoutParams(layoutParams);
            rootLayout.addView(view, 0);
        }
    }

    private void initEvent() {
        plannerMyToolBar.setClickRightListener(this);
        plannerMyCollectLayout.setOnClickListener(this);
        plannerMyWorkLayout.setOnClickListener(this);
        plannerMyClientLayout.setOnClickListener(this);
//        myClientList.setOnItemClickListener(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        CommonUtil.FlymeSetStatusBarLightMode(getActivity().getWindow(), true);
        CommonUtil.MIUISetStatusBarLightMode(getActivity().getWindow(), true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.plannerMyCollectLayout:
                IntentTools.startCollectionActivity(getActivity(), "我的收藏");
                break;
            case R.id.plannerMyWorkLayout:
//                IntentTools.startMyWorkActivity(getActivity());
                IntentTools.startRecordSearchActivity(getActivity());
                break;
            case R.id.plannerMyClientLayout:
                IntentTools.startMyClientActivity(getActivity());
                break;
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        view.showContextMenu();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        if (EventManager.getInstance().isRegister(this)) {
            EventManager.getInstance().unregister(this);
        }
    }

    @Override
    public void OnClickRight(View view) {
        IntentTools.startChatList(getActivity());
    }
}
