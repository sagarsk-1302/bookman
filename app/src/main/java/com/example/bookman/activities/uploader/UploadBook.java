package com.example.bookman.activities.uploader;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.bookman.R;
import com.example.bookman.models.Book;
import com.example.bookman.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.bookman.Constants.APPNAME;
import static com.example.bookman.Constants.EMAIL;

public class UploadBook extends AppCompatActivity {
    Uri pdfuri = null;
    Book book;
    String filename;
    private List<String> genre;
    TextView pdffilename;
    EditText bookname, authorname, publishedyear, aboutbook;
    ImageView pdfuploadicon,publish;
    private StorageReference storageRef;
    FirebaseFirestore firebaseFirestore;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstance) {
        book = new Book();
        firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference().child("uploadedpdfs");
        pdfuri = null;
        genre = new ArrayList<String>();
        super.onCreate(savedInstance);
        setContentView(R.layout.uploader_addbook);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        pdffilename = (TextView) findViewById(R.id.filename);
        bookname = (EditText) findViewById(R.id.book_name);
        authorname = (EditText) findViewById(R.id.author_name);
        publishedyear = (EditText) findViewById(R.id.publishedyear);
        aboutbook = (EditText) findViewById(R.id.about);
        pdfuploadicon = (ImageView) findViewById(R.id.select_pdf_iv);
        publish =(ImageView) findViewById(R.id.publish);
        pdfuploadicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                // We will be redirected to choose pdf
                galleryIntent.setType("application/pdf");
                startActivityForResult(galleryIntent, 1);
            }
        });
        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences(APPNAME,MODE_PRIVATE);
                String email = sharedPreferences.getString(EMAIL,"nomail");
                book.setFilename(filename);
                book.setBookname(bookname.getText().toString().trim());
                book.setAuthorname(authorname.getText().toString().trim());
                book.setGenre(genre);
                book.setPublishedyear(Integer.valueOf(publishedyear.getText().toString().trim()));
                book.setAbout(aboutbook.getText().toString().trim());
                book.setUploadedon(new Timestamp(new Date()));
                book.setEmail(email);
                sendpdftoStorage();
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode== Activity.RESULT_OK){
            pdfuri = data.getData();
            File file = new File(pdfuri.getPath());
            filename =file.getName().substring(8);
            pdffilename.setText(filename);
        }else{
            Toast.makeText(UploadBook.this, "Please select a pdf to upload", Toast.LENGTH_SHORT).show();
        }

    }

    public void genre(View view) {
        ImageView iv;
        switch (view.getId()) {
            case R.id.adventure_iv:
                iv = (ImageView) findViewById(R.id.adventure_iv);
                if (genre.contains("adventure")) {
                    iv.setImageDrawable(ContextCompat.getDrawable(UploadBook.this, R.drawable.adventure_unselected));
                    genre.remove("adventure");
                } else {
                    iv.setImageDrawable(ContextCompat.getDrawable(UploadBook.this, R.drawable.adventure_selected));
                    genre.add("adventure");
                }
                break;
            case R.id.romance_iv:
                iv = (ImageView) findViewById(R.id.romance_iv);
                if (genre.contains("romance")) {
                    iv.setImageDrawable(ContextCompat.getDrawable(UploadBook.this, R.drawable.romance_unselected));
                    genre.remove("romance");
                } else {
                    iv.setImageDrawable(ContextCompat.getDrawable(UploadBook.this, R.drawable.romance_selected));
                    genre.add("romance");
                }
                break;
            case R.id.crime_iv:
                iv = (ImageView) findViewById(R.id.crime_iv);
                if (genre.contains("crime")) {
                    iv.setImageDrawable(ContextCompat.getDrawable(UploadBook.this, R.drawable.crime_unselected));
                    genre.remove("crime");
                } else {
                    iv.setImageDrawable(ContextCompat.getDrawable(UploadBook.this, R.drawable.crime_selected));
                    genre.add("crime");
                }
                break;
            case R.id.comedy_iv:
                iv = (ImageView) findViewById(R.id.comedy_iv);
                if (genre.contains("comedy")) {
                    iv.setImageDrawable(ContextCompat.getDrawable(UploadBook.this, R.drawable.comedy_unselected));
                    genre.remove("comedy");
                } else {
                    iv.setImageDrawable(ContextCompat.getDrawable(UploadBook.this, R.drawable.comedy_selected));
                    genre.add("comedy");
                }
                break;
            case R.id.fantasy_iv:
                iv = (ImageView) findViewById(R.id.fantasy_iv);
                if (genre.contains("fantasy")) {
                    iv.setImageDrawable(ContextCompat.getDrawable(UploadBook.this, R.drawable.fantasy_unselected));
                    genre.remove("fantasy");
                } else {
                    iv.setImageDrawable(ContextCompat.getDrawable(UploadBook.this, R.drawable.fantasy_selected));
                    genre.add("fantasy");
                }
                break;
            case R.id.scifi_iv:
                iv = (ImageView) findViewById(R.id.scifi_iv);
                if (genre.contains("scifi")) {
                    iv.setImageDrawable(ContextCompat.getDrawable(UploadBook.this, R.drawable.scifi_unselected));
                    genre.remove("scifi");
                } else {
                    iv.setImageDrawable(ContextCompat.getDrawable(UploadBook.this, R.drawable.scifi_selected));
                    genre.add("scifi");
                }
                break;
            case R.id.biography_iv:
                iv = (ImageView) findViewById(R.id.biography_iv);
                if (genre.contains("biography")) {
                    iv.setImageDrawable(ContextCompat.getDrawable(UploadBook.this, R.drawable.biography_unselected));
                    genre.remove("biography");
                } else {
                    iv.setImageDrawable(ContextCompat.getDrawable(UploadBook.this, R.drawable.biography_selected));
                    genre.add("biography");
                }
                break;
        }
    }
    private void sendpdftoStorage(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading");
        progressDialog.show();
        File file = new File(pdfuri.getPath());
        String filename =file.getName().substring(8);
        SharedPreferences sharedPreferences = getSharedPreferences(APPNAME,MODE_PRIVATE);
        StorageReference storageReference = storageRef.child(sharedPreferences.getString(EMAIL,"nomail")).child(filename);
        UploadTask uploadTask = storageReference.putFile(pdfuri);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> urlTask =taskSnapshot.getStorage().getDownloadUrl();
                while (!urlTask.isSuccessful()) ;
                progressDialog.dismiss();
                Uri downloadUrl = urlTask.getResult();
                String pdfUrl = downloadUrl.toString();
                Log.d("TAG", "onSuccess: " + pdfUrl);
                sendTofirebase(pdfUrl);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(UploadBook.this,"Error in uploading",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void sendTofirebase(String pdfurl){
        book.setFileurl(pdfurl);
        DocumentReference documentReference = firebaseFirestore.collection("books").document("allbooks");
        documentReference.collection("book").add(book).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                String id = documentReference.getId();
                documentReference.update("documentId",id);
                book.setDocumentId(id);
                updateUser(id);
                updateGenre(id);
                Toast.makeText(UploadBook.this,"Book has been Published successfully",Toast.LENGTH_SHORT).show();
                setContentView(R.layout.uploader_addbook);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }
    public void updateUser(String id){
        SharedPreferences sharedPreferences = getSharedPreferences(APPNAME,MODE_PRIVATE);
        DocumentReference documentReference = firebaseFirestore.collection("users")
                .document(sharedPreferences.getString(EMAIL,"nomail"));
         documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
             @Override
             public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                 if(task.isSuccessful()){
                     DocumentSnapshot document = task.getResult();
                     if(document.exists()){
                        User user = document.toObject(User.class);
                        List<String> uploadedbooks = user.getUploadedbooks();
                        uploadedbooks.add(id);
                        documentReference.collection("books_published").document(id).set(book)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("UploadBooks", "onSuccess: added to the users catalog");
                                    }
                                });
                        documentReference.update("uploadedbooks",uploadedbooks);
                     }
                 }
             }
         });
    }
    public void updateGenre(String id){
        for(String genreIndividual: genre){
            DocumentReference documentReference = firebaseFirestore.collection("books").document(genreIndividual);
            documentReference.collection("book").document(id).set(book).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d("UploadBooks", "the book is added to "+ genreIndividual);
                }
            });
        }
    }
}
