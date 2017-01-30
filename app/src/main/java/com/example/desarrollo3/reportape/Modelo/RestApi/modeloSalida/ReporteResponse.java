package com.example.desarrollo3.reportape.Modelo.RestApi.modeloSalida;

import com.example.desarrollo3.reportape.Datos.ReporteSemaforo;


import java.util.ArrayList;

/**
 * Created by Desarrollo3 on 11/01/2017.
 */

public class ReporteResponse {

    // Modelo
    ArrayList<ReporteSemaforo> reportes;

    public ArrayList<ReporteSemaforo> getReportes() {
        return reportes;
    }
    public void setReportes(ArrayList<ReporteSemaforo> reportes) {
        this.reportes = reportes;
    }
}
