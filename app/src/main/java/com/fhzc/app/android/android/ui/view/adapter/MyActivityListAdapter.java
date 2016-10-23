package com.fhzc.app.android.android.ui.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.platform.utils.java.DateTools;
import com.fhzc.app.android.R;
import com.fhzc.app.android.configer.enums.MyActivityType;
import com.fhzc.app.android.models.ActivityModel;
import com.fhzc.app.android.models.ActivityStatusModel;
import com.fhzc.app.android.models.OrderActivityModel;
import com.fhzc.app.android.utils.im.ImageLoader;

import java.util.List;

/**
 * Created by yanbo on 2016/7/20.
 */
public class MyActivityListAdapter extends BaseAdapter {
    Context mContext;
    List<Object> list;

    public MyActivityListAdapter(Context context) {

        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int typeIndex = getItemViewType(position);
        MyActivityType renderType = MyActivityType.values()[typeIndex];
        switch (renderType) {
            case ACTIVITY_TYPE_TIME_NORMAL:
                // 直接返回
                convertView = activityRender(position, convertView);
                break;
            case ACTIVITY_TYPE_TIME_STATUS:
                convertView = statusRender(position, convertView);
                break;
        }
        return convertView;
    }

    class ActivityViewHolder {
        TextView time;
        ImageView image;
        ImageView activity_item_status;
    }

    class StatusViewHolder {
        TextView status;
    }

    /**
     * 状态的渲染
     */
    private View statusRender(int position, View convertView) {
        StatusViewHolder statusViewHolder;
        ActivityStatusModel model = (ActivityStatusModel) getItem(position);
        if (null == convertView) {
            statusViewHolder = new StatusViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.view_my_activity_status, null);
            convertView.setTag(statusViewHolder);
        } else {
            statusViewHolder = (StatusViewHolder) convertView.getTag();
        }
        statusViewHolder.status = (TextView)convertView.findViewById(R.id.statusTv);
        statusViewHolder.status.setText("已结束");
        return convertView;
    }

    /**
     * 状态的渲染
     */
    private View activityRender(int position, View convertView) {
        ActivityViewHolder activityViewHolder;
        OrderActivityModel model = (OrderActivityModel) getItem(position);
        if (null == convertView) {
            activityViewHolder = new ActivityViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.view_my_activity_item, null);
            convertView.setTag(activityViewHolder);
        } else {
            activityViewHolder = (ActivityViewHolder) convertView.getTag();
        }
        activityViewHolder.activity_item_status = (ImageView) convertView.findViewById(R.id.activity_item_status) ;
        if (model.getStatus() == 0) {
            activityViewHolder.activity_item_status.setImageResource(R.drawable.activity_notstrart);
        } else if (model.getStatus() == 1) {
            activityViewHolder.activity_item_status.setImageResource(R.drawable.activity_underway);
        } else if (model.getStatus() == 2) {
            activityViewHolder.activity_item_status.setImageResource(R.drawable.complete);
        } else if (model.getStatus() == 3) {
            activityViewHolder.activity_item_status.setImageResource(R.drawable.complete);
        }
        activityViewHolder.time = (TextView)convertView.findViewById(R.id.activity_item_time) ;
        activityViewHolder.image = (ImageView) convertView.findViewById(R.id.activity_item_image) ;
        activityViewHolder.time.setText("报名截止时间：" + DateTools.formatDateWithSecondSince1970(DateTools.DATE_FORMAT_STYLE_5, model.getApplyEndTime()));
        ImageLoader.getInstance(mContext, R.drawable.default_error_long).displayImage(model.getCover(), activityViewHolder.image);
        return convertView;
    }

    @Override
    public Object getItem(int position) {
        if (position >= getCount() || position < 0) {
            return null;
        }
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        if (null == list) {
            return 0;
        } else {
            return list.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        int type = MyActivityType.ACTIVITY_TYPE_TIME_INVAILD.ordinal();
        try {
            Object obj = list.get(position);

            if (obj instanceof ActivityStatusModel) {
                type = MyActivityType.ACTIVITY_TYPE_TIME_STATUS.ordinal();
            }else {
                type = MyActivityType.ACTIVITY_TYPE_TIME_NORMAL.ordinal();
            }
        } catch (Exception e) {
        }

        return type;
    }

    @Override
    public int getViewTypeCount() {
        return MyActivityType.values().length;
    }

    public void setRes(List<Object> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}
