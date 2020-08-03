package com.guilherme.appempresas.empresas;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.guilherme.appempresas.R;

import butterknife.ButterKnife;

public class EmpresaActivity extends AppCompatActivity {

    EmpresaFragment fragment;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enterprise_activity);
        Intent intent = getIntent();
        if (savedInstanceState == null) {
            Bundle bundle = new Bundle();
            Log.d("access-token", intent.getStringExtra("access-token"));
            bundle.putString("client", intent.getStringExtra("client"));
            bundle.putString("access-token", intent.getStringExtra("access-token"));
            bundle.putString("uid", intent.getStringExtra("uid"));
            fragment = EmpresaFragment.newInstance();
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
        }
        Toolbar toolbar = findViewById(R.id.bar);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        handleIntent(getIntent());
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Intent INTENT = new Intent("QUERY");
            INTENT.putExtra("QUERY", query);
            sendBroadcast(INTENT);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);

        return true;
    }
}