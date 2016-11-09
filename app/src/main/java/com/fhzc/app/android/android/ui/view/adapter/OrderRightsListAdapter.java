package com.fhzc.app.android.android.ui.view.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.platform.utils.java.DateTools;
import com.base.view.adapter.BasePlatAdapter;
import com.fhzc.app.android.R;
import com.fhzc.app.android.models.OrderRightModel;
import com.fhzc.app.android.models.RightModel;
import com.fhzc.app.android.utils.android.Logger;
import com.fhzc.app.android.utils.im.ImageLoader;


/**
 * 权益列表Adapter
 */
public class OrderRightsListAdapter extends BasePlatAdapter<OrderRightModel> {
    private Context mContext;
    private boolean flag;
    public OrderRightsListAdapter(Context context,boolean flags) {
        super(context);
        mContext = context;
        flag=flags;
    }
    @Override
    public View getView(int position, View itemView, ViewGroup parent) {
        ViewHolder vh;
        if (itemView == null) {
            vh = new ViewHolder();
            itemView = LayoutInflater.from(context).inflate(R.layout.adapter_right_wist_flag_list_layout, null);
            vh.content=(TextView)itemView.findViewById(R.id.right_item_content_new);
            vh.iamge=(ImageView)itemView.findViewById(R.id.right_item_image_new);
            vh.dradleImage=(ImageView)itemView.findViewById(R.id.dradleImage);

            vh.title=(TextView)itemView.findViewById(R.id.right_item_title_new);
            vh.timeTextViews=(TextView)itemView.findViewById(R.id.timeTextViews);
            vh.levelTextViews=(TextView)itemView.findViewById(R.id.leaval_text);
            vh.statexTextView=(TextView)itemView.findViewById(R.id.statexTextView);
            itemView.setTag(vh);
        } else {
            vh = (ViewHolder) itemView.getTag();
        }

        OrderRightModel model=getItem(position);
        vh.title.setText(model.getName());
        ImageLoader.getInstance(mContext).displayImage(model.getCover(),vh.iamge);
        Logger.e("modelsssss"+model.toString());
        if(String.valueOf(model.getMark_date())!=null){
            vh.timeTextViews.setText(DateTools.formatDateWithSecondSince1970(DateTools.DATE_FORMAT_STYLE_5, model.getMark_date()));
        }
        try {
            if (String.valueOf(model.getSpendScore()) != null) {
                vh.content.setText(model.getSpendScore() + "积分");
            }
            if (String.valueOf(model.getLevel()) != null) {
                Logger.e("mode.leaveliuu"+model.getLevel());
                if (model.getLevel() == 5) {
                    vh.dradleImage.setBackground(context.getResources().getDrawable(R.drawable.black_leavel));
                    vh.levelTextViews.setText(model.getLevelName()+"以上专享");
                } else if (model.getLevel() == 4) {
                    vh.dradleImage.setBackground(context.getResources().getDrawable(R.drawable.gold_leveal));
                    vh.levelTextViews.setText(model.getLevelName()+"以上专享");
                } else if(model.getLevel()==3){
                    vh.dradleImage.setBackground(context.getResources().getDrawable(R.drawable.silver_leavel));
                    vh.levelTextViews.setText(model.getLevelName()+"以上专享");
                }else if(model.getLevel()==2){
                    vh.dradleImage.setBackground(context.getResources().getDrawable(R.drawable.finalcial_leavel));
                    vh.levelTextViews.setText(model.getLevelName()+"以上专享");
                }else if(model.getLevel()==1){
                    vh.dradleImage.setBackground(context.getResources().getDrawable(R.drawable.vip_leavel));
                    vh.levelTextViews.setText(model.getLevelName()+"以上专享");
                }
            }

            if(String.valueOf(model.getStatus())!=null&&flag){

                Logger.e("model.getStates()"+model.getStatus());
                if(model.getStatus()==2){
                    vh.statexTextView.setVisibility(View.VISIBLE);
                    vh.statexTextView.setText("预约失败");
                }else if(model.getStatus()==3){
                    vh.statexTextView.setVisibility(View.VISIBLE);
                    vh.statexTextView.setText("客户取消预约");
                }else if(model.getStatus()==4){
                    vh.statexTextView.setVisibility(View.VISIBLE);
                    vh.statexTextView.setText("客户消费");
                }else if(model.getStatus()==5){
                    vh.statexTextView.setVisibility(View.VISIBLE);
                    vh.statexTextView.setText("客户缺席");
                }
            }
        }catch (Exception e){
            Logger.e("models22222"+model.toString());
            Logger.e(e.getMessage());
        }
        return itemView;
    }
    class ViewHolder {
        ImageView iamge,dradleImage;
        TextView title,statexTextView,content,timeTextViews,levelTextViews;
    }
}
