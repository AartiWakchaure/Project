package com.example.smartvillage.schemes;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.smartvillage.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SchemeAdapter extends RecyclerView.Adapter<SchemeAdapter.SchemeViewAdapter> {

    private Context context;
    private ArrayList<SchemeData> list;

    public SchemeAdapter(Context context, ArrayList<SchemeData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public SchemeAdapter.SchemeViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.schemefeed_item_layout, parent, false);
        return new SchemeAdapter.SchemeViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SchemeAdapter.SchemeViewAdapter holder, int position) {

        SchemeData currentItem = list.get(position);

        holder.schemeName.setText(currentItem.getName());
        holder.startDate = currentItem.getStartDate();
        holder.endDate = currentItem.getEndDate();
        holder.link = currentItem.getLink();
        holder.description = currentItem.getDescription();
        holder.name = currentItem.getName();
        holder.image = currentItem.getImage();
        holder.uploadDate.setText(currentItem.getDate());
        holder.uploadTime.setText(currentItem.getTime());



        try {
            if (currentItem.getImage() != null)
                Glide.with(context).load(currentItem.getImage()).into(holder.schemeImage);
//                Picasso.get().load(currentItem.getImage()).into(holder.schemeImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.readMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ReadMoreSchemeActivity.class);
                intent.putExtra("image", currentItem.getImage());
                intent.putExtra("name", currentItem.getName());
                intent.putExtra("date", currentItem.getDate());
                intent.putExtra("time", currentItem.getTime());
                intent.putExtra("startDate", currentItem.getStartDate());
                intent.putExtra("endDate", currentItem.getEndDate());
                intent.putExtra("description", currentItem.getDescription());
                intent.putExtra("link", currentItem.getLink());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SchemeViewAdapter extends RecyclerView.ViewHolder {
        private TextView schemeName,readMore, uploadDate, uploadTime;
        ImageView schemeImage;
        private String name, image, startDate, endDate, link, description, date, time;
        public SchemeViewAdapter(@NonNull View itemView) {
            super(itemView);
            schemeName = itemView.findViewById(R.id.schemeName);
            readMore = itemView.findViewById(R.id.readMore);
            schemeImage = itemView.findViewById(R.id.schemeImage);
            uploadDate = itemView.findViewById(R.id.uploadDate);
            uploadTime = itemView.findViewById(R.id.uploadTime);


        }
    }
}
