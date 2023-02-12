package com.example.smartvillage.news;

import android.content.Context;
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

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewAdapter> {
    private Context context;
    private ArrayList<NewsData> list ;

    public NewsAdapter(Context context, ArrayList<NewsData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public NewsAdapter.NewsViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.newsfeed_item_layout, parent, false);
        return new NewsViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.NewsViewAdapter holder, int position) {


        NewsData currentItem = list.get(position);

        holder.newsTitle.setText(currentItem.getTitle());
        holder.newsDescription.setText(currentItem.getDescription());
        holder.newsTime.setText(currentItem.getTime());
        holder.newsDate.setText(currentItem.getDate());

        try {
            if (currentItem.getImage() != null)
                Glide.with(context).load(currentItem.getImage()).into(holder.newsImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NewsViewAdapter extends RecyclerView.ViewHolder {

        private TextView newsTitle, newsDescription,newsTime,newsDate;
        ImageView newsImage;

        public NewsViewAdapter(@NonNull View itemView) {
            super(itemView);
            newsTitle = itemView.findViewById(R.id.newsTitle);
            newsDescription = itemView.findViewById(R.id.newsDescription);
            newsTime = itemView.findViewById(R.id.newsTime);
            newsDate = itemView.findViewById(R.id.newsDate);
            newsImage = itemView.findViewById(R.id.newsImage);

        }
    }
}
