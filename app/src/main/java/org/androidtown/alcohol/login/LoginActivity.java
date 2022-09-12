package org.androidtown.alcohol.LoginAndJoin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;

import org.androidtown.alcohol.MainActivity;
import org.androidtown.alcohol.OnBackPressedListener;
import org.androidtown.alcohol.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, OnBackPressedListener {
    //define view objects
    EditText editTextID;
    EditText editTextPassword;
    Button buttonSignin;
    TextView textviewSingin;
    TextView textviewMessage;
    TextView textviewFindPassword;
    ProgressDialog progressDialog;
    TextView textViewFindId;
    //define firebase object
    FirebaseAuth firebaseAuth;
    SignInButton Google_Login;
    // 구글api클라이언트
    private GoogleSignInClient googleSignInClient;
    private static final int RC_SIGN_IN = 900;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //initializig firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            //이미 로그인 되었다면 이 액티비티를 종료함
            finish();
            //그리고 profile 액티비티를 연다.
            startActivity(new Intent(getApplicationContext(), MainActivity.class)); //추가해 줄 ProfileActivity
        }
        //initializing views
        editTextID = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        textviewSingin = (TextView) findViewById(R.id.textViewSignin);
        textviewFindPassword = (TextView) findViewById(R.id.textViewFindpassword);
        buttonSignin = (Button) findViewById(R.id.buttonSignup);
        textViewFindId=findViewById(R.id.textViewFindID);
        textViewFindId.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);

        //button click event
        buttonSignin.setOnClickListener(this);
        textviewSingin.setOnClickListener(this);
        textviewFindPassword.setOnClickListener(this);
        Google_Login = findViewById(R.id.Google_Login);//구글로그인 버튼
        Google_Login.setOnClickListener(this);
        // Google 로그인을 앱에 통합
        // GoogleSignInOptions 개체를 구성할 때 requestIdToken을 호출
       GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
        Google_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = googleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    //firebase userLogin method
    private void userLogin() {
        String email = editTextID.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "email을 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "password를 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("로그인중입니다. 잠시 기다려 주세요...");
        progressDialog.show();
        //logging in the user
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            Bundle bundle=new Bundle();
                            bundle.putString("ID",editTextID.getText().toString());
                            finish();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            Toast.makeText(getApplicationContext(), "비밀번호가 맞지 않습니다.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


    @Override
    public void onClick(View view) {
        if (view == buttonSignin) {
            userLogin();
        }
        if (view == textviewSingin) {//신규 회원가입버튼
            finish();
            startActivity(new Intent(this, JoinActivity.class));
        }
        if (view == textviewFindPassword) {
            finish();
            startActivity(new Intent(this, PasswordFindActivity.class));
        }
        if(view==textViewFindId){
            finish();
            startActivity(new Intent(this,IDFindActivity.class));
        }
    }//click
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                //구글 로그인 성공해서 파베에 인증
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            }
            else{
                //구글 로그인 실패
            }
        }
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct){
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(),null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "인증 실패", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(LoginActivity.this, "구글 로그인 인증 성공", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                    }
                });
    }


    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}

