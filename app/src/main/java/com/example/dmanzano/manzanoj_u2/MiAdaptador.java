package com.example.dmanzano.manzanoj_u2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MiAdaptador extends RecyclerView.Adapter<MiAdaptador.ViewHolder> {
    private LayoutInflater inflador;
    private List<String> lista;
    protected View.OnClickListener onClickListener;

    public MiAdaptador(Context context, List<String> lista) {
        this.lista = lista;
        inflador = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setOnItemClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflador.inflate(R.layout.elemento_lista, parent, false);
        v.setOnClickListener(onClickListener);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {
        holder.titulo.setText(lista.get(i));
        switch (Math.round((float) Math.random() * 3)) {
            case 0:
                holder.icon.setImageResource(R.drawable.asteroide1);
                break;
            case 1:
                holder.icon.setImageResource(R.drawable.asteroide2);
                break;
            default:
                holder.icon.setImageResource(R.drawable.asteroide3);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titulo, subtitutlo;
        public ImageView icon;

        ViewHolder(View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.titulo);
            subtitutlo = itemView.findViewById(R.id.subtitulo);
            icon = itemView.findViewById(R.id.icono);
        }
    }
}
