package com.example.apiconsumer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

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

        Call<List<Character>> call = service.getCharacters();

        call.enqueue(new Callback<List<Character>>() {
            @Override
            public void onResponse(Call<List<Character>> call, Response<List<Character>> response) {

                List<Character> chars = response.body();

                Log.e("xd", chars+"");

                if (chars != null) {
                    for (Character c : chars) {
                        Log.e("xd", c.getName());
                    }
                    ArrayAdapter<Character> list = new ArrayAdapter<Character>(MainActivity.this, android.R.layout.simple_list_item_1, chars);
                    lv.setAdapter(list);
                }

            }

            @Override
            public void onFailure(Call<List<Character>> call, Throwable t) {

                Toast.makeText(MainActivity.this, "API error", Toast.LENGTH_SHORT).show();
                Log.e("xd", t.getMessage());
            }
        });
    }
}
