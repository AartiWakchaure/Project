package com.example.adminsmartvillage.doctor;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.adminsmartvillage.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DoctorViewAdapter>
{
    private List<DoctorData> list;
    private Context context;
    private String qualification;
    public DoctorAdapter(List<DoctorData> list, Context context, String qualification) {
        this.list = list;
        this.context = context;
        this.qualification = qualification;
    }

    @NonNull
    @Override
    public DoctorViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.qualification_item_layout, parent, false);
        return new DoctorViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorViewAdapter holder, int position) {
        DoctorData item = list.get(position);
        holder.name.setText(item.getName());
        holder.mobileNumber.setText(item.getMobileNumber());
        try {
//            Glide.with(context).load(item.getImage()).into(holder.imageView);

            Picasso.get().load(item.getImage()).into(holder.imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.updateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateDoctorInfoActivity.class);
                intent.putExtra("name", item.getName());
                intent.putExtra("mobileNumber", item.getMobileNumber());
                intent.putExtra("image", item.getImage());
                intent.putExtra("key", item.getKey());
                intent.putExtra("qualification", qualification);

                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class DoctorViewAdapter extends RecyclerView.ViewHolder {

        private TextView name, mobileNumber;
        private Button updateInfo;
        private ImageView imageView;

        public DoctorViewAdapter(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.doctorNameLayout);
            mobileNumber = itemView.findViewById(R.id.doctorMobileLayout);
            updateInfo = itemView.findViewById(R.id.doctorUpdateBtnLayout);
            imageView = itemView.findViewById(R.id.doctorImageLayout);


        }
    }
}
