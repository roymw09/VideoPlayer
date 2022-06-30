package com.silverorange.videoplayer.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.silverorange.videoplayer.model.Video;
import com.silverorange.videoplayer.repository.VideoRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Video>> videoListLiveData;
    private VideoRepository videoRepository;
    
    public void init() {
        if (videoListLiveData == null) {
            videoRepository = VideoRepository.getInstance();
            videoListLiveData = videoRepository.getVideos();
            if (videoListLiveData.getValue() != null) {
                System.out.println("NOT NULL");
            }
            System.out.println("TEST: " + videoListLiveData.getValue());
        }
    }

    public MutableLiveData<ArrayList<Video>> getVideoRepository() {
        if (videoListLiveData.getValue() != null) {
            System.out.println("NOT NULL");
        }
        return videoListLiveData;
    }

    public void sortVideosByDate(ArrayList<Video> videos) {
        Collections.sort(videos, (video, t1) -> t1.getDate().compareTo(video.getDate()));
    }
}
