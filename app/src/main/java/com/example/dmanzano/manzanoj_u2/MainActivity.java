package com.example.dmanzano.manzanoj_u2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.LruCache;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {
    private FragmentTabHost tabHost;
    private Button bAcercaDe;
    private Button bSalir;
    private Button bconfig;
    private Button bjugar;
    public static AlmacenPuntuaciones almacen;
    private TextView textView;
    private Animation animation, animation1, animation2;
    private MediaPlayer mp;
    static final int ACTIV_JUEGO = 0;
    private SharedPreferences pref;
    public static RequestQueue colaPeticiones;
    public static ImageLoader lectorImagenes;

    private String[] PERMISSIONS = {
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        if (pref.getString("puntuaciones", "1").equals("0")) {
            almacen = new AlmacenPuntuacionesList();
        }
        if (pref.getString("puntuaciones", "1").equals("1")) {
            almacen = new AlmacenPuntuacionesPreferencias(this);
        }
        if (pref.getString("puntuaciones", "1").equals("2")) {
            almacen = new AlmacenPuntuacionesFicheroInterno(this);

        }
        if (pref.getString("puntuaciones", "1").equals("3")) {
            if (!hasPermissions(this, PERMISSIONS)) {
                ActivityCompat.requestPermissions(this, PERMISSIONS, 1);
            }
            almacen = new AlmacenPuntuacionesFicheroExterno(this);
        }
        if (pref.getString("puntuaciones", "1").equals("4")) {

            almacen = new AlmacenPuntuacionesFicheroExtApl(this);
        }
        if (pref.getString("puntuaciones", "1").equals("5")) {

            almacen = new AlmacenPuntuacionesXML_SAX(this);
        }
        if (pref.getString("puntuaciones", "1").equals("6")) {

            almacen = new AlmacenPuntuacionesGSon(this);
        }
        if (pref.getString("puntuaciones", "1").equals("7")) {

            almacen = new AlmacenPuntuacionesJSon(this);
        }
        if (pref.getString("puntuaciones", "1").equals("8")) {

            almacen = new AlmacenPuntuacionesSQLite(this);
        }
        if (pref.getString("puntuaciones", "1").equals("9")) {

            almacen = new AlmacenPuntuacionesSQLiteRel(this);
        }
        if (pref.getString("puntuaciones", "1").equals("10")) {

            almacen = new AlmacenPuntuacionesProvider(this);
        }
        if (pref.getString("puntuaciones", "1").equals("11")) {

            almacen = new AlmacenPuntuacionesSocket();
        }
        if (pref.getString("puntuaciones", "1").equals("12")) {

            almacen = new AlmacenPuntuacionesSW_PHP();
        }
        textView = (TextView) findViewById(R.id.textView2);
        bAcercaDe = findViewById(R.id.button3);
        bSalir = findViewById(R.id.button4);
        bconfig = findViewById(R.id.button2);
        bjugar = findViewById(R.id.button);


        bjugar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mostrarPreferencias(null);
                lanzarJuego(null);
            }
        });
        bAcercaDe.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                lanzarAcercaDe(null);
            }
        });
        bconfig.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                lanzarPreferencias(null);
            }
        });

        bSalir.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                lanzarPuntuaciones(null);
                // finish();
            }
        });
        animation = AnimationUtils.loadAnimation(this, R.anim.giro_con_zoom);
        animation1 = AnimationUtils.loadAnimation(this, R.anim.aparecer);
        animation2 = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_derecha);

        bjugar.startAnimation(animation1);
        bconfig.startAnimation(animation2);

        mp = MediaPlayer.create(this, R.raw.audio);
        mp.start();
        colaPeticiones = Volley.newRequestQueue(this);
        lectorImagenes = new ImageLoader(colaPeticiones,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>(10);

                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }

                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }
                });

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());

    }

    public void sePulsa(View view) {
        Toast.makeText(this, "Pulsado", Toast.LENGTH_SHORT).show();
    }

    public void lanzarAcercaDe(View view) {
        textView.startAnimation(animation);
        Intent i = new Intent(this, AcercaDeActivity.class);
        startActivity(i);
    }

    public void lanzarPreferencias(View view) {
        Intent i = new Intent(this, PreferenciasActivity.class);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true; /** true -> el menú ya está visible */
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            lanzarPreferencias(null);
            return true;
        }
        if (id == R.id.acercaDe) {
            lanzarAcercaDe(null);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void mostrarPreferencias(View view) {
        SharedPreferences pref =
                PreferenceManager.getDefaultSharedPreferences(this);
        String s = "música: " + pref.getBoolean("musica", true)
                + ", gráficos: " + pref.getString("graficos", "?");
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    public void lanzarPuntuaciones(View view) {
        Intent i = new Intent(this, Puntuaciones.class);
        startActivity(i);
    }

    public void lanzarJuego(View view) {
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        if (pref.getString("puntuaciones", "1").equals("0")) {
            almacen = new AlmacenPuntuacionesList();
        }
        if (pref.getString("puntuaciones", "1").equals("1")) {
            almacen = new AlmacenPuntuacionesPreferencias(this);
        }
        if (pref.getString("puntuaciones", "1").equals("2")) {
            almacen = new AlmacenPuntuacionesFicheroInterno(this);

        }
        if (pref.getString("puntuaciones", "1").equals("3")) {
            if (!hasPermissions(this, PERMISSIONS)) {
                ActivityCompat.requestPermissions(this, PERMISSIONS, 1);
            }
            almacen = new AlmacenPuntuacionesFicheroExterno(this);

        }
        if (pref.getString("puntuaciones", "1").equals("4")) {

            almacen = new AlmacenPuntuacionesFicheroExtApl(this);

        }
        if (pref.getString("puntuaciones", "1").equals("5")) {

            almacen = new AlmacenPuntuacionesXML_SAX(this);
        }
        if (pref.getString("puntuaciones", "1").equals("6")) {

            almacen = new AlmacenPuntuacionesGSon(this);
        }
        if (pref.getString("puntuaciones", "1").equals("7")) {

            almacen = new AlmacenPuntuacionesJSon(this);
        }
        if (pref.getString("puntuaciones", "1").equals("8")) {

            almacen = new AlmacenPuntuacionesSQLite(this);
        }
        if (pref.getString("puntuaciones", "1").equals("9")) {

            almacen = new AlmacenPuntuacionesSQLiteRel(this);
        }
        if (pref.getString("puntuaciones", "1").equals("10")) {

            almacen = new AlmacenPuntuacionesProvider(this);
        }
        if (pref.getString("puntuaciones", "1").equals("11")) {

            almacen = new AlmacenPuntuacionesSocket();
        }
        if (pref.getString("puntuaciones", "1").equals("12")) {

            almacen = new AlmacenPuntuacionesSW_PHP();
        }
        Intent i = new Intent(this, Juego.class);
        startActivityForResult(i, ACTIV_JUEGO);
    }


    @Override
    protected void onPause() {
        mp.pause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mp.start();
    }

    @Override
    protected void onDestroy() {
        mp.stop();
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle estadoGuardado) {
        super.onSaveInstanceState(estadoGuardado);
        if (mp != null) {
            int pos = mp.getCurrentPosition();
            estadoGuardado.putInt("posicion", pos);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle estadoGuardado) {
        super.onRestoreInstanceState(estadoGuardado);
        if (estadoGuardado != null && mp != null) {
            int pos = estadoGuardado.getInt("posicion");
            mp.seekTo(pos);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTIV_JUEGO && resultCode == RESULT_OK && data != null) {
            int puntuacion = data.getExtras().getInt("puntuacion");
            String nombre = "Jose_Manzano";
// Mejor leer nombre desde un AlertDialog.Builder o preferencias
            almacen.guardarPuntuacion(puntuacion, nombre,
                    System.currentTimeMillis());
            lanzarPuntuaciones(null);
        }
    }

    public boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}
