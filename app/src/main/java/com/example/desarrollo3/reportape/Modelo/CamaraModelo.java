package com.example.desarrollo3.reportape.Modelo;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.desarrollo3.reportape.Datos.ReporteSemaforo;
import com.example.desarrollo3.reportape.Datos.Ubicacion;
import com.example.desarrollo3.reportape.Modelo.RestApi.EndpointsApi;
import com.example.desarrollo3.reportape.Modelo.RestApi.adaptador.RestApiAdapter;
import com.example.desarrollo3.reportape.View.CamaraFragment;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Created by Desarrollo3 on 28/01/2017.
 */

public class CamaraModelo extends AppCompatActivity implements iCamaraModelo, LocationListener {
    Context context;
    CamaraFragment camaraFragment;
    //// Variables PERMISOS
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_NETWORK_STATE
    };
    ///// VARIABLES CAMARA
    private static String APP_DIRECTORY = "ReportaPe/";
    private static String MEDIA_DIRECTORY = APP_DIRECTORY + "MiReportePE";
    private final int MY_PERMISSIONS = 100;
    private final int PHOTO_CODE = 200;
    private Bitmap fotoEnviar;
    private ImageView mSetImage;
    private Button mOptionButton;
    private RelativeLayout mRlView;
    private String mPath;
    public String foto;
    public Bitmap bitmap;

    //// VARIABLES EMAIL
    private String email;

    //// VARIABLES LOCALIZACION
    LocationManager handle;
    boolean isGPSEnabled, isNetworkEnabled;
    private Ubicacion ubicacion;
    double latitud, longitud;
    AlertDialog alert = null;
    public String direccion;
    Location location, localizacion;
    boolean result;


    @Override
    public boolean verificarPermisosModel(Activity activity) {
        // Check if we have write permission
        int permission3 = ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permission3 != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS,
                    REQUEST_EXTERNAL_STORAGE
            );
            result = true;
        } else {

            result = false;
        }

        return result;
    }

    @Override
    public boolean verificarPermisosCamaraModel() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true;

        if ((checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) &&
                (checkSelfPermission(CAMERA) == PackageManager.PERMISSION_GRANTED))
            return true;

        if ((shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)) || (shouldShowRequestPermissionRationale(CAMERA))) {
            Snackbar.make(mRlView, "Los permisos son necesarios para poder usar la aplicación",
                    Snackbar.LENGTH_INDEFINITE).setAction(android.R.string.ok, new View.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, MY_PERMISSIONS);
                }
            });
        } else {
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, MY_PERMISSIONS);
        }

        return false;
    }

    //// CAMARA

    @Override
    public Bitmap tomarFotoModel() {
        File file = new File(Environment.getExternalStorageDirectory(), MEDIA_DIRECTORY);
        boolean isDirectoryCreated = file.exists();

        if (!isDirectoryCreated)
            isDirectoryCreated = file.mkdirs();

        if (isDirectoryCreated) {
            Long timestamp = System.currentTimeMillis() / 1000;
            String imageName = timestamp.toString() + ".jpg";

            mPath = Environment.getExternalStorageDirectory() + File.separator + MEDIA_DIRECTORY
                    + File.separator + imageName;

            File newFile = new File(mPath);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(newFile));
            startActivityForResult(intent, PHOTO_CODE);
        }
        return fotoEnviar;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PHOTO_CODE:
                    MediaScannerConnection.scanFile(context,
                            new String[]{mPath}, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                                @Override
                                public void onScanCompleted(String path, Uri uri) {
                                    Log.i("ExternalStorage", "Scanned " + path + ":");
                                    Log.i("ExternalStorage", "-> Uri = " + uri);
                                }
                            });

                    //Bitmap bitmap = BitmapFactory.decodeFile(mPath);
                    // mSetImage.setImageBitmap(bitmap);
                    int targetW = mSetImage.getWidth();
                    System.out.println("WIDTH: " + targetW);
                    int targetH = mSetImage.getHeight();
                    System.out.println("HEIGHT: " + targetH);


                    // Get the dimensions of the bitmap
                    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                    bmOptions.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(mPath, bmOptions);
                    int photoW = bmOptions.outWidth;

                    System.out.println("WIDTH: " + photoW);

                    int photoH = bmOptions.outHeight;

                    System.out.println("HEIGHT: " + photoH);

                    // Determine how much to scale down the image
                    int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

                    // Decode the image file into a Bitmap sized to fill the View
                    bmOptions.inJustDecodeBounds = false;
                    bmOptions.inSampleSize = scaleFactor;
                    bmOptions.inPurgeable = true;

                    Bitmap bitmap = BitmapFactory.decodeFile(mPath, bmOptions);
                    fotoEnviar = bitmap;
                    //foto = getBase64(bitmap);
                    //mSetImage.setImageBitmap(bitmap);
                    break;

            }
        }
    }

    public String obtenerCadenaFoto() {
        foto = getBase64(bitmap);
        System.out.println("STRING --->" + foto);
        return foto;
    }


    public static String getBase64(Bitmap bitmap) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();

            return Base64.encodeToString(byteArray, Base64.NO_WRAP);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_PERMISSIONS) {
            if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(context, "Permisos aceptados", Toast.LENGTH_SHORT).show();
                mOptionButton.setEnabled(true);
            }
        } else {
            showExplanation();
        }
    }

    private void showExplanation() {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        builder.setTitle("Permisos denegados");
        builder.setMessage("Para usar las funciones de la app necesitas aceptar los permisos");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
            /*@Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }*/
        });

        builder.show();
    }

    @Override
    public void desactivarBotonesCamara(View view) {

    }
    ////  LOCALIZACIÓN


    public Location iniciarServicio() {
        // Primero obtenemos la localización por GPS
        if (isGPSEnabled) {
            //Se activan las notificaciones de localización con los parámetros: proveedor,
            // tiempo mínimo de actualización, distancia mínima, Locationlistener
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return location;
            }
            handle.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2 * 20 * 1000, 10, this);
            // enviamos la locaclizacion a una variable
            location = handle.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            System.out.println("LOCATION  ----> " + location);
            // en caso de ser null obtenemos localizacion por network
            if (location == null) {
                handle.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 20 * 1000, 10, this);
            } else {
                System.out.println("LOCALIZACION POR GPS  ----> " + location);
            }
            // enviamos la locaclizacion a la una función que nos da lat y long
            // guardamos el resultado en un objeto Ubicación.
            ubicacion = obtenerUbicacion(location);
        }
        return  location;
    }


    public void AlertNoGpsModel() {
        // Creamos un Dialog para activar el GPS
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Light_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(context);
        }
        // Mensaje que se muestra
        builder.setMessage("El sistema GPS esta desactivado, ¿Desea activarlo?")
                // mensaje que no se puede cerrar
                .setCancelable(false)
                // Creamos botones
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        alert = builder.create();
        alert.show();
    }


    @Override
    public boolean verificarLocalizacionModel() {
        //Crea el objeto que gestiona las localizaciones
        handle = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        // obtiene el estadoo del GPS
        isGPSEnabled = handle.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // si está desactivado enviamos alerta de activar.
        if (!isGPSEnabled) {
            AlertNoGpsModel();
        }
        // Obtiene estado de la red
        isNetworkEnabled = handle.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        return true;
    }

    @Override
    public Ubicacion obtenerUbicacion(Location loc) {
        Ubicacion ubicacion;
        if (loc == null) {
            //Si no se encuentra localización, se mostrará "Desconocida"
            ubicacion = new Ubicacion("latitud Desconocida", "longitud Desconocida");
            System.out.println("UBICACIÓN DESCONOCIDA");

        } else {
            //Si se encuentra, se mostrará la latitud y longitud
            latitud = loc.getLatitude();
            longitud = loc.getLongitude();
            // Convertimos lat y log a string para guardarlos en ubicacion.
            ubicacion = new Ubicacion(String.valueOf(latitud), String.valueOf(longitud));
            System.out.println("Entró a la ubcación");
        }
        return ubicacion;
    }

    @Override
    public Location obtenerLocation() {
        localizacion = iniciarServicio();
        return localizacion;
    }

    //// Devuelve dirección
    public String setLocation(Location loc) {
        //Obtener la direccion de la calle a partir de la latitud y la longitud
        if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {
            try {
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(
                        loc.getLatitude(), loc.getLongitude(), 1);
                if (!list.isEmpty()) {
                    Address DirCalle = list.get(0);
                    direccion = DirCalle.getAddressLine(0);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return direccion;
    }
    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }


    /// OBTENER CORREO
    @Override
    public String obtenerEmail() {
        email = camaraFragment.Email();
        return email;
    }


    @Override
    public void enviarReporteModelo() {
        // Creamos el adaptador para el envio REST
        RestApiAdapter restApiAdapter = new RestApiAdapter();
        Gson gsonMediaRecent = restApiAdapter.construyeGsonDeserializador();
        EndpointsApi endpointsApi = restApiAdapter.establecerConexionRestApi(gsonMediaRecent);
        System.out.println("EL EMAIL ES ---> " + email);
        ReporteSemaforo enviarReporte = new ReporteSemaforo(foto, email, String.valueOf(latitud), String.valueOf(longitud),"Descripcion");
        Call<ReporteSemaforo> call = endpointsApi.createUser(enviarReporte);
        call.enqueue(new Callback<ReporteSemaforo>() {
            @Override
            public void onResponse(Call<ReporteSemaforo> call, Response<ReporteSemaforo> response) {
                System.out.printf(String.valueOf(call));
                System.out.printf(String.valueOf(response));
                System.out.println("ENVIADO");
                //ReporteSemaforo enviar = response.body();
            }

            @Override
            public void onFailure(Call<ReporteSemaforo> call, Throwable t) {
                System.out.printf(String.valueOf(call));
                System.out.println("*+++++++++++++++++++++*");
                System.out.printf(String.valueOf(t));
                System.out.println("NO ENVIADO");
            }
        });
    }

}