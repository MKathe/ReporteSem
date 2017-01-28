package com.example.desarrollo3.reportape.Datos;

import java.util.ArrayList;

/**
 * Created by ejdez on 13/01/2017.
 */

public class User {
    public String estado;
    public ArrayList<ReporteSemaforo> datos;

    public User(String estado, ArrayList<ReporteSemaforo> datos) {
        this.estado=estado;
        this.datos=datos;
    }
}
