package org.androidtown.alcohol.detail;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.androidtown.alcohol.CardviewInfo;
import org.androidtown.alcohol.MainActivity;
import org.androidtown.alcohol.R;
import org.androidtown.alcohol.RecyclerAdapter;
import org.androidtown.alcohol.SearchFragment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener{
    String DrinkingName;
    String DrinkingType;//술종류(대분류)
    String DrinkingTypeSmall;//술종류(중분류) //type2
    String description;
    Double percent;
    String producer;
    ImageView imageView;//술 아이콘 이미지
    ImageView heartview;//좋아요.
    ImageView IconView;//아이콘(상세설명 띄움)
    TextView AlcoholName;//술이름
    TextView AlcoholType;//술유형
    TextView DrinkPercent;
    TextView DrinkProducer;
    TextView comment;
    RecyclerView recyclerView;
    RecyclerView recyclerView2;
    int flag=0;


    TextView detail_tv;
    ImageView detail_img;
    LinearLayout detail_linear;
    int imgcheck =1;

    DatabaseReference dr = FirebaseDatabase.getInstance().getReference();

   LinearLayoutManager layoutManager;
    ArrayList<CardviewInfo> cardviewInfos =new ArrayList<CardviewInfo>();


    TextView toolbar_name_tv;
    Toolbar toolbar;

    int favoritecheck =0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent=getIntent();
//        DrinkingName=intent.getExtras().getString("DrinkingName");
//        DrinkingType=intent.getExtras().getString("Type");
//        DrinkingTypeSmall=intent.getExtras().getString("Type2");
//        description=intent.getExtras().getString("description");
//        percent=intent.getExtras().getDouble("percent");//도수
//        producer=intent.getExtras().getString("producer");//생산지
        imageView=findViewById(R.id.detail_view);
        AlcoholName=findViewById(R.id.detail_drink_name);
        IconView=findViewById(R.id.detail_drink_icon);
        AlcoholType=findViewById(R.id.detail_drink_type);
        DrinkPercent=findViewById(R.id.alcohol_percent);
        DrinkProducer=findViewById(R.id.producer);
        recyclerView=findViewById(R.id.detail_recyclerview);
//        heartview.setOnClickListener(this);
//        IconView.setOnClickListener(this);
//        setting();

        toolbar = findViewById(R.id.detail_toolbar);
        toolbar_name_tv =findViewById(R.id.detail_toolbar_tv);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar() ;
        ab.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ab.setHomeAsUpIndicator(R.drawable.ic_back_24dp);

        recyclerView =findViewById(R.id.detail_recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager =new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        recyclerView.setLayoutManager(layoutManager);

        cardviewInfos.add(new CardviewInfo("뉴벨지움 팻 타이어 엠버에일","맥주","에일"));
        cardviewInfos.add(new CardviewInfo("라구달 그랑크루","맥주","에일"));
        cardviewInfos.add(new CardviewInfo("버드 아이스","맥주","페일 라거"));
        cardviewInfos.add(new CardviewInfo("패러독스 아일레이병","맥주","임페리얼 스타우트"));
        cardviewInfos.add(new CardviewInfo("캘리포니아 콜쉬","맥주","쾰쉬"));

        RecyclerAdapter adapter =new RecyclerAdapter(DetailActivity.this,cardviewInfos);
        recyclerView.setAdapter(adapter);



        recyclerView2 =findViewById(R.id.detail2_recyclerview);
        recyclerView2.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView2.setLayoutManager(linearLayoutManager);
        ArrayList<CardviewInfo> cardviewInfos2 = new ArrayList<CardviewInfo>();

        cardviewInfos2.add(new CardviewInfo("산다라 와인 모히토","와인","스파클링"));
        cardviewInfos2.add(new CardviewInfo("바타시올로, 모스카토 스푸만테","와인","스파클링"));
        cardviewInfos2.add(new CardviewInfo("여포의 꿈 레드","전통주","과실주"));
        cardviewInfos2.add(new CardviewInfo("술이 매화주","전통주","약.청주"));
        cardviewInfos2.add(new CardviewInfo("참나무통 맑은 이슬","소주",""));

        RecyclerAdapter adapter2 = new RecyclerAdapter(DetailActivity.this,cardviewInfos2);
        recyclerView2.setAdapter(adapter2);

        detail_img = findViewById(R.id.detail_drink_icon);
        detail_tv = findViewById(R.id.detail_drink_tv);
        detail_linear = findViewById(R.id.detail_drink_linear);


        detail_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imgcheck ==1){
                    detail_img.setImageResource(R.drawable.ic_chevron_right_black_24dp);
                    detail_tv.setText("상세정보");
                    detail_linear.setVisibility(View.GONE);
                    imgcheck=0;
                }else if(imgcheck==0){
                    detail_img.setImageResource(R.drawable.ic_chevron_left_black_24dp);
                    detail_tv.setText("상세정보 접기");
                    detail_linear.setVisibility(View.VISIBLE);
                    imgcheck=1;
                }
            }
        });



    }

    //툴바 관련 함수
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.detail_actionbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.detail_favorites_btn: {
                if(favoritecheck==1){
                    item.setIcon(R.drawable.ic_favorite_check_24dp);
                    dr.child("favorite").child("type1").setValue("맥주");
                    dr.child("favorite").child("type2").setValue("에일");
                    dr.child("favorite").child("name").setValue("레페 로얄 윗브레드 골딩");
                    favoritecheck =0;
                }else if( favoritecheck==0){
                    item.setIcon(R.drawable.ic_favorite_notcheck_24dp);
                    favoritecheck=1;
                    dr.child("favorite").removeValue();
                } break;
            }
            case android.R.id.home: finish();
        }
        return super.onOptionsItemSelected(item);
    }
    
    public void setting(){
        //주종별 아이콘 이미지
        if(DrinkingType.equals("Beer")){
            imageView.setImageResource(R.drawable.ic_beer);
        }else if(DrinkingType.equals("Wine")){
            imageView.setImageResource(R.drawable.ic_wine2);
        }else if(DrinkingType.equals("Soju")){
            imageView.setImageResource(R.drawable.ic_soju);
        }else if(DrinkingType.equals("막걸리")){
            imageView.setImageResource(R.drawable.ic_mak);
        }else if(DrinkingType.equals("전통주")){
            imageView.setImageResource(R.drawable.ic_mak);
        }else if(DrinkingType.equals("Vodka")){
            imageView.setImageResource(R.drawable.ic_vodka);
        }else if(DrinkingType.equals("Whiskey")){
            imageView.setImageResource(R.drawable.ic_whiskey);
        }
        //술 이름
        AlcoholName.setText(DrinkingName);
        //술 종류(대분류 중분류)
        if(DrinkingTypeSmall!=null)
            AlcoholType.setText(DrinkingType+" "+DrinkingTypeSmall);
        if(DrinkingTypeSmall==null)
            AlcoholType.setText(DrinkingType);
        DrinkPercent.setText(percent.toString());
        DrinkProducer.setText(producer);
    }
    @Override
    public void onClick(View view) {
        if(view==IconView){
            CreateDialog();
        }
    }
    public void CreateDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("상세설명")
                .setMessage(description)
                .show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }



}
