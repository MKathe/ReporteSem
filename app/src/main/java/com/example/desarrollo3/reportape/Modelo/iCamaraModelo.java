package com.example.desarrollo3.reportape.Modelo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;

import com.example.desarrollo3.reportape.Datos.Ubicacion;

/**
 * Created by Desarrollo3 on 27/01/2017.
 */

public interface iCamaraModelo {
    boolean verificarPermisosModel(Activity activity);
    boolean verificarPermisosCamaraModel();
    void tomarFotoModel();
    void desactivarBotonesCamara(View view);
    Boolean AlertNoGpsModel();
    Boolean verificarLocalizacionModel();
    Ubicacion obtenerUbicacion();
    void enviarReporteModelo();
}
