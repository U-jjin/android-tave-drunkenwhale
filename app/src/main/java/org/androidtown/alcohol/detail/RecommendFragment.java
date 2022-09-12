package org.androidtown.alcohol;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;


/**
 * A simple {@link androidx.fragment.app.Fragment} subclass.
 */
public class RecommendFragment extends Fragment implements OnBackPressedListener{

    Context context;
    public RecommendFragment() {
        // Required empty public constructor
    }

    public static RecommendFragment newInstance(){
        Bundle args =new Bundle();
        RecommendFragment recommendFragment =new RecommendFragment();

        return recommendFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context=container.getContext();
        return inflater.inflate(R.layout.fragment_recommend, container, false);
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
