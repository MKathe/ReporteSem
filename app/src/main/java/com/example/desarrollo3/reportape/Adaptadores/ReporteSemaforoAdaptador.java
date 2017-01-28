package com.example.desarrollo3.reportape.Adaptadores;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.desarrollo3.reportape.Datos.ReporteSemaforo;
import com.example.desarrollo3.reportape.R;

import java.util.ArrayList;

/**
 * Created by Dise07 on 30/12/2016.
 */

public class ReporteSemaforoAdaptador  extends RecyclerView.Adapter<ReporteSemaforoAdaptador.ReporteSemaforoViewHolder> {

    ArrayList<ReporteSemaforo> reportes;
    Activity activity;


    public ReporteSemaforoAdaptador(ArrayList<ReporteSemaforo> reportes, Activity activity) {
        this.reportes  = reportes;
        this.activity   = activity;
    }

    //Inflar el layout y lo pasará al viewholder para que le obtenga la lista
    public ReporteSemaforoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_rs, parent, false);
        //Esta linea de codigo asocia el layour cardview_contacto con el MainActivity

        return new ReporteSemaforoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ReporteSemaforoViewHolder reporteSemaforoViewHolder, int position) {
        //Aca pasamos la lista de reportes
        final ReporteSemaforo reporteSemaforo = reportes.get(position);

        reporteSemaforoViewHolder.tvfechaReporte.setText(reporteSemaforo.reporteusuario_fechahora);
        reporteSemaforoViewHolder.tvDireccionReporte.setText(reporteSemaforo.reporteusuario_descripcion);
        //reporteSemaforoViewHolder.tvNumReporte.setText(String.valueOf(reporteSemaforo.)+" Reporte");

    }

    @Override
    public int getItemCount() {
        //Cantidad de elementos que contiene la cardview
        return reportes.size();
    }


    public static class ReporteSemaforoViewHolder extends RecyclerView.ViewHolder{
        //Esta clase es la que interacciona con el layout
        private TextView tvNumReporte;
        private TextView tvDireccionReporte;
        private TextView tvfechaReporte;

        public ReporteSemaforoViewHolder(View itemView) {
            super(itemView);

            tvNumReporte        = (TextView) itemView.findViewById(R.id.num_denuncia);
            tvDireccionReporte  = (TextView) itemView.findViewById(R.id.direccion);
            tvfechaReporte      = (TextView) itemView.findViewById(R.id.fecha);
        }
    }
}
