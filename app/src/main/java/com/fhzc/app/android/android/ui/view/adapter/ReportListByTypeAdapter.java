package com.fhzc.app.android.android.ui.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.platform.utils.java.DateTools;
import com.base.view.adapter.BasePlatAdapter;
import com.fhzc.app.android.R;
import com.fhzc.app.android.models.ReportModel;
import com.fhzc.app.android.utils.im.ImageLoader;

/**
 * Created by yanbo on 2016/7/20.
 */
public class ReportListByTypeAdapter extends BasePlatAdapter<ReportModel> {
    Context mContext;
    public ReportListByTypeAdapter(Context context) {
        super(context);
        mContext=context;
    }

    @Override
    public View getView(int position, View itemView, ViewGroup parent) {
        ViewHolder vh;
        ReportModel model = getItem(position);
        if (itemView == null) {
            vh = new ViewHolder();
            itemView = LayoutInflater.from(mContext).inflate(R.layout.view_report_item_new, null);
            vh.time = (TextView)itemView.findViewById(R.id.report_item_time);
            vh.title = (TextView)itemView.findViewById(R.id.report_item_title);
            vh.content = (TextView)itemView.findViewById(R.id.report_item_content);
            vh.number = (TextView)itemView.findViewById(R.id.report_item_bumber);
            vh.image = (ImageView)itemView.findViewById(R.id.report_item_image);
            itemView.setTag(vh);
        } else {
            vh = (ViewHolder) itemView.getTag();
        }
        vh.title.setText(model.getName());
        ImageLoader.getInstance(mContext,R.drawable.default_error).displayImage(model.getCover(),vh.image);
        vh.content.setText(model.getSummary());
        vh.time.setText(DateTools.formatDateWithSecondSince1970(DateTools.DATE_FORMAT_STYLE_5,model.getCtime()));
        return itemView;
    }
    class ViewHolder {
        TextView title, content,time,number;
        ImageView image;
    }
}
