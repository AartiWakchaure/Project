package com.example.smartvillage.news;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.smartvillage.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity {
    private RecyclerView newsRecycler;
    private ProgressBar progressBar;
    private ArrayList<NewsData> list;
    private NewsAdapter newsAdapter;

    private DatabaseReference reference,dRef;
    private StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        getSupportActionBar().setTitle("News");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.teal_700)));


        reference = FirebaseDatabase.getInstance().getReference().child("News");

        newsRecycler = findViewById(R.id.newsRecycler);
        progressBar = findViewById(R.id.progressBar);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        newsRecycler.setLayoutManager(layoutManager);
        newsRecycler.setHasFixedSize(true);

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
                newsAdapter = new NewsAdapter(NewsActivity.this, list);
                newsAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                newsRecycler.setAdapter(newsAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(NewsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}