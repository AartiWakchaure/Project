package com.example.smartvillage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.smartvillage.doctor.DoctorActivity;
import com.example.smartvillage.news.NewsActivity;
import com.example.smartvillage.posts.VillagePostsActivity;
import com.example.smartvillage.schemes.SchemesActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{

    private BottomNavigationView bottomNavigationView;
    private CardView villagePosts, schemes, news, doctors;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().setTitle("Smart Village");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.teal_700)));

        ImageSlider imageSlider = findViewById(R.id.image_slider);

        List<SlideModel> imageList = new ArrayList<>();
        imageList.add(new SlideModel(R.drawable.img1, ScaleTypes.FIT));
        imageList.add(new SlideModel(R.drawable.img2, ScaleTypes.FIT));
        imageList.add(new SlideModel(R.drawable.img3, ScaleTypes.FIT));
        imageList.add(new SlideModel(R.drawable.img4, ScaleTypes.FIT));
        imageList.add(new SlideModel(R.drawable.img5, ScaleTypes.FIT));
        imageSlider.setImageList(imageList, ScaleTypes.FIT);

        villagePosts = findViewById(R.id.villagePosts);
        schemes = findViewById(R.id.schemes);
        news = findViewById(R.id.news);
        doctors = findViewById(R.id.doctors);

        villagePosts.setOnClickListener(this);
        schemes.setOnClickListener(this);
        news.setOnClickListener(this);
        doctors.setOnClickListener(this);


        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.home:
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class ));
                        overridePendingTransition(0,0);
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

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId())
        {
            case R.id.villagePosts:
                intent = new Intent(HomeActivity.this, VillagePostsActivity.class);
                startActivity(intent);
                break;
            case R.id.schemes:
                intent = new Intent(HomeActivity.this, SchemesActivity.class);
                startActivity(intent);
                break;
            case R.id.news:
                intent = new Intent(HomeActivity.this, NewsActivity.class);
                startActivity(intent);
                break;
            case R.id.doctors:
                intent = new Intent(HomeActivity.this, DoctorActivity.class);
                startActivity(intent);
                break;
        }
    }
}