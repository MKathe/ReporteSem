package com.example.desarrollo3.reportape.Presentador;

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

import com.example.desarrollo3.reportape.Datos.ReporteSemaforo;
import com.example.desarrollo3.reportape.Datos.User;
import com.example.desarrollo3.reportape.Modelo.RestApi.EndpointsApi;
import com.example.desarrollo3.reportape.Modelo.RestApi.adaptador.RestApiAdapter;
import com.example.desarrollo3.reportape.View.IRecicleViewReportesFragment;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;

/**
 * Created by Desarrollo3 on 28/01/2017.
 */

public class RecyclerViewReportesFragmentPresenter implements IRecyclerViewReportesFragmentPresenter {
    private IRecicleViewReportesFragment iRecicleViewReportesFragment;
    private Context context;
    private ArrayList<ReporteSemaforo> reportes=null;
    User user;

    public RecyclerViewReportesFragmentPresenter(IRecicleViewReportesFragment iRecicleViewReportesFragment, Context context) {
        this.iRecicleViewReportesFragment= iRecicleViewReportesFragment;
        this.context = context;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        // obtenerReportes();
        try {
            obtenerReportesRecientes();

        }catch(Exception e){
            Log.d("Error","ERROR AL OBTENER REPORTES --> "+e);
        }
    }

    @Override
    public void obtenerReportesRecientes() throws IOException {

        // IMPLEMENTACION DE RETROFIT

        //Ejecuta la conexion, accede a endpoints para ejecutar la llamada conttibutors
        //
        RestApiAdapter restApiAdapter = new RestApiAdapter();
        Gson gsonReporte = restApiAdapter.construyeGsonDeserializador();
        EndpointsApi endpointsApi = restApiAdapter.establecerConexionRestApi(gsonReporte);
        Call<User> call = endpointsApi.contributors(iRecicleViewReportesFragment.obtenerEmail());
        user = call.execute().body();
        reportes=user.datos;
        for (ReporteSemaforo us : reportes) {
            System.out.println(us.getReportegrupal_id());
            System.out.println(us.getReporteusuario_correo());
            System.out.println(us.getReporteusuario_fechahora());

        }
        mostrarReportesRV();
    }

    @Override
    public void mostrarReportesRV() {
        iRecicleViewReportesFragment.iniciaLizarAdaptador(iRecicleViewReportesFragment.crearAdaptador(reportes));
        iRecicleViewReportesFragment.generarLinearLayoutVertical();
    }
}
