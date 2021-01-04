package com.example.sdmonitorapp.ui.main;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sdmonitorapp.models.Sensor;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainViewModel extends ViewModel {
    // TODO: Implement the ViewModel


    private final String baseUri = "http://192.168.15.55:3000/sensors";

    private MutableLiveData<String> proprietario;
    private MutableLiveData<String> mensurando;
    private MutableLiveData<String> latitude;
    private MutableLiveData<String> longitude;
    private MutableLiveData<String> topico;
    private MutableLiveData<Sensor[]> sensores;

    private Context context;
    private RequestQueue queue;


    public MainViewModel() {
        proprietario = new MutableLiveData<>();
        mensurando = new MutableLiveData<>();
        latitude = new MutableLiveData<>();
        longitude = new MutableLiveData<>();
        topico = new MutableLiveData<>();
        sensores = new MutableLiveData<Sensor[]>();

    }

    public void setContext(Context ctx) {
        this.context = ctx;
        this.queue = Volley.newRequestQueue(context);

    }

    public void loadDataFromIDComponent(){


        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, baseUri, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Gson gson = new Gson();
                sensores.setValue(gson.fromJson(response.toString(), Sensor[].class));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }
        );

        queue.add(jsonObjectRequest);

    }


    public MutableLiveData<String> getLatitude() {
        return latitude;
    }

    public MutableLiveData<String> getLongitude() {
        return longitude;
    }

    public MutableLiveData<String> getMensurando() {
        return mensurando;
    }

    public MutableLiveData<String> getProprietario() {
        return proprietario;
    }

    public MutableLiveData<String> getTopico() {
        return topico;
    }

    public MutableLiveData<Sensor[]> getSensores() {
        return sensores;
    }
}