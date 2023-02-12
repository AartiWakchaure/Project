package com.example.adminsmartvillage.news;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.adminsmartvillage.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class DeleteNewsActivity extends AppCompatActivity {
    private  RecyclerView deleteNewsRecycler;
    private ProgressBar progressBar;
    private ArrayList<NewsData> list;
    private NewsAdapter newsAdapter;

    private DatabaseReference reference,dRef;
    private StorageReference storageReference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_news);

        getSupportActionBar().setTitle("Delete News");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.teal_700)));


        reference = FirebaseDatabase.getInstance().getReference().child("News");

        deleteNewsRecycler = findViewById(R.id.deleteNewsRecycler);
        progressBar = findViewById(R.id.progressBar);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        deleteNewsRecycler.setLayoutManager(layoutManager);
        deleteNewsRecycler.setHasFixedSize(true);

        getNews();

    }

    private void getNews() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<>();
                for(DataSnapshot snapshot: dataSnapshot.getChildren())
                {
                    NewsData data = snapshot.getValue(NewsData.class);
                    list.add(data);
                }
                newsAdapter = new NewsAdapter(DeleteNewsActivity.this, list);
                newsAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                deleteNewsRecycler.setAdapter(newsAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(DeleteNewsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}