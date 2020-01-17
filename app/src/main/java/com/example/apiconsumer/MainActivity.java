package com.example.apiconsumer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    apiService service;
    ListView lv;
    int page = 1;
    boolean done = false;

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

        Call<JsonElement> call = service.getCharacters(page);

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                JsonElement jsonElement = response.body();
                JsonObject obj = jsonElement.getAsJsonObject();

                Log.e("xd", "xddd");

                if (obj != null) {

                    Log.e("xd", obj.has("error")+"");

                    if(obj.has("error")){
                        done = true;
                    }



                    JsonObject charsJson = obj.getAsJsonObject("results");

                    if (page == 1) {
                        JsonObject info = obj.getAsJsonObject("info");
                        page = info.get("pages").getAsInt();
                    }

                    System.out.println(charsJson.toString());

//                        ArrayAdapter<String> list = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, chars);
//                        lv.setAdapter(list);
                }

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

                Toast.makeText(MainActivity.this, "API error", Toast.LENGTH_SHORT).show();
                Log.e("xd", t.getMessage());
            }
        });
    }
}
