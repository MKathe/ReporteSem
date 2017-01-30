package com.example.desarrollo3.reportape.Modelo.RestApi;

import com.example.desarrollo3.reportape.Datos.Login;
import com.example.desarrollo3.reportape.Datos.ReporteSemaforo;
import com.example.desarrollo3.reportape.Datos.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Desarrollo3 on 11/01/2017.
 */

public interface EndpointsApi {
    // Creamos la interfaz
    // Aqui van todos los metodos que generan las peticiones para retrofit
    // la respuesta recibida se almacena en un objeto JSONresponse.

    // Utilizamos solicitud GET para obtener datos JSON.

    @GET("reporte/reporteporid/{reporteusuario_correo}")
    Call<User> contributors(
            @Path("reporteusuario_correo") String reporteusuario_correo
    );


    // Utilizamos solicitud POST para enviar datos a un JSON.
    //
    @POST("registrarreporteusuario/insertalo")
    Call<ReporteSemaforo> createUser(
            @Body ReporteSemaforo enviarReporte
    );

    @POST("login/registro_login")
    Call<Login> registroLogin(
            @Body Login login
    );

    /*
    @POST("registrarreporteusuario/insertalo")
    Call<ReporteSemaforo> createUser(@Part("reporteusuario_foto") RequestBody foto,
                                     @Part("reporteusuario_correo") String email,
                                     @Part("reporteusuario_latitud ") String latitud,
                                     @Part("reporteusuario_longitud") String longitud);*/



}
