package org.androidtown.alcohol.Community;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidtown.alcohol.R;

import java.util.ArrayList;

public class CommunityListviewAdapter extends BaseAdapter {

   private ArrayList<CommunityListviewItem> comListviewitems =new ArrayList<CommunityListviewItem>();

    public CommunityListviewAdapter (ArrayList<CommunityListviewItem> item){

        this.comListviewitems = item;
    }


    @Override
    public int getCount() {
        return comListviewitems.size();
    }

    @Override
    public CommunityListviewItem getItem(int position) {
        return comListviewitems.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    public void addItem(CommunityListviewItem item){
        comListviewitems.add(item);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final int pos = position;
        final Context context = parent.getContext();


        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_community_listview, parent, false);
        }

        TextView category_tv =convertView.findViewById(R.id.community_lv_category_tv);
        TextView name_tv = convertView.findViewById(R.id.community_lv_name_tv);
        TextView date_tv =convertView.findViewById(R.id.community_lv_date_tv);
        TextView text_tv =convertView.findViewById(R.id.community_lv_text_tv);
        TextView good_cnt_tv=convertView.findViewById(R.id.community_lv_goodcnt_tv);
        TextView comment_cnt_tv=convertView.findViewById(R.id.community_lv_commentcnt_tv);
        TextView title_tv = convertView.findViewById(R.id.community_lv_title_tv);

        CommunityListviewItem item = comListviewitems.get(pos);


        name_tv.setText(item.getName());
        text_tv.setText(item.getText());
        comment_cnt_tv.setText(item.getComment());
        good_cnt_tv.setText(item.getGood());
        date_tv.setText(item.getDate());
        category_tv.setText(item.getCategory());
        title_tv.setText(item.getTitle());

        return convertView;
    }
}
