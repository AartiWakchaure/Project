package com.example.smartvillage.posts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.smartvillage.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class VillagePostsActivity extends AppCompatActivity {
    FloatingActionButton addPostBtn;
    private RecyclerView postRecycler;
    private ProgressBar progressBar;
    private ArrayList<PostData> list;
    private PostAdapter postAdapter;

    private DatabaseReference reference,dRef;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_village_posts);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Posts");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.teal_700)));

        reference = FirebaseDatabase.getInstance().getReference().child("Posts");

        postRecycler = findViewById(R.id.postRecycler);
        progressBar = findViewById(R.id.progressBar);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        postRecycler.setLayoutManager(layoutManager);
        postRecycler.setHasFixedSize(true);

        getPost();


        addPostBtn = findViewById(R.id.addPostBtn);

        addPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VillagePostsActivity.this, AddPostActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getPost() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<PostData>();
                for(DataSnapshot snapshot: dataSnapshot.getChildren())
                {
                    PostData data = snapshot.getValue(PostData.class);
                    list.add(data);
                }
                postAdapter = new PostAdapter(VillagePostsActivity.this, list);
                postAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                postRecycler.setAdapter(postAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(VillagePostsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}