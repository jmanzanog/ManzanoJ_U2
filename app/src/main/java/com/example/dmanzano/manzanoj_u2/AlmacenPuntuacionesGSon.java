package com.example.dmanzano.manzanoj_u2;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AlmacenPuntuacionesGSon implements AlmacenPuntuaciones {
    private String string;
    private Gson gson = new Gson();
    private Type type = new TypeToken<List<Puntuacion>>() {
    }.getType();
    private Context context;

    public AlmacenPuntuacionesGSon(Context context) {
        this.context = context;
        guardarPuntuacion(45000, "Mi nombre", System.currentTimeMillis());
        guardarPuntuacion(31000, "Otro nombre", System.currentTimeMillis());
    }

    @Override
    public void guardarPuntuacion(int puntos, String nombre, long fecha) {
       /* ArrayList<Puntuacion> puntuaciones;
        if (string == null) {
            puntuaciones = new ArrayList<>();
        } else {
            puntuaciones = gson.fromJson(string, type);
        }
        puntuaciones.add(new Puntuacion(puntos, nombre, fecha));
        string = gson.toJson(puntuaciones, type);*/

        Clase objeto;
        if (string == null) {
            objeto = new Clase();
        } else {
            objeto = gson.fromJson(string, type);
        }
        objeto.puntuaciones.add(new Puntuacion(puntos, nombre, fecha));
        string = gson.toJson(objeto, type);
        String FICHERO = context.getExternalFilesDir(null).getAbsolutePath() + "/puntuacionesAPL.txt";

        try {
            FileOutputStream f = new FileOutputStream(FICHERO, true);
            String texto = string;
            f.write(texto.getBytes());
            f.close();
        } catch (Exception e) {
            Log.e("Asteroides", e.getMessage(), e);
        }
    }

    @Override
    public List<String> listaPuntuaciones(int cantidad) {
        /*ArrayList<Puntuacion> puntuaciones;
        if (string == null) {
            puntuaciones = new ArrayList<>();
        } else {
            puntuaciones = gson.fromJson(string, type);
        }
        List<String> salida = new ArrayList<>();
        for (Puntuacion puntuacion : puntuaciones) {
            salida.add(puntuacion.getPuntos() + " " + puntuacion.getNombre());
        }
        return salida;*/
        Clase objeto;
        if (string == null) {
            objeto = new Clase();
        } else {
            objeto = gson.fromJson(string, type);
        }
        List<String> salida = new ArrayList<>();
        for (Puntuacion puntuacion : objeto.puntuaciones) {
            salida.add(puntuacion.getPuntos() + " " + puntuacion.getNombre());
        }
        return salida;
    }

    public class Clase {
        private ArrayList<Puntuacion> puntuaciones = new ArrayList<>();
        private boolean guardado;
    }
}
