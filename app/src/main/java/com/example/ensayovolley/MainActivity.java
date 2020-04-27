package com.example.ensayovolley;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AvisoRecyclerView {

    private String TAG = getClass().toString();
    private List<Noticia> listaDeNoticias;
    private RecyclerView recyclerViewNoticias;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewNoticias = findViewById(R.id.RecyclerViewNoticias);

        String url = "https://newsapi.org/v2/top-headlines?country=ar&apiKey=958203d2e91b4511936cf0ad0acc25ae";
        sendGetRequest(url);

    }

    private void sendGetRequest(String url) {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArticulos = jsonObject.getJSONArray("articles");
                    listaDeNoticias = new ArrayList<>();
                    for (int i = 0; i < jsonArticulos.length(); i++) {
                        JSONObject jsonNoticia = (JSONObject) jsonArticulos.get(i);
                        JSONObject jsonFuente = (JSONObject) jsonNoticia.get("source");
                        String noticiaTitulo = jsonNoticia.getString("title");
                        String noticiaAutor = jsonNoticia.getString("author");
                        String noticiaFuente = jsonFuente.getString("name");
                        String noticiaDescripcion = jsonNoticia.getString("description");
                        Noticia noticia = new Noticia(noticiaFuente, noticiaAutor, noticiaTitulo, noticiaDescripcion, new Date());
                        listaDeNoticias.add(noticia);
                    }
                } catch (JSONException e) {
                    Log.d(TAG, "*** Error leyendo respuesta del servicio web ***");
                    e.printStackTrace();
                }
                NoticiaAdapter noticiaAdapter = new NoticiaAdapter(listaDeNoticias, MainActivity.this);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerViewNoticias.setLayoutManager(layoutManager);
                recyclerViewNoticias.setAdapter(noticiaAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error!!:" + error.toString());
            }
        });
        queue.add(stringRequest);
    }

    @Override
    public void recyclerViewClick(Object object) {
        Toast.makeText(this, ((Noticia)object).getTitulo(), Toast.LENGTH_LONG).show();
    }
}
