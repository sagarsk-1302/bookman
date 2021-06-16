package com.example.bookman.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookman.R;
import com.example.bookman.activities.DownloadBook;
import com.example.bookman.activities.uploader.ViewBook;
import com.example.bookman.models.Book;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.ObservableSnapshotArray;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GenreAdapter extends FirestoreRecyclerAdapter<Book,GenreAdapter.GenreViewHolder> {
    Context context;

    public GenreAdapter(Context context, @NonNull FirestoreRecyclerOptions<Book> books){
        super(books);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull GenreViewHolder holder, int position, @NonNull Book model) {
        Log.d("TAG", "onBindViewHolder: "+model.getBookname());
        holder.setIsRecyclable(false);
        holder.bookname.setText(model.getBookname());
        holder.authorname.setText(model.getAuthorname());
        String genre="";
        if(model.getGenre()!=null) {
            for (String genreStr : model.getGenre()) {
                genre += genreStr + ", ";
            }
        }
        holder.genre.setText(genre);
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("bookname",model.getBookname());
                bundle.putString("authorname",model.getAuthorname());
                bundle.putInt("publishedyear",model.getPublishedyear());
                bundle.putString("about",model.getAbout());
                ArrayList<String> list = new ArrayList<>(model.getGenre());
                bundle.putStringArrayList("genre",list);
                bundle.putString("documenturl",model.getFileurl());
                bundle.putString("email",model.getEmail());
                bundle.putString("documentId",model.getDocumentId());
                bundle.putString("filename",model.getFilename());
                Log.d("TAG", "onClick: "+model.getEmail());
                if(context.getClass().getName().equals("com.example.bookman.activities.GenreActivity")){
                    Intent intent = new Intent(context, DownloadBook.class);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }else{
                    Intent intent = new Intent(context, ViewBook.class);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            }
        });
    }

    @NonNull
    @Override
    public GenreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_container, parent, false);
        return new GenreViewHolder(view);
    }



    public class GenreViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView bookname,authorname,genre;
        public ConstraintLayout container;
        public GenreViewHolder(@NonNull View itemView) {
            super(itemView);
            bookname = (TextView) itemView.findViewById(R.id.book_name_tv);
            authorname = (TextView) itemView.findViewById(R.id.author_tv);
            genre = (TextView) itemView.findViewById(R.id.genre);
            container = (ConstraintLayout) itemView.findViewById(R.id.container_item);
        }

        @Override
        public void onClick(View v) {
        }
    }
}
