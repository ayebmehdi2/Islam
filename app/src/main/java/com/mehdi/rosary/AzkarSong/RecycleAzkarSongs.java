package com.mehdi.rosary.AzkarSong;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mehdi.rosary.R;

import java.util.ArrayList;

public class RecycleAzkarSongs extends RecyclerView.Adapter<RecycleAzkarSongs.holder> {


    private ArrayList<SongsDetail> data;

    public void swapAdapter(ArrayList<SongsDetail> da){
        if (this.data == da) return;
        this.data = da;
        if (da != null){
            this.notifyDataSetChanged();
        }
    }

    class holder extends RecyclerView.ViewHolder{
        TextView id;
        TextView name;

        ImageView play;
        ImageView pause;
        ImageView love;
        ImageView love1;

        public holder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id);
            name = itemView.findViewById(R.id.name);
            play = itemView.findViewById(R.id.play);
            love = itemView.findViewById(R.id.love);

            pause = itemView.findViewById(R.id.pause);
            pause.setVisibility(View.GONE);

            love1 = itemView.findViewById(R.id.love1);
            love1.setVisibility(View.GONE);

        }
    }

    @NonNull
    @Override
    public RecycleAzkarSongs.holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new holder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.zeker_song_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleAzkarSongs.holder holder, int i) {
        SongsDetail detail = data.get(i);
        if (detail == null) return;
        if (detail.getId() > 0) holder.id.setText(String.valueOf(detail.getId()));
        if (detail.getName() != null) holder.name.setText(detail.getName());
    }

    @Override
    public int getItemCount() {
        if (data == null){
            return 0;
        }else {
            return data.size();
        }
    }
}
