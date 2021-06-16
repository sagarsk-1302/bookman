package com.example.bookman.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.bookman.R;
import com.example.bookman.activities.uploader.UploaderHome;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {
    ImageButton search_btn;
    ImageView categories_btn, uploaderbtn;
    EditText search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);
        search_btn = (ImageButton) findViewById(R.id.search_btn);
        categories_btn = (ImageView) findViewById(R.id.browsecat_btn);
        uploaderbtn = (ImageView) findViewById(R.id.uploadhome_iv);
        search = (EditText) findViewById(R.id.search_et);
        categories_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CategoriesActivity.class);
                startActivity(intent);
            }
        });
        uploaderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UploaderHome.class);
                startActivity(intent);
            }
        });
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String querystring = search.getText().toString().trim();
                if(querystring.length()>3) {
                    Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                    intent.putExtra("querystring", querystring);
                    startActivity(intent);
                }else{
                    Toast.makeText(MainActivity.this,"Enter 3 or more characters to search.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}