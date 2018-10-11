package com.example.dmanzano.manzanoj_u2;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {
    private FragmentTabHost tabHost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabHost = findViewById(android.R.id.tabhost);
        tabHost.setup(this, getSupportFragmentManager(),android.R.id.tabcontent);
        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator(this.getApplicationContext().getString(R.string.asteroides)),
                Tab1.class, null);
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator(this.getApplicationContext().getString(R.string.calculadora)),
                Tab2.class, null);
        tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator(this.getApplicationContext().getString(R.string.boton)),
                Tab3.class, null);
    }

    public void sePulsa(View view){
        Toast.makeText(this, "Pulsado", Toast.LENGTH_SHORT).show();
    }
}
