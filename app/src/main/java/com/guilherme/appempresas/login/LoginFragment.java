package com.guilherme.appempresas.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputLayout;
import com.guilherme.appempresas.EspressoTestingIdlingResource;
import com.guilherme.appempresas.R;
import com.guilherme.appempresas.domain.model.Login;
import com.guilherme.appempresas.domain.model.Response;
import com.guilherme.appempresas.domain.model.TokenResponse;
import com.guilherme.appempresas.empresas.EmpresaActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import dagger.android.support.AndroidSupportInjection;

public class LoginFragment extends Fragment {

    @BindView(R.id.email)
    EditText email_editText;

    @BindView(R.id.password_edit)
    EditText password_editText;

    @BindView(R.id.password)
    TextInputLayout password_textInput;

    @BindView(R.id.login_btn)
    Button login_btn;

    @BindView(R.id.loadingIndicator)
    View progressBar;

    @BindView(R.id.error_credentials)
    TextView error_credentials_textView;

    private View mView;

    private LoginViewModel mViewModel;

    @Inject
    LoginViewModelFactory factory;


    @Nullable
    private EspressoTestingIdlingResource mIdlingResource;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, mView);
        mIdlingResource = new EspressoTestingIdlingResource();
        password_textInput.setPasswordVisibilityToggleEnabled(false);

        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AndroidSupportInjection.inject(this);

        mViewModel = new ViewModelProvider(this, factory).get(LoginViewModel.class);
        mViewModel.response().observe(getViewLifecycleOwner(), this::processResponse);

    }

    @OnClick(R.id.login_btn)
    void login() {
        String email = email_editText.getText().toString();
        String password = password_textInput.getEditText().getText().toString();

        if (!validateEmail(email)) {
            email_editText.requestFocus();
            email_editText.setError(getString(R.string.error_invalid_email));
        }
        if (!validatePassword(password)) {
            password_textInput.requestFocus();
            password_textInput.setPasswordVisibilityToggleEnabled(false);
            password_editText.setError(getString(R.string.error_invalid_password));
        }
        if (validateEmail(email) && validatePassword(password)) {
            Login login = new Login(email, password);
            password_textInput.setError(null);
            email_editText.setError(null);
            password_textInput.setErrorEnabled(true);
            mViewModel.doLogin(login);
        }
    }

    @OnTextChanged(R.id.password_edit)
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (password_textInput.getEditText().getText().toString().length() > 0)
            password_textInput.setPasswordVisibilityToggleEnabled(true);
        else
            password_textInput.setPasswordVisibilityToggleEnabled(false);
    }


    public void processResponse(Response<TokenResponse> response) {
        switch (response.status) {
            case SUCCESS:
                Log.d("Login", "Success");
                renderSuccessFullLoginState(response);
                break;

            case ERROR:
                renderErrorState();
                break;


            case LOADING:
                renderLoadingState();
                break;
        }

    }


    private void renderLoadingState() {
        Log.d("Login", "Loading");
        email_editText.setEnabled(false);
        password_textInput.setEnabled(false);
        login_btn.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);

        error_credentials_textView.setVisibility(View.INVISIBLE);
    }

    private void renderSuccessFullLoginState(Response<TokenResponse> response) {

        Log.d("Login", "Sucesso");

        Intent myIntent = new Intent(getActivity(), EmpresaActivity.class);

        myIntent.putExtra("client", response.data.getClient());
        myIntent.putExtra("access-token", response.data.getAccessToken());
        myIntent.putExtra("uid", response.data.getUid());

        email_editText.setEnabled(true);
        password_textInput.setEnabled(true);
        login_btn.setEnabled(true);

        getActivity().startActivity(myIntent);

        progressBar.setVisibility(View.GONE);

        error_credentials_textView.setVisibility(View.INVISIBLE);

        email_editText.setError(null);
    }

    private void renderErrorState() {
        email_editText.setError("");
        email_editText.setEnabled(true);

        password_textInput.setPasswordVisibilityToggleEnabled(false);
        password_textInput.setEnabled(true);
        password_editText.setError("");


        progressBar.setVisibility(View.INVISIBLE);

        error_credentials_textView.setVisibility(View.VISIBLE);

        login_btn.setEnabled(true);
        login_btn.setBackgroundColor(Color.GRAY);

    }

    private boolean validateEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean validatePassword(String password) {
        return password.length() > 4;
    }
}
