package com.example.movies;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailAdapter extends RecyclerView.Adapter<MovieDetailAdapter.TrailerViewHolder> {

    public void setTrailerList(List<Trailer> trailerList) {
        this.trailerList = trailerList;
        notifyDataSetChanged();


    }

    private List<Trailer> trailerList = new ArrayList<>();

    public void setOnKlick(OnKlickItemListener onKlick) {
        this.onKlick = onKlick;
    }

    private OnKlickItemListener onKlick;



    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Log.d("MovieDetailAdapter1", trailerList.get(1).getName());
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.trailer_item,
                parent,
                false);



        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {

        Trailer trailer = trailerList.get(position);

        holder.trailerView.setText(trailer.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onKlick != null) {
                    onKlick.onKlickItemListener(trailer);
                }
            }
        });





    }

    @Override
    public int getItemCount() {
        return trailerList.size();
    }

    interface OnKlickItemListener{

       void onKlickItemListener(Trailer trailer);

    }



    public class TrailerViewHolder extends RecyclerView.ViewHolder {

        TextView trailerView;

        public TrailerViewHolder(@NonNull View itemView) {
            super(itemView);
            trailerView = itemView.findViewById(R.id.trailerView);

        }



    }

}
