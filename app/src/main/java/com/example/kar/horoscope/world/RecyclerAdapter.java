package com.example.kar.horoscope.world;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private Context context;
    private List<Image> imageList;
    private LayoutInflater inflater;


    RecyclerAdapter(Context context, List<Image> imageList) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.imageList = imageList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = inflater.inflate(R.layout.image_layout, viewGroup, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        final Image image = imageList.get(i);

        viewHolder.textView.setText(image.name);
        Glide.with(context)
                .load(image.download_url)
                .into(viewHolder.imageView);

        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Forecast.class );
                intent.putExtra("Title", image.name );
                context.startActivity(intent);
                ( (Activity) context).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }


    void setImageList(List<Image> imageList) {
        this.imageList = imageList;
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        CircleImageView imageView;
        RelativeLayout parentLayout;

        ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            imageView = itemView.findViewById(R.id.imageview);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }

}