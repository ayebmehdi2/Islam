package com.mehdi.rosary.AzkarSong;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.mehdi.rosary.Main2Activity;
import com.mehdi.rosary.R;
import com.mehdi.rosary.databinding.ActivitySongBinding;

import java.util.ArrayList;

public class PlaySong extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<SongsDetail>> {

    ActivitySongBinding binding;
    RecycleAzkarSongs azkarSongs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_song);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        azkarSongs = new RecycleAzkarSongs();
        binding.rec.setHasFixedSize(true);
        binding.rec.setLayoutManager(manager);
        binding.rec.setAdapter(azkarSongs);

        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<ArrayList<SongsDetail>> onCreateLoader(int id, Bundle args) {
        return new SongsDetail.AsynSong(PlaySong.this);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<SongsDetail>> loader, ArrayList<SongsDetail> data) {
        azkarSongs.swapAdapter(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<SongsDetail>> loader) {

    }



    public void toback(View view){
        startActivity(new Intent(this, Main2Activity.class));
    }

}
