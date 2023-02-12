package com.example.adminsmartvillage.doctor;

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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.adminsmartvillage.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AddDoctorActivity extends AppCompatActivity {
    private ImageView addDoctorImage;
    private EditText addDoctorName, addMobileNumber;
    private Spinner sp;
    private final int REQ = 1;
    private Bitmap bitmap = null;
    private String qualification;
    private String name, mobileNumber, downloadUrl = "";
    private String mobilePattern = "[6-9][0-9]{9}";
    private ProgressDialog pd;
    private StorageReference storageReference;
    private DatabaseReference reference, dbRef;


    Button addDoctor;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doctor);
        getSupportActionBar().setTitle("Add Doctor");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.teal_700)));

        addDoctorImage = findViewById(R.id.addDoctorImage);
        addDoctorName = findViewById(R.id.doctorName);
        addMobileNumber = findViewById(R.id.doctorMobile);
        sp = findViewById(R.id.qualificationCatagory);
        addDoctor = findViewById(R.id.addDoctor);

        pd = new ProgressDialog(this);

        reference = FirebaseDatabase.getInstance().getReference().child("Doctors");
        storageReference = FirebaseStorage.getInstance().getReference();

        String[] items = new String[] {"Select Qualification", "MBBS","BHMS", "BYNS", "BUMS", "Others"};
        sp.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,items));

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                qualification = sp.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        addDoctorImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }

        });

        addDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkValidation();
            }
        });

    }

    private void checkValidation() {
        name = addDoctorName.getText().toString();
        mobileNumber = addMobileNumber.getText().toString();

        if(name.isEmpty())
        {
            addDoctorName.setError("Name is Required");
            addDoctorName.requestFocus();
        }
        else if(!mobileNumber.matches(mobilePattern))
        {
            addMobileNumber.setError("Valid Mobile Number is Required");
            addMobileNumber.requestFocus();
        }
        else if(qualification.equals("Select Qualification"))
        {
            Toast.makeText(this, "Please provide doctor qualification", Toast.LENGTH_SHORT).show();
        }
        else if (bitmap == null)
        {
            pd.setMessage("Uploading...");
            pd.show();
            uploadData();
        }
        else
        {
            pd.setMessage("Uploading...");
            pd.show();
            uploadImage();
        }
    }
    private void uploadImage() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] finalimg = baos.toByteArray();
        final StorageReference filePath;
        filePath = storageReference.child("Doctors").child(finalimg+"jpg");
        final UploadTask uploadTask = filePath.putBytes(finalimg);
        uploadTask.addOnCompleteListener(AddDoctorActivity.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful())
                {
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadUrl = String.valueOf(uri);
                                    uploadData();
                                }
                            });
                        }
                    });
                }
                else
                {
                    pd.dismiss();
                    Toast.makeText(AddDoctorActivity.this, "Something went Wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void uploadData() {
        dbRef = reference.child(qualification);
        final String uniqueKey = dbRef.push().getKey();


        DoctorData doctorData = new DoctorData(name,mobileNumber,downloadUrl, uniqueKey);
        dbRef.child(uniqueKey).setValue(doctorData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                pd.dismiss();
                Toast.makeText(AddDoctorActivity.this, "Doctor added Successfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(AddDoctorActivity.this, "Something went Wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void openGallery() {
            Intent pickImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(pickImage, REQ);
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQ && resultCode == RESULT_OK)
        {
            Uri uri = data.getData();
            try
            {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            addDoctorImage.setImageBitmap(bitmap);

        }

    }
}