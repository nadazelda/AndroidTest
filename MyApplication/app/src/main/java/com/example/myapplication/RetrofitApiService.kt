package com.example.myapplication

public interface RetrofitApiService {

    String BASE_URL = "http://jsonplaceholder.typicode.com";

    @GET("/posts") // 전체 URL 에서 EndPoint URL
    Call<List<Post>> getData(@Query("userId") String id);
}