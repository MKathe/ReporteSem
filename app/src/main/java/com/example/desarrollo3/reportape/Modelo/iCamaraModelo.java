package com.example.desarrollo3.reportape.Modelo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.location.Location;
import android.view.View;

import com.example.desarrollo3.reportape.Datos.Ubicacion;

/**
 * Created by Desarrollo3 on 27/01/2017.
 */

public interface iCamaraModelo {
    boolean verificarPermisosModel(Activity activity);
    boolean verificarPermisosCamaraModel();
    Bitmap tomarFotoModel();
    String obtenerCadenaFoto();
    String obtenerEmail();
    void desactivarBotonesCamara(View view);
    void AlertNoGpsModel();
    boolean verificarLocalizacionModel();
    Ubicacion obtenerUbicacion(Location loc);
    Location obtenerLocation();
    void enviarReporteModelo();
}
