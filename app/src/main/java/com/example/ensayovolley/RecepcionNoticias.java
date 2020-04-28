package com.example.ensayovolley;

import org.json.JSONObject;

public interface RecepcionNoticias {
    void llegoPaqueteDeNoticias ( JSONObject jsonNoticias );
    void errorPedidoNoticia ();
}
