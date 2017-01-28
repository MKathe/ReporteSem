package com.example.desarrollo3.reportape.Datos;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Desarrollo3 on 23/01/2017.
 */

public class Login {
    public String id;
    @SerializedName("usuario_correo")
    public String correo;
    public String estado;
    public String token;
    public String contador;
    public String avatar;

    public Login(String correo){
        this.correo = correo;
    }



}
