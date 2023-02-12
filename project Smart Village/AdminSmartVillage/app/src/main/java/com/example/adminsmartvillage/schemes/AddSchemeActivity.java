package com.example.adminsmartvillage.schemes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddSchemeActivity extends AppCompatActivity {
    private CardView addImage;
    private ImageView schemeImagePreview;
    private EditText schemeName, schemeStartDate, schemeEndDate,schemeLink, schemeDescription;
    private Button addSchemeButton;

    private final int REQ = 1;
    private Bitmap bitmap;
    private DatabaseReference reference, dbRef;
    private StorageReference storageReference;

    String downloadUrl = "";
    private ProgressDialog pd;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_scheme);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Scheme");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.teal_700)));

        reference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
        pd = new ProgressDialog(this);

        addImage = findViewById(R.id.addImage);
        schemeImagePreview = findViewById(R.id.schemeImagePreview);
        schemeName = findViewById(R.id.schemeName);
        schemeStartDate = findViewById(R.id.schemeStartDate);
        schemeEndDate = findViewById(R.id.schemeEndDate);
        schemeDescription = findViewById(R.id.schemeDescription);
        addSchemeButton = findViewById(R.id.addSchemeButton);
        schemeLink = findViewById(R.id.schemeLink);

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        addSchemeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(schemeName.getText().toString().isEmpty())
                {
                    schemeName.setError("Scheme name is Required");
                    schemeName.requestFocus();
                }
                else if(schemeStartDate.getText().toString().isEmpty())
                {
                    schemeStartDate.setError("Enter Scheme Start Date");
                    schemeStartDate.requestFocus();
                }
                else if(schemeEndDate.getText().toString().isEmpty())
                {
                    schemeEndDate.setError("Enter Scheme End Date");
                    schemeEndDate.requestFocus();
                }
                else if(schemeLink.getText().toString().isEmpty())
                {
                    schemeLink.setError("Enter Scheme Link");
                    schemeLink.requestFocus();
                }
                else if(schemeDescription.getText().toString().isEmpty())
                {
                    schemeDescription.setError("Description is Required");
                    schemeDescription.requestFocus();
                }
                else if(bitmap == null)
                {
                    uploadData();
                }
                else
                {
                    uploadImage();
                }

            }
        });


    }

    private void uploadImage() {
        pd.setMessage("Uploading...");
        pd.show();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] finalimg = baos.toByteArray();
        final StorageReference filePath;
        filePath = storageReference.child("Schemes").child(finalimg+"jpg");
        final UploadTask uploadTask = filePath.putBytes(finalimg);
        uploadTask.addOnCompleteListener(AddSchemeActivity.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
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
                    Toast.makeText(AddSchemeActivity.this, "Something went Wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void uploadData() {
        dbRef = reference.child("Schemes");
        final String uniqueKey = dbRef.push().getKey();

        String name = schemeName.getText().toString();
        String startDate = schemeStartDate.getText().toString();
        String endDate = schemeEndDate.getText().toString();
        String link = schemeLink.getText().toString();
        String description = schemeDescription.getText().toString();

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MM-yyyy");
        String date = currentDate.format(calForDate.getTime());

        Calendar calForTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
        String time = currentTime.format(calForTime.getTime());

        SchemeData schemeData = new SchemeData(downloadUrl, name, startDate, endDate, link, description,date, time, uniqueKey);
        dbRef.child(uniqueKey).setValue(schemeData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                pd.dismiss();
                Toast.makeText(AddSchemeActivity.this, "Scheme Added Successfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(AddSchemeActivity.this, "Something went Wrong", Toast.LENGTH_SHORT).show();
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
            schemeImagePreview.setImageBitmap(bitmap);

        }

    }


}