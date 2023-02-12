package com.example.adminsmartvillage.doctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.adminsmartvillage.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ManageDoctor extends AppCompatActivity {
    FloatingActionButton addDoctorBtn;
    private RecyclerView mbbs, bhms, byns, bums, others;
    private LinearLayout mbbsNoData, bhmsNoData, bynsNoData, bumsNoData, othersNoData;
    private List<DoctorData> list1, list2, list3, list4, list5;
    private DatabaseReference reference, dbRef;

    private DoctorAdapter adapter;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_doctor);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Manage Doctor");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.teal_700)));

        mbbs = findViewById(R.id.mbbs);
        bhms = findViewById(R.id.bhms);
        byns = findViewById(R.id.byns);
        bums = findViewById(R.id.bums);
        others = findViewById(R.id.others);

        mbbsNoData = findViewById(R.id.mbbsNoData);
        bhmsNoData = findViewById(R.id.bhmsNoData);
        bynsNoData = findViewById(R.id.bynsNoData);
        bumsNoData = findViewById(R.id.bumsNoData);
        othersNoData = findViewById(R.id.othersNoData);

        reference = FirebaseDatabase.getInstance().getReference().child("Doctors");

        mbbs();
        bhms();
        byns();
        bums();
        others();


        addDoctorBtn = findViewById(R.id.addDoctorBtn);

        addDoctorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManageDoctor.this, AddDoctorActivity.class);
                startActivity(intent);
            }
        });
    }

    private void mbbs() {
        dbRef = reference.child("MBBS");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list1 = new ArrayList<>();
                if(!dataSnapshot.exists())
                {
                    mbbsNoData.setVisibility(View.VISIBLE);
                    mbbs.setVisibility(View.GONE);
                }
                else
                {
                    mbbsNoData.setVisibility(View.GONE);
                    mbbs.setVisibility(View.VISIBLE);
                    for(DataSnapshot snapshot:dataSnapshot.getChildren())
                    {
                        DoctorData data = snapshot.getValue(DoctorData.class);
                        list1.add(data);
                    }
                    mbbs.setHasFixedSize(true);
                    mbbs.setLayoutManager(new LinearLayoutManager(ManageDoctor.this));
                    adapter = new DoctorAdapter(list1,ManageDoctor.this,"MBBS");
                    mbbs.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ManageDoctor.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void bhms() {
        dbRef = reference.child("BHMS");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list2 = new ArrayList<>();
                if(!dataSnapshot.exists())
                {
                    bhmsNoData.setVisibility(View.VISIBLE);
                    bhms.setVisibility(View.GONE);
                }
                else
                {
                    bhmsNoData.setVisibility(View.GONE);
                    bhms.setVisibility(View.VISIBLE);
                    for(DataSnapshot snapshot:dataSnapshot.getChildren())
                    {
                        DoctorData data = snapshot.getValue(DoctorData.class);
                        list2.add(data);
                    }
                    bhms.setHasFixedSize(true);
                    bhms.setLayoutManager(new LinearLayoutManager(ManageDoctor.this));
                    adapter = new DoctorAdapter(list2,ManageDoctor.this,"BHMS");
                    bhms.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ManageDoctor.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void byns() {
        dbRef = reference.child("BYNS");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list3 = new ArrayList<>();
                if(!dataSnapshot.exists())
                {
                    bynsNoData.setVisibility(View.VISIBLE);
                    byns.setVisibility(View.GONE);
                }
                else
                {
                    bynsNoData.setVisibility(View.GONE);
                    byns.setVisibility(View.VISIBLE);
                    for(DataSnapshot snapshot:dataSnapshot.getChildren())
                    {
                        DoctorData data = snapshot.getValue(DoctorData.class);
                        list3.add(data);
                    }
                    byns.setHasFixedSize(true);
                    byns.setLayoutManager(new LinearLayoutManager(ManageDoctor.this));
                    adapter = new DoctorAdapter(list3,ManageDoctor.this,"BYNS");
                    byns.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ManageDoctor.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void bums() {
        dbRef = reference.child("BUMS");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list4 = new ArrayList<>();
                if(!dataSnapshot.exists())
                {
                    bumsNoData.setVisibility(View.VISIBLE);
                    bums.setVisibility(View.GONE);
                }
                else
                {
                    bumsNoData.setVisibility(View.GONE);
                    bums.setVisibility(View.VISIBLE);
                    for(DataSnapshot snapshot:dataSnapshot.getChildren())
                    {
                        DoctorData data = snapshot.getValue(DoctorData.class);
                        list4.add(data);
                    }
                    bums.setHasFixedSize(true);
                    bums.setLayoutManager(new LinearLayoutManager(ManageDoctor.this));
                    adapter = new DoctorAdapter(list4,ManageDoctor.this,"BUMS");
                    bums.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ManageDoctor.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void others() {
        dbRef = reference.child("Others");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list5 = new ArrayList<>();
                if(!dataSnapshot.exists())
                {
                    othersNoData.setVisibility(View.VISIBLE);
                    others.setVisibility(View.GONE);
                }
                else
                {
                    othersNoData.setVisibility(View.GONE);
                    others.setVisibility(View.VISIBLE);
                    for(DataSnapshot snapshot:dataSnapshot.getChildren())
                    {
                        DoctorData data = snapshot.getValue(DoctorData.class);
                        list5.add(data);
                    }
                    others.setHasFixedSize(true);
                    others.setLayoutManager(new LinearLayoutManager(ManageDoctor.this));
                    adapter = new DoctorAdapter(list5,ManageDoctor.this,"Others");
                    others.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ManageDoctor.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}