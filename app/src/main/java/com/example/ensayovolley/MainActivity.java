package com.example.ensayovolley;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements AvisoRecyclerView, RecepcionNoticias {

    private String TAG = getClass().toString();
    private List<Noticia> listaDeNoticias;
    private RecyclerView recyclerViewNoticias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerViewNoticias = findViewById(R.id.RecyclerViewNoticias);

        Log.d(TAG, "**************************** Inicio del programa *************************************");

        BuscarNoticias buscarNoticias = new BuscarNoticias(MainActivity.this);

        buscarNoticias.titularesNuevos(BuscarNoticias.KEY_PAIS_ARGENTINA, BuscarNoticias.KEY_TEMA_DEPORTES);

        // Ejemplos
        // buscarNoticias.porTema("Coronavirus");
        // buscarNoticias.titularesNuevos(BuscarNoticias.KEY_PAIS_ARGENTINA);

    }

    private void mostrarRespuesta(JSONObject jsonNoticias) {
        try {
            JSONArray jsonArticulos = jsonNoticias.getJSONArray("articles");
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


    @Override
    public void recyclerViewClick(Object object) {
        if (object instanceof Noticia) {
            Toast.makeText(this, ((Noticia) object).getTitulo(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void llegoPaqueteDeNoticias(JSONObject jsonNoticias) {
        // Por aca llegan las noticias en formato JSON
        mostrarRespuesta(jsonNoticias);
    }

    @Override
    public void errorPedidoNoticia() {
        Log.d(TAG, "*********** ERROR PIDIENDO LA NOTICA *******************");
        // TODO: Decidir que hacer si no se puede obtener noticias.
    }
}

