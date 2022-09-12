package org.androidtown.alcohol.LoginAndJoin;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.credentials.HintRequest;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.androidtown.alcohol.R;

public class IDFindActivity extends AppCompatActivity implements View.OnClickListener {
    //define view objects
    private EditText editTextUserPhone;
    private Button buttonFind;
    private TextView textviewMessage;
    private ProgressDialog progressDialog;
    //define firebase object
    private FirebaseAuth firebaseAuth;
    private Button HomeBtn;
    private DatabaseReference mDatabase;

    Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_id);

        toolbar = (Toolbar)findViewById(R.id.findid_toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar() ;
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowTitleEnabled(false);
        ab.setHomeAsUpIndicator(R.drawable.ic_back_24dp);






        editTextUserPhone = (EditText) findViewById(R.id.editTextUserPhone);
        buttonFind = (Button) findViewById(R.id.buttonFindID);
        HomeBtn=(Button)findViewById(R.id.home);
        firebaseAuth = FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();
        HomeBtn.setOnClickListener(this);
        buttonFind.setOnClickListener(this);
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
                Intent intent =new Intent(IDFindActivity.this, LoginActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,LoginActivity.class));
    }

    @Override
    public void onClick(View view) {
        if(view==buttonFind){
            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String check = dataSnapshot.child("FindInfo").child("Phone").getValue(String.class);
                    String input = editTextUserPhone.getText().toString().trim();
                    if (input.equals(check)) {
                        String ID = dataSnapshot.child("FindInfo").child("ID").getValue(String.class);
                        Intent intent=new Intent(getApplicationContext(),IDFindResultActivity.class);
                        intent.putExtra("ID",ID);
                        startActivity(intent);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}
