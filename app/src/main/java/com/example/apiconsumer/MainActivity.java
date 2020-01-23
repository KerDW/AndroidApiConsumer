package com.example.apiconsumer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
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
    TextView name;
    TextView species;
    TextView status;
    EditText charName;
    Button nameR;

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
        charName = findViewById(R.id.charName);
        nameR = findViewById(R.id.nameRequest);
        name = findViewById(R.id.name);
        species = findViewById(R.id.species);
        status = findViewById(R.id.status);

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

    public void nameRequest(View view){
        Call<Character> callAsync = service.getCharacter(charName.getText().toString());

        callAsync.enqueue(new Callback<Character>()
        {

            @Override
            public void onResponse(Call<Character> call, Response<Character> response)
            {
                if (response.isSuccessful())
                {
                    Character c = response.body();

                    name.setText(c.getName());
                    species.setText(c.getSpecies());
                    status.setText(c.getStatus());
                }
                else
                {
                    System.out.println("Request Error :: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Character> call, Throwable t)
            {
                System.out.println("Network Error :: " + t.getLocalizedMessage());
            }
        });
    }
}
