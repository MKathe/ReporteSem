package com.example.desarrollo3.reportape.View;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.desarrollo3.reportape.Presentador.CamaraPresenter;
import com.example.desarrollo3.reportape.R;

/**
 * Created by Desarrollo3 on 28/01/2017.
 */

public class CamaraFragment extends Fragment implements iCamaraFragment{
    CamaraPresenter camaraPresenter;
    private Context context;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_camara, container, false);
        context = v.getContext();
        verificarPermisos();
        return v;

    }
    @Override
    public void verificarPermisos() {
        camaraPresenter.permisosVerificados();

    }

    @Override
    public void tomarFoto() {

    }

    @Override
    public void mostrarFoto(Bitmap image) {

    }

    @Override
    public void enviarReporte() {

    }

    @Override
    public void limpiarVista() {

    }

    @Override
    public void activarBotones() {

    }
}
