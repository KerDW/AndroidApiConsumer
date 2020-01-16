package com.example.apiconsumer;

import com.google.gson.JsonElement;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface apiService {

    @GET("character/{idChar}")
    Call<Character> getCharacter(@Path("idChar") int idChar);

    @GET("character")
    Call<JsonElement> getCharacters();
}
