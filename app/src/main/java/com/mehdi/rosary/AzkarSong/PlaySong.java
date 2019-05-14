package com.mehdi.rosary.AzkarSong;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.mehdi.rosary.Main2Activity;
import com.mehdi.rosary.NetworkUtil;
import com.mehdi.rosary.R;
import com.mehdi.rosary.databinding.ActivitySongBinding;

import java.util.ArrayList;

public class PlaySong extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<SongsDetail>>, RecycleAzkarSongs.click,  ExoPlayer.EventListener {

    ActivitySongBinding binding;
    RecycleAzkarSongs azkarSongs;
    private SimpleExoPlayer player;
    private String audi;
    private String name;
    private ImageView pl;
    private ImageView pa;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_song);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        azkarSongs = new RecycleAzkarSongs(this);
        binding.rec.setHasFixedSize(true);
        binding.rec.setLayoutManager(manager);
        binding.rec.setAdapter(azkarSongs);

        if (!(NetworkUtil.isNetworkOnline(this))){
            binding.back.setVisibility(View.VISIBLE);
            binding.connection.setVisibility(View.VISIBLE);
            binding.restart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getLoaderManager().restartLoader(0, null, PlaySong.this);
                }
            });
        }else {
            getLoaderManager().initLoader(0, null, this);
        }

    }
    @Override
    public Loader<ArrayList<SongsDetail>> onCreateLoader(int id, Bundle args) {


            binding.pr.setVisibility(View.VISIBLE);
            binding.rec.setVisibility(View.GONE);
            return new SongsDetail.AsynSong(PlaySong.this);

    }
    @Override
    public void onLoadFinished(Loader<ArrayList<SongsDetail>> loader, ArrayList<SongsDetail> data) {
        binding.pr.setVisibility(View.GONE);
            binding.rec.setVisibility(View.VISIBLE);
            azkarSongs.swapAdapter(data);

    }
    @Override
    public void onLoaderReset(Loader<ArrayList<SongsDetail>> loader) {

    }
    public void toback(View view){
        startActivity(new Intent(this, Main2Activity.class));
    }
    @Override
    public void play(String audio, String nam, int pos, int checkPos, ImageView pl, ImageView pa) {
        this.name = nam;
        this.pl = pl;
        this.pa = pa;
    if (audi == null){
            audi = audio;
            initilazePlayer(audi);
        }else {
            if (audi.equals(audio)){
                startPlayer();
            }else {
                releasePlayer();
                audi = audio;
                initilazePlayer(audi);
            }
        }
    }
    @Override
    public void pause(ImageView pl, ImageView pa) {
        this.pl = pl;
        this.pa = pa;
        pausePlayer();
    }
    @Override
    public void love() {
    }
    @Override
    public void inlove() {

    }
    private void pausePlayer(){
        if (player == null) return;
        player.setPlayWhenReady(false);
        player.getPlaybackState();
    }
    private void startPlayer(){
        if (player == null) return;
        player.setPlayWhenReady(true);
        player.getPlaybackState();
    }
    private void initilazePlayer(String uri) {

        binding.player.setVisibility(View.VISIBLE);
        binding.nameZeker.setText(name);
        binding.clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                releasePlayer();
                audi = null;
                binding.player.setVisibility(View.GONE);
            }
        });
        binding.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPlayer();
            }
        });
        binding.pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pausePlayer();
            }
        });

        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        final ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        TrackSelection.Factory trackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        String userAgent = Util.getUserAgent(this, "Rosary");
        DefaultHttpDataSourceFactory httpDataSourceFactory = new DefaultHttpDataSourceFactory(
                userAgent,
                null ,
                DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS,
                DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS,
                true
        );

        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(
                this,
                null ,
                httpDataSourceFactory
        );

        MediaSource mediaSource =
                new ExtractorMediaSource(Uri.parse(uri),
                        dataSourceFactory, extractorsFactory, new Handler(), Throwable::printStackTrace);

        player = ExoPlayerFactory.newSimpleInstance(this, new DefaultTrackSelector(trackSelectionFactory));
        player.addListener(this);
        player.prepare(mediaSource);
        startPlayer();
    }
    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if ((playbackState == ExoPlayer.STATE_READY) && playWhenReady){
            binding.play.setVisibility(View.GONE);
            binding.pause.setVisibility(View.VISIBLE);
            pl.setVisibility(View.GONE);
            pa.setVisibility(View.VISIBLE);
            if (azkarSongs.getCheckedPosition() != azkarSongs.getPosition()) {
                azkarSongs.notifyItemChanged(azkarSongs.getCheckedPosition());
                azkarSongs.setCheckedPosition(azkarSongs.getPosition());
            }
        }else if ((playbackState == ExoPlayer.STATE_READY)){
            binding.play.setVisibility(View.VISIBLE);
            binding.pause.setVisibility(View.GONE);
            pl.setVisibility(View.VISIBLE);
            pa.setVisibility(View.GONE);
        }
    }
    public void releasePlayer(){
        if (player == null ) return;
        player.stop();
        player.release();
        player = null;
    }
    @Override
    protected void onPause() {
        super.onPause();
        pausePlayer();
    }
    @Override
    protected void onStart() {
        super.onStart();
        startPlayer();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }
}
