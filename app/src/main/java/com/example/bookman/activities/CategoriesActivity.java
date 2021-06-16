package com.example.bookman.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookman.R;

public class CategoriesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void genre(View view){
        Intent intent= new Intent(CategoriesActivity.this,GenreActivity.class);
                switch (view.getId()){
                    case R.id.index_iv:
                        intent.putExtra("genre","allbooks");
                        break;
                    case R.id.adventure_iv:
                        intent.putExtra("genre","adventure");
                        break;
                    case R.id.romance_iv:
                        intent.putExtra("genre","romance");
                        break;
                    case R.id.crime_iv:
                        intent.putExtra("genre","crime");
                        break;
                    case R.id.comedy_iv:
                        intent.putExtra("genre","comedy");
                        break;
                    case R.id.fantasy_iv:
                        intent.putExtra("genre","fantasy");
                        break;
                    case R.id.scifi_iv:
                        intent.putExtra("genre","scifi");
                        break;
                    case R.id.bio_iv:
                        intent.putExtra("genre","biography");
                        break;
                }
        startActivity(intent);

    }
}
