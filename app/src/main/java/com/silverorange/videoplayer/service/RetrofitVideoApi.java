package com.silverorange.videoplayer.service;

import com.silverorange.videoplayer.model.Video;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitVideoApi {
    @GET("videos")
    Call<ArrayList<Video>> getVideoList();
}
