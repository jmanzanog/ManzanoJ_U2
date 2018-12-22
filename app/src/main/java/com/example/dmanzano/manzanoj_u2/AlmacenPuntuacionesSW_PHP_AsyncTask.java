package com.example.dmanzano.manzanoj_u2;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class AlmacenPuntuacionesSW_PHP_AsyncTask implements AlmacenPuntuaciones {
    HttpURLConnection conexion;
    private Context contexto;

    public AlmacenPuntuacionesSW_PHP_AsyncTask(Context contexto) {
        this.contexto = contexto;
    }

    public List<String> listaPuntuaciones(int cantidad) {
        try {
            TareaLista tarea = new TareaLista();
            tarea.execute(cantidad);
            return tarea.get(4, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            Toast.makeText(contexto, "Tiempo excedido al conectar",
                    Toast.LENGTH_LONG).show();
        } catch (CancellationException e) {
            Toast.makeText(contexto, "Error al conectar con servidor",
                    Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(contexto, "Error con tarea asíncrona",
                    Toast.LENGTH_LONG).show();
        }
        return new ArrayList<String>();
    }

    private class TareaLista extends AsyncTask<Integer, Void, List<String>> {
        @Override
        protected List<String> doInBackground(Integer... cantidad) {
            List<String> result = new ArrayList<String>();
            try {
                //URL url = new URL("http://158.42.146.127/puntuaciones/lista.php" + "?max=20");
                URL url = new URL("https://jmanzanog.000webhostapp.com/puntuaciones/lista.php" + "?max=20");
                conexion = (HttpURLConnection) url.openConnection();
                if (conexion.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
                    String linea = reader.readLine();
                    while (!linea.equals("")) {
                        result.add(linea);
                        linea = reader.readLine();
                    }
                    reader.close();
                } else {
                    Log.e("Asteroides", conexion.getResponseMessage());
                }
            } catch (Exception e) {
                Log.e("Asteroides", e.getMessage(), e);
            } finally {
                if (conexion != null) conexion.disconnect();
                return result;
            }
        }
    }


    public void guardarPuntuacion(int puntos, String nombre, long fecha) {
        try {
            TareaGuardar tarea = new TareaGuardar();
            tarea.execute(String.valueOf(puntos), nombre, String.valueOf(fecha));
            tarea.get(4, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            Toast.makeText(contexto, "Tiempo excedido al conectar",
                    Toast.LENGTH_LONG).show();
        } catch (CancellationException e) {
            Toast.makeText(contexto, "Error al conectar con servidor",
                    Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(contexto, "Error con tarea asíncrona",
                    Toast.LENGTH_LONG).show();
        }
    }

    private class TareaGuardar extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... param) {
            try {
                //URL url = new URL("http://158.42.146.127/puntuaciones/nueva.php?" + "puntos=" + puntos + "&nombre=" + URLEncoder.encode(nombre, "UTF-8") + "&fecha=" + fecha);
                URL url = new URL("https://jmanzanog.000webhostapp.com/puntuaciones/nueva.php?" + "puntos=" + param[0] + "&nombre=" + URLEncoder.encode((param[1]), "UTF-8") + "&fecha=" + param[2]);
                HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
                if (conexion.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
                    String linea = reader.readLine();
                    if (!linea.equals("OK")) {
                        Log.e("Asteroides", "Error en servicio Web nueva");
                    }
                } else {
                    Log.e("Asteroides", conexion.getResponseMessage());
                }
            } catch (Exception e) {
                Log.e("Asteroides", e.getMessage(), e);
            } finally {
                if (conexion != null) conexion.disconnect();
            }
            return null;
        }
    }
}

