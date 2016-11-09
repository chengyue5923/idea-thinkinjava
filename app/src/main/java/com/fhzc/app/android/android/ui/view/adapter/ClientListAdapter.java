package com.fhzc.app.android.android.ui.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.view.adapter.BasePlatAdapter;
import com.fhzc.app.android.R;
import com.fhzc.app.android.configer.enums.CustomerType;
import com.fhzc.app.android.models.MemberModel;
import com.fhzc.app.android.utils.im.ImageLoader;


/**
 * 选择客户Adapter
 */
public class ClientListAdapter extends BasePlatAdapter<MemberModel> {


    public ClientListAdapter(Context context) {
        super(context);

    }

    @Override
    public View getView(int position, View itemView, ViewGroup parent) {
        final ViewHolder vh;
        final MemberModel memberModel = getItem(position);
        if (itemView == null) {
            vh = new ViewHolder();
            itemView = LayoutInflater.from(context).inflate(R.layout.view_client_item, null);
            vh.name = (TextView) itemView.findViewById(R.id.clientName);
            vh.level = (ImageView) itemView.findViewById(R.id.clientLevel);
            vh.money = (TextView) itemView.findViewById(R.id.clientMoney);
            vh.avatar = (ImageView) itemView.findViewById(R.id.clientAvatar);

            itemView.setTag(vh);
        } else {
            vh = (ViewHolder) itemView.getTag();
        }
        vh.money.setText(memberModel.getAssetsSum()+"元");
        vh.level.setImageResource(CustomerType.getCustomerByType(memberModel.getLevel()).getClientListBg());
        vh.name.setText(memberModel.getRealname());
        ImageLoader.getInstance(context, R.drawable.im_default_user_portrait_corner).displayImage(memberModel.getAvatar(), vh.avatar);
        return itemView;
    }

    class ViewHolder {
        TextView name, money;
        ImageView avatar, level;

    }
}
