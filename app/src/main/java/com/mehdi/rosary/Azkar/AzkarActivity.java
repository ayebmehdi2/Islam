package com.mehdi.rosary.Azkar;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mehdi.rosary.Main2Activity;
import com.mehdi.rosary.R;
import com.mehdi.rosary.databinding.FragAzkarBinding;

import java.util.ArrayList;

public class AzkarActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<AzkarDetail>> {


    private RecycleAzkar recycleAzkar;
    private int ty = 1;
    FragAzkarBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.frag_azkar);


        RecyclerView recyclerView = binding.recAzkar;
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recycleAzkar = new RecycleAzkar(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recycleAzkar);

        getLoaderManager().initLoader(0, null, this);

        binding.tm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                def();
                ty = 1;
                binding.vm.setVisibility(View.GONE);
                binding.tm.setTextColor(getResources().getColor(R.color.white));
                getLoaderManager().restartLoader(0, null, AzkarActivity.this);
            }
        });

        binding.ta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                def();
                ty = 2;
                binding.va.setVisibility(View.GONE);
                binding.ta.setTextColor(getResources().getColor(R.color.white));
                getLoaderManager().restartLoader(0, null, AzkarActivity.this);
            }
        });

        binding.td.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                def();
                ty = 3;
                binding.vd.setVisibility(View.GONE);
                binding.td.setTextColor(getResources().getColor(R.color.white));
                getLoaderManager().restartLoader(0, null, AzkarActivity.this);
            }
        });

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ty = 1;
                startActivity(new Intent(AzkarActivity.this, Main2Activity.class));
            }
        });


}

public void def(){
    binding.va.setVisibility(View.VISIBLE);
    binding.vm.setVisibility(View.VISIBLE);
    binding.vd.setVisibility(View.VISIBLE);
    binding.tm.setTextColor(getResources().getColor(R.color.black));
    binding.ta.setTextColor(getResources().getColor(R.color.black));
    binding.td.setTextColor(getResources().getColor(R.color.black));
    }

    @Override
    public Loader<ArrayList<AzkarDetail>> onCreateLoader(int id, Bundle args) {
        binding.pr.setVisibility(View.VISIBLE);
        binding.recAzkar.setVisibility(View.INVISIBLE);
        return new AzkarDetail.AsyncAzkar(AzkarActivity.this, ty);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<AzkarDetail>> loader, ArrayList<AzkarDetail> data) {
        binding.pr.setVisibility(View.GONE);
        binding.recAzkar.setVisibility(View.VISIBLE);
        recycleAzkar.swapAdapter(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<AzkarDetail>> loader) {

    }
}


