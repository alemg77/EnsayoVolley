package com.example.ensayovolley;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ensayovolley.APINoticias.BuscarNoticias;
import com.example.ensayovolley.APINoticias.RecepcionNoticias;
import com.example.ensayovolley.RecyclerView.AvisoRecyclerView;
import com.example.ensayovolley.RecyclerView.Noticia;
import com.example.ensayovolley.RecyclerView.NoticiaAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AvisoRecyclerView, RecepcionNoticias {

    private String TAG = getClass().toString();
    private List<Noticia> listaDeNoticias;
    private RecyclerView recyclerViewNoticias;
    private EditText editTextBusqueda;

    // TODO: Mas logos:
    // Ambito.com
    // ElTerritorio.com.ar
    // Depo.com.ar
    // Motorsport.com
    // Rosario3.com
    // Tn.com.ar
    // Cadena3.com

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerViewNoticias = findViewById(R.id.RecyclerViewNoticias);
        editTextBusqueda = findViewById(R.id.editTextBusqueda);

        Log.d(TAG, "**************************** Inicio del programa *************************************");

        final BuscarNoticias buscarNoticias = new BuscarNoticias(MainActivity.this);

        buscarNoticias.titularesNuevos(BuscarNoticias.KEY_PAIS_ARGENTINA, BuscarNoticias.KEY_TEMA_DEPORTES);

        editTextBusqueda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarNoticias.porTemaEnEsp(editTextBusqueda.getText().toString());
            }
        });

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
                String urlNoticia = jsonNoticia.getString("url");
                String urlToImage = jsonNoticia.getString("urlToImage");
                Noticia noticia = new Noticia(noticiaFuente, noticiaAutor, noticiaTitulo, noticiaDescripcion, urlNoticia, urlToImage, new Date());
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
            String descripcion = ((Noticia) object).getDescripcion();
            Toast.makeText(this, descripcion, Toast.LENGTH_LONG).show();
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

