package com.example.smartvillage.posts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.smartvillage.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewAdapter> {
    private Context context;
    private ArrayList<PostData> list;

    public PostAdapter(Context context, ArrayList<PostData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public PostAdapter.PostViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.postfeed_item_layout, parent, false);
        return new PostAdapter.PostViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.PostViewAdapter holder, int position) {
        PostData currentItem = list.get(position);

        holder.postUser.setText(currentItem.getName());
        holder.postTitle.setText(currentItem.getTitle());
        holder.postTime.setText(currentItem.getTime());
        holder.postDate.setText(currentItem.getDate());

        try {
            if (currentItem.getImage() != null)
                Glide.with(context).load(currentItem.getImage()).into(holder.postImage);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class PostViewAdapter extends RecyclerView.ViewHolder {
        private TextView postTitle, postTime, postDate, postUser;
        ImageView postImage;
        public PostViewAdapter(@NonNull View itemView) {
            super(itemView);
            postTitle = itemView.findViewById(R.id.viewPostTitle);
            postTime = itemView.findViewById(R.id.viewPostTime);
            postDate = itemView.findViewById(R.id.viewPostDate);
            postImage = itemView.findViewById(R.id.viewPostImage);
            postUser  = itemView.findViewById(R.id.viewUserName);

        }
    }
}
