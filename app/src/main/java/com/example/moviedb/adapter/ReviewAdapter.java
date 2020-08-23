package com.example.moviedb.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.moviedb.R;
import com.example.moviedb.models.Review;
import com.example.moviedb.models.Trailer;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.MyViewHolder> {

    private Context mContext;
    private List<Review> reviewList;

    public ReviewAdapter(Context mContext, List<Review> reviewList){
        this.mContext = mContext;
        this.reviewList = reviewList;

    }

    @Override
    public ReviewAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.review_card, viewGroup, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final ReviewAdapter.MyViewHolder viewHolder, int i){
        viewHolder.title.setText(reviewList.get(i).getAuthor());
        viewHolder.content.setText(reviewList.get(i).getContent());

    }

    @Override
    public int getItemCount(){

        return reviewList.size();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView title;
        public ImageView thumbnail;
        public TextView content;

        public MyViewHolder(View view){
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            content = (TextView) view.findViewById(R.id.content);


        }
    }
}
