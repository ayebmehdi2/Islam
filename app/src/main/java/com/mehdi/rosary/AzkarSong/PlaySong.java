package com.mehdi.rosary.AzkarSong;

import android.app.LoaderManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.media.session.MediaButtonReceiver;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
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

    private ActivitySongBinding binding;
    private RecycleAzkarSongs azkarSongs;
    private SimpleExoPlayer player;
    private static MediaSessionCompat compat;
    private PlaybackStateCompat.Builder playCompat;
    private NotificationManager manager;
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
        initilazeMediaSessionCompat();

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
        binding.seekBar.setVisibility(View.VISIBLE);
        binding.nameZeker.setText(name);
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
        binding.clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player != null) releasePlayer();
                manager.cancelAll();
                audi = null;
                binding.player.setVisibility(View.GONE);
                binding.seekBar.setVisibility(View.GONE);

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
    public void initilazeMediaSessionCompat(){

        compat = new MediaSessionCompat(this, "PlaySong");
        compat.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS | MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        compat.setMediaButtonReceiver(null);

        playCompat = new PlaybackStateCompat.Builder()
                .setActions(PlaybackStateCompat.ACTION_PLAY |
                        PlaybackStateCompat.ACTION_PAUSE |
                        PlaybackStateCompat.ACTION_PLAY_PAUSE);

        compat.setPlaybackState(playCompat.build());

        compat.setCallback(new myMediaSession());

        compat.setActive(true);

    }

    private Runnable runnable;
    private Handler handler;
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if (player == null) return;
                binding.seekBar.setProgress((int) ((player.getCurrentPosition()*100)/player.getDuration()));
                handler.postDelayed(runnable, 1000);
            }
        };
        handler.postDelayed(runnable, 0);

        if ((playbackState == ExoPlayer.STATE_READY) && playWhenReady){
            binding.play.setVisibility(View.GONE);
            binding.pause.setVisibility(View.VISIBLE);
            pl.setVisibility(View.GONE);
            pa.setVisibility(View.VISIBLE);
            if (azkarSongs.getCheckedPosition() != azkarSongs.getPosition()) {
                azkarSongs.notifyItemChanged(azkarSongs.getCheckedPosition());
                azkarSongs.setCheckedPosition(azkarSongs.getPosition());
            }

            playCompat.setState(PlaybackStateCompat.STATE_PLAYING, player.getContentPosition(), 1f);

        }else if ((playbackState == ExoPlayer.STATE_READY)){
            binding.play.setVisibility(View.VISIBLE);
            binding.pause.setVisibility(View.GONE);
            pl.setVisibility(View.VISIBLE);
            pa.setVisibility(View.GONE);

            playCompat.setState(PlaybackStateCompat.STATE_PAUSED, player.getContentPosition(), 1f);

        }
        compat.setPlaybackState(playCompat.build());
        showNotification(playCompat.build(), name);

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
        if (compat != null) compat.setActive(false);
        manager.cancelAll();
    }

    class myMediaSession extends MediaSessionCompat.Callback{
        @Override
        public void onPlay() {
            super.onPlay();
            startPlayer();
        }

        @Override
        public void onPause() {
            super.onPause();
            pausePlayer();
        }
    }
    public void showNotification(PlaybackStateCompat compa, String title){


        int icone = 0;
        String playPause = null;
        if (compa.getState() == PlaybackStateCompat.STATE_PLAYING){
            icone = R.drawable.ic_pause_b;
            playPause = "PAUSE";
        }else if (compa.getState() == PlaybackStateCompat.STATE_PAUSED){
            icone = R.drawable.ic_play_button;
            playPause = "PLAY";
        }

        NotificationCompat.Action action = new NotificationCompat.Action(icone, playPause,
                MediaButtonReceiver.buildMediaButtonPendingIntent(this, PlaybackStateCompat.ACTION_PLAY_PAUSE));

        PendingIntent intent = PendingIntent.getActivity(this, 0, new Intent(this, PlaySong.class), 0);

        Notification notification  = new NotificationCompat.Builder(this).setContentTitle(title)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setSmallIcon(R.drawable.ic_music)
                .addAction(action)
                .setColor(0x0288D1)
                .setStyle(new android.support.v4.media.app.NotificationCompat.MediaStyle()
                        .setMediaSession(compat.getSessionToken()))
                .setContentIntent(intent).build();


        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(0, notification);

        }
    public static class MediaReceiver extends BroadcastReceiver {
        public MediaReceiver() {}
        @Override
        public void onReceive(Context context, Intent intent) {
            MediaButtonReceiver.handleIntent(compat, intent);
        }
    }

}
