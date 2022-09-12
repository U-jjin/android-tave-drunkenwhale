package org.androidtown.alcohol.LoginAndJoin;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.androidtown.alcohol.R;
import org.androidtown.alcohol.SearchClasses.DBIDSearch;

public class JoinActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;

    EditText EmailID;
    EditText Password;
    EditText CheckPassword;
    EditText Name;
    EditText NickName;
    EditText PhoneNumber;
    Button JoinButton;
    FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;
    DBIDSearch dbidSearch;
    boolean Duplicated=false;//id 중보검사기
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);


        EmailID=findViewById(R.id.input_id); //아이디 입력
        Password=findViewById(R.id.input_password);
        CheckPassword=findViewById(R.id.check_password);
        Name=findViewById(R.id.name);
        NickName=findViewById(R.id.nickname);
        PhoneNumber=findViewById(R.id.phone_number);
        JoinButton=findViewById(R.id.join_btn);
        firebaseAuth=FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        JoinButton.setOnClickListener(this);

        toolbar = (Toolbar)findViewById(R.id.join_toolbar);
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
            case android.R.id.home:  finish();
        }
        return super.onOptionsItemSelected(item);
    }




    private boolean checkPassword(){
        if(!Password.getText().toString().equals(CheckPassword.getText().toString())){
            Toast.makeText(this,"비밀번호가 일치하지 않습니다.",Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }
    }
    //firebase userLogin method
    private void userLogin() {
        String email = EmailID.getText().toString().trim();
        String password = Password.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "email을 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "password를 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
    }
    // 회원가입
    private void createUser(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // 회원가입 성공
                            Toast.makeText(JoinActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();

                        } else {
                            // 회원가입 실패
                            Toast.makeText(JoinActivity.this, "회원가입 실패", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    //중복검사 iD중복검사
    private void duplicateIdAndNickName(){
        dbidSearch=new DBIDSearch(EmailID.getText().toString().trim());
        mDatabase.child("Info").child(dbidSearch.getID()).child("ID").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Duplicated=false;
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    if(dbidSearch.getID().contains(snapshot.getKey())){
                        Duplicated=true;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    @Override
    public void onClick(View view) {
        if(view==JoinButton){
            if(checkPassword()){
                userLogin();
                duplicateIdAndNickName();
                dbidSearch=new DBIDSearch(EmailID.getText().toString().trim());
                if(!Duplicated){
                    mDatabase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            mDatabase.child("Info").child(dbidSearch.getID()).child("ID").setValue(EmailID.getText().toString().trim());
                            mDatabase.child("Info").child(dbidSearch.getID()).child("password").setValue(Password.getText().toString().trim());
                            mDatabase.child("Info").child(dbidSearch.getID()).child("name").setValue(Name.getText().toString().trim());
                            mDatabase.child("Info").child(dbidSearch.getID()).child("NickName").setValue(NickName.getText().toString().trim());
                            //mDatabase.child("Info").child(Email.getText().toString().trim()).child("Email").setValue(Email.getText().toString().trim());
                            mDatabase.child("Info").child(dbidSearch.getID()).child("Phone").setValue(PhoneNumber.getText().toString().trim());
                            mDatabase.child("FindInfo").child("Phone").setValue(PhoneNumber.getText().toString().trim());
                            mDatabase.child("FindInfo").child("ID").setValue(EmailID.getText().toString().trim());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    createUser(EmailID.getText().toString(),Password.getText().toString());
                    finish();
                    startActivity(new Intent(this,LoginActivity.class));
                }//중복 x
                else{
                    Toast.makeText(this,"중복된ID입니다.",Toast.LENGTH_LONG).show();
                }

            }
        }
    }
}
