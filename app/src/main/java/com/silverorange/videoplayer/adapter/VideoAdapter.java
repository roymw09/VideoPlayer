package com.silverorange.videoplayer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.exoplayer2.Player;
import com.silverorange.videoplayer.R;
import com.silverorange.videoplayer.model.Video;
import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {
    private Context context;
    private ArrayList<Video> videoArrayList;
    static int videoListPosition = 0;

    public VideoAdapter(Context context, ArrayList<Video> videoArrayList) {
        this.context = context;
        this.videoArrayList = videoArrayList;
    }

    @NonNull
    @Override
    public VideoAdapter.VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.video_item, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoAdapter.VideoViewHolder holder, int position) {
        holder.videoTitle.setText(videoArrayList.get(videoListPosition).getTitle());
        holder.videoAuthor.setText(videoArrayList.get(videoListPosition).getAuthor().getName());
        holder.videoDescription.setText(videoArrayList.get(videoListPosition).getDescription());
    }

    // display 1 list item at a time
    @Override
    public int getItemCount() {
        return 1;
    }

    // retrieve the video hls urls so they can be added to the Exoplayer's media source
    public ArrayList<String> getVideoUrl() {
        ArrayList<String> arrayList = new ArrayList<>();
        for (Video video : videoArrayList) {
            arrayList.add(video.getHlsUrl());
        }
        return arrayList;
    }

    // increase the counter to access the next video & its details
    public void increasePosition() {
        if (videoListPosition < videoArrayList.size()-1) {
            videoListPosition++;
            notifyDataSetChanged();
        }
    }

    // decrease the counter to access the previous video & its details
    public void decreasePosition() {
        if (videoListPosition > 0) {
            videoListPosition--;
            notifyDataSetChanged();
        }
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder implements Player.Listener {
        TextView videoTitle;
        TextView videoAuthor;
        TextView videoDescription;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            videoTitle = itemView.findViewById(R.id.videoTitle);
            videoAuthor = itemView.findViewById(R.id.videoAuthor);
            videoDescription = itemView.findViewById(R.id.videoDescription);
        }
    }
}
