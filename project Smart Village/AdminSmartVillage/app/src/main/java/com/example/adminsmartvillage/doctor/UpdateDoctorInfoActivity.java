package com.example.adminsmartvillage.doctor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class UpdateDoctorInfoActivity extends AppCompatActivity {

    private ImageView updateDoctorImage;
    private EditText updateDoctorName, updateDoctorMobileNumber;
    private Button updateDoctorInfoBtn, deleteDoctorInfoBtn;
    private final int REQ = 1;
    private Bitmap bitmap = null;
    private String mobilePattern = "[6-9][0-9]{9}";
    private StorageReference storageReference;
    private DatabaseReference reference, dbRef;
    private  String name, mobileNumber, image,downloadUrl = "", qualification, uniqueKey;
    @SuppressLint("MissingInflatedId")



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_doctor_info);

        getSupportActionBar().setTitle("Update Doctor");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.teal_700)));

        name = getIntent().getStringExtra("name");
        mobileNumber = getIntent().getStringExtra("mobileNumber");
        image = getIntent().getStringExtra("image");

         uniqueKey = getIntent().getStringExtra("key");
         qualification = getIntent().getStringExtra("qualification");

        updateDoctorImage = findViewById(R.id.updateDoctorImage);
        updateDoctorName = findViewById(R.id.updateDoctorName);
        updateDoctorMobileNumber = findViewById(R.id.updateDoctorMobileNumber);
        updateDoctorInfoBtn = findViewById(R.id.updateDoctorInfoBtn);
        deleteDoctorInfoBtn = findViewById(R.id.deleteDoctorInfoBtn);

        reference = FirebaseDatabase.getInstance().getReference().child("Doctors");
        storageReference = FirebaseStorage.getInstance().getReference();

        try {
            Picasso.get().load(image).into(updateDoctorImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        updateDoctorName.setText(name);
        updateDoctorMobileNumber.setText(mobileNumber);

        updateDoctorImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        updateDoctorInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = updateDoctorName.getText().toString();
                mobileNumber = updateDoctorMobileNumber.getText().toString();

                checkValidation();
            }
        });

        deleteDoctorInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteData();
            }
        });

    }
    private  void deleteData(){

        reference.child(qualification).child(uniqueKey).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(UpdateDoctorInfoActivity.this, "Doctor Deleted Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UpdateDoctorInfoActivity.this, ManageDoctor.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UpdateDoctorInfoActivity.this, "Something went Wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void checkValidation() {
        if(name.isEmpty())
        {
            updateDoctorName.setError("Name is Required");
            updateDoctorName.requestFocus();
        }
        else if(!mobileNumber.matches(mobilePattern))
        {
            updateDoctorMobileNumber.setError("Valid Mobile Number is Required");
            updateDoctorMobileNumber.requestFocus();
        }
        else if(bitmap == null)
        {
            updateData(image);
        }
        else{
            uploadImage();
        }
    }

    private void updateData(String s) {

        HashMap hp = new HashMap();
        hp.put("name",name );
        hp.put("mobileNumber",mobileNumber );
        hp.put("image",s );



        reference.child(qualification).child(uniqueKey).updateChildren(hp).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(UpdateDoctorInfoActivity.this, "Doctor Info Updated Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UpdateDoctorInfoActivity.this, ManageDoctor.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UpdateDoctorInfoActivity.this, "Something went Wrong", Toast.LENGTH_SHORT).show();
            }
        });


    }
    private void uploadImage() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] finalimg = baos.toByteArray();
        final StorageReference filePath;
        filePath = storageReference.child("Doctors").child(finalimg+"jpg");
        final UploadTask uploadTask = filePath.putBytes(finalimg);
        uploadTask.addOnCompleteListener(UpdateDoctorInfoActivity.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
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
                                   updateData(downloadUrl);
                                }
                            });
                        }
                    });
                }
                else
                {
//                    pd.dismiss();
                    Toast.makeText(UpdateDoctorInfoActivity.this, "Something went Wrong", Toast.LENGTH_SHORT).show();
                }
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
            updateDoctorImage.setImageBitmap(bitmap);

        }

    }

}