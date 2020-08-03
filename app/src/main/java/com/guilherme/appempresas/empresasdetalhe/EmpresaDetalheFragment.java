package com.guilherme.appempresas.empresasdetalhe;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.guilherme.appempresas.R;
import com.guilherme.appempresas.domain.model.Enterprise;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EmpresaDetalheFragment extends Fragment {

    private EmpresaDetalheViewModel mViewModel;

    @BindView(R.id.description)
    TextView description;

    @BindView(R.id.imageView)
    ImageView imageView;

    private View mView;

    public static EmpresaDetalheFragment newInstance() {
        return new EmpresaDetalheFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.enterprise_detail_fragment, container, false);
        ButterKnife.bind(this, mView);

        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(EmpresaDetalheViewModel.class);
        Bundle bundle = getArguments();
        Log.d("Empresa Detalhe", String.valueOf((bundle != null)));
        Enterprise enterprise = bundle.getParcelable("ENTERPRISE");
        mViewModel.initEnterpriseLiveData(enterprise);
        mViewModel.getEnterpriseLiveData().observe(getViewLifecycleOwner(), this::renderLiveData);

    }


    private void renderLiveData(Enterprise enterprise) {

        description.setText(enterprise.getDescription());
        Glide.with(imageView.getContext()).load("https://empresas.ioasys.com.br" + enterprise.getPhoto()).placeholder(new ColorDrawable(Color.GREEN)).into(imageView);

    }

}