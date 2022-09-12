package org.androidtown.alcohol.Community;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.androidtown.alcohol.MainActivity;
import org.androidtown.alcohol.OnBackPressedListener;
import org.androidtown.alcohol.R;

import java.util.ArrayList;

public class CommunityFragment extends Fragment implements OnBackPressedListener {

    CommunityListviewAdapter adapter;
    FloatingActionButton fab ;
    Context context;

    ListView listView;
    ListView comment_listview;

   ArrayList<CommunityListviewItem> total = new ArrayList<CommunityListviewItem>();
    ArrayList<CommunityListviewItem> wine = new ArrayList<CommunityListviewItem>();
    ArrayList<CommunityListviewItem> beer = new ArrayList<CommunityListviewItem>();
    ArrayList<CommunityListviewItem> mak = new ArrayList<CommunityListviewItem>();
    ArrayList<CommunityListviewItem> soju = new ArrayList<CommunityListviewItem>();
    ArrayList<CommunityListviewItem> whiskey = new ArrayList<CommunityListviewItem>();
    ArrayList<CommunityListviewItem> atc = new ArrayList<CommunityListviewItem>();

    CommunityListviewAdapter t_adapter, b_adapter, w_adapter, s_adapter, m_adapter, a_adapter, wh_adapter;

    TabLayout tabLayout;
    DatabaseReference databaseReference ;

    public CommunityFragment() {
        // Required empty public constructor
    }

    public static CommunityFragment newInstance(){
        Bundle args =new Bundle();
       CommunityFragment communityFragment =new CommunityFragment();

        return communityFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view =  inflater.inflate(R.layout.fragment_community, container, false);
        context=container.getContext();


         tabLayout = view.findViewById(R.id.community_tablayout);

        tabLayout.addTab(tabLayout.newTab().setText("전체"));
        tabLayout.addTab(tabLayout.newTab().setText("맥주"));
        tabLayout.addTab(tabLayout.newTab().setText("소주"));
        tabLayout.addTab(tabLayout.newTab().setText("막걸리"));
        tabLayout.addTab(tabLayout.newTab().setText("와인"));
        tabLayout.addTab(tabLayout.newTab().setText("양주"));
        tabLayout.addTab(tabLayout.newTab().setText("기타"));


        //리스트뷰 연결  -->디비연결 하자
          listView = (ListView)view.findViewById(R.id.community_listview);
        ArrayList<CommunityListviewItem> item = new ArrayList<CommunityListviewItem>();




        databaseReference= FirebaseDatabase.getInstance().getReference().child("community");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> temp = new ArrayList<String>();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                        temp.add(ds.getKey());

                }
                total.clear();
                wine.clear();
                beer.clear();
                whiskey.clear();
                mak.clear();
                soju.clear();
                atc.clear();
                String category, name, good, comment, date, title,text;
                for(int i=temp.size()-1; i>=0;i--){
                    category = dataSnapshot.child(temp.get(i)).child("category").getValue().toString();
                    name = dataSnapshot.child(temp.get(i)).child("name").getValue().toString();
                    good = dataSnapshot.child(temp.get(i)).child("good").getValue().toString();
                    comment = dataSnapshot.child(temp.get(i)).child("comment").getValue().toString();
                    date = dataSnapshot.child(temp.get(i)).child("date").getValue().toString();
                    title = dataSnapshot.child(temp.get(i)).child("title").getValue().toString();
                    text = dataSnapshot.child(temp.get(i)).child("text").getValue().toString();
                    CommunityListviewItem item =new CommunityListviewItem(name,category,date,good,title,comment,text);
                    total.add(item);

                    switch (category){
                        case "와인": wine.add(item); break;
                        case "맥주": beer.add(item); break;
                        case "양주": whiskey.add(item); break;
                        case "소주": soju.add(item); break;
                        case "기타": atc.add(item); break;
                        case "막걸리":mak.add(item); break;
                    }

                }


                t_adapter =new CommunityListviewAdapter(total);
                w_adapter =new CommunityListviewAdapter(wine);
                s_adapter  =new CommunityListviewAdapter(soju);
                b_adapter  =new CommunityListviewAdapter(beer);
                m_adapter  =new CommunityListviewAdapter(mak);
                a_adapter  =new CommunityListviewAdapter(atc);
                wh_adapter  =new CommunityListviewAdapter(whiskey);

                tabLayout.getTabAt(0).select();
                listView.setAdapter(t_adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent =new Intent(context,CommunityDetailActivity.class);
                intent.putExtra("date",total.get(position).getDate());
                startActivity(intent);
            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                    switch (tab.getText().toString()){
                        case "전체": listView.setAdapter(t_adapter); break;
                        case "맥주": listView.setAdapter(b_adapter); break;
                        case "와인": listView.setAdapter(w_adapter); break;
                        case "소주": listView.setAdapter(s_adapter); break;
                        case "막걸리": listView.setAdapter(m_adapter); break;
                        case "양주": listView.setAdapter(wh_adapter); break;
                        case "기타": listView.setAdapter(a_adapter); break;
                    }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



        //float 액션버튼
        fab =view.findViewById(R.id.writing_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),WritingActivity.class);
                startActivity(intent);
            }
        });


        // Inflate the layout for this fragment
        return view;
    }
    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("종료창")
                .setMessage("종료하시겠습니까?")
                .setPositiveButton("종료", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getActivity().finish();
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }


}
