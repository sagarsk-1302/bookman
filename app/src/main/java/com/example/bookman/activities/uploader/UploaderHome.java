package com.example.bookman.activities.uploader;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.bookman.Constants;
import com.example.bookman.R;
import com.example.bookman.activities.MainActivity;
import com.google.firebase.firestore.FirebaseFirestore;

import static com.example.bookman.Constants.*;

public class UploaderHome extends AppCompatActivity {
    ImageView logout,addbook,manage;
    @Override
    protected void onCreate(Bundle savedInstance){
        SharedPreferences sharedPreferences = getSharedPreferences(APPNAME,MODE_PRIVATE);
        super.onCreate(savedInstance);
        setContentView(R.layout.uploader_home);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        logout = (ImageView) findViewById(R.id.logout1_iv);
        addbook = (ImageView) findViewById(R.id.addbook_iv);
        manage = (ImageView) findViewById(R.id.manage_iv);
        if(!sharedPreferences.getBoolean(ISLOGGEDIN,false)){
            Intent intent = new Intent(UploaderHome.this,UploaderSignin.class);
            startActivity(intent);
            finish();
        }
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.putBoolean(ISLOGGEDIN,false);
                edit.apply();
                finish();
            }
        });
      addbook.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent = new Intent(UploaderHome.this,UploadBook.class);
              startActivity(intent);
          }
      });
      manage.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent = new Intent(UploaderHome.this,ManageBooks.class);
              startActivity(intent);
          }
      });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }
}
