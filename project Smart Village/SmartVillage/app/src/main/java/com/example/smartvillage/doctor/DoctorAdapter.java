package com.example.smartvillage.doctor;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.smartvillage.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DoctorViewAdapter>
{
    private List<DoctorData> list;
    private Context context;
    private String qualification;
    final int REQ = 1;

    public DoctorAdapter(List<DoctorData> list, Context context, String qualification) {
        this.list = list;
        this.context = context;
        this.qualification = qualification;
    }

    @NonNull
    @Override
    public DoctorAdapter.DoctorViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.qualification_item_layout, parent, false);
        return new DoctorViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorAdapter.DoctorViewAdapter holder, int position) {
        DoctorData item = list.get(position);
        holder.name.setText(item.getName());
        holder.mobileNumber.setText(item.getMobileNumber());
        holder.number = item.getMobileNumber();


        try {
//            Glide.with(context).load(item.getImage()).into(holder.imageView);

            Picasso.get().load(item.getImage()).into(holder.imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.callNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+holder.number));
                context.startActivity(callIntent);

            }
        });


    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class DoctorViewAdapter extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView mobileNumber;
        private ImageView imageView;
        private Button callNow;
        String number;

        public DoctorViewAdapter(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.doctorName);
            mobileNumber = itemView.findViewById(R.id.doctorMobileNumber);
            imageView = itemView.findViewById(R.id.doctorImage);
            callNow = itemView.findViewById(R.id.callNow);

        }
    }

}
