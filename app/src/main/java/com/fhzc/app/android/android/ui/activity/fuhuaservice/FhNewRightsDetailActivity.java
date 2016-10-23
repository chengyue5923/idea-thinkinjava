package com.fhzc.app.android.android.ui.activity.fuhuaservice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.base.framwork.net.lisener.ViewNetCallBack;
import com.base.platform.utils.android.ToastTool;
import com.fhzc.app.android.R;
import com.fhzc.app.android.android.base.BaseActivity;
import com.fhzc.app.android.android.ui.view.dialog.RightsCancelPopUpDialog;
import com.fhzc.app.android.android.ui.view.widget.CommonToolBar;
import com.fhzc.app.android.android.ui.view.widget.EmptyLayout;
import com.fhzc.app.android.configer.constants.Constant;
import com.fhzc.app.android.configer.enums.HttpConfig;
import com.fhzc.app.android.controller.EventManager;
import com.fhzc.app.android.controller.RightController;
import com.fhzc.app.android.db.UserPreference;
import com.fhzc.app.android.event.CancelCollectEvent;
import com.fhzc.app.android.models.RightModel;
import com.fhzc.app.android.utils.IntentTool;
import com.fhzc.app.android.utils.android.IntentTools;
import com.fhzc.app.android.utils.android.Logger;
import com.fhzc.app.android.utils.im.ImageLoader;

import org.json.JSONObject;

import java.io.Serializable;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 私行权益详情
 * Created by lenovo on 2016/7/11.
 */
public class FhNewRightsDetailActivity extends BaseActivity implements View.OnClickListener, CommonToolBar.ClickRightListener {


    @Bind(R.id.rightDetailToolbar)
    CommonToolBar rightDetailToolbar;
    @Bind(R.id.reportImageView)
    ImageView reportImageView;
    @Bind(R.id.timeTextView)
    TextView timeTextView;
    @Bind(R.id.rightDetailName)
    TextView rightDetailName;
    @Bind(R.id.leavelTextView)
    TextView leavelTextView;
    @Bind(R.id.levealline)
    RelativeLayout levealline;
    @Bind(R.id.rightDetailPoint)
    TextView rightDetailPoint;
    @Bind(R.id.rightDetailMechanism)
    TextView rightDetailMechanism;
    @Bind(R.id.rightDetail)
    TextView rightDetail;
    @Bind(R.id.ScrollViewLayout)
    ScrollView ScrollViewLayout;
    @Bind(R.id.rightDetailShare)
    TextView rightDetailShare;
    @Bind(R.id.rightDetailPlannerLay)
    RelativeLayout rightDetailPlannerLay;
    @Bind(R.id.signUpForOtherActivityTextView)
    TextView signUpForOtherActivityTextView;
    @Bind(R.id.signUpActivityTextView)
    TextView signUpActivityTextView;
    @Bind(R.id.singUpRightLayout)
    LinearLayout singUpRightLayout;
    @Bind(R.id.rootLayout)
    LinearLayout rootLayout;
    private int position;
    private RightModel  Model;
    private EmptyLayout emptyLayout;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private static final int flag = 0;
    private int rightId;

    //    private int status;
    @Override
    protected void initView() {
        ButterKnife.bind(this);
        emptyLayout = new EmptyLayout(this, rootLayout);
//        if (UserPreference.isCustomer()) {
//            rightDetailPlannerLay.setVisibility(View.GONE);
//            rightDetailCustomerLay.setVisibility(View.VISIBLE);
//            rightCancelLay.setVisibility(View.VISIBLE);
//        } else {
//            rightDetailPlannerLay.setVisibility(View.VISIBLE);
//            rightDetailCustomerLay.setVisibility(View.GONE);
//            rightCancelLay.setVisibility(View.GONE);
//        }
    }

    @Override
    protected void initEvent() {
        signUpActivityTextView.setOnClickListener(this);
        signUpForOtherActivityTextView.setOnClickListener(this);
        rightDetailShare.setOnClickListener(this);
        rightDetailToolbar.setClickRightListener(this);
    }

