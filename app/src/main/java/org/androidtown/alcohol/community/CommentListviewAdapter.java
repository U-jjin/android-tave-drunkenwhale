package org.androidtown.alcohol.Community;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.androidtown.alcohol.R;

import java.util.ArrayList;

public class CommentListviewAdapter extends BaseAdapter {

    ArrayList<CommentInfo> commentInfos = new ArrayList<CommentInfo>();

    public CommentListviewAdapter(ArrayList<CommentInfo> commentInfos) {
        this.commentInfos = commentInfos;
    }


    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        int pos = i;
        Context context = viewGroup.getContext();

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.comment_listview_item, viewGroup, false);
        }

        TextView nickname = view.findViewById(R.id.comment_nickname_listview_item);
        TextView date = view.findViewById(R.id.comment_date_listview_item);
        TextView text = view.findViewById(R.id.comment_text_listview_item);

        nickname.setText(commentInfos.get(pos).getNickname());
        date.setText(commentInfos.get(pos).getDate());
        text.setText(commentInfos.get(pos).getComment());

        return view;
    }
}
