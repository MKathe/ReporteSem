package com.example.desarrollo3.reportape.View;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;

/**
 * Created by Desarrollo3 on 28/01/2017.
 */

public interface iCamaraFragment {
    boolean verificarPermisos(Activity activity);
    void tomarFoto();
    void mostrarFoto(Bitmap image);
    String mostrarDireccion();
    void enviarReporte(View view);
    void limpiarVista();
    void activarBotones();
}
