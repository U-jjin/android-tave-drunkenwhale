package org.androidtown.alcohol.Community;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.androidtown.alcohol.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CommunityDetailActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView toolbar_name_tv;

    String checkdate;


    TextView date, title, nickname, text, good_cnt, comment_cnt;
    ImageView good_img;

    EditText comment_et;
    Button submit;

    TextView com_name, com_date, com_text;
    LinearLayout check_linear;

    ListView comment_listview;
    CommentListviewAdapter adapter;
    ArrayList<CommentInfo> commentInfos =new ArrayList<CommentInfo>();

    DatabaseReference dr = FirebaseDatabase.getInstance().getReference().child("community");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_detail);

        toolbar_name_tv =findViewById(R.id.community_detail_toolbar_tv);
        toolbar = (Toolbar) findViewById(R.id.community_detail_toolbar);
        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar() ;
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowTitleEnabled(false);
        ab.setHomeAsUpIndicator(R.drawable.ic_back_24dp);

        date = findViewById(R.id.detail_community_date);
        title =findViewById(R.id.detail_community_title);
        nickname =findViewById(R.id.detail_community_nickname);
        text =findViewById(R.id.detail_community_text_tv);
        good_cnt =findViewById(R.id.detail_community_good_cnt);
        comment_cnt = findViewById(R.id.detail_community_comment_cnt);
        good_img =findViewById(R.id.detail_community_good_img);

        comment_et =findViewById(R.id.detail_community_comment_et);
        submit = findViewById(R.id.detail_community_comment_btn);

        comment_listview =findViewById(R.id.comment_listview);
        com_date =findViewById(R.id.comment_date_item);
        com_text =findViewById(R.id.comment_text_item);
        com_name =findViewById(R.id.comment_nickname_item);
        check_linear =findViewById(R.id.comment_linear);
        check_linear.setVisibility(View.GONE);

        Intent intent =getIntent();
        checkdate =intent.getStringExtra("date");

        dr.child(checkdate).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                date.setText(dataSnapshot.child("date").getValue().toString());
                title.setText(dataSnapshot.child("title").getValue().toString());
                nickname.setText(dataSnapshot.child("name").getValue().toString());
                good_cnt.setText(dataSnapshot.child("good").getValue().toString());
                comment_cnt.setText(dataSnapshot.child("comment").getValue().toString());
                toolbar_name_tv.setText(dataSnapshot.child("category").getValue().toString());
                text.setText(dataSnapshot.child("text").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        good_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                good_img.setImageResource(R.drawable.ic_favorite_check_24dp);
                good_cnt.setText("1");
                good_cnt.setTextColor(getResources().getColor(R.color.AlmanchuPrimary));
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", java.util.Locale.getDefault());
                    Date date = new Date();
                    String strDate = dateFormat.format(date);
                    com_date.setText(strDate);
                    com_name.setText("가자");
                    com_text.setText(comment_et.getText().toString());
                    check_linear.setVisibility(View.VISIBLE);

                    comment_et.setText("");
                    comment_cnt.setText("1");




            }
        });



    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                dr.child(checkdate).child("comment").setValue("1");
                dr.child(checkdate).child("good").setValue("1");
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
