package com.fhzc.app.android.android.ui.activity.fuhuaservice;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fhzc.app.android.R;
import com.fhzc.app.android.android.base.BaseActivity;
import com.fhzc.app.android.android.ui.view.adapter.ProductListAdapter;
import com.fhzc.app.android.android.ui.view.widget.CommonToolBar;
import com.fhzc.app.android.android.ui.view.widget.EmptyLayout;
import com.fhzc.app.android.controller.EventManager;
import com.fhzc.app.android.controller.ProductController;
import com.fhzc.app.android.dao.MessageDao;
import com.fhzc.app.android.event.IMEvent;
import com.fhzc.app.android.models.ProductModel;
import com.fhzc.app.android.models.ProductModels;
import com.fhzc.app.android.utils.android.IntentTools;

import java.io.Serializable;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 私行专属理财
 * Created by lenovo on 2016/7/11.
 */
public class FhExclusiveFinancialActivity extends BaseActivity implements View.OnClickListener, CommonToolBar.ClickRightListener {

    @Bind(R.id.financialListView)
    ListView financialListView;

    ProductListAdapter financialAdapter;
    EmptyLayout emptyLayout;
    @Bind(R.id.rightDetailToolbar)
    CommonToolBar rightDetailToolbar;
    SwipeRefreshLayout swipeLayout;
    @Override
    protected void initView() {
        ButterKnife.bind(this);
        if (!EventManager.getInstance().isRegister(this)) {
            EventManager.getInstance().register(this);
        }
        emptyLayout = new EmptyLayout(this, financialListView);
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

                setData();
            }
        });
        swipeLayout.setColorSchemeResources(R.color.color_bule2, R.color.color_bule, R.color.color_bule2, R.color.color_bule3);
    }

    @Override
    protected void initEvent() {
        financialListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                IntentTools.startProduceDetail(FhExclusiveFinancialActivity.this, financialAdapter.getItem(i).getPid());
            }
        });
        rightDetailToolbar.setClickRightListener(this);
    }
    public void onEventMainThread(IMEvent event) {
        if (null != event) {
            rightDetailToolbar.setRedImage(new MessageDao().getUnReadCount());

        }
    }
    @Override
    protected void initData() {
        rightDetailToolbar.setRedImage(new MessageDao().getUnReadCount());
        financialAdapter = new ProductListAdapter(this);
        financialListView.setAdapter(financialAdapter);

        setData();  //测试布局页面
    }

    public void setData() {

        ProductController.getInstance().selectProduct(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_financial_management;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }

    @Override
    public void onConnectStart() {
        super.onConnectStart();
        emptyLayout.showLoading();
    }

    @Override
    public void onFail(Exception e) {
        super.onFail(e);
        emptyLayout.showError();
    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o) {
        List<ProductModel> list = ((ProductModels) result).getItems();
        swipeLayout.setRefreshing(false);
        emptyLayout.showSuccess(list.size() <= 0);
        financialAdapter.setRes(list);
    }

    @Override
    public void OnClickRight(View view) {
        IntentTools.startChatList(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventManager.getInstance().isRegister(this)) {
            EventManager.getInstance().unregister(this);
        }
    }
}
