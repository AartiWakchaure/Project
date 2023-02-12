package com.example.adminsmartvillage.schemes;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.adminsmartvillage.doctor.UpdateDoctorInfoActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SchemeAdapter extends RecyclerView.Adapter<SchemeAdapter.SchemeViewAdapter>
{
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
    public void onBindViewHolder(@NonNull SchemeAdapter.SchemeViewAdapter holder, @SuppressLint("RecyclerView") int position)
    {
        SchemeData currentItem = list.get(position);

        holder.deleteSchemeName.setText(currentItem.getName());
        holder.uploadDate.setText(currentItem.getDate());
        holder.uploadTime.setText(currentItem.getTime());
        holder.startDate = currentItem.getStartDate();
        holder.endDate = currentItem.getEndDate();
        holder.link = currentItem.getLink();
        holder.description = currentItem.getDescription();
        holder.name = currentItem.getName();
        holder.image = currentItem.getImage();


        try {
            if (currentItem.getImage() != null)
                Glide.with(context).load(currentItem.getImage()).into(holder.deleteSchemeImage);

//                Picasso.get().load(currentItem.getImage()).into(holder.deleteSchemeImage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.deleteSchemeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure want to delete this scheme ?");
                builder.setCancelable(true);
                builder.setPositiveButton(
                        "Ok",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Schemes");
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

        holder.readMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ReadMoreSchemeActivity.class);
                intent.putExtra("image", currentItem.getImage());
                intent.putExtra("name", currentItem.getName());
                intent.putExtra("startDate", currentItem.getStartDate());
                intent.putExtra("endDate", currentItem.getEndDate());
                intent.putExtra("description", currentItem.getDescription());
                intent.putExtra("date", currentItem.getDate());
                intent.putExtra("time", currentItem.getTime());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SchemeViewAdapter extends RecyclerView.ViewHolder {
        private Button deleteSchemeButton;
        private TextView deleteSchemeName,readMore, uploadDate, uploadTime;
        private String name, image, startDate, endDate, link, description;
        ImageView deleteSchemeImage;
        public SchemeViewAdapter(@NonNull View itemView) {
            super(itemView);
            deleteSchemeName = itemView.findViewById(R.id.deleteSchemeName);
            deleteSchemeButton = itemView.findViewById(R.id.deleteSchemeButton);
            deleteSchemeImage = itemView.findViewById(R.id.deleteSchemeImage);
            readMore = itemView.findViewById(R.id.readMore);
            uploadDate = itemView.findViewById(R.id.uploadDate);
            uploadTime = itemView.findViewById(R.id.uploadTime);

        }
    }
}
