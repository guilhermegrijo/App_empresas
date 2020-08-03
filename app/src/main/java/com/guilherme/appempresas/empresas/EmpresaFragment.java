package com.guilherme.appempresas.empresas;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.guilherme.appempresas.R;
import com.guilherme.appempresas.domain.model.Enterprise;
import com.guilherme.appempresas.domain.model.Response;
import com.guilherme.appempresas.empresasdetalhe.EmpresaDetalheActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

public class EmpresaFragment extends Fragment {

    @Inject
    EmpresaViewModelFactory factory;


    private EmpresaViewModel mViewModel;

    private View mView;


    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    @BindView(R.id.placeholder)
    TextView placeholder;

    @BindView(R.id.shimmer_view_container)
    ShimmerFrameLayout shimmerFrameLayout;

    @BindView(R.id.empty_list)
    TextView emptyList_textView;

    private ItemAdapter mAdapter;

    private Map<String, String> token;

    public static EmpresaFragment newInstance() {
        return new EmpresaFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, mView);
        IntentFilter filter = new IntentFilter("QUERY");
        Bundle bundle = getArguments();
        token = new HashMap<>();
        token.put("access-token", bundle.get("access-token").toString());
        token.put("client", bundle.get("client").toString());
        token.put("uid", bundle.get("uid").toString());
        getActivity().registerReceiver(receiverUpdateDownload, filter);
        mAdapter = new ItemAdapter();
        setupView();
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter("QUERY");
        getActivity().registerReceiver(receiverUpdateDownload, filter);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AndroidSupportInjection.inject(this);

        mViewModel = new ViewModelProvider(this, factory).get(EmpresaViewModel.class);
        mViewModel.response().observe(getViewLifecycleOwner(), this::processResponse);

    }

    public void processResponse(Response<List<Enterprise>> response) {
        switch (response.status) {
            case SUCCESS:
                placeholder.setVisibility(View.GONE);
                mAdapter.setItemList(response.data);
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.INVISIBLE);
                break;
            case ERROR:
                placeholder.setVisibility(View.GONE);
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.INVISIBLE);
                break;
            case LOADING:
                Log.d("Empresas", "Loading ");
                placeholder.setVisibility(View.GONE);
                shimmerFrameLayout.startShimmer();
                emptyList_textView.setVisibility(View.INVISIBLE);
                shimmerFrameLayout.setVisibility(View.VISIBLE);
                break;
        }

    }

    private void setupView() {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setEmptyList_textView(emptyList_textView);
        mAdapter.setListener((item, sharedImageView) -> {
            Log.d("Empresa", item.enterpriseName);
            Intent intent = new Intent(getActivity(), EmpresaDetalheActivity.class);
            intent.putExtra("ENTERPRISE", item);

            startActivity(intent);

        });
    }

    BroadcastReceiver receiverUpdateDownload = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("Empresas", "QUERY ");
            mViewModel.doSearch(intent.getStringExtra("QUERY"), token);
        }
    };

    @Override
    public void onStop() {
        super.onStop();
        if (receiverUpdateDownload != null) {
            try {
                getActivity().unregisterReceiver(receiverUpdateDownload);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
