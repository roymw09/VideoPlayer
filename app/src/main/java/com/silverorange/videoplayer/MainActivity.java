package com.silverorange.videoplayer;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSource;
import com.silverorange.videoplayer.adapter.VideoAdapter;
import com.silverorange.videoplayer.model.Video;
import com.silverorange.videoplayer.viewmodel.MainViewModel;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Player.Listener {
    private ArrayList<Video> videoArrayList = new ArrayList<>();
    private VideoAdapter videoAdapter;
    private RecyclerView recyclerView;
    private MainViewModel mainViewModel;
    private long playBackPosition = 0;
    private ExoPlayer exoPlayer;
    private PlayerView playerView;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.rvVideo);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.init();
        mainViewModel.getVideoRepository().observe(this, videoList -> {
            videoArrayList.addAll(videoList);
            // sort videos from newest - oldest
            mainViewModel.sortVideosByDate(videoArrayList);
            // initialize media player once videos have been gathered from the api
            initializePlayer();
            videoAdapter.notifyDataSetChanged();
        });

        initRecyclerView();

        // decrease ArrayList position with videoAdapter.decreasePosition()
        // to display the required details
        // for the previous video in the que
        // then skip to the previous video with seekToPreviousMediaItem
        ImageButton prevButton = findViewById(R.id.exo_prev);
        prevButton.setOnClickListener(view -> {
            videoAdapter.decreasePosition();
            exoPlayer.seekToPreviousMediaItem();
            if (exoPlayer.isPlaying()) {
                exoPlayer.pause();
            }
        });

        // increase ArrayList position with videoAdapter.increasePosition()
        // to display the required details
        // for the next video in the que
        // then skip to the next video with seekToNextMediaItem
        ImageButton nextButton = findViewById(R.id.exo_next);
        nextButton.setOnClickListener(view -> {
            videoAdapter.increasePosition();
            exoPlayer.seekToNextMediaItem();
            if (exoPlayer.isPlaying()) {
                exoPlayer.pause();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        releasePlayer();
    }

    private void initializePlayer() {
        exoPlayer = new ExoPlayer.Builder(this).build();
        preparePlayer();
        playerView = findViewById(R.id.exoplayerView);
        playerView.setPlayer(exoPlayer);
        exoPlayer.seekTo(playBackPosition);
        exoPlayer.setPlayWhenReady(false);
        exoPlayer.addListener(this);
    }

    private void releasePlayer() {
        playBackPosition = exoPlayer.getCurrentPosition();
        exoPlayer.release();
    }

    private MediaSource buildMediaSource(Uri uri) {
        DefaultDataSource.Factory dataSourceFactory = new DefaultDataSource.Factory(this);
        return new HlsMediaSource.Factory(dataSourceFactory).createMediaSource(MediaItem.fromUri(uri));
    }

    // Get hls urls and add them to the ExoPlayer's ConcatenatingMediaSource
    private void preparePlayer() {
        ArrayList<String> urls = videoAdapter.getVideoUrl();
        ArrayList<MediaSource> sources = new ArrayList<>();
        for (String url : urls) {
            MediaSource mediaSource = buildMediaSource(Uri.parse(url));
            sources.add(mediaSource);
        }
        ConcatenatingMediaSource concatenatingMediaSource = new ConcatenatingMediaSource();
        concatenatingMediaSource.addMediaSources(sources);
        exoPlayer.setMediaSource(concatenatingMediaSource);
        exoPlayer.prepare();
    }

    private void initRecyclerView() {
        if (videoAdapter == null) {
            videoAdapter = new VideoAdapter(MainActivity.this, videoArrayList);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(videoAdapter);
            recyclerView.setNestedScrollingEnabled(true);
        } else {
            videoAdapter.notifyDataSetChanged();
        }
    }
}
