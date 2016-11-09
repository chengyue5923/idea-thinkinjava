package com.fhzc.app.android.android.ui.view.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.platform.utils.android.Logger;
import com.fhzc.app.android.R;

/**
 *
 * 产品详情   预约成功
 * Created by lenovo on 2016/7/12.
 */
public class ProduceSuccessReservationDialog extends DialogFragment implements View.OnClickListener {


    private TextView cancelNotText,cancelText;

    private ImageView activityBacksIv;
    private TextView rightsTextView,demandTextView,signUpSureTextView;


    public ProduceSuccessReservationDialog(){

    }

    @SuppressLint("ValidFragment")
    public ProduceSuccessReservationDialog(Context context){
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        WindowManager.LayoutParams localLayoutParams = getDialog().getWindow().getAttributes();
        localLayoutParams.gravity = Gravity.CENTER;
        localLayoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        localLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = new Dialog(getActivity(), R.style.MyDialogStyleBottom);
        View view = View.inflate(getActivity(), R.layout.produce_success_reservation_window, null);
        dialog.setContentView(view);
        Logger.e(">>>" + "dialog on create run");
        cancelNotText=(TextView)dialog.findViewById(R.id.cancelsuxxessNotText);
        cancelNotText.setOnClickListener(this);
//        cancelText=(TextView)dialog.findViewById(R.id.cancelText);
//        cancelText.setOnClickListener(this);
        return dialog;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.cancelsuxxessNotText:
                dismiss();
                break;
//            case R.id.cancelText:
//                break;
        }
    }
}
