package com.example.desarrollo3.reportape.View;

import com.example.desarrollo3.reportape.Adaptadores.ReporteSemaforoAdaptador;
import com.example.desarrollo3.reportape.Datos.ReporteSemaforo;

import java.util.ArrayList;

/**
 * Created by Desarrollo3 on 28/01/2017.
 */

public interface IRecicleViewReportesFragment {
    public void generarLinearLayoutVertical();

    public ReporteSemaforoAdaptador crearAdaptador(ArrayList<ReporteSemaforo> reporte);

    public void iniciaLizarAdaptador(ReporteSemaforoAdaptador adaptador);

    public String obtenerEmail();
}
