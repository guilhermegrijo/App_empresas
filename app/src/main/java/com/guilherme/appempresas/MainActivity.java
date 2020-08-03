package com.guilherme.appempresas;

import android.Manifest;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.guilherme.appempresas.login.LoginFragment;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.listener.single.DialogOnDeniedPermissionListener;
import com.karumi.dexter.listener.single.PermissionListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PermissionListener dialogPermissionListener =
                DialogOnDeniedPermissionListener.Builder
                        .withContext(this)
                        .withTitle("PERMISSÃO PARA USAR INTERNET")
                        .withMessage("A conexão com a internet é necessária para utilizar o aplicativo")
                        .withButtonText(android.R.string.ok)
                        .build();


        Dexter.withContext(this).withPermission(Manifest.permission.INTERNET).withListener(dialogPermissionListener).check();

        setContentView(R.layout.main_activity);


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, LoginFragment.newInstance())
                    .commit();
        }

    }
}
