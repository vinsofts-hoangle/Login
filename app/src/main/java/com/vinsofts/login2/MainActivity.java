package com.vinsofts.login2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends FullScreenActivity {
    private SharedPreferences sharedPreferences;

    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.et_User)
    EditText etUser;
    @BindView(R.id.et_pass)
    EditText etPass;
    @BindView(R.id.cb_checkpass)
    CheckBox cbCheckpass;
    @BindView(R.id.bt_login)
    Button btLogin;
    @BindView(R.id.bt_Register)
    Button btRegister;
    @BindView(R.id.tv_forgetPass)
    TextView tvForgetPass;
    @BindView(R.id.tv_Loginwith)
    TextView tvLoginwith;
    @BindView(R.id.iv_User)
    ImageView ivUser;
    @BindView(R.id.iv_Pass)
    ImageView ivPass;
    @BindView(R.id.iv_face)
    ImageView ivFace;
    @BindView(R.id.iv_google)
    ImageView ivGoogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        sharedPref();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String email;
        String password;
        if (requestCode == FinalVariable.REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            email = data.getStringExtra(FinalVariable.INTENT_EMAIL);
            password = data.getStringExtra(FinalVariable.INTENT_PASSWORD);
            etUser.setText(email);
            etPass.setText(password);
            etUser.setSelection(email.length());
            etPass.setSelection(password.length());
        }
    }

    @OnClick({R.id.bt_login, R.id.bt_Register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_login:
                login();
                break;
            case R.id.bt_Register:
                gotoRegister();
                break;
        }
    }

    private void sharedPref() {
        String email;
        String password;

        sharedPreferences = getSharedPreferences(FinalVariable.SHAREPREF_FILE, MODE_PRIVATE);
        email = sharedPreferences.getString(FinalVariable.SHAREPREF_USER, "");
        password = sharedPreferences.getString(FinalVariable.SHAREPREF_PASS, "");
        etUser.setText(email);
        etPass.setText(password);
        cbCheckpass.setChecked(sharedPreferences.getBoolean(FinalVariable.SHAREPREF_CHECK, false));
        etUser.setSelection(email.length());
        etPass.setSelection(password.length());
    }

    private void login() {
        String email;
        String password;
        SharedPreferences.Editor editor;

        email = etUser.getText().toString().trim();
        password = etPass.getText().toString().trim();
        if (email.equals("") || password.equals("")) {
            Message.sMessage(this, "You must enter User and password");
        } else {
            editor = sharedPreferences.edit();
            if (cbCheckpass.isChecked()) {
                editor.putString(FinalVariable.SHAREPREF_USER, email);
                editor.putString(FinalVariable.SHAREPREF_PASS, password);
                editor.putBoolean(FinalVariable.SHAREPREF_CHECK, true);
                editor.apply();
                Message.sMessage(this, "Save User and password");
            } else {
                editor.clear();
                editor.apply();
                Message.sMessage(this, "Don't save User and Pass");
            }
        }
    }

    private void gotoRegister() {
        Intent intentRegis = new Intent(MainActivity.this, RegisterActivity.class);
        startActivityForResult(intentRegis, FinalVariable.REQUEST_CODE);
    }

}
