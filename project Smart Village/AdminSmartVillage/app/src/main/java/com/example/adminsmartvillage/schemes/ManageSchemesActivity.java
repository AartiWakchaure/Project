package com.example.adminsmartvillage.schemes;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.adminsmartvillage.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ManageSchemesActivity extends AppCompatActivity {
    FloatingActionButton addSchemeButton;
    private RecyclerView deleteSchemeRecycler;
    private ProgressBar progressBar;
    private ArrayList<SchemeData> list;
    private SchemeAdapter schemeAdapter;
    private TextView readMore;
    private DatabaseReference reference,dRef;
    private StorageReference storageReference;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_schemes);
        getSupportActionBar().setTitle("Manage Schemes");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.teal_700)));

        reference = FirebaseDatabase.getInstance().getReference().child("Schemes");

        deleteSchemeRecycler = findViewById(R.id.deleteSchemeRecycler);
        progressBar = findViewById(R.id.progressBar);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        deleteSchemeRecycler.setLayoutManager(linearLayoutManager);
        deleteSchemeRecycler.setHasFixedSize(true);

        getScheme();

        addSchemeButton = findViewById(R.id.addSchemeButton);

        addSchemeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManageSchemesActivity.this, AddSchemeActivity.class);
                startActivity(intent);
            }
        });
    }
    private void getScheme() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<SchemeData>();
                for(DataSnapshot snapshot: dataSnapshot.getChildren())
                {
                    SchemeData data = snapshot.getValue(SchemeData.class);
                    list.add(data);
                }
                schemeAdapter = new SchemeAdapter(ManageSchemesActivity.this, list);
                schemeAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                deleteSchemeRecycler.setAdapter(schemeAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(ManageSchemesActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}