package com.example.apiconsumer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LocalAPI extends AppCompatActivity {

    apiService service;
    Gson gson = new Gson();

    TextView text1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_api);

        text1 = findViewById(R.id.text);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://localhost:44311/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(apiService.class);

        Call<Contact> callAsync = service.getContact(0);

        callAsync.enqueue(new Callback<Contact>()
        {

            @Override
            public void onResponse(Call<Contact> call, Response<Contact> response)
            {
                if (response.body() != null)
                {

                    Contact c = response.body();

                    text1.setText(c.nom);

                } else
                {
                    System.out.println("Request Error :: " + response.errorBody());
                    Toast.makeText(
                            getApplicationContext(),
                            "Contact not found.",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Contact> call, Throwable t)
            {
                System.out.println("Network Error :: " + t.getLocalizedMessage());
            }
        });
    }
}
