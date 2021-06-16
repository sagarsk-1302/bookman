package com.example.bookman.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookman.R;
import com.example.bookman.adapter.GenreAdapter;
import com.example.bookman.models.Book;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.api.LogDescriptor;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import static com.google.firebase.firestore.FieldPath.documentId;

public class GenreActivity extends AppCompatActivity {
    String genre;
    FirebaseFirestore firebaseFirestore;
    private GenreAdapter adapter;
    RecyclerView recyclerView;
    TextView noresult;
    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        Intent intent = getIntent();
        setContentView(R.layout.genre_default);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        noresult =(TextView) findViewById(R.id.tv_noresult);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        genre = intent.getStringExtra("genre");
        getSupportActionBar().setTitle(genre.toUpperCase());
        firebaseFirestore = FirebaseFirestore.getInstance();
        Query query =firebaseFirestore.collection("books").document(genre).collection("book")
                .orderBy("bookname");
        FirestoreRecyclerOptions<Book> books =new FirestoreRecyclerOptions.Builder<Book>().setQuery(query,Book.class).build();
        Log.d("TAG", "onCreate: "+query.toString());
        adapter = new GenreAdapter(this,books);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
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
