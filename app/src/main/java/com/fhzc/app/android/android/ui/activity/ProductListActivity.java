package com.fhzc.app.android.android.ui.activity;


import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fhzc.app.android.R;
import com.fhzc.app.android.android.base.BaseActivity;
import com.fhzc.app.android.android.ui.view.adapter.ProductListAdapter;
import com.fhzc.app.android.android.ui.view.widget.CommonToolBar;
import com.fhzc.app.android.android.ui.view.widget.EmptyLayout;
import com.fhzc.app.android.android.ui.view.widget.NoScrollListView;
import com.fhzc.app.android.android.ui.view.widget.RefreshLayout;
import com.fhzc.app.android.android.ui.view.widget.ScrollViewExtend;
import com.fhzc.app.android.configer.enums.HttpConfig;
import com.fhzc.app.android.controller.ProductController;
import com.fhzc.app.android.dao.MessageDao;
import com.fhzc.app.android.db.UserPreference;
import com.fhzc.app.android.event.IMEvent;
import com.fhzc.app.android.models.ProductModel;
import com.fhzc.app.android.models.ProductModels;
import com.fhzc.app.android.utils.android.IntentTools;
import com.fhzc.app.android.utils.android.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by User on 2016/7/28.
 */
public class ProductListActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener,CommonToolBar.ClickRightListener {
    @Bind(R.id.rightseDetailToolbar)
    CommonToolBar rightDetailToolbar;
    @Bind(R.id.myOrderProductLay)
    LinearLayout myOrderProductLay;
    @Bind(R.id.oldProductLv)
    NoScrollListView oldProductLv;
    @Bind(R.id.oldProductLay)
    LinearLayout oldProductLay;
    @Bind(R.id.hotProductLv)
    NoScrollListView hotProductLv;
    @Bind(R.id.hotProductLay)
    LinearLayout hotProductLay;
    @Bind(R.id.myOrderCount)
    TextView myOrderCount;
    List<ProductModel> hotList, oldList;
    @Bind(R.id.mScrollView)
    ScrollViewExtend mScrollView;
    private ProductListAdapter oldAdapter;
    private ProductListAdapter hotAdapter;
    private EmptyLayout emptyLayout;
    SwipeRefreshLayout swipeLayout;
    int page=1;
    @Override
    protected void initView() {
        ButterKnife.bind(this);
        emptyLayout = new EmptyLayout(this, mScrollView);
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
        rightDetailToolbar.setClickRightListener(this);
        myOrderProductLay.setOnClickListener(this);
        oldProductLv.setOnItemClickListener(this);
        hotProductLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IntentTools.startProduceDetail(ProductListActivity.this, hotList.get(position).getPid());
            }
        });

    }

    @Override
    protected void initData() {
        rightDetailToolbar.setRedImage(new MessageDao().getUnReadCount());
        if (UserPreference.isCustomer()) {
            myOrderProductLay.setVisibility(View.VISIBLE);
            ProductController.getInstance().myOrderProduct(this, UserPreference.getRoleId());
        }
        ProductController.getInstance().productList(this,page);
        oldAdapter = new ProductListAdapter(this);
        hotAdapter = new ProductListAdapter(this);
        oldProductLv.setAdapter(oldAdapter);
        hotProductLv.setAdapter(hotAdapter);
        hotProductLv.setFocusable(false);
        oldProductLv.setFocusable(false);
        oldProductLv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_product_list;
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
        swipeLayout.setRefreshing(false);
        if (flag == HttpConfig.myOrderProduct.getType()) {
            List<ProductModel> list = (List<ProductModel>) result;
            myOrderCount.setText(list.size() + "件产品");
        }
        if (flag == HttpConfig.productList.getType()) {
            List<ProductModel> list = ((ProductModels) result).getItems();
            oldList = new ArrayList<>();
            hotList = new ArrayList<>();
            for (ProductModel model : list) {
                if (model.getStatus() == 1 || model.getStatus() == 2) {
                    hotList.add(model);
                } else if (model.getStatus() != 3) {
                    oldList.add(model);
                }

            }
            emptyLayout.showSuccess(list.size() <= 0);
            hotAdapter.setRes(hotList);
            oldAdapter.setRes(oldList);
        }
    }

    @Override
    public void onClick(View v) {
        IntentTools.startServationActivity(ProductListActivity.this, UserPreference.getRoleId());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        IntentTools.startProduceDetail(ProductListActivity.this, oldList.get(position).getPid());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
    public void onEventMainThread(IMEvent event) {
        if (null != event) {
            rightDetailToolbar.setRedImage(new MessageDao().getUnReadCount());

        }
    }

    @Override
    public void OnClickRight(View view) {
        IntentTools.startChatList(this);
    }
}
