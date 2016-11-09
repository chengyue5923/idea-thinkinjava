package com.fhzc.app.android.android.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.base.framwork.net.lisener.ViewNetCallBack;
import com.base.platform.utils.android.Logger;
import com.fhzc.app.android.R;
import com.fhzc.app.android.android.ui.view.adapter.ReportListAdapter;
import com.fhzc.app.android.android.ui.view.widget.EmptyLayout;
import com.fhzc.app.android.configer.constants.Constant;
import com.fhzc.app.android.controller.EventManager;
import com.fhzc.app.android.controller.UserController;
import com.fhzc.app.android.event.CancelCollectEvent;
import com.fhzc.app.android.models.CollectMapModel;
import com.fhzc.app.android.models.ReportModel;
import com.fhzc.app.android.utils.android.IntentTools;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by User on 2016/7/28.
 */
public class MyCollectReportFragment extends Fragment implements AdapterView.OnItemClickListener,ViewNetCallBack {
    @Bind(R.id.myActivityList)
    ListView myActivityList;
    ReportListAdapter adapter;
    private List<ReportModel> mList = new ArrayList<>();
    EmptyLayout emptyLayout;
    SwipeRefreshLayout swipeLayout;
    public void setList(List<ReportModel> list) {
        this.mList = list;
        if (adapter!=null)
            initData();
//        emptyLayout.showSuccess(list.size() <= 0);
//        adapter.setRes(list);
    }

    public List<ReportModel> getmList() {
        return mList;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_activity, container, false);
        ButterKnife.bind(this, view);
        if (!EventManager.getInstance().isRegister(this)) {
            EventManager.getInstance().register(this);
        }
        emptyLayout = new EmptyLayout(getActivity(), myActivityList);
        adapter = new ReportListAdapter(getActivity());
        myActivityList.setAdapter(adapter);
        swipeLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe_containerddd);
        swipeLayout.setColorSchemeResources(R.color.color_bule2, R.color.color_bule, R.color.color_bule2, R.color.color_bule3);

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        swipeLayout.setRefreshing(false);
//                    }
//                }, 2000);
                intidatas();
            }
        });
        initData();
        return view;
    }

    public void intidatas(){
        UserController.getInstance().myCollection(MyCollectReportFragment.this);
    }
    private void initData() {
        myActivityList.setOnItemClickListener(this);
        emptyLayout.showSuccess(mList.size() <= 0);
        adapter.setRes(mList);
    }

    public void onEventMainThread(CancelCollectEvent event) {
        if (null != event && event.type.equals(Constant.COLLECTTYPE_REPORT)) {
            if (event.isCollect) {
                mList.add((ReportModel) event.serializable);
                adapter.append((ReportModel) event.serializable);
            } else {
                mList.remove(event.position);
                adapter.removeByPosition(event.position);
            }

            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        IntentTools.startReportDetail(getActivity(), mList.get(position).getId(), position);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        if (EventManager.getInstance().isRegister(this)) {
            EventManager.getInstance().unregister(this);
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
        swipeLayout.setRefreshing(false);
        Logger.e("ddd"+result);
        {
            CollectMapModel model = (CollectMapModel) result;
            setList(model.getReport());
        }
    }
}
