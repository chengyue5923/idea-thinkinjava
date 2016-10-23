package com.fhzc.app.android.android.ui.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fhzc.app.android.R;
import com.fhzc.app.android.android.base.BaseActivity;
import com.fhzc.app.android.android.ui.view.adapter.ActivityListAdapter;
import com.fhzc.app.android.android.ui.view.widget.CommonToolBar;
import com.fhzc.app.android.android.ui.view.widget.EmptyLayout;
import com.fhzc.app.android.controller.ActivityController;
import com.fhzc.app.android.controller.EventManager;
import com.fhzc.app.android.dao.MessageDao;
import com.fhzc.app.android.event.IMEvent;
import com.fhzc.app.android.models.ActivityModels;
import com.fhzc.app.android.utils.android.IntentTools;

import java.io.Serializable;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 活动列表
 * Created by yanbo on 2016/7/21.
 */
public class ActivityListActivity extends BaseActivity implements View.OnClickListener, CommonToolBar.ClickRightListener {
    @Bind(R.id.activityListToolBar)
    CommonToolBar activityListToolBar;
    @Bind(R.id.activityList)
    ListView activityList;
    private ActivityListAdapter adapter;
    private EmptyLayout mEmptyLayout;
    SwipeRefreshLayout swipeLayout;
    @Override
    protected void initView() {
        ButterKnife.bind(this);
        if (!EventManager.getInstance().isRegister(this)) {
            EventManager.getInstance().register(this);
        }
        mEmptyLayout = new EmptyLayout(this, activityList);
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
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

    }

    @Override
    protected void initEvent() {
        activityListToolBar.setClickRightListener(this);
    }

    public void onEventMainThread(IMEvent event) {
        if (null != event) {
            activityListToolBar.setRedImage(new MessageDao().getUnReadCount());

        }
    }

    @Override
    protected void initData() {
        activityListToolBar.setRedImage(new MessageDao().getUnReadCount());
        adapter = new ActivityListAdapter(this);
        activityList.setAdapter(adapter);
        ActivityController.getInstance().activityList(this);
        activityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                IntentTools.startNewActivityDetail(ActivityListActivity.this, adapter.getItem(i).getId());
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_activity_list;
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
        mEmptyLayout.showLoading();
    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o) {
        ActivityModels models = (ActivityModels) result;
        swipeLayout.setRefreshing(false);
        mEmptyLayout.showSuccess(models.getItems().size() <= 0);
        adapter.setRes(models.getItems());
    }

    @Override
    public void onFail(Exception e) {
        super.onFail(e);
        mEmptyLayout.showError();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void OnClickRight(View view) {
        IntentTools.startChatList(this);
    }
}
