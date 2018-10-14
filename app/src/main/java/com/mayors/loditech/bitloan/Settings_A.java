package com.mayors.loditech.bitloan;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mayors.loditech.bitloan.Data.RetrofitClient;
import com.mayors.loditech.bitloan.Models.Account;
import com.mayors.loditech.bitloan.Models.Wallet;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Settings_A extends BaseDrawerActivity {
    EditText email, password;
    TextView balance;
    ImageView btnBack7;
    Button btnUpdate, btnCancel3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.settings_a,frameLayout);

        email = (EditText) findViewById(R.id.txtNewEmail);
        password = (EditText) findViewById(R.id.txtPassword3);
        balance = (TextView) findViewById(R.id.txtBalance2);
        btnBack7 = (ImageView) findViewById(R.id.btnBack7);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnCancel3 = (Button) findViewById(R.id.btnCancel3);

        balance.setText(Double.toString(Wallet.wallet.getAmount()));

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        ScreenHeight = PxToDp(drawerLayout.getHeight());
        ScreenWidth = PxToDp(drawerLayout.getWidth());

        ConstraintLayout.LayoutParams balanceParams = (ConstraintLayout.LayoutParams)balance.getLayoutParams();
        balanceParams.setMargins(0,Scale(40, ScreenHeight, defHeight),Scale(16, ScreenWidth, defWidth),0);
        balance.setLayoutParams(balanceParams);

        ConstraintLayout.LayoutParams btnbackParams = (ConstraintLayout.LayoutParams)btnBack7.getLayoutParams();
        btnbackParams.setMargins(Scale(16, ScreenWidth, defWidth),Scale(32, ScreenHeight, defHeight),0,0);
        btnBack7.setLayoutParams(btnbackParams);

        ConstraintLayout.LayoutParams emailParams = (ConstraintLayout.LayoutParams)email.getLayoutParams();
        emailParams.setMargins(29,Scale(192, ScreenHeight, defHeight),29,0);
        email.setLayoutParams(emailParams);

        ConstraintLayout.LayoutParams passwordParams = (ConstraintLayout.LayoutParams)password.getLayoutParams();
        passwordParams.setMargins(29,0,29,Scale(248, ScreenHeight, defHeight));
        password.setLayoutParams(passwordParams);

        /*ConstraintLayout.LayoutParams btnupdateParams = (ConstraintLayout.LayoutParams)btnUpdate.getLayoutParams();
        btnupdateParams.setMargins(Scale(52,ScreenWidth,defWidth),0,0,Scale(168, ScreenHeight, ScreenHeight));
        btnUpdate.setLayoutParams(btnupdateParams);

        ConstraintLayout.LayoutParams btncancel3Params = (ConstraintLayout.LayoutParams)btnCancel3.getLayoutParams();
        btncancel3Params.setMargins(0,0,Scale(52, ScreenWidth, defWidth),Scale(168, ScreenHeight, defHeight));
        btnCancel3.setLayoutParams(btncancel3Params);*/


    }

    public void cmdUpdate(View v){
        if(Check()){
            Call<Boolean> call = RetrofitClient.getInstance().getAPI().ChangeEmail(Account.account.getID(), password.getText().toString(), email.getText().toString());
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(retrofit2.Call<Boolean> call, Response<Boolean> response) {
                    try {
                        if (response.body()) {
                            Toast.makeText(Settings_A.this, "Email Changed.", Toast.LENGTH_LONG).show();
                            email.getText().clear();
                            password.getText().clear();
                        } else {
                            Toast.makeText(Settings_A.this, "Incorrect Password.", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(Settings_A.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(retrofit2.Call<Boolean> call, Throwable t) {
                    Toast.makeText(Settings_A.this, "ERROR", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public void cmdCancel(View v){
        Intent i = new Intent(Settings_A.this, Settings_Menu.class);
        startActivity(i);
    }

    public boolean Check(){
        if(!email.getText().toString().isEmpty()&&!email.getText().toString().equals(" ")&&!password.getText().toString().isEmpty()&&!password.getText().toString().equals(" ")){
            return true;
        }
        Toast.makeText(this, "Fill the required fields.", Toast.LENGTH_LONG).show();
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        navigationView.getMenu().getItem(6).setChecked(true);
    }
}
