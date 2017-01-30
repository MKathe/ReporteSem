package com.example.desarrollo3.reportape.Modelo.RestApi.adaptador;

import com.example.desarrollo3.reportape.Modelo.RestApi.EndpointsApi;
import com.example.desarrollo3.reportape.Modelo.RestApi.deserializador.ReporteDeserializador;
import com.example.desarrollo3.reportape.Modelo.RestApi.modeloSalida.ReporteResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Desarrollo3 on 11/01/2017.
 */

public class RestApiAdapter {
    //devolvemos un objeto EndpointsApi
    public EndpointsApi establecerConexionRestApi(Gson gson) {
        // Creamos un constructor Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                // recibe url base a la que se conecta
                .baseUrl("http://rockenwebperu.com/api.semaforo.com/v1/")
                //.addConverterFactory(GsonConverterFactory.create() ---> forma general
                .addConverterFactory(GsonConverterFactory.create())
                //GsonConverter.. lo que hace es transformar ese json en un map clave: valor
                .build();
        // luego de ejecutar la llamada, deja inicializado el objeto
        // accede a la clase para poder usar sus metodos
        return retrofit.create(EndpointsApi.class);
    }

    // Gson para recibir datos
    public Gson construyeGsonDeserializador() {
        // Construimos un gson
        GsonBuilder gsonBuilder = new GsonBuilder();
        // se hace coincidir reporteResponse con ReporteDeserializador
        gsonBuilder.registerTypeAdapter(ReporteResponse.class, new ReporteDeserializador());
        return gsonBuilder.create();
    }

    public EndpointsApi ConexionRegistro(){
        // Creamos un constructor Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                // recibe url base a la que se conecta
                .baseUrl("http://rockenwebperu.com/api.semaforo.com/v1/")
                //.addConverterFactory(GsonConverterFactory.create() ---> forma general
                .addConverterFactory(GsonConverterFactory.create())
                //GsonConverter.. lo que hace es transformar ese json en un map clave: valor
                .build();
        // luego de ejecutar la llamada, deja inicializado el objeto
        // accede a la clase para poder usar sus metodos
        return retrofit.create(EndpointsApi.class);
    }
}
