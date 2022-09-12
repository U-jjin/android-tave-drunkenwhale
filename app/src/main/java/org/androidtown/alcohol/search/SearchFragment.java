package org.androidtown.alcohol;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.androidtown.alcohol.detail.DetailActivity;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment  {

    Context context;
    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;

    androidx.appcompat.widget.SearchView searchView;

    ArrayList<CardviewInfo> infos = new ArrayList<CardviewInfo>();

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance() {
        Bundle args = new Bundle();
        SearchFragment searchFragment = new SearchFragment();

        return searchFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        context = container.getContext();


        infos.add(new CardviewInfo("레페 로얄 윗브레드 골딩","맥주","에일"));
        infos.add(new CardviewInfo("레페 루비","맥주","에일"));
        infos.add(new CardviewInfo("레페 브라운","맥주","에일"));
        infos.add(new CardviewInfo("레페 브론드","맥주","에일"));


        recyclerView =view.findViewById(R.id.search_recyclerView);
        gridLayoutManager=new GridLayoutManager(context,2);
        RecyclerAdapter adapter =new RecyclerAdapter(getContext(),infos);
        recyclerView.setLayoutManager(gridLayoutManager);

        searchView =view.findViewById(R.id.searchview);
        searchView.setQueryHint("주종 및 이름을 검색하세요.");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                RecyclerAdapter adapter =new RecyclerAdapter(getContext(),infos);
                recyclerView.setAdapter(adapter);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {


                return true;
            }
        });



        return view;
    }





}
