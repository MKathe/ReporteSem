package com.example.desarrollo3.reportape.Presentador;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import com.example.desarrollo3.reportape.Modelo.CamaraModelo;

import retrofit2.Call;

/**
 * Created by Desarrollo3 on 28/01/2017.
 */

public class CamaraPresenter implements iCamaraPresenter {
    private CamaraModelo camaraModelo;
    private  iCamaraPresenter icamarapresenter;
    private Context context;
    private Activity activity;

    public CamaraPresenter(iCamaraPresenter icamarapresenter, Context context){
        this.icamarapresenter = icamarapresenter;
        this.context = context;
        permisosVerificados();

    }
    @Override
    public void permisosVerificados() {
        if ( camaraModelo.verificarPermisosModel(activity) == true){
            gestionarCamara(); // ----> LOCALIZACIÓN???
        }else{
            Toast.makeText(context, "Para continuaar debe aceptar los permisos de localización", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void permisosCamaraVerificados() {
        if ( camaraModelo.verificarPermisosCamaraModel() == false){
            gestionarCamara();
        }else{
            Toast.makeText(context, "Para continuaar debe aceptar los permisos de localización", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public Bitmap gestionarCamara() {
        return null;
    }

    @Override
    public void desactivarBotones(View view) {

    }

    @Override
    public void enviarReportePresenter() {

    }
}
