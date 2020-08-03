package com.guilherme.appempresas.empresasdetalhe;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.guilherme.appempresas.R;

import butterknife.ButterKnife;

public class EmpresaDetalheActivity extends AppCompatActivity {

    EmpresaDetalheFragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enterprise_detail_activity);
        Intent intent = getIntent();
        Toolbar toolbar = findViewById(R.id.bar);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        ab.setDisplayShowHomeEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowCustomEnabled(true);
        ab.setDisplayShowTitleEnabled(false);
        if (savedInstanceState == null) {

            Bundle bundle = new Bundle();

            bundle.putParcelable("ENTERPRISE", intent.getExtras().getParcelable("ENTERPRISE"));

            Log.d("Empresa Detalhe", String.valueOf((bundle != null)));

            fragment = EmpresaDetalheFragment.newInstance();
            fragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment)
                    .commitNow();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
}