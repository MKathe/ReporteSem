package com.example.desarrollo3.reportape.Modelo.RestApi.deserializador;

import android.util.Log;

import com.example.desarrollo3.reportape.Datos.ReporteSemaforo;
import com.example.desarrollo3.reportape.Modelo.RestApi.JsonKeys;
import com.example.desarrollo3.reportape.Modelo.RestApi.modeloSalida.ReporteResponse;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Desarrollo3 on 11/01/2017.
 */

public class ReporteDeserializador implements JsonDeserializer<ReporteResponse>{
    @Override
    public ReporteResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new Gson();
        ReporteResponse reporteResponse = gson.fromJson(json, ReporteResponse.class);
        JsonArray reporteResponseData = json.getAsJsonObject().getAsJsonArray();
        reporteResponse.setReportes(deserializarReporteDeJson(reporteResponseData));
        //reporteResponse.setReportes(deserializarReporteDeJson(reporteResponseData));
        return reporteResponse;
    }

    private ArrayList<ReporteSemaforo> deserializarReporteDeJson(JsonArray reporteResponseData) {
        Log.e("FALLO LA CONEXION", reporteResponseData.toString());

        ArrayList<ReporteSemaforo> reportes = new ArrayList<>();
        for (int i = 0; i < reporteResponseData.size() ; i++) {
            JsonObject contactoResponseDataObject = reporteResponseData.get(i).getAsJsonObject();

            JsonObject userJson     = contactoResponseDataObject.getAsJsonObject(JsonKeys.USER_DATA);
            //Boolean estado          = userJson.get(JsonKeys.REPORTE_ESTADO).getAsBoolean();
            String direccion   = userJson.get(JsonKeys.REPORTE_DIRECCION).getAsString();
            String fecha = userJson.get(JsonKeys.REPORTE_FECHA).getAsString();

           /* ReporteSemaforo reporteActual = new ReporteSemaforo(foto, fecha,"lat", "long",direccion);
            reporteActual.setReporteusuario_fechahora(fecha);
            reporteActual.setReporteusuario_descripcion(direccion);
            //reporteActual.setEstado(estado);
            reportes.add(reporteActual);*/

        }

        return reportes;

    }
}
