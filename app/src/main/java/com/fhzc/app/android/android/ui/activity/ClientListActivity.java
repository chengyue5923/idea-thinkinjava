package com.fhzc.app.android.android.ui.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fhzc.app.android.R;
import com.fhzc.app.android.android.base.BaseActivity;
import com.fhzc.app.android.android.ui.view.adapter.ClientListAdapter;
import com.fhzc.app.android.controller.UserController;
import com.fhzc.app.android.models.MemberModel;
import com.fhzc.app.android.utils.IntentTool;
import com.fhzc.app.android.utils.android.IntentTools;

import java.io.Serializable;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by yanbo on 2016/8/2.
 */
public class ClientListActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    @Bind(R.id.myClientList)
    ListView myClientList;
    ClientListAdapter clientListAdapter;
    SwipeRefreshLayout swipeLayout;
    @Override
    protected void initView() {
        ButterKnife.bind(this);
//        registerForContextMenu(myClientList);
        swipeLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_container);
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
        myClientList.setOnItemClickListener(this);
    }

    @Override
    protected void initData() {
        clientListAdapter = new ClientListAdapter(this);
        myClientList.setAdapter(clientListAdapter);
        UserController.getInstance().customerList(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_client_list;
    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o) {
        List<MemberModel> list = (List<MemberModel>) result;
        swipeLayout.setRefreshing(false);
        clientListAdapter.setRes(list);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        IntentTools.startCustomerDetail(ClientListActivity.this, clientListAdapter.getItem(position));

    }

//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        super.onCreateContextMenu(menu, v, menuInfo);
//        getMenuInflater().inflate(R.menu.planner_context_client_list, menu);
//    }
//
//    @Override
//    public boolean onContextItemSelected(MenuItem item) {
//        int position = ((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position;
//        MemberModel memberModel = clientListAdapter.getItem(position);
//        if (item.getItemId() == R.id.contact_customer) {
//            IntentTool.chat(ClientListActivity.this, null, memberModel.getUid());
//            return true;
//        } else if (item.getItemId() == R.id.wealth_right) {
//            IntentTools.startCustomerDetail(ClientListActivity.this,memberModel);
//            return true;
//        }
//        return super.onContextItemSelected(item);
//    }

}
