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

public class CamaraPresenter extends CamaraModelo implements iCamaraPresenter {
    private CamaraModelo camaraModelo;
    private  iCamaraPresenter icamarapresenter;
    private Context context;
    private Activity activity;
    private String fotoEnviar;
    public  String direccion;

    public CamaraPresenter(iCamaraPresenter icamarapresenter, Context context){
        this.icamarapresenter = icamarapresenter;
        this.context = context;

        camaraModelo = new CamaraModelo();

    }
    @Override
    public void permisosVerificados() {
        camaraModelo.verificarPermisosModel(activity);
        /*if ( camaraModelo.verificarPermisosModel(activity) == true){
                activaLocalizacion();        }
        else{
            Toast.makeText(context, "Para continuar debe aceptar los permisos de localización", Toast.LENGTH_SHORT).show();
        }*/

    }

    @Override
    public void permisosCamaraVerificados() {
        camaraModelo.verificarPermisosCamaraModel();
        /*if ( camaraModelo.verificarPermisosCamaraModel() == false){
            gestionarCamara();
        }else{
            Toast.makeText(context, "Para continuaar debe aceptar los permisos de Cámara", Toast.LENGTH_SHORT).show();
        }*/
    }

    @Override
    public Bitmap gestionarCamara() {
        camaraModelo.tomarFotoModel();
        return null;
    }

    @Override
    public String activaLocalizacion() {
        if ( camaraModelo.verificarLocalizacionModel() == true){
            direccion = obtenerDireccion();
        }else{
            Toast.makeText(context, "No se ha podido obtener la localización", Toast.LENGTH_SHORT).show();
        }
        return direccion;
    }


    @Override
    public String obtenerDireccion() {
        direccion = camaraModelo.setLocation(camaraModelo.obtenerLocation());
        return direccion;
    }

    @Override
    public void desactivarBotones(View view) {

    }

    @Override
    public void enviarReportePresenter() {
        camaraModelo.enviarReporteModelo();
    }
}
