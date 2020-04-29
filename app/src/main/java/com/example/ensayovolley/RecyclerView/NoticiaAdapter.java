package com.example.ensayovolley.RecyclerView;

import android.content.Context;
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
        private TextView textViewFuente;
        private ImageView imageView;

        public NoticiaViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewFuente = itemView.findViewById(R.id.CeldaNoticiaFuente);
            textViewTitulo = itemView.findViewById(R.id.CeldaNoticiaTitulo);
            imageView = itemView.findViewById(R.id.CeldaNoticiaImagen);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.recyclerViewClick(listaDeNoticias.get(getAdapterPosition()));
                }
            });
        }

        public void cargarNoticia(Noticia unaNoticia) {
            textViewTitulo.setText(unaNoticia.getTitulo());
            textViewFuente.setText(unaNoticia.getFuente());
            Picasso.with((Context)listener).load(unaNoticia.getUrlImagen()).into(imageView);
        }
    }
}



