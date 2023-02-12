package com.example.smartvillage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    private final int REQ = 1;
    private Bitmap bitmap = null;
    private EditText inputFullName, inputEmail, inputMobileNumber, inputPassword, inputConfirmPassword, inputAge;
    private Spinner spGender, spOccupation, spMarital;
    private Button btnRegister;
    private String mbPattern = "[6-9][0-9]{9}";
    String downloadUrl = "";
    private ProgressDialog progressDialog;
    private String fullName, email, mobileNumber, password, confirmPassword, age, genderText, occupationText, maritalText;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private StorageReference storageReference;
    private DatabaseReference reference, dbRef;

    ArrayList<String> gender = new ArrayList<>();
    ArrayList<String> occupation = new ArrayList<>();
    ArrayList<String> marital = new ArrayList<>();


    @SuppressLint("SuspiciousIndentation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setTitle("Registration");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.teal_700)));


        inputFullName = findViewById(R.id.full_name);
        inputEmail = findViewById(R.id.email);
        inputMobileNumber = findViewById(R.id.mobile_number);
        inputPassword = findViewById(R.id.password);
        inputConfirmPassword = findViewById(R.id.confirm_password);
        inputAge = findViewById(R.id.age);

        spGender = findViewById(R.id.gender);
        spOccupation = findViewById(R.id.occupation);
        spMarital = findViewById(R.id.marital);

        btnRegister = findViewById(R.id.sv_registerbtn);

        progressDialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference();
        reference = FirebaseDatabase.getInstance().getReference();

        fullName = inputFullName.getText().toString();
        email = inputEmail.getText().toString();
        mobileNumber = inputMobileNumber.getText().toString();
        password = inputPassword.getText().toString();
        confirmPassword = inputConfirmPassword.getText().toString();
        age = inputAge.getText().toString();


            //Genders
            gender.add("Male");
            gender.add("Female");
            gender.add("Transgender");

            //Business Occupation
            occupation.add("Farmer");
            occupation.add("Student");
            occupation.add("Private Service");
            occupation.add("Government Service");
            occupation.add("Social Worker");
            occupation.add("Self Employment");
            occupation.add("Other");

            //Marital Status
            marital.add("Married");
            marital.add("Single");
            marital.add("Divorced");
            marital.add("Widowed");

            ArrayAdapter<String> ad1 = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, gender);
            spGender.setAdapter(ad1);
            ArrayAdapter<String> ad2 = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, occupation);
            spOccupation.setAdapter(ad2);
            ArrayAdapter<String> ad3 = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, marital);
            spMarital.setAdapter(ad3);

            int pos1 = spGender.getSelectedItemPosition();
            int pos2 = spOccupation.getSelectedItemPosition();
            int pos3 = spMarital.getSelectedItemPosition();

            genderText = gender.get(pos1);
            occupationText = occupation.get(pos2);
            maritalText = marital.get(pos3);



        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PerforAuth();
            }
        });

    }

    private void PerforAuth() {
        fullName = inputFullName.getText().toString();
        email = inputEmail.getText().toString();
        mobileNumber = inputMobileNumber.getText().toString();
        password = inputPassword.getText().toString();
        confirmPassword = inputConfirmPassword.getText().toString();
        age = inputAge.getText().toString();
        int pos1 = spGender.getSelectedItemPosition();
        int pos2 = spOccupation.getSelectedItemPosition();
        int pos3 = spMarital.getSelectedItemPosition();

        genderText = gender.get(pos1);
        occupationText = occupation.get(pos2);
        maritalText = marital.get(pos3);


        if(fullName.isEmpty())
        {
            inputFullName.setError("Name is required!");
            inputFullName.requestFocus();
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            inputEmail.setError("valid email address is required!");
            inputEmail.requestFocus();
        }
        else if(!mobileNumber.matches(mbPattern))
        {
            inputMobileNumber.setError("valid Mobile Number required!");
            inputMobileNumber.requestFocus();
        }
        else if(password.isEmpty() || password.length()<8)
        {
            inputPassword.setError("Password must be atleast 8 character/digits!");
            inputPassword.requestFocus();
        }
        else if(confirmPassword.isEmpty() || !confirmPassword.equals(password))
        {
            inputConfirmPassword.setError("Password is not matched!");
            inputConfirmPassword.requestFocus();
        }
        else
        {
            progressDialog.setMessage("Please wait while Registration...");
            progressDialog.show();
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        uploadData();
                        sendToNextActivity();
                        Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_LONG).show();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, "Already Registered...", Toast.LENGTH_LONG).show();
                    }
                }
            });


        }

    }

    private void uploadData(){
        dbRef = reference.child("Users");
        final String uniqueKey = dbRef.push().getKey();
        UserData userData = new UserData(fullName, email, mobileNumber, age, genderText, occupationText, maritalText, uniqueKey);
        dbRef.child(uniqueKey).setValue(userData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                progressDialog.dismiss();
                Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(RegisterActivity.this, "Something went Wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void sendToNextActivity() {
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


}