package com.example.desarrollo3.reportape;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements   View.OnClickListener,
        GoogleApiClient.OnConnectionFailedListener{
    //Variables Gmail
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int RC_SIGN_IN = 007;
    private GoogleApiClient mGoogleApiClient;
    private SignInButton btnSignIn;   //Botón predeterminado de ingreso
    private ProgressDialog mProgressDialog;
    public String personName,email; // para obtener datos de usuario
    public Uri fotoPersona;

    private  final int CODIGO_SOLICITUD_PERMISO = 1;
    private Context context;
    private Activity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        activity = this;
        btnSignIn = (SignInButton) findViewById(R.id.btn_sign_in);  // asignamos el botón de google
        btnSignIn.setOnClickListener(this);
        // Configuramos el inicio de sesión para solicitar el ID del usuario y/o de correo electrónico
        // El ID y el perfil básico se incluyen en DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Creamos el GoogleApiClient con acceso al API de loginGmail
        //  y a las opciones especificadas en el gso
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .addApi(AppIndex.API).build();

        // Personalización del botón G +
        btnSignIn.setSize(SignInButton.SIZE_STANDARD);
        btnSignIn.setScopes(gso.getScopeArray());

    }

    private void signIn() {
        // Se inicia la intención para que el usuario elija una cuenta de Google.
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    //// Recupera el resultado de inicio de sesión con GoogleSignInResult,
        public void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {  // se comprueba si tuvo éxito con el isSuccess
            // Se llama al método getSignInAccount para obtener un objeto GoogleSignInAccount
            // que contiene información sobre el usuario.
            GoogleSignInAccount acct = result.getSignInAccount();
            Log.e(TAG, "display name: " + acct.getDisplayName());
            personName = acct.getDisplayName();
            email = acct.getEmail();
            Uri personPhoto = acct.getPhotoUrl();
            fotoPersona = personPhoto;
            Log.e(TAG, "Name: " + personName + ", email: " + email);
            RegistroLogin(email);


        } else {
            // Signed out, show unauthenticated UI.
            //updateUI(false);
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Resultado devuelto al iniciar el intento en signIn()
        if (requestCode == RC_SIGN_IN) {
            // Si selecciona un correo Google se inicia la intención
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            // Enviamos en resultado para obtener el nombre y el email del correo elegido
            handleSignInResult(result);

        }
            // Se crea la intención para pasar a la siguiente actividad (Coordinador)
            Intent intent = new Intent(activity, Coordinador.class);
            // Pasamos el valor del email a la siguiente actividaad
            intent.putExtra("email",email);
            // Inicia la actividad
            startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // Si las credenciales del caché son validas se conecta de forma automática.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);



        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            //showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    handleSignInResult(googleSignInResult);
                }
            });
    }
    }

    //// Todos lo que se refiere a permisos de servicios como : bluetooth, gps se hace así.
    public boolean checarPermisoCamara() {
        int resultado = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA);
        if (resultado == PackageManager.PERMISSION_GRANTED) { //Compara codigo de permiso con el que devuelve el contexto
            return true;
        } else {
            return false;
        }
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        //Al dar click en el boton sign in se accede a signIn(), lo mismo
        //para las demás opciones (signOut,revokeAccess)
        switch (id) {
            case R.id.btn_sign_in:
                signIn();
                break;
        }



    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // muestra el estado de la conexión tras un error no resuelto o un problema
        // de conexión con Google APIs
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    //// Función para gestionar los permisos.
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case CODIGO_SOLICITUD_PERMISO:
                if (checarPermisoCamara()) {
                    Toast.makeText(this, "Ya esta activo el permiso", Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(this, "Debe activar el permiso", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    public  void RegistroLogin(String email) {
        /*RestApiAdapter restApiAdapter = new RestApiAdapter();
        EndpointsApi endpointsApi = restApiAdapter.ConexionRegistro();
        Login login = new Login(email);
        System.out.println("E M A I L: " + email);
        Call<Login> call = endpointsApi.registroLogin(login);

        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                System.out.println(response.body());
                System.out.println(response.message());
                System.out.println("ENVIADO");
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                System.out.println("NO ENVIADO");
                System.out.println("error: " + t.toString());
            }
        });*/

}}
