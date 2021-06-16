package com.example.bookman.activities.uploader;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import static  com.example.bookman.Constants.*;
import com.example.bookman.R;
import com.example.bookman.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UploaderSignin extends AppCompatActivity {
    ImageView signin,signup;
    EditText email,password;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = db.collection("users");
        setContentView(R.layout.uploader_signin);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        signup = (ImageView) findViewById(R.id.signup);
        signin = (ImageView) findViewById(R.id.signin);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UploaderSignin.this,UploaderRegister.class);
                startActivity(intent);
            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().trim().length() > 0 && password.getText().toString().trim().length() > 0) {
                    collectionReference.document(email.getText().toString().trim()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            DocumentSnapshot snapshot = task.getResult();
                            if (snapshot != null && snapshot.exists()) {
                                User user = snapshot.toObject(User.class);
                                if (user.getPassword().equals(password.getText().toString().trim())) {
                                    SharedPreferences sharedPreferences = getSharedPreferences(APPNAME, MODE_PRIVATE);
                                    SharedPreferences.Editor edit = sharedPreferences.edit();
                                    edit.putString(USERNAME, user.getUsername());
                                    edit.putString(EMAIL, user.getEmail());
                                    edit.putBoolean(ISLOGGEDIN, true);
                                    edit.apply();
                                    Intent intent = new Intent(UploaderSignin.this, UploaderHome.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(UploaderSignin.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(UploaderSignin.this, "Account does not exist, please sign up.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UploaderSignin.this, "Some error occured", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    Toast.makeText(UploaderSignin.this,"Enter the credentials properly",Toast.LENGTH_SHORT).show();
                }
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
