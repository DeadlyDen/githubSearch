package com.githubsearch.ui.login;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;

import android.os.Bundle;

import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.githubsearch.R;
import com.githubsearch.base.BaseMvpViewActivity;
import com.githubsearch.data.network.model.UserResponse;
import com.githubsearch.ui.recent_search.RecentSearchActivity;
import com.githubsearch.ui.user_profile.SearchRepoActivity;
import com.githubsearch.utils.TextInputValidator;


import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseMvpViewActivity implements LoginView {

    @InjectPresenter
    LoginPresenter loginPresenter;

    @BindView(R.id.login_form)
    ScrollView loginForm;
    @BindView(R.id.email_login_form)
    LinearLayout emailLoginForm;
    @BindView(R.id.email_in)
    TextInputLayout emailIn;
    @BindView(R.id.email)
    AutoCompleteTextView email;
    @BindView(R.id.password_in)
    TextInputLayout passwordIn;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.email_sign_in_button)
    Button emailSignInButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    @OnClick(R.id.email_sign_in_button)
    public void login() {
            if (TextInputValidator.validateFields(Arrays.asList(email, password), Arrays.asList(emailIn, passwordIn),
                    Arrays.asList(getResources().getString(R.string.email_or_username_empty_en), getResources().getString(R.string.password_empty_en)))) {
                showLoading();
                loginPresenter.doLoginCheck(email.getText().toString(), password.getText().toString());
            }
    }

    @Override
    public void starSearchRepoActivity(UserResponse userResponse) {
        Intent searchRepoIntent = new Intent(this, SearchRepoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", userResponse);
        searchRepoIntent.putExtras(bundle);
        startActivity(searchRepoIntent);
    }

    @OnClick(R.id.guset_sign_in_button)
    public void startRecentSearchActivity() {
        startActivity(new Intent(this, RecentSearchActivity.class));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

