package com.example.bookman.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bookman.R;
import com.example.bookman.models.Book;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import static com.example.bookman.Constants.APPNAME;
import static com.example.bookman.Constants.EMAIL;

public class DownloadBook extends AppCompatActivity {
    TextView bookname,authorname,publishedyear,about,genre;
    ImageView download,delete;
    FirebaseFirestore firebaseFirestore;
    String documentId;
    @Override
    protected void onCreate(Bundle savedInstance){
        firebaseFirestore = FirebaseFirestore.getInstance();
        super.onCreate(savedInstance);
        setContentView(R.layout.downloadbook);
        Intent intent = getIntent();
        Bundle book = intent.getExtras();
        bookname = (TextView) findViewById(R.id.bookname);
        authorname = (TextView) findViewById(R.id.author_name);
        publishedyear = (TextView) findViewById(R.id.published_year);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        genre = (TextView) findViewById(R.id.genre);
        about = (TextView) findViewById(R.id.about);
        download = (ImageView) findViewById(R.id.download);
        bookname.setText(book.getString("bookname").trim());
        authorname.setText(book.getString("authorname").trim());
        publishedyear.setText(book.getInt("publishedyear")+"");
        documentId = (book.getString("documentId").trim());
        String genrestring ="";
        int i=0;
        if(book.getStringArrayList("genre")!=null) {
            for (; i < book.getStringArrayList("genre").size() - 1; i++) {
                genrestring += book.getStringArrayList("genre").get(i)+" ,";
            }
        }
        genrestring+= book.getStringArrayList("genre").get(i);
        genre.setText(genrestring);
        about.setText(book.getString("about"));
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = book.getString("documenturl");
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
