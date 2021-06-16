package com.example.bookman.activities.uploader;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.bookman.R;
import com.example.bookman.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.List;

public class UploaderRegister extends AppCompatActivity {
    private FirebaseFirestore db;
    public static final String TAG="UploaderRegister";
    EditText username,email,password,confpassword;
    ImageView signup;
    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        setContentView(R.layout.uploader_signup);
        db = FirebaseFirestore.getInstance();
        username = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        confpassword = (EditText) findViewById(R.id.conf_password);
        signup = (ImageView) findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(password.getText().toString().trim().equals(confpassword.getText().toString().trim())){
                    DocumentReference documentReference = db.collection("users").document("email");
                    documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful()) {
                                DocumentSnapshot snapshot = task.getResult();
                                if (snapshot != null && snapshot.exists()) {
                                    Log.d(TAG, "user already exists");
                                    Toast.makeText(UploaderRegister.this, "User already Exits. Please Login", Toast.LENGTH_SHORT).show();
//                                Intent intent = new Intent(UploaderRegister.this,UploaderSignin.class);
//                                startActivity(intent);
//                                finish();
                                } else {
                                    User user = new User();
                                    user.setEmail(email.getText().toString().trim());
                                    user.setUsername(username.getText().toString().trim());
                                    List<String> list = new ArrayList<String>();
                                    user.setUploadedbooks(list);
                                    user.setPassword(password.getText().toString().trim());
                                    db.collection("users").document(email.getText().toString().trim()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(UploaderRegister.this, "Registered Successfully. Please login", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(UploaderRegister.this, "Error with registering", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }else {
                                Log.e(TAG, "onComplete: failed!", task.getException());
                            }
                        }
                    });
                }else{
                    Toast.makeText(UploaderRegister.this,"Password do not match",Toast.LENGTH_SHORT).show();
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