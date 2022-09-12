package org.androidtown.alcohol.Community;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.androidtown.alcohol.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WritingActivity extends AppCompatActivity {
    TextView  toolbar_name_tv;
    Toolbar toolbar;

    CommunityListviewItem listitem;


    DatabaseReference dr;

    Spinner category;
    EditText title_et, text_et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing);

         toolbar = (Toolbar)findViewById(R.id.writing_toobar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar() ;
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowTitleEnabled(false);
        ab.setHomeAsUpIndicator(R.drawable.ic_back_24dp);

        toolbar_name_tv =findViewById(R.id.writing_toolbar_tv);


        category= findViewById(R.id.writing_spinner);
        title_et =findViewById(R.id.writing_title_et);
        text_et =findViewById(R.id.writing_text_et);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.writing_actionbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:  finish();
            case R.id.writing_submit_btn:
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", java.util.Locale.getDefault());
                Date date = new Date();
                String strDate = dateFormat.format(date);
                String titlel = title_et.getText().toString();
                String  textl = text_et.getText().toString();
                listitem = new CommunityListviewItem("가자",category.getSelectedItem().toString(),strDate,"0",titlel,"0",textl);
                 dr = FirebaseDatabase.getInstance().getReference().child("community");
                dr.child(listitem.getDate()).setValue(listitem);
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
