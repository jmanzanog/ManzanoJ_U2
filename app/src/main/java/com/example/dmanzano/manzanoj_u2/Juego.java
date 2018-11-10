package com.example.dmanzano.manzanoj_u2;

import android.app.Activity;
import android.os.Bundle;

public class Juego extends Activity {
    private VistaJuego vistaJuego;
    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.juego);
        vistaJuego = findViewById(R.id.VistaJuego);
    }

    @Override protected void onPause() {
        vistaJuego.getThread().pausar();
        vistaJuego.desactivarSensores();
        super.onPause();
    }
    @Override protected void onResume() {
        super.onResume();
        vistaJuego.getThread().reanudar();
        vistaJuego.activarSensores();
    }
    @Override protected void onDestroy() {
        vistaJuego.getThread().detener();
        vistaJuego.desactivarSensores();
        super.onDestroy();
    }
}