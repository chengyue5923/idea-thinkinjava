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
import com.fhzc.app.android.android.ui.view.adapter.CollectProductAdapter;
import com.fhzc.app.android.android.ui.view.widget.EmptyLayout;
import com.fhzc.app.android.configer.constants.Constant;
import com.fhzc.app.android.controller.EventManager;
import com.fhzc.app.android.controller.UserController;
import com.fhzc.app.android.event.CancelCollectEvent;
import com.fhzc.app.android.models.CollectMapModel;
import com.fhzc.app.android.models.CollectProductModel;
import com.fhzc.app.android.utils.android.IntentTools;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by User on 2016/7/28.
 */
public class MyCollectProductFragment extends Fragment implements AdapterView.OnItemClickListener ,ViewNetCallBack {
    @Bind(R.id.myActivityList)
    ListView myActivityList;
    CollectProductAdapter adapter;
    private List<CollectProductModel> list = new ArrayList<>();
    EmptyLayout emptyLayout;
    SwipeRefreshLayout swipeLayout;
    public void setList(List<CollectProductModel> list) {
        this.list = list;
        if (adapter!=null){
            emptyLayout.showSuccess(list.size() <= 0);
            adapter.setRes(list);
        }
    }

        public List<CollectProductModel> getList() {
            return list;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_my_activity, container, false);
            ButterKnife.bind(this, view);
            if (!EventManager.getInstance().isRegister(this)) {
                EventManager.getInstance().register(this);
            }
            swipeLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe_containerddd);
        emptyLayout = new EmptyLayout(getActivity(), myActivityList);
        adapter = new CollectProductAdapter(getActivity());
        myActivityList.setAdapter(adapter);
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
            swipeLayout.setColorSchemeResources(R.color.color_bule2, R.color.color_bule, R.color.color_bule2, R.color.color_bule3);

            initData();
        return view;
    }
    public void intidatas(){
        UserController.getInstance().myCollection(MyCollectProductFragment.this);
    }
    private void initData() {
        myActivityList.setOnItemClickListener(this);
        emptyLayout.showSuccess(list.size() <= 0);
        adapter.setRes(list);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        IntentTools.startProduceDetail(getActivity(), list.get(position).getId(), position);
    }
    public void onEventMainThread(CancelCollectEvent event) {
        if (null != event && event.type.equals(Constant.COLLECTTYPE_PRODUCT)) {
            if (event.isCollect) {
                adapter.append((CollectProductModel) event.serializable);
                list.add((CollectProductModel) event.serializable);
            } else {
                adapter.removeByPosition(event.position);
                list.remove(event.position);
            }
            adapter.notifyDataSetChanged();
        }
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
            setList(model.getProduct());
        }
    }
}
