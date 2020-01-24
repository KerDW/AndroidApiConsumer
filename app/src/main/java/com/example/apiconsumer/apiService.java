package com.example.apiconsumer;

import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface apiService {

    @GET("character")
    Call<JsonElement> getCharacter(@Query("name") String name);

    @GET("character")
    Call<JsonElement> getCharacters(@Query("page") int page);

    @GET("contacte/{id}")
    Call<Contact> getContact(@Path("id") int id);

    @GET("contactes")
    Call<ArrayList<Contact>> getContacts();

    @PUT("contacte/{id}")
    Call<Contact> putContact(@Path("id") int id, @Body Contact c);

    @POST("contacte")
    Call<Contact> postContact(@Body Contact c);

    @DELETE("contacteTot/{id}")
    Call<Contact> delContact(@Path("id") int id);


}
