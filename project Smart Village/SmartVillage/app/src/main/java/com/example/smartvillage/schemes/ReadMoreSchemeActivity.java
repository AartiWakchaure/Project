package com.example.smartvillage.schemes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.smartvillage.R;
import com.squareup.picasso.Picasso;

public class ReadMoreSchemeActivity extends AppCompatActivity {

    private ImageView schemeImage;
    private TextView schemeName,startDate,endDate, schemeDescription, uploadDate, uploadTime;
    private Button applySchemeButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_more_scheme);
        getSupportActionBar().setTitle("About Scheme");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.teal_700)));

        schemeImage = findViewById(R.id.schemeImage);
        schemeName = findViewById(R.id.schemeName);
        uploadDate = findViewById(R.id.uploadDate);
        uploadTime = findViewById(R.id.uploadTime);
        startDate = findViewById(R.id.startDate);
        endDate = findViewById(R.id.endDate);
        schemeDescription = findViewById(R.id.schemeDescription);
        applySchemeButton = findViewById(R.id.applySchemeButton);

        schemeName.setText(getIntent().getStringExtra("name"));
        uploadDate.setText(getIntent().getStringExtra("date"));
        uploadTime.setText(getIntent().getStringExtra("time"));
        startDate.setText(getIntent().getStringExtra("startDate"));
        endDate.setText(getIntent().getStringExtra("endDate"));
        schemeDescription.setText(getIntent().getStringExtra("description"));
        String str = getIntent().getStringExtra("image");
        String strLink = getIntent().getStringExtra("link");


        try {
            Glide.with(this).load(str).into(schemeImage);

//            Picasso.get().load(str).into(schemeImage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        applySchemeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(strLink));
                startActivity(intent);
            }
        });



    }
}