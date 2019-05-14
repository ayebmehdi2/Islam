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

public class RecycleAzkarSongs extends RecyclerView.Adapter<RecycleAzkarSongs.holder>{


    private ArrayList<SongsDetail> data;
    private int i;

    public RecycleAzkarSongs(click c) {
        this.c = c;
    }

    public void swapAdapter(ArrayList<SongsDetail> da){
        if (this.data == da) return;
        this.data = da;
        if (da != null){
            this.notifyDataSetChanged();
        }
    }

    interface click {
        void play(String audio, String nam, int pos, int checkPos, ImageView pl, ImageView pa);
        void pause(ImageView pl, ImageView pa);
        void love();
        void inlove();
    }

    private final click c;

    private int checkedPosition = -1;



    class holder extends RecyclerView.ViewHolder{
        TextView id;
        TextView name;

        ImageView play;
        ImageView pause;
        ImageView love;
        ImageView love1;
        View view;

        public holder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
            i = getAdapterPosition();

            id = itemView.findViewById(R.id.id);
            name = itemView.findViewById(R.id.name);
            play = itemView.findViewById(R.id.play);
            love = itemView.findViewById(R.id.love);
            love1 = itemView.findViewById(R.id.love1);
            love1.setVisibility(View.GONE);
            pause = itemView.findViewById(R.id.pause);
            pause.setVisibility(View.GONE);

            }

        void bind(final SongsDetail detail) {

            love.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    c.love();
                }
            });
            love1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    c.inlove();
                }
            });


            if (checkedPosition == -1) {
                play.setVisibility(View.VISIBLE);
                pause.setVisibility(View.GONE);
            } else {
                if (checkedPosition == getAdapterPosition()) {
                    play.setVisibility(View.GONE);
                    pause.setVisibility(View.VISIBLE);
                } else {
                    play.setVisibility(View.VISIBLE);
                    pause.setVisibility(View.GONE);
                }
            }


            if (detail == null) return;
            if (detail.getId() > 0) id.setText(String.valueOf(detail.getId()));
            if (detail.getName() != null) name.setText(detail.getName());

            play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    c.play(data.get(getAdapterPosition()).getAudio(), data.get(getAdapterPosition()).getName(), getAdapterPosition(), checkedPosition,  play, pause);
                }
            });

            pause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    c.pause(play, pause);
                }
            });
        }


        }




    @NonNull
    @Override
    public RecycleAzkarSongs.holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new holder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.zeker_song_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleAzkarSongs.holder holder, int i) {

        holder.bind(data.get(i));

    }

    @Override
    public int getItemCount() {
        if (data == null){
            return 0;
        }else {
            return data.size();
        }
    }

    public void setCheckedPosition(int checkedPosition) {
        this.checkedPosition = checkedPosition;
    }

    public int getCheckedPosition() {
        return checkedPosition;
    }

    public int getPosition() {
        return i;
    }

}
