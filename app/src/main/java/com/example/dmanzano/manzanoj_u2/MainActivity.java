package com.example.dmanzano.manzanoj_u2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private FragmentTabHost tabHost;
    private Button bAcercaDe;
    private Button bSalir;
    private Button bconfig;
    private Button bjugar;
    public static AlmacenPuntuaciones almacen= new AlmacenPuntuacionesList();
    private TextView textView;
    private Animation animation, animation1, animation2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        Intent i = new Intent(this, Juego.class);
        startActivity(i);
    }

}
