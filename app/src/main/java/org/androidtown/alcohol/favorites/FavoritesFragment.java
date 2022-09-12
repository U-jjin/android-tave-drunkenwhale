package org.androidtown.alcohol;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.androidtown.alcohol.detail.DetailActivity;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends Fragment {


    Context context;
    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;

    ArrayList<CardviewInfo> infos = new ArrayList<CardviewInfo>();

    public FavoritesFragment() {
        // Required empty public constructor
    }

    public  static  FavoritesFragment newInstance(){
        Bundle args = new Bundle();
        FavoritesFragment favoritesFragment =new FavoritesFragment();
        return  favoritesFragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        context=container.getContext();

        infos.add(new CardviewInfo("인 드림스 피노 누아","와인","레드"));
        infos.add(new CardviewInfo("꿀막걸리","막걸리","생막걸리"));
        infos.add(new CardviewInfo("스미노프 블랙","보드카","증류주"));
        infos.add(new CardviewInfo("제임슨 스탠다드 700ml","위스키","Irish"));
        infos.add(new CardviewInfo("C1","소주",""));

        recyclerView =view.findViewById(R.id.favorite_recyclerview);
        gridLayoutManager =new GridLayoutManager(context,2);
        final RecyclerAdapter adapter =new RecyclerAdapter(getContext(),infos);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);


        DatabaseReference ds = FirebaseDatabase.getInstance().getReference();

        ds.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               if( dataSnapshot.child("favorite").exists()){
                   CardviewInfo cardviewInfo =new CardviewInfo();
                   cardviewInfo = dataSnapshot.child("favorite").getValue(CardviewInfo.class);
                   infos.add(cardviewInfo);
                   adapter.notifyDataSetChanged();
               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return view;
    }

}
