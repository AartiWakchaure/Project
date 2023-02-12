package com.example.smartvillage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    TextView inputFullName, inputEmail, inputMobileNumber, inputGender, inputOccupation, inputMarital;
    Button btnLogout;

    private FirebaseDatabase database;
    private DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.teal_700)));

        inputFullName = findViewById(R.id.inputFullName);
        inputMobileNumber = findViewById(R.id.inputMobileNumber);
        inputGender = findViewById(R.id.inputGender);
        inputOccupation = findViewById(R.id.inputOccupation);
        inputMarital = findViewById(R.id.inputMarital);
        inputEmail = findViewById(R.id.inputEmail);
        String email = MainActivity.inputEmail.getText().toString();

        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("Users");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren())
                {
                    if(ds.child("email").getValue().equals(email))
                    {
                        inputFullName.setText(ds.child("name").getValue(String.class));
                        inputEmail.setText(ds.child("email").getValue(String.class));
                        inputMobileNumber.setText(ds.child("mobileNumber").getValue(String.class));
                        inputGender.setText(ds.child("gender").getValue(String.class));
                        inputOccupation.setText(ds.child("occupation").getValue(String.class));
                        inputMarital.setText(ds.child("maritalStatus").getValue(String.class));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });



        //Bottom navigation view code
        bottomNavigationView = findViewById(R.id.navigation);

        bottomNavigationView.setSelectedItemId(R.id.profile);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class ));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.profile:
                        return true;
                    case R.id.contactUs:
                        startActivity(new Intent(getApplicationContext(), ContactUsActivity.class ));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

    }
}