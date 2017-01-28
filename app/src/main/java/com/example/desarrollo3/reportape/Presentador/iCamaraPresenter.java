package com.example.desarrollo3.reportape.Presentador;

import android.graphics.Bitmap;
import android.view.View;

/**
 * Created by Desarrollo3 on 28/01/2017.
 */

public interface iCamaraPresenter {
    void permisosVerificados();
    void permisosCamaraVerificados();
    Bitmap gestionarCamara();
    void desactivarBotones(View view);
    void enviarReportePresenter();
}
