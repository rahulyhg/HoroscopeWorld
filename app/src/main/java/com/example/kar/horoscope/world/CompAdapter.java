package com.example.kar.horoscope.world;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class CompAdapter extends RecyclerView.Adapter<CompAdapter.ViewHolder> {

    private Context context;
    private List<String> nameList;
    private LayoutInflater inflater;


    CompAdapter ( Context context, List<String> nameList ) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.nameList = nameList;
    }

    @NonNull
    @Override
    public CompAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = inflater.inflate(R.layout.name, viewGroup, false);
        return new CompAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompAdapter.ViewHolder viewHolder, int i) {

        final String s = nameList.get(i);
        viewHolder.textView.setText(s);

        ///TODO
        viewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Set picture", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return nameList.size();
    }


    void setNameList(List<String> nameList) {
        this.nameList = nameList;
        notifyDataSetChanged();
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
