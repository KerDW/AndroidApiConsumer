package com.example.apiconsumer;

import com.google.gson.JsonElement;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface apiService {

    @GET("character")
    Call<JsonElement> getCharacter(@Query("name") String name);

    @GET("character")
    Call<JsonElement> getCharacters(@Query("page") int page);

    @GET("contacte/{id}")
    Call<Contact> getContact(@Path("id") int id);
}
