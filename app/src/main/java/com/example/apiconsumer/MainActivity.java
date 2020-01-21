package com.example.apiconsumer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    apiService service;
    ListView lv;

    int pageNo = 1;
    boolean done = false;

    ArrayList<String> chars = new ArrayList<>();
    ArrayAdapter<String> list;

    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = findViewById(R.id.characters);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://rickandmortyapi.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(apiService.class);

        list = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, chars);
        lv.setAdapter(list);

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {

                for (int i = 0; i < pageNo; i++) {

                    Call<JsonElement> call = service.getCharacters(i+1);

                    try {
                        Response <JsonElement> response = call.execute();

                        JsonElement jsonElement = response.body();
                        JsonObject obj = jsonElement.getAsJsonObject();

                        if (obj != null) {

                            if(obj.has("error")){
                                done = true;
                            }

                            for (JsonElement e : obj.getAsJsonArray("results")) {
                                chars.add(gson.fromJson(e, Character.class).getName());
                            }

                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {

                                    list.notifyDataSetChanged();

                                }
                            });


                            if (pageNo == 1) {
                                JsonObject info = obj.getAsJsonObject("info");
                                pageNo = info.get("pages").getAsInt();
                            }

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        thread.start();

    }
}
