package org.androidtown.alcohol;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.androidtown.alcohol.detail.DetailActivity;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    Context context;
    ArrayList<CardviewInfo> items;
    int item_layout;

    public RecyclerAdapter(Context context, ArrayList<CardviewInfo> items) {
        this.context = context;
        this.items = items;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_alcohol, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final CardviewInfo item = items.get(position);

        holder.type1.setText(items.get(position).getType1());
        holder.type2.setText(items.get(position).getType2());
        holder.name.setText(items.get(position).getName());

        switch(items.get(position).getType1()){
            case "와인": holder.img.setImageResource(R.drawable.ic_wine2); break;
            case "맥주": holder.img.setImageResource(R.drawable.ic_beer); break;
            case "소주":  holder.img.setImageResource(R.drawable.ic_soju); break;
            case "막걸리":  holder.img.setImageResource(R.drawable.ic_mak); break;
            case "보드카":  holder.img.setImageResource(R.drawable.ic_vodka); break;
            case "위스키": holder.img.setImageResource(R.drawable.ic_whiskey); break;
            case "전통주": holder.img.setImageResource(R.drawable.ic_mak); break;
        }

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(context, DetailActivity.class);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name, type1, type2;

        public ViewHolder(View itemView) {
            super(itemView);
            img =itemView.findViewById(R.id.img_cardview);
            name =itemView.findViewById(R.id.name_cardview_tv);
            type1 =itemView.findViewById(R.id.type1_cardview_tv);
            type2 =itemView.findViewById(R.id.type2_cardview_tv);


        }
    }
}

