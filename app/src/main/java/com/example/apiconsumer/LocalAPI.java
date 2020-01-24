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
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

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

    ListView lv;
    ArrayList<String> contacts = new ArrayList<>();
    ArrayAdapter<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_api);

        lv = findViewById(R.id.conts);
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

        list = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contacts);
        lv.setAdapter(list);

        doGet();
    }

    public void doGet(){

        Call<ArrayList<Contact>> callAsync = service.getContacts();

        callAsync.enqueue(new Callback<ArrayList<Contact>>()
        {

            @Override
            public void onResponse(Call<ArrayList<Contact>> call, Response<ArrayList<Contact>> response)
            {
                if (response.body() != null)
                {
                    contacts.clear();
                    Log.e("xd", "here");

                    for (Contact c : response.body()) {
                        contacts.add(c.contacteId +" "+c.nom+" "+c.cognoms);
                    }

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            list.notifyDataSetChanged();
                        }

                    });

                } else
                {
                    System.out.println("Request Error :: " + response.errorBody());
                    Toast.makeText(
                            getApplicationContext(),
                            "Contacts not found.",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Contact>> call, Throwable t)
            {
                System.out.println("Network Error :: " + t.getLocalizedMessage());
            }
        });

    }

    public void doPost(View view) {

        Contact c = new Contact();

        c.nom = name.getText().toString();
        c.cognoms = surname.getText().toString();

        Call<Contact> callAsync = service.postContact(c);

        callAsync.enqueue(new Callback<Contact>()
        {

            @Override
            public void onResponse(Call<Contact> call, Response<Contact> response)
            {
                Toast.makeText(
                        getApplicationContext(),
                        "Contact created.",
                        Toast.LENGTH_LONG).show();

                doGet();
            }

            @Override
            public void onFailure(Call<Contact> call, Throwable t)
            {
                System.out.println("Network Error :: " + t.getLocalizedMessage());
            }
        });

    }

    public void doDel(View view) {

        Call<Contact> callAsync = service.delContact(Integer.parseInt(id.getText().toString()));

        callAsync.enqueue(new Callback<Contact>()
        {

            @Override
            public void onResponse(Call<Contact> call, Response<Contact> response)
            {
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

        doGet();
    }

    public void doPut(View view) {

        Contact c = new Contact();

        c.nom = name.getText().toString();
        c.cognoms = surname.getText().toString();

        Call<Contact> callAsync = service.putContact(Integer.parseInt(id.getText().toString()), c);

        callAsync.enqueue(new Callback<Contact>()
        {

            @Override
            public void onResponse(Call<Contact> call, Response<Contact> response)
            {
                Log.e("xd", call.request().url().toString());

                Toast.makeText(
                        getApplicationContext(),
                        "Contact modified.",
                        Toast.LENGTH_LONG).show();


                doGet();
            }

            @Override
            public void onFailure(Call<Contact> call, Throwable t)
            {
                System.out.println("Network Error :: " + t.getLocalizedMessage());
            }
        });

    }
}
