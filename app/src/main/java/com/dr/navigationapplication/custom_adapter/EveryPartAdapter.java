package com.dr.navigationapplication.custom_adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dr.navigationapplication.R;
import com.dr.navigationapplication.util.AsyncImageLoader;
import com.dr.navigationapplication.util.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 董神 on 2015/9/5.
 * I love programming
 */
public class EveryPartAdapter extends BaseAdapter {

    public List<EveryPartClass> everyPartClassList;
    private Context mContext;
    private AsyncImageLoader asyncImageLoader;

    public EveryPartAdapter(Context mContext) {
        this.mContext = mContext;
        everyPartClassList = new ArrayList<>();
        asyncImageLoader = new AsyncImageLoader(mContext);
    }

    public EveryPartAdapter() {

    }

    @Override
    public int getCount() {
        return everyPartClassList.size();
    }

    @Override
    public Object getItem(int position) {
        return everyPartClassList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_main_adapter, null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.item_main_adapter_imageview);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.item_main_adapter_textView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        asyncImageLoader.loadImage(viewHolder.imageView, everyPartClassList.get(position).imageUrl);
        viewHolder.textView.setText(everyPartClassList.get(position).name);
        return convertView;
    }

    /*
    每个控件的信息
     */
    public class EveryPartClass {

        public EveryPartClass() {

        }

        public String imageUrl;
        public String name;
        public Integer imageDownloadFlags;
    }


}
