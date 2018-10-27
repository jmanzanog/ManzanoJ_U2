package com.example.dmanzano.manzanoj_u2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

public class Puntuaciones extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private MiAdaptador adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.puntuaciones);
        recyclerView = findViewById(R.id.recycler_view);
        adaptador = new MiAdaptador(this, MainActivity.almacen.listaPuntuaciones(10));
        adaptador.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = recyclerView.getChildAdapterPosition(v);
                String s = MainActivity.almacen.listaPuntuaciones(10).get(pos);
                Toast.makeText(Puntuaciones.this, "Selección: " + pos + " - " + s, Toast.LENGTH_LONG).show();
            }
        });
        recyclerView.setAdapter(adaptador);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }
}