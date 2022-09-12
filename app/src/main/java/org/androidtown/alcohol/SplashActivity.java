package org.androidtown.alcohol;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import org.androidtown.alcohol.LoginAndJoin.LoginActivity;

public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Handler hd=new Handler();
        hd.postDelayed(new splashHandler(),3000);
    }
    private class splashHandler implements Runnable{
        @Override
        public void run() {
            startActivity(new Intent(getApplication(), LoginActivity.class));
            finish();
        }
    }

    @Override
    public void onBackPressed() {

    }
}
