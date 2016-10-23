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
import com.fhzc.app.android.models.RightModel;
import com.fhzc.app.android.utils.android.Logger;
import com.fhzc.app.android.utils.im.ImageLoader;


/**
 * 权益列表Adapter
 */
public class RightsListAdapter extends BasePlatAdapter<RightModel> {
    private Context mContext;
    boolean isCollect;

    public RightsListAdapter(Context context, boolean isCollect) {
        super(context);
        mContext = context;
        this.isCollect = isCollect;
    }

    @Override
    public View getView(int position, View itemView, ViewGroup parent) {
        ViewHolder vh;
        if (itemView == null) {
            vh = new ViewHolder();
            itemView = LayoutInflater.from(context).inflate(R.layout.adapter_right_list_layout, null);
            vh.content = (TextView) itemView.findViewById(R.id.right_item_content);
            vh.iamge = (ImageView) itemView.findViewById(R.id.right_item_image);
            vh.title = (TextView) itemView.findViewById(R.id.right_item_title);
            vh.timeTextViews = (TextView) itemView.findViewById(R.id.timeTextViews);
            vh.levelTextViews = (TextView) itemView.findViewById(R.id.levelTextViews);
            itemView.setTag(vh);
        } else {
            vh = (ViewHolder) itemView.getTag();
        }

        RightModel model = getItem(position);
        vh.title.setText(model.getName());
        vh.timeTextViews.setText(DateTools.formatDateWithSecondSince1970(DateTools.DATE_FORMAT_STYLE_5, model.getCtime()));

        vh.content.setText(isCollect ? model.getSpend_score() + "" : model.getSpendScore() + "");
        try {
            if (model.getLevel() == 5) {
                if (model.getLevelName() == null) {
                    vh.levelTextViews.setText("黑金卡");
                } else {
                    vh.levelTextViews.setText(model.getLevelName());
                }
                vh.levelTextViews.setTextColor(mContext.getResources().getColor(R.color.black));
                vh.levelTextViews.setBackground(mContext.getResources().getDrawable(R.drawable.adapter_right_black_card_bg));
            } else if (model.getLevel() == 4) {
                if (model.getLevelName() == null) {
                    vh.levelTextViews.setText("金卡");
                } else {
                    vh.levelTextViews.setText(model.getLevelName());
                }
                vh.levelTextViews.setTextColor(mContext.getResources().getColor(R.color.goldColor));
                vh.levelTextViews.setBackground(mContext.getResources().getDrawable(R.drawable.adapter_right_glod_card_bg));
            } else if (model.getLevel() == 3) {
                if (model.getLevelName() == null) {
                    vh.levelTextViews.setText("银卡");
                } else {
                    vh.levelTextViews.setText(model.getLevelName());
                }
                vh.levelTextViews.setTextColor(mContext.getResources().getColor(R.color.silverColor));
                vh.levelTextViews.setBackground(mContext.getResources().getDrawable(R.drawable.adapter_right_silver_card_bg));
            } else if (model.getLevel() == 2) {
                if (model.getLevelName() == null) {
                    vh.levelTextViews.setText("准会员");
                } else {
                    vh.levelTextViews.setText(model.getLevelName());
                }
                vh.levelTextViews.setTextColor(mContext.getResources().getColor(R.color.silverColor));
                vh.levelTextViews.setBackground(mContext.getResources().getDrawable(R.drawable.adapter_right_silver_card_bg));
            } else if (model.getLevel() == 1) {
                if (model.getLevelName() == null) {
                    vh.levelTextViews.setText("投资人");
                } else {
                    vh.levelTextViews.setText(model.getLevelName());
                }
                vh.levelTextViews.setTextColor(mContext.getResources().getColor(R.color.silverColor));
                vh.levelTextViews.setBackground(mContext.getResources().getDrawable(R.drawable.adapter_right_silver_card_bg));
            }

        } catch (Exception e) {
            Logger.e("models22222" + model.toString());
            Logger.e(e.getMessage());
        }
        ImageLoader.getInstance(mContext, R.drawable.default_error).displayImage(model.getCover(), vh.iamge);

        return itemView;
    }

    class ViewHolder {
        ImageView iamge;
        TextView title, content, timeTextViews, levelTextViews;
    }
}
