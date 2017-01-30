package com.example.desarrollo3.reportape.View;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.desarrollo3.reportape.Presentador.CamaraPresenter;
import com.example.desarrollo3.reportape.Presentador.RecyclerViewReportesFragmentPresenter;
import com.example.desarrollo3.reportape.R;

/**
 * Created by Desarrollo3 on 28/01/2017.
 */

public class CamaraFragment extends Fragment implements iCamaraFragment{
    CamaraPresenter camaraPresenter;
    private Bitmap bitmap;
    private ImageView mSetImage;
    private Context context;
    private TextView mensaje2;
    private Button mOptionButton;
    private String direccion;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_camara, container, false);
        context = v.getContext();
        camaraPresenter = new CamaraPresenter(camaraPresenter, context);
        verificarPermisos(activity);
        mensaje2 = (TextView) v.findViewById(R.id.mensaje2);

        mSetImage = (ImageView) v.findViewById(R.id.mostrarFoto);
        mOptionButton = (Button) v.findViewById(R.id.tomar_foto);
        if(verificarPermisos(getActivity()))
            mOptionButton.setEnabled(true);
        else
            mOptionButton.setEnabled(false);

        mOptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tomarFoto();
            }
        });
        mensaje2.setText(mostrarDireccion());
        return v;

    }
    @Override
    public boolean verificarPermisos(Activity activity) {
        camaraPresenter.permisosVerificados();
        camaraPresenter.permisosCamaraVerificados();

        return false;
    }

    @Override
    public void tomarFoto() {
         bitmap   = camaraPresenter.gestionarCamara();
        mostrarFoto(bitmap);
    }

    @Override
    public void mostrarFoto(Bitmap image) {
        // fotoString = camaraPresenter.cadenaFoto();
        mSetImage.setImageBitmap(bitmap);
    }

    @Override
    public String mostrarDireccion() {
        direccion = camaraPresenter.activaLocalizacion();
        return direccion;
    }

    @Override
    public void enviarReporte(View v) {

        Button btn_enviar = (Button) v.findViewById(R.id.btn_Enviar_Foto);
        btn_enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    camaraPresenter.enviarReportePresenter();
            }
        });
    }

    @Override
    public void limpiarVista() {

    }

    @Override
    public void activarBotones() {

    }

    //// obtiene correo
    public String Email () {
        String email = this.getArguments().getString("email");
        System.out.println("EL EMAIL ES ---------> " + email);
        return email;
    }
}
