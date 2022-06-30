package com.silverorange.videoplayer.repository;

import androidx.lifecycle.MutableLiveData;
import com.silverorange.videoplayer.model.Video;
import com.silverorange.videoplayer.service.RetrofitService;
import com.silverorange.videoplayer.service.RetrofitVideoApi;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoRepository {
    private static VideoRepository videoRepository;
    private RetrofitVideoApi videoApi;

    public static VideoRepository getInstance() {
        if (videoRepository == null) {
            videoRepository = new VideoRepository();
        }
        return videoRepository;
    }

    public VideoRepository() {
        videoApi = RetrofitService.createService(RetrofitVideoApi.class);
    }

    public MutableLiveData<ArrayList<Video>> getVideos() {
        MutableLiveData<ArrayList<Video>> videoData = new MutableLiveData<>();
        videoApi.getVideoList().enqueue(new Callback<ArrayList<Video>>() {
            @Override
            public void onResponse(Call<ArrayList<Video>> call, Response<ArrayList<Video>> response) {
                if (response.isSuccessful()) {
                    videoData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Video>> call, Throwable t) {
                System.err.println("CAUSE: " + t.getCause());
            }
        });
        return videoData;
    }
}
