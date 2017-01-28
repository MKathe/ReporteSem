package com.example.desarrollo3.reportape.View;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.desarrollo3.reportape.Adaptadores.ReporteSemaforoAdaptador;
import com.example.desarrollo3.reportape.Datos.ReporteSemaforo;
import com.example.desarrollo3.reportape.Presentador.RecyclerViewReportesFragmentPresenter;
import com.example.desarrollo3.reportape.R;

import java.util.ArrayList;

/**
 * Created by Desarrollo3 on 28/01/2017.
 */

public class RecicleViewReportesFragment extends Fragment implements IRecicleViewReportesFragment{


    private RecyclerView listareportes;
    private RecyclerViewReportesFragmentPresenter presenter;
    private  String email;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recicleviewrs, container, false);

        listareportes = (RecyclerView) v.findViewById(R.id.rvReportes);
        presenter = new RecyclerViewReportesFragmentPresenter(this, context);
        email = obtenerEmail();
        return v;
    }

    @Override
    public void generarLinearLayoutVertical() {
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        System.out.println("Se ha generado el LinearLayout");
        listareportes.setLayoutManager(llm);
    }

    @Override
    public ReporteSemaforoAdaptador crearAdaptador(ArrayList<ReporteSemaforo> reportes) {
        ReporteSemaforoAdaptador adaptador = new ReporteSemaforoAdaptador(reportes,getActivity());
        System.out.println("Se ha creado el adaptador");
        return adaptador;
    }

    @Override
    public void iniciaLizarAdaptador(ReporteSemaforoAdaptador adaptador) {
        System.out.println("Se ha inicializado el adaptador");
        listareportes.setAdapter(adaptador);
    }

    @Override
    public String obtenerEmail() {
        // Recogemos el email enviado desde Coordinador
        String  email = this.getArguments().getString("email");
        System.out.println("--------------------------------------");
        System.out.println("EMAIL ----> :" + email);
        return email;
    }
}