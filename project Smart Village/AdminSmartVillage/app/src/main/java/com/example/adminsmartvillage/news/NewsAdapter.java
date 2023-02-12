package com.example.adminsmartvillage.news;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.adminsmartvillage.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewAdapter>
{
    private Context context;
    private ArrayList<NewsData> list ;

    public NewsAdapter(Context context, ArrayList<NewsData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public NewsViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.newsfeed_item_layout, parent, false);
        return new NewsViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewAdapter holder, @SuppressLint("RecyclerView") int position) {

        NewsData currentItem = list.get(position);

        holder.deleteNewsTitle.setText(currentItem.getTitle());
        holder.deleteNewsDescription.setText(currentItem.getDescription());
        holder.deleteNewsTime.setText(currentItem.getTime());
        holder.deleteNewsDate.setText(currentItem.getDate());

        try {
            if (currentItem.getImage() != null)
//                Glide.with(context).load(currentItem.getImage()).into(holder.deleteNewsImage);
                Picasso.get().load(currentItem.getImage()).into(holder.deleteNewsImage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.deleteNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure want to delete this news ?");
                builder.setCancelable(true);
                builder.setPositiveButton(
                        "Ok",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("News");
                                reference.child(currentItem.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();

                                    }
                                });
                                notifyItemRemoved(position);
                            }
                        }
                );
                builder.setNegativeButton(
                        "Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        }
                );

                AlertDialog dialog = null;

                try {
                     dialog = builder.create();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(dialog != null)
                    dialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NewsViewAdapter extends RecyclerView.ViewHolder {
        private Button deleteNews;
        private TextView deleteNewsTitle, deleteNewsDescription,deleteNewsTime,deleteNewsDate;
        private ImageView deleteNewsImage;
        public NewsViewAdapter(@NonNull View itemView) {
            super(itemView);

            deleteNews = itemView.findViewById(R.id.deleteNews);
            deleteNewsTitle = itemView.findViewById(R.id.deleteNewsTitle);
            deleteNewsDescription = itemView.findViewById(R.id.deleteNewsDescription);
            deleteNewsImage = itemView.findViewById(R.id.deleteNewsImage);
            deleteNewsTime = itemView.findViewById(R.id.deleteNewsTime);
            deleteNewsDate = itemView.findViewById(R.id.deleteNewsDate);
        }
    }
}
