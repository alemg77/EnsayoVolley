package com.example.ensayovolley.RecyclerView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ensayovolley.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NoticiaAdapter extends RecyclerView.Adapter {

    private List<Noticia> listaDeNoticias;
    private AvisoRecyclerView listener;
    private String TAG = getClass().toString();

    public NoticiaAdapter(List<Noticia> listaDeNoticias, AvisoRecyclerView listener) {
        this.listaDeNoticias = listaDeNoticias;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View viewcelda = layoutInflater.inflate(R.layout.celda_noticia, parent, false);
        return new NoticiaViewHolder(viewcelda);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((NoticiaViewHolder) holder).cargarNoticia(listaDeNoticias.get(position));
    }

    @Override
    public int getItemCount() {
        return listaDeNoticias.size();
    }

    private class NoticiaViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewTitulo;
        private ImageView imageView;
        private ImageView logo;

        private NoticiaViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitulo = itemView.findViewById(R.id.CeldaNoticiaTitulo);
            imageView = itemView.findViewById(R.id.CeldaNoticiaImagen);
            logo = itemView.findViewById(R.id.CeladaNoticiaimageViewLogo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.recyclerViewClick(listaDeNoticias.get(getAdapterPosition()));
                }
            });
        }

        private void cargarNoticia(Noticia unaNoticia) {
            String titulo = unaNoticia.getTitulo();
            if ( titulo.contains("-") ) {
                textViewTitulo.setText(titulo.substring(0,titulo.indexOf("-")-1));
            } else {
                textViewTitulo.setText(titulo);
            }
            Picasso.with((Context)listener).load(unaNoticia.getUrlImagen()).into(imageView);

            String fuente = unaNoticia.getFuente();
            switch (fuente ) {
                case "La Nacion":
                    Picasso.with((Context)listener).load(R.drawable.logo_lanacion).into(logo);
                    break;

                case "Tntsports.com":
                    Picasso.with((Context)listener).load(R.drawable.logo_tntsports).into(logo);
                    break;

                case "Clarin.com":
                    Picasso.with((Context)listener).load(R.drawable.logo_clarin).into(logo);
                    break;

                case "Eldia.com":
                    Picasso.with((Context)listener).load(R.drawable.logo_eldia).into(logo);
                    break;

                case "Infobae":
                    Picasso.with((Context)listener).load(R.drawable.logo_infobae).into(logo);
                    break;

                case "Tycsports.com":
                    Picasso.with((Context)listener).load(R.drawable.logo_tycsports).into(logo);
                    break;

                case "Ole.com.ar":
                    Picasso.with((Context)listener).load(R.drawable.logo_ole).into(logo);
                    break;

                case "Mdzol.com":
                    Picasso.with((Context)listener).load(R.drawable.logo_mdzol).into(logo);
                    break;

                case "Marca":
                    Picasso.with((Context)listener).load(R.drawable.logo_marca).into(logo);
                    break;

                case "El Mundo":
                    Picasso.with((Context)listener).load(R.drawable.logo_elmundo).into(logo);
                    break;

                default:
                    Log.d(TAG,"****** FALTA AGREGAR EL LOGO DE:"+fuente+"*****************");
                    break;
            }
        }
    }
}

