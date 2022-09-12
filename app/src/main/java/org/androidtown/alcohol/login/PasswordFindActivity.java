package org.androidtown.alcohol.LoginAndJoin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import org.androidtown.alcohol.R;

public class PasswordFindActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "PasswordFindActivity";

    //define view objects
    private EditText editTextUserEmail;
    private Button buttonFind;
    private TextView textviewMessage;
    private ProgressDialog progressDialog;
    //define firebase object
    private FirebaseAuth firebaseAuth;
    private Button HomeBtn;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

        editTextUserEmail = (EditText) findViewById(R.id.editTextUserEmail);
        buttonFind = (Button) findViewById(R.id.buttonFind);
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        HomeBtn.setOnClickListener(this);
        buttonFind.setOnClickListener(this);

        toolbar = (androidx.appcompat.widget.Toolbar)findViewById(R.id.find_toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar() ;
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowTitleEnabled(false);
        ab.setHomeAsUpIndicator(R.drawable.ic_back_24dp);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent =new Intent(PasswordFindActivity.this, LoginActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if (view == buttonFind) {
            progressDialog.setMessage("처리중입니다. 잠시 기다려 주세요...");
            progressDialog.show();
            //비밀번호 재설정 이메일 보내기
            String emailAddress = editTextUserEmail.getText().toString().trim();
            firebaseAuth.sendPasswordResetEmail(emailAddress)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(PasswordFindActivity.this, "이메일을 보냈습니다.", Toast.LENGTH_LONG).show();
                                finish();
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            } else {
                                Toast.makeText(PasswordFindActivity.this, "메일 보내기 실패!", Toast.LENGTH_LONG).show();
                            }
                            progressDialog.dismiss();
                        }
                    });

        }//find버튼
        else if(view==HomeBtn){
            //로그인 화면으로 가기
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        }
    }
}
