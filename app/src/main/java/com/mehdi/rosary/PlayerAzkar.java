package com.mehdi.rosary;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.mehdi.rosary.databinding.PlayerLayoutBinding;

public class PlayerAzkar extends AppCompatActivity  implements ExoPlayer.EventListener{


    public static SongsDetail detail = null;

    private SimpleExoPlayer player;

    PlayerLayoutBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.player_layout);

        if (detail == null ){
            Log.e("MEHDI", "DETAIL IS NULL");
            return;
        }

        TextView ch = binding.chik;
        ch.setText(detail.getChikh());
        TextView nam = binding.nam;
        nam.setText(detail.getName());

        initilazePlayer(detail.getSong_name());

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

    private void pausePlayer(){
        player.setPlayWhenReady(false);
        player.getPlaybackState();
    }
    private void startPlayer(){
        player.setPlayWhenReady(true);
        player.getPlaybackState();
    }

    public void pl(View view){
        startPlayer();
    }

    public void pa(View view){
        pausePlayer();
    }

    private void initilazePlayer(String uri) {

        Log.e("MEHDI", "music is " + uri);

        if (player != null) return;
        TrackSelector trackSelector = new DefaultTrackSelector();
        LoadControl loadControl = new DefaultLoadControl();
        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector, loadControl);
        player.addListener(this);

        String userAgent = Util.getUserAgent(this, getString(R.string.app_name));
        MediaSource source = new ExtractorMediaSource(Uri.parse(uri), new DefaultDataSourceFactory(this, userAgent),
                new DefaultExtractorsFactory(),
                null, null);
        player.prepare(source);
        player.setPlayWhenReady(true);
    }

    public void releasePlayer(){
        player.stop();
        player.release();
        player = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    public void toback(View view){
        startActivity(new Intent(this, Main2Activity.class));
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if ((playbackState == ExoPlayer.STATE_READY) && playWhenReady){
            binding.exoPlay.setVisibility(View.INVISIBLE);
            binding.exoPause.setVisibility(View.VISIBLE);
        }else if ((playbackState == ExoPlayer.STATE_READY)){
            binding.exoPlay.setVisibility(View.VISIBLE);
            binding.exoPause.setVisibility(View.INVISIBLE);
        }
    }
}
