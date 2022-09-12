package org.androidtown.alcohol.LoginAndJoin;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.androidtown.alcohol.R;

public class IDFindResultActivity extends AppCompatActivity implements View.OnClickListener{
    TextView ResultID;
    Button Login;
    Button FindPassword;
    String Id;

    Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_find_result);


        ResultID=findViewById(R.id.id_result);
        Login=findViewById(R.id.go_login);
        FindPassword=findViewById(R.id.go_find_password);
        ///받아온 id 보여주기(텍스트뷰)///
        Intent intent=getIntent();
        Id=intent.getExtras().getString("ID");
        ResultID.setText(Id);
        Login.setOnClickListener(this);
        FindPassword.setOnClickListener(this);


        toolbar = (Toolbar)findViewById(R.id.findresult_toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar() ;
        ab.setDisplayShowTitleEnabled(false);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(this,LoginActivity.class));
    }

    @Override
    public void onClick(View view) {
        if(view==Login){
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }
        if(view==FindPassword){
            finish();
            startActivity(new Intent(this,PasswordFindActivity.class));
        }
    }
}
