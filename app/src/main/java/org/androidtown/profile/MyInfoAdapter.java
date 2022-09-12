package org.androidtown.alcohol;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MyInfoAdapter extends BaseAdapter {
    ArrayList<String> drinkInfos;
    ArrayList<Integer> ratings;
    Context context;
    MyInfoAdapter(Context context,ArrayList<String> drinkInfos,ArrayList<Integer> ratings){
        this.drinkInfos=drinkInfos;
        this.ratings=ratings;
        this.context=context;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {//viewGroup은 parent//view는 convertview
        if(view!=null){
            return view;
        }
        View convertview = ((Activity) context).getLayoutInflater().inflate(R.layout.drink_list, null);
        RatingBar ratingBar=convertview.findViewById(R.id.rating_bar);
        TextView tv = convertview.findViewById(R.id.response_view);
        ratingBar.setRating(ratings.get(i));
        tv.setText(drinkInfos.get(i));
        //추후 UserID에 따른 rating 및 댓글 firebase db 연동해서 구현하기
        return convertview;
    }


    @Override
    public Object getItem(int i) {
        return drinkInfos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getCount() {
        return drinkInfos.size();
    }
}
