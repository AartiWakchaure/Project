package com.example.adminsmartvillage.posts;

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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.adminsmartvillage.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewAdapter>
{
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
    public void onBindViewHolder(@NonNull PostAdapter.PostViewAdapter holder, @SuppressLint("RecyclerView") int position)
    {
        PostData currentItem = list.get(position);
        holder.adminName.setText(currentItem.getName());
        holder.deletePostTitle.setText(currentItem.getTitle());
        holder.deletePostTime.setText(currentItem.getTime());
        holder.deletePostDate.setText(currentItem.getDate());

        try {
            if (currentItem.getImage() != null)
                Glide.with(context).load(currentItem.getImage()).into(holder.deletePostImage);

//                Picasso.get().load(currentItem.getImage()).into(holder.deletePostImage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.deletePostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure want to delete this post ?");
                builder.setCancelable(true);
                builder.setPositiveButton(
                        "Ok",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Posts");
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

    public class PostViewAdapter extends RecyclerView.ViewHolder {
        private Button deletePostButton;
        private TextView deletePostTitle, deletePostTime, deletePostDate, adminName;
        ImageView deletePostImage;
        public PostViewAdapter(@NonNull View itemView) {
            super(itemView);
            deletePostButton = itemView.findViewById(R.id.deletePostButton);
            deletePostTitle = itemView.findViewById(R.id.deletePostTitle);
            deletePostImage = itemView.findViewById(R.id.deletePostImage);
            deletePostTime = itemView.findViewById(R.id.deletePostTime);
            deletePostDate = itemView.findViewById(R.id.deletePostDate);
            adminName = itemView.findViewById(R.id.adminName);

        }
    }
}
