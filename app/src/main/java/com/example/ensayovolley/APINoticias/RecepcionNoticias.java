package com.example.ensayovolley.APINoticias;

import org.json.JSONObject;

public interface RecepcionNoticias {
    void llegoPaqueteDeNoticias ( JSONObject jsonNoticias );
    void errorPedidoNoticia ();
}
