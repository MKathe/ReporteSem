package com.example.desarrollo3.reportape.View;

import android.graphics.Bitmap;

/**
 * Created by Desarrollo3 on 28/01/2017.
 */

public interface iCamaraFragment {
    void verificarPermisos();
    void tomarFoto();
    void mostrarFoto(Bitmap image);
    void enviarReporte();
    void limpiarVista();
    void activarBotones();
}
