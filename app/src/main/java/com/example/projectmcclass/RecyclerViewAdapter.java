//Recycler View

package com.example.projectmcclass;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private ArrayList<String> rNames = new ArrayList<>();
    private ArrayList<String> rImages = new ArrayList<>();
    private ArrayList<String> rService = new ArrayList<String>();
    private ArrayList<String> rQuality = new ArrayList<String>();
    private ArrayList<String> rHygiene = new ArrayList<String>();
    private ArrayList<String> rTaste = new ArrayList<String>();
    private ArrayList<String> rExp = new ArrayList<String>();
    private ArrayList<String> rKey = new ArrayList<String>();
    private ArrayList<String> rRating = new ArrayList<String>();
    private String rDish;
    private Context rContext;

    public RecyclerViewAdapter(ArrayList<String> Rating,ArrayList<String> Names, ArrayList<String> Images, ArrayList<String> Service,ArrayList<String> Quality,ArrayList<String> Hygiene,ArrayList<String> Taste,ArrayList<String> Exp,ArrayList<String> Key,String dish, Context context) {
        rNames = Names ;
        rImages = Images;
        rContext = context;
        rService = Service;
        rQuality = Quality;
        rHygiene = Hygiene;
        rTaste = Taste;
        rExp = Exp;
        rDish = dish;
        rRating = Rating;
        rKey = Key;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout._layoutsingle,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Glide.with(rContext)
                .asBitmap()
                .load(rImages.get(position))
                .into(holder.restImage);
        holder.restName.setText(rNames.get(position).toUpperCase());
        /*holder.ratingBar.setRating(Float.parseFloat(rRating.get(position)));*/
        holder.ratingBar.setRating(Float.parseFloat(rRating.get(position)));
        holder.ratingBar.setIsIndicator(true);
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),Ratting.class);
                Bundle extras = new Bundle();
                extras.putString("name",rNames.get(position));
                extras.putString("service",rService.get(position));
                extras.putString("quality",rQuality.get(position));
                extras.putString("hygiene",rHygiene.get(position));
                extras.putString("taste",rTaste.get(position));
                extras.putString("exp",rExp.get(position));
                extras.putString("dish",rDish);
                extras.putString("image",rImages.get(position));
                extras.putString("key",rKey.get(position));
                intent.putExtras(extras);
                rContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return rNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView restImage;
        TextView restName;
        RatingBar ratingBar;
        ConstraintLayout mainLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            restImage = itemView.findViewById(R.id.restImage);
            restName = itemView.findViewById(R.id.restName);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
