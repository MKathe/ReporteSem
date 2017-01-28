package com.example.desarrollo3.reportape.Datos;

/**
 * Created by Dise07 on 30/12/2016.
 */

public class ReporteSemaforo {
    public String reporteusuario_id;
    public String reporteusuario_foto;
    public String reporteusuario_longitud;
    public String reporteusuario_latitud;
    public String reporteusuario_descripcion;
    public String reporteusuario_fechahora;
    public String reporteusuario_correo;
    public String reportegrupal_id;
    public String usuario_id;


    public ReporteSemaforo(String foto, String email, String latitud, String longitud, String descripcion ) {
        this.reporteusuario_foto = foto;
        this.reporteusuario_correo = email;
        this.reporteusuario_latitud = latitud;
        this.reporteusuario_longitud = longitud;
        this.reporteusuario_descripcion = descripcion;
    }

    public String getReporteusuario_id() {
        return reporteusuario_id;
    }

    public void setReporteusuario_id(String reporteusuario_id) {
        this.reporteusuario_id = reporteusuario_id;
    }

    public String getReporteusuario_correo() {
        return reporteusuario_correo;
    }

    public void setReporteusuario_correo(String reporteusuario_correo) {
        this.reporteusuario_correo = reporteusuario_correo;
    }

    public String getReporteusuario_longitud() {
        return reporteusuario_longitud;
    }

    public void setReporteusuario_longitud(String reporteusuario_longitud) {
        this.reporteusuario_longitud = reporteusuario_longitud;
    }

    public String getReporteusuario_latitud() {
        return reporteusuario_latitud;
    }

    public void setReporteusuario_latitud(String reporteusuario_latitud) {
        this.reporteusuario_latitud = reporteusuario_latitud;
    }

    public String getReporteusuario_fechahora() {
        return reporteusuario_fechahora;
    }

    public void setReporteusuario_fechahora(String reporteusuario_fechahora) {
        this.reporteusuario_fechahora = reporteusuario_fechahora;
    }

    public String getReporteusuario_descripcion() {
        return reporteusuario_descripcion;
    }

    public void setReporteusuario_descripcion(String reporteusuario_descripcion) {
        this.reporteusuario_descripcion = reporteusuario_descripcion;
    }

    public String getReporteusuario_foto() {
        return reporteusuario_foto;
    }

    public void setReporteusuario_foto(String reporteusuario_foto) {
        this.reporteusuario_foto = reporteusuario_foto;
    }

    public String getReportegrupal_id() {
        return reportegrupal_id;
    }

    public void setReportegrupal_id(String reportegrupal_id) {
        this.reportegrupal_id = reportegrupal_id;
    }

    public String getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(String usuario_id) {
        this.usuario_id = usuario_id;
    }
}
