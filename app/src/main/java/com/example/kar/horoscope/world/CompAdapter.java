package com.example.kar.horoscope.world;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CompAdapter extends RecyclerView.Adapter<CompAdapter.ViewHolder> {

    private Context context;
    private String[] Names;
    private int[] Images;
    private LayoutInflater inflater;
    private ClickItem clickItem;



    CompAdapter ( Context context,String[] Names, int[] Images, ClickItem clickItem ) {
        this.clickItem = clickItem;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.Names = Names;
        this.Images = Images;
    }

    @NonNull
    @Override
    public CompAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.name, viewGroup, false);
        return new CompAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final CompAdapter.ViewHolder viewHolder, int i) {


        final String s = Names[i];
        final int URL = Images[i];
        viewHolder.textView.setText(s);

        viewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickItem.ItemClicked( s, URL );
            }
        });
    }

    @Override
    public int getItemCount() {
        return Images.length;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        RelativeLayout relativeLayout;

        ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.zodiacName);
            relativeLayout = itemView.findViewById( R.id.name_layout );
        }
    }
}
