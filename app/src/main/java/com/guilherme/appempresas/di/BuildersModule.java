package com.guilherme.appempresas.di;

import com.guilherme.appempresas.empresas.EmpresaFragment;
import com.guilherme.appempresas.login.LoginFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class BuildersModule {

    @ContributesAndroidInjector
    abstract LoginFragment bindLoginFragment();

    @ContributesAndroidInjector
    abstract EmpresaFragment bindEmpresaFragment();
}