package com.example.loditech.bitloan;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.loditech.bitloan.Data.RetrofitClient;
import com.example.loditech.bitloan.Models.Account;

import retrofit2.Call;
import retrofit2.Response;

public class SignUp_B extends AppCompatActivity {
    EditText username, password, confirmPassword;
    DrawerLayout drawerLayout;
    int ScreenHeight, ScreenWidth, defHeight = 566, defWidth = 383;
    boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_b);

        username = (EditText) findViewById(R.id.txtUsername2);
        password = (EditText) findViewById(R.id.txtPassword2);
        confirmPassword = (EditText) findViewById(R.id.txtConfirmPass);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Call<Boolean> call = RetrofitClient.getInstance().getAPI().CheckExisting(s.toString());
                call.enqueue(new retrofit2.Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        try{
                            if(response.body()){
                                flag=true;
                            }else{
                                flag=false;
                            }
                        }catch (Exception e){
                            Toast.makeText(SignUp_B.this, e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {

                    }
                });
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        ScreenHeight = PxToDp(drawerLayout.getHeight());
        ScreenWidth = PxToDp(drawerLayout.getWidth());

        ConstraintLayout.LayoutParams usernameParams = (ConstraintLayout.LayoutParams)username.getLayoutParams();
        usernameParams.setMargins(39,Scale(141, ScreenHeight, defHeight),39,0);
        username.setLayoutParams(usernameParams);
    }

    public int PxToDp(int a){
        a = (int)(a/ Resources.getSystem().getDisplayMetrics().density);
        return a;
    }

    public int Scale(float margin, float size, float scale){
        margin = size*(margin/scale);
        margin = (int)(margin * Resources.getSystem().getDisplayMetrics().density);
        return (int)margin;
    }

    public void BackLogin(View v){
        Intent i = new Intent(SignUp_B.this, Login.class);
        startActivity(i);
    }

    public void CreateAccount(View v){
        if(Check()){
            if(!flag){
                Account.account.setUsername(username.getText().toString());
                Account.account.setPassword(password.getText().toString());
                Call<Boolean> call = RetrofitClient.getInstance().getAPI().RegisterUser(Account.account);
                call.enqueue(new retrofit2.Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        try{
                            if(response.body()){
                                Intent i = new Intent(SignUp_B.this, Login.class);
                                startActivity(i);
                            }else{
                                Toast.makeText(SignUp_B.this, "ERROR", Toast.LENGTH_LONG).show();
                            }
                        }catch (Exception e){
                            Toast.makeText(SignUp_B.this, "ERROR", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        Toast.makeText(SignUp_B.this, "ERROR", Toast.LENGTH_LONG).show();
                    }
                });
            }else{
                Toast.makeText(this, "Username already taken", Toast.LENGTH_LONG).show();
                username.getText().clear();
            }
        }else{
            Toast.makeText(this, "Fill the required fields.", Toast.LENGTH_LONG).show();
        }
    }

    public boolean Check(){
        if(!username.getText().toString().isEmpty()&&!username.getText().toString().equals(" ")&&!password.getText().toString().isEmpty()&&!password.getText().toString().equals(" ")&&!confirmPassword.getText().toString().isEmpty()&&!confirmPassword.getText().toString().equals(" ")){
            if(password.getText().toString().equals(confirmPassword.getText().toString())) {
                return true;
            }else {
                Toast.makeText(this, "Password and Confirm Password not the same.", Toast.LENGTH_LONG).show();
            }
        }
        return false;
    }
}
