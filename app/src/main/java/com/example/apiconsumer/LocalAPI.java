package com.example.apiconsumer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

    Button del;
    Button post;

    EditText id;
    EditText name;
    EditText surname;

    TextView text1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_api);

        text1 = findViewById(R.id.text);
        id = findViewById(R.id.idC);
        name = findViewById(R.id.cName);
        surname = findViewById(R.id.cSurname);
        del = findViewById(R.id.delC);
        post = findViewById(R.id.postC);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:44311/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(apiService.class);

        Call<Contact> callAsync = service.getContact(3);

        callAsync.enqueue(new Callback<Contact>()
        {

            @Override
            public void onResponse(Call<Contact> call, Response<Contact> response)
            {
                Log.e("xd", call.request().url().toString());
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

    public void doPost(View view) {

        Call<Contact> callAsync = service.postContact();

        callAsync.enqueue(new Callback<Contact>()
        {

            @Override
            public void onResponse(Call<Contact> call, Response<Contact> response)
            {
                Log.e("xd", call.request().url().toString());
                Log.e("xd", "good");
                Toast.makeText(
                        getApplicationContext(),
                        "Contact deleted.",
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Contact> call, Throwable t)
            {
                System.out.println("Network Error :: " + t.getLocalizedMessage());
            }
        });

    }

    public void doDel(View view) {

        Log.e("xd", Integer.parseInt(id.getText().toString())+"");

        Call<Contact> callAsync = service.delContact(Integer.parseInt(id.getText().toString()));

        callAsync.enqueue(new Callback<Contact>()
        {

            @Override
            public void onResponse(Call<Contact> call, Response<Contact> response)
            {
                Log.e("xd", call.request().url().toString());
                Log.e("xd", "good");
                    Toast.makeText(
                            getApplicationContext(),
                            "Contact deleted.",
                            Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Contact> call, Throwable t)
            {
                System.out.println("Network Error :: " + t.getLocalizedMessage());
            }
        });

    }
}
