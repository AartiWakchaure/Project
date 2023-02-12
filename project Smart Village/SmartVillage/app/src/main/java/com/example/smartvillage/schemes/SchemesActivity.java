package com.example.smartvillage.schemes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartvillage.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class SchemesActivity extends AppCompatActivity {

    private RecyclerView schemeRecycler;
    private ProgressBar progressBar;
    private ArrayList<SchemeData> list;
    private SchemeAdapter schemeAdapter;
    private TextView readMore;
    private DatabaseReference reference,dRef;
    private StorageReference storageReference;

    public SchemesActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schemes);

        getSupportActionBar().setTitle("Schemes");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.teal_700)));

        reference = FirebaseDatabase.getInstance().getReference().child("Schemes");

        schemeRecycler = findViewById(R.id.schemeRecycler);
        progressBar = findViewById(R.id.progressBar);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        schemeRecycler.setLayoutManager(linearLayoutManager);
        schemeRecycler.setHasFixedSize(true);

        getScheme();


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
                schemeAdapter = new SchemeAdapter(SchemesActivity.this, list);
                schemeAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                schemeRecycler.setAdapter(schemeAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(SchemesActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}