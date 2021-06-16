package com.example.bookman.activities.uploader;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookman.R;
import com.example.bookman.adapter.GenreAdapter;
import com.example.bookman.models.Book;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

import static com.example.bookman.Constants.APPNAME;
import static com.example.bookman.Constants.EMAIL;

public class ManageBooks extends AppCompatActivity {
    FirebaseFirestore firebaseFirestore;
    private GenreAdapter adapter;
    TextView noresult;
    RecyclerView recyclerView;
    private ArrayList<Map<String,Object>> booksObject = new ArrayList<Map<String,Object>>();
    @Override
    protected void onCreate(Bundle savedInsatance){
        super.onCreate(savedInsatance);
        setContentView(R.layout.genre_default);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        SharedPreferences sharedPreferences = getSharedPreferences(APPNAME,MODE_PRIVATE);
        String email = sharedPreferences.getString(EMAIL,"nomail");
        booksObject.clear();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        noresult =(TextView) findViewById(R.id.tv_noresult);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        firebaseFirestore = FirebaseFirestore.getInstance();
        Query query =firebaseFirestore.collection("users").document(email).collection("books_published")
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
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
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