    @Override
    protected void initData() {
        //没有数据
        rightId = getIntent().getIntExtra("rightId", -1);
        position = getIntent().getIntExtra("position", -1);
//        status = getIntent().getIntExtra("status", MyOrderRightType.ORDER_RIGHT_TYPE_ORDER.getType());
        if (rightId == -1)
            return;
        RightController.getInstance().rightDetail(new ViewNetCallBack() {
            @Override
            public void onConnectStart() {
                emptyLayout.showLoading();
            }

            @Override
            public void onConnectEnd() {

            }

            @Override
            public void onFail(Exception e) {
                emptyLayout.showError();
            }

            @Override
            public void onData(Serializable result, int flag, boolean fromNet, Object o) {
                emptyLayout.showSuccess(false);
                Model = (RightModel) result;
                timeTextView.setVisibility(View.GONE);
//                timeTextView.setText("");
                rightDetailName.setText(Model.getName());
                leavelTextView.setText(Model.getLevelNeed()+"以上专享");
                rightDetailPoint.setText(String.valueOf(Model.getSpendScore()));
                rightDetailMechanism.setText(Model.getSupply());
                rightDetail.setText(Model.getSummary());
                Logger.e("message" + Model.getCover());
                ImageLoader.getInstance(FhNewRightsDetailActivity.this, R.drawable.default_error_long).displayImage(Model.getCover(), reportImageView);

                if (UserPreference.isCustomer()) {
                    rightDetailPlannerLay.setVisibility(View.GONE);
                    if (Model.getReservationStatus() != null && Model.getReservationStatus() != null) {
//                        if (rightModel.getReservationStatus().equals("") || rightModel.getReservationStatus().equals("3") || rightModel.getReservationStatus().equals("2")) {
//                            rightCancelLay.setVisibility(View.GONE);
//                            rightDetailCustomerLay.setVisibility(View.VISIBLE);
//                        } else if (rightModel.getReservationStatus().equals("0") || rightModel.getReservationStatus().equals("1")) {
//                            rightCancelLay.setVisibility(View.VISIBLE);
//                            rightDetailCustomerLay.setVisibility(View.GONE);
//                        }
                    } else {
                        Logger.e("rightModel.getReservationStatus()=null");
                    }
                } else {
                    rightDetailPlannerLay.setVisibility(View.VISIBLE);
//                    rightDetailCustomerLay.setVisibility(View.GONE);
//                    rightCancelLay.setVisibility(View.GONE);
                }
            }
        }, rightId);
        RightController.getInstance().focusStatus(this, rightId);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onConnectStart() {
    }

    @Override
    public void onFail(Exception e) {
        super.onFail(e);
    }

    @Override
    public void onConnectEnd() {
        super.onConnectEnd();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.right_detail_activity_new;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rightDetailExchange:
                //// TODO: 2016/7/26 兑换权益

                Logger.e("eeeeqq" + UserPreference.getLevel() + Model.getLevel());

//                if(String.valueOf(rightModel.getLevel())!=null){
//                    if(rightModel.getLevel()==0){
//                        condentionText.setText("投资人");
//                    }else if(rightModel.getLevel()==1){
//                        condentionText.setText("投资人");
//                    }else if(rightModel.getLevel()==2){
//                        condentionText.setText("准会员");
//                    }else if(rightModel.getLevel()==3){
//                        condentionText.setText("银卡");
//                    }else if(rightModel.getLevel()==4){
//                        condentionText.setText("金卡");
//                    }else if(rightModel.getLevel()==5){
//                        condentionText.setText("黑金卡");
//                    }
//                }
//
                if (UserPreference.getLevel() != null && UserPreference.getLevel().length() > 1) {
                    if (UserPreference.getLevel().equals("金卡")) {
                        if (Model.getLevel() > 4) {
                            ToastTool.show("您不能预约黑金卡权益");
                            break;
                        }
                    } else if (UserPreference.getLevel().equals("银卡")) {
                        if (Model.getLevel() > 3) {
                            ToastTool.show("您不能预约金卡及以上权益");
                            break;
                        }
                    } else if (UserPreference.getLevel().equals("准会员")) {
                        ToastTool.show("您不能预约银卡及以上权益");
                        break;
                    } else if (UserPreference.getLevel().equals("投资人")) {
                        ToastTool.show("您不能预约银卡及以上权益");
                        break;
                    }
                }


                IntentTools.startsureDetailActivity(this, Model, flag);
                break;
            case R.id.rightDetailShare:
                IntentTools.startClientList(FhNewRightsDetailActivity.this, "rights", rightId);
                break;
            //取消预约
            case R.id.rightcancelShare:
                ReservaSuccessPopUpDialog();
                break;
            case R.id.contextTexttd:
                //// TODO: 2016/7/31 联系理财师
                if (UserPreference.getPlannerId() == 0) {
                    ToastTool.show("您没有理财师");
                    return;
                }
                IntentTool.chat(FhNewRightsDetailActivity.this, null, UserPreference.getPlannerUid());
                break;
            case R.id.signUpForOtherActivityTextView:
                if (UserPreference.getPlannerUid() == 0) {
                    ToastTool.show("您没有理财师");
                    return;
                }
                IntentTool.chat(FhNewRightsDetailActivity.this, null, UserPreference.getPlannerUid());
                break;
            case R.id.signUpActivityTextView:

                if (UserPreference.getLevel() != null && UserPreference.getLevel().length() > 1) {
                    if (UserPreference.getLevel().equals("金卡")) {
                        if (Model.getLevel() > 4) {
                            ToastTool.show("您不能预约黑金卡权益");
                            break;
                        }
                    } else if (UserPreference.getLevel().equals("银卡")) {
                        if (Model.getLevel() > 3) {
                            ToastTool.show("您不能预约金卡及以上权益");
                            break;
                        }
                    } else if (UserPreference.getLevel().equals("准会员")) {
                        ToastTool.show("您不能预约银卡及以上权益");
                        break;
                    } else if (UserPreference.getLevel().equals("投资人")) {
                        ToastTool.show("您不能预约银卡及以上权益");
                        break;
                    }
                }
                IntentTools.startsureDetailActivity(this, Model, flag);
                break;
        }
    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o) {

        if (flag == HttpConfig.focus.getType()) {
            if (rightDetailToolbar.isCollect()) {
                rightDetailToolbar.setCollect(false);
                EventManager.getInstance().post(new CancelCollectEvent(Constant.COLLECTTYPE_RIGHT, position, false, null));
                ToastTool.show("取消关注");
            } else {
                rightDetailToolbar.setCollect(true);
                EventManager.getInstance().post(new CancelCollectEvent(Constant.COLLECTTYPE_RIGHT, position, true, Model));
                ToastTool.show("已关注");
            }
        }

        if (flag == HttpConfig.focusStatus.getType()) {
            int status = (int) result;
            if (status == 0) {
                rightDetailToolbar.setCollect(false);
            } else {
                rightDetailToolbar.setCollect(true);
            }
        }
        if (flag == HttpConfig.cancelExchangeRight.getType()) {
            try {
                JSONObject object = new JSONObject(o.toString());
                if (object.getInt("code") == 200) {
                    PopUpDialogs.dismiss();
                    ToastTool.show("取消预约成功");
//                    rightCancelLay.setVisibility(View.GONE);
//                    rightDetailCustomerLay.setVisibility(View.VISIBLE);
                } else {
                    ToastTool.show(object.getString("message"));
                }

            } catch (Exception e) {
                ToastTool.show("取消预约失败");
            }
        }
    }

    @Override
    public void OnClickRight(View view) {
        if (rightId == -1) {
            return;
        }
        if (rightDetailToolbar.isCollect()) {
            RightController.getInstance().cancelFocusRight(this, rightId);
        } else {
            RightController.getInstance().focusRight(this, rightId);
        }
    }

    RightsCancelPopUpDialog PopUpDialogs;

    public void ReservaSuccessPopUpDialog() {   //取消预约
        PopUpDialogs = new RightsCancelPopUpDialog(this);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        PopUpDialogs.setDialogListener(new RightsCancelPopUpDialog.DialogListener() {
            @Override
            public void onclickConfirm() {
                if (Model.getReservationId() != null) {
                    RightController.getInstance().cancelExchangeRight(FhNewRightsDetailActivity.this, Integer.parseInt(Model.getReservationId()));
                } else {
                    Logger.e("rightModel.getReservationId()=null");
                }

            }
        });
        PopUpDialogs.show(fragmentTransaction, "RightsCancelPopUpDialog");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 0:
//                RightController.getInstance().getRightDetail(this,rightModel.getId());
//                rightCancelLay.setVisibility(View.VISIBLE);
//                rightDetailCustomerLay.setVisibility(View.GONE);
                break;
        }
    }

}
