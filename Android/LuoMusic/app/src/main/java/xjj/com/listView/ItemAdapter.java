package xjj.com.listView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import xjj.com.luomusic.R;

/**
 * Created by XJJ on 2018/12/10.
 * listView适配器
 */

public class ItemAdapter extends ArrayAdapter<Item> {
    private int reId;
    public ItemAdapter(Context context, int textViewResourceId, List<Item> obj) {
        super(context, textViewResourceId, obj);
        reId = textViewResourceId;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // 该项
        Item item = getItem(position);
        View view;
        ViewHolder viewHolder;
        // 减少缓存
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(reId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.itemImg = view.findViewById(R.id.mine_item_img);
            viewHolder.itemDesc = view.findViewById(R.id.mine_item_name);
            view.setTag(viewHolder);
        }else {
            view = convertView;
            viewHolder = (ViewHolder)view.getTag();
        }

        viewHolder.itemImg.setImageResource(item.getImgId());
        viewHolder.itemDesc.setText(item.getItemName());
        return view;
    }

    class ViewHolder {
        ImageView itemImg;
        TextView itemDesc;
    }
}
