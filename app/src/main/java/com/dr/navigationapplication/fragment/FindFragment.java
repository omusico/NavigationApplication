package com.dr.navigationapplication.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.dr.navigationapplication.R;
import com.dr.navigationapplication.dao.PlaceTable;
import com.dr.navigationapplication.dao.daoimpl.Data;
import com.dr.navigationapplication.listViewAnimation.ScaleInAnimationAdapter;
import com.dr.navigationapplication.util.AsyncImageLoader;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by jct on 2015/9/15.
 */
public class FindFragment extends Fragment {

    private static final String TAG = "FindFragment";
    private ListView listView;
    private MyAdapter myAdapter;
    private View v;
    private static int cityID;
    private Handler handler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_find, container, false);
        init();
        return v;
    }

    void init() {


        listView = (ListView) v.findViewById(R.id.second_list);
        listView.setDivider(null);
        myAdapter = new MyAdapter(getActivity().getApplicationContext(), handler);

        ScaleInAnimationAdapter scaleInAnimationAdapter = new ScaleInAnimationAdapter(myAdapter, 0f);
        scaleInAnimationAdapter.setListView(listView);

        listView.setAdapter(scaleInAnimationAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*Intent intent = new Intent(getActivity(), EveryPartActivity.class);
                int pid = myAdapter.placeTables.get(position).getId();
                String s = myAdapter.placeTables.get(position).getImage();
                intent.putExtra("PlaceID", pid);
                intent.putExtra("MainPlace", s);
                intent.putExtra("name",myAdapter.placeTables.get(position).getName());
                intent.putExtra("intro", myAdapter.placeTables.get(position).getIntro());
                startActivity(intent);*/
            }
        });
    }

    public void upDate() {
        myAdapter.update();
        myAdapter.notifyDataSetChanged();

    }

    class MyAdapter extends BaseAdapter {
        public List<PlaceTable> placeTables;
        private Context mContext;
        private AsyncImageLoader asyncImageLoader;

        public MyAdapter(Context context, Handler handler) {
            this.mContext = context;
            placeTables = new ArrayList<>();
            asyncImageLoader = new AsyncImageLoader(context, handler);
            for (int i = 0; i < Data.placeTableList.size(); i++) {
                if (Data.placeTableList.get(i).getCid() == cityID) {
                    placeTables.add(Data.placeTableList.get(i));
                }
            }
        }

        @Override
        public int getCount() {
            return placeTables.size();
        }


        @Override
        public Object getItem(int position) {
            return placeTables.get(position);
        }


        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            FindViewHolder viewHolder;

            if (convertView == null) {
                convertView = View.inflate(getActivity(), R.layout.list_find, null);
                viewHolder = new FindViewHolder();
                viewHolder.imageView = (ImageView) convertView.findViewById(R.id.activity_googlecards_card_imageview);
                viewHolder.textView = (TextView) convertView.findViewById(R.id.activity_googlecards_card_textview);
                convertView.setTag(viewHolder);

            } else {
                viewHolder = (FindViewHolder) convertView.getTag();
            }
            viewHolder.imageView.setTag(placeTables.get(position).getImage());
            viewHolder.imageView.setBackground(mContext.getResources().getDrawable(R.drawable.user_photo_background));

            Bitmap bitmap = asyncImageLoader.loadImage(viewHolder.imageView, placeTables.get(position).getImage());
            if (bitmap != null) {
                Drawable drawable = new BitmapDrawable(null, bitmap);
                viewHolder.imageView.setBackground(drawable);
            }
            viewHolder.textView.setText(placeTables.get(position).getName());

            return convertView;
        }

        public void update() {
            placeTables.clear();
            for (int i = 0; i < Data.placeTableList.size(); i++) {
                if (Data.placeTableList.get(i).getCid() == cityID) {
                    placeTables.add(Data.placeTableList.get(i));
                }
            }
            Log.i(TAG, "更新成功" + cityID);
        }

    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public static void setCityID(int cityID) {
        FindFragment.cityID = cityID;
    }

    public ListView getListView() {
        return listView;
    }
}

class FindViewHolder {
    public ImageView imageView;
    public TextView textView;
}