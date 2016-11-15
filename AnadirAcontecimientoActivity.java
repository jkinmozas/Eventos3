package com.example.joaquin.events;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AnadirAcontecimientoActivity extends AppCompatActivity  {
    Button buttonOk;
    EditText editTextID;
    public static Context context;
    final private int REQUEST_CODE_INTERNET = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_acontecimiento);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        buttonOk = (Button) findViewById(R.id.buttonAnadir);
        editTextID = (EditText) findViewById(R.id.editTextID);
        context = this;

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textoID = editTextID.getText().toString();
                if (textoID.isEmpty()) {
                    Snackbar.make(v, "Introduce un ID", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                } else {
                    MyLog.d("build", " " + Build.VERSION.SDK_INT);
                    // Comprobar permiso
                    int permissionCheck = ContextCompat.checkSelfPermission(context, Manifest.permission.INTERNET);

                    if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                        new AnadirAcontecimientoAsyncTask(textoID).execute();
                    } else {
                        if (Build.VERSION.SDK_INT >= 23) {
                            // Marshmallow+
                            // Explicar permiso
                            if (ActivityCompat.shouldShowRequestPermissionRationale(AnadirAcontecimientoActivity.this, Manifest.permission.INTERNET)) {
                                Toast.makeText(context, "El permiso es necesario para utilizar internet", Toast.LENGTH_SHORT).show();
                            }

                            // Solicitar el permiso
                            ActivityCompat.requestPermissions(AnadirAcontecimientoActivity.this, new String[]{Manifest.permission.INTERNET}, REQUEST_CODE_INTERNET);

                        } else {
                            // Pre-Marshmallow
                            Snackbar.make(v, "No tienes permisos de internet", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                        }


                    }
                }

            }
        });




    }

}
