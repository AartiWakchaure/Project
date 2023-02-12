package com.example.adminsmartvillage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.adminsmartvillage.doctor.ManageDoctor;
import com.example.adminsmartvillage.news.DeleteNewsActivity;
import com.example.adminsmartvillage.news.UploadNewsActivity;
import com.example.adminsmartvillage.posts.ManagePostActivity;
import com.example.adminsmartvillage.schemes.ManageSchemesActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    CardView uploadNews, manageDoctors, deleteNews, managePosts, manageSchemes;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.teal_700)));

        uploadNews = findViewById(R.id.uploadNews);
        manageDoctors = findViewById(R.id.manageDoctors);
        deleteNews = findViewById(R.id.deleteNews);
        managePosts = findViewById(R.id.managePosts);
        manageSchemes = findViewById(R.id.manageSchemes);

        uploadNews.setOnClickListener(this);
        manageDoctors.setOnClickListener(this);
        deleteNews.setOnClickListener(this);
        managePosts.setOnClickListener(this);
        manageSchemes.setOnClickListener(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.logout:
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;

        }
        return false;
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId())
        {
            case R.id.uploadNews:
                intent = new Intent(MainActivity.this, UploadNewsActivity.class);
                startActivity(intent);
                break;
            case R.id.manageDoctors:
                intent = new Intent(MainActivity.this, ManageDoctor.class);
                startActivity(intent);
                break;
            case R.id.deleteNews:
                intent = new Intent(MainActivity.this, DeleteNewsActivity.class);
                startActivity(intent);
                break;
            case R.id.managePosts:
                intent = new Intent(MainActivity.this, ManagePostActivity.class);
                startActivity(intent);
                break;
            case R.id.manageSchemes:
                intent = new Intent(MainActivity.this, ManageSchemesActivity.class);
                startActivity(intent);
                break;
        }
    }
}