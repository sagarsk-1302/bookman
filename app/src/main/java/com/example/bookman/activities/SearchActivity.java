package com.example.bookman.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookman.R;
import com.example.bookman.adapter.GenreAdapter;
import com.example.bookman.models.Book;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import org.w3c.dom.Text;

public class SearchActivity extends AppCompatActivity {
    FirebaseFirestore firebaseFirestore;
    TextView searchstring,noresult;
    RecyclerView recyclerview;
    private GenreAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        firebaseFirestore = FirebaseFirestore.getInstance();
        Intent intent = getIntent();
        String queryString = intent.getStringExtra("querystring");
        setContentView(R.layout.search_layout);
        searchstring = (TextView) findViewById(R.id.search_string);
        recyclerview =(RecyclerView) findViewById(R.id.recyclerview_search);
        noresult =(TextView) findViewById(R.id.tv_noresult);
        searchstring.setText(queryString.trim());
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Query query = firebaseFirestore.collection("books").document("allbooks").collection("book")
                .whereGreaterThanOrEqualTo("bookname", queryString.trim());
        FirestoreRecyclerOptions<Book> books =new FirestoreRecyclerOptions.Builder<Book>().setQuery(query,Book.class).build();
        Log.d("TAG", "onCreate: "+query.toString());
        adapter = new GenreAdapter(this,books);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(adapter);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
}
