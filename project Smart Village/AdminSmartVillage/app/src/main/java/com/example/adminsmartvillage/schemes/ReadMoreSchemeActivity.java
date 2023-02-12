package com.example.adminsmartvillage.schemes;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.adminsmartvillage.R;
import com.squareup.picasso.Picasso;

public class ReadMoreSchemeActivity extends AppCompatActivity {

        private ImageView schemeImage;
        private TextView schemeName,startDate,endDate, schemeDescription,uploadDate, uploadTime;
//    private Button applySchemeButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_more_scheme);
        getSupportActionBar().setTitle("About Scheme");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.teal_700)));

        schemeImage = findViewById(R.id.schemeImage);
        schemeName = findViewById(R.id.schemeName);
        startDate = findViewById(R.id.startDate);
        endDate = findViewById(R.id.endDate);
        schemeDescription = findViewById(R.id.schemeDescription);
        uploadDate = findViewById(R.id.uploadDate);
        uploadTime = findViewById(R.id.uploadTime);

        schemeName.setText(getIntent().getStringExtra("name"));
        uploadDate.setText(getIntent().getStringExtra("date"));
        uploadTime.setText(getIntent().getStringExtra("time"));
        startDate.setText(getIntent().getStringExtra("startDate"));
        endDate.setText(getIntent().getStringExtra("endDate"));
        schemeDescription.setText(getIntent().getStringExtra("description"));
        String str = getIntent().getStringExtra("image");
        try {
            Glide.with(this).load(str).into(schemeImage);

//            Picasso.get().load(str).into(schemeImage);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        name = getIntent().getStringExtra("name");

    }
}