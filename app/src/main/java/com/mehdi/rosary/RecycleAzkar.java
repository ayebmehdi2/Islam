package com.mehdi.rosary;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RecycleAzkar extends RecyclerView.Adapter<RecycleAzkar.holder>{


    private ArrayList<ReadAzkarJson> data;

    public void swapAdapter(ArrayList<ReadAzkarJson> da){
        if (this.data == da) return;
        this.data = da;
        if (da != null) this.notifyDataSetChanged();
    }



    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new holder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.zeker, viewGroup, false));

    }



    @Override
    public void onBindViewHolder(@NonNull holder holder, int i) {


            ReadAzkarJson dat = data.get(i);

            if (dat.getZekr() == null || !(dat.getZekr().length() > 0)){
                return;
            }else{
                holder.zeker.setText(dat.getZekr());
            }

            /*
            if (dat.getCategory() == null || !(dat.getCategory().length() > 0)){
                holder.type.setVisibility(View.GONE);
            }else{
                holder.type.setVisibility(View.VISIBLE);
                holder.type.setText(dat.getCategory());
            }*/

            //
            if (dat.getDescription() == null || !(dat.getDescription().length() > 0)){
                holder.desc.setVisibility(View.GONE);
            }else{
                holder.desc.setVisibility(View.VISIBLE);
                holder.desc.setText(dat.getDescription());
            }


            //  ussser
            if (dat.getReference() == null || !(dat.getReference().length() > 0)){
                holder.user.setVisibility(View.GONE);
                holder.vec.setVisibility(View.GONE);
            }else{
                holder.user.setVisibility(View.VISIBLE);
                holder.vec.setVisibility(View.VISIBLE);
                holder.user.setText(dat.getReference());
            }


    }

    @Override
    public int getItemCount() {
        if (data == null){
            return 0;
        }else {
            return data.size();
        }

    }

    private Context context;

    public RecycleAzkar(Context context){
        this.context = context;
    }

    class holder extends RecyclerView.ViewHolder{

        TextView zeker;
        TextView desc;
        TextView user;
        TextView type;
        ImageView vec;


        public holder(@NonNull View itemView) {
            super(itemView);
            Typeface myTypeface = Typeface.createFromAsset(context.getAssets(), "arabe_font.otf");
            zeker = itemView.findViewById(R.id.zeker);
            zeker.setTypeface(myTypeface);
            desc = itemView.findViewById(R.id.desc);
            desc.setTypeface(myTypeface);
            user = itemView.findViewById(R.id.user);
            user.setTypeface(myTypeface);
            //type = itemView.findViewById(R.id.type);
                vec = itemView.findViewById(R.id.vec_user);


        }
    }



}
