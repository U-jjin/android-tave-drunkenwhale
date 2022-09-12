package org.androidtown.alcohol;

import android.Manifest;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import org.androidtown.alcohol.detail.DetailActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG="MainActivity";

    MenuInflater menuInflater;
    TextView toolbar_name_tv;

    ViewPager viewPager;

    TabLayout tabLayout;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //툴바
        toolbar = findViewById(R.id.main_toolbar);
        toolbar_name_tv =findViewById(R.id.main_toolbar_tv);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        //탭레이아웃과 뷰페이져 연결
        tabLayout=findViewById(R.id.main_tablayout);
        viewPager =findViewById(R.id.main_viewpager);

        MainPagerAdapter mainPagerAdapter =new MainPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(mainPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.search_select);
        tabLayout.getTabAt(1).setIcon(R.drawable.comment_non);
        tabLayout.getTabAt(2).setIcon(R.drawable.favorite_non);
        tabLayout.getTabAt(3).setIcon(R.drawable.profile_non);

        menuInflater = getMenuInflater();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {

                tabLayout.getTabAt(0).setIcon(R.drawable.search_non);
                tabLayout.getTabAt(1).setIcon(R.drawable.comment_non);
                tabLayout.getTabAt(2).setIcon(R.drawable.favorite_non);
                tabLayout.getTabAt(3).setIcon(R.drawable.profile_non);


                toolbar.getMenu().clear();
                switch(i){
                    case 0:   menuInflater.inflate(R.menu.main_actionbar_menu, toolbar.getMenu());
                        toolbar_name_tv.setText("SEARCH");
                        tabLayout.getTabAt(0).setIcon(R.drawable.search_select);
                        break;
                    case 1: menuInflater.inflate(R.menu.community_actionbar_menu, toolbar.getMenu());
                        toolbar_name_tv.setText("COMMUNITY");
                        tabLayout.getTabAt(1).setIcon(R.drawable.comment_select);
                        break;
                    case 2: menuInflater.inflate(R.menu.main_actionbar_menu, toolbar.getMenu());
                        toolbar_name_tv.setText("FAVORITES");
                        tabLayout.getTabAt(2).setIcon(R.drawable.favorite_select);
                        break;
                    case 3: menuInflater.inflate(R.menu.main_actionbar_menu, toolbar.getMenu());
                        toolbar_name_tv.setText("MY PROFILE");
                        tabLayout.getTabAt(3).setIcon(R.drawable.profile_select);
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });


        //requestReadExternalStoragePermission();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "권한 설정 완료");
            } else {
                Log.d(TAG, "권한 설정 요청");
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    @Override
    public void onBackPressed() {
        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        if (fragmentList != null) {
            //TODO: Perform your logic to pass back press here
            for(Fragment fragment : fragmentList){
                if(fragment instanceof OnBackPressedListener){
                    ((OnBackPressedListener)fragment).onBackPressed();
                }
            }
        }
    }

    // 권한 요청
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionsResult");
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
        }
    }

    //툴바 관련 함수
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_actionbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.community_reset_btn: tabLayout.getTabAt(1);break;
            case R.id.community_myboard_btn:  Intent intent =new Intent(MainActivity.this, DetailActivity.class);
                                            startActivity(intent);
                                                             break;
        }
        return super.onOptionsItemSelected(item);
    }

}
