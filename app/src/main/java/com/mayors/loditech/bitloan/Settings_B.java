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

public class Settings_B extends BaseDrawerActivity {
    TextView balance;
    EditText oldPassword, newPassword, confirmPassword;
    ImageView btnBack8;
    Button btnUpdate2, btnCancel4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.settings_b,frameLayout);

        oldPassword = (EditText) findViewById(R.id.txtOldPassword);
        newPassword = (EditText) findViewById(R.id.txtNewPassword);
        confirmPassword = (EditText) findViewById(R.id.txtConfirmPassword);
        balance = (TextView) findViewById(R.id.txtBalance3);
        balance.setText(Double.toString(Wallet.wallet.getAmount()));
        btnBack8 = (ImageView) findViewById(R.id.btnBack8);
        btnUpdate2 = (Button) findViewById(R.id.btnUpdate2);
        btnCancel4 = (Button) findViewById(R.id.btnCancel4);
    }

    public void cmdUpdate(View v){
        if(Check()) {
            Call<Boolean> call = RetrofitClient.getInstance().getAPI().ChangePassword(Account.account.getID(), oldPassword.getText().toString(), newPassword.getText().toString());
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    try {
                        if (response.body()) {
                            Toast.makeText(Settings_B.this, "Changed Password", Toast.LENGTH_LONG).show();
                            oldPassword.getText().clear();
                            newPassword.getText().clear();
                            confirmPassword.getText().clear();
                        } else {
                            Toast.makeText(Settings_B.this, "Incorrect Password", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(Settings_B.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Toast.makeText(Settings_B.this, "ERROR", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        ScreenHeight = PxToDp(drawerLayout.getHeight());
        ScreenWidth = PxToDp(drawerLayout.getWidth());

        ConstraintLayout.LayoutParams btnbackParams = (ConstraintLayout.LayoutParams)btnBack8.getLayoutParams();
        btnbackParams.setMargins(Scale(16, ScreenWidth, defWidth),Scale(32, ScreenHeight, defHeight),0,0);
        btnBack8.setLayoutParams(btnbackParams);

        ConstraintLayout.LayoutParams balanceParams = (ConstraintLayout.LayoutParams)balance.getLayoutParams();
        balanceParams.setMargins(0,Scale(40, ScreenHeight, defHeight),Scale(16, ScreenWidth, defWidth),0);
        balance.setLayoutParams(balanceParams);

        ConstraintLayout.LayoutParams oldpasswordParams = (ConstraintLayout.LayoutParams)oldPassword.getLayoutParams();
        oldpasswordParams.setMargins(29,Scale(192,ScreenHeight,defHeight),29,0);
        oldPassword.setLayoutParams(oldpasswordParams);

        ConstraintLayout.LayoutParams newpasswordParams = (ConstraintLayout.LayoutParams)newPassword.getLayoutParams();
        newpasswordParams.setMargins(29,Scale(272, ScreenHeight,defHeight),29,0);
        newPassword.setLayoutParams(newpasswordParams);

        ConstraintLayout.LayoutParams txtconfirmpasswordParams = (ConstraintLayout.LayoutParams)confirmPassword.getLayoutParams();
        txtconfirmpasswordParams.setMargins(29,0,29,Scale(168,ScreenHeight, defHeight));
        confirmPassword.setLayoutParams(txtconfirmpasswordParams);

        ConstraintLayout.LayoutParams btnupdate2Params = (ConstraintLayout.LayoutParams)btnUpdate2.getLayoutParams();
        btnupdate2Params.setMargins(Scale(52, ScreenWidth,defWidth),0,0,Scale(112, ScreenHeight, defHeight));
        btnUpdate2.setLayoutParams(btnupdate2Params);

        ConstraintLayout.LayoutParams btncancel4Params = (ConstraintLayout.LayoutParams)btnCancel4.getLayoutParams();
        btncancel4Params.setMargins(0,0,Scale(52,ScreenWidth, defWidth),Scale(112, ScreenHeight, defHeight));
        btnCancel4.setLayoutParams(btncancel4Params);
    }

    public void cmdCancel(View v){
        Intent i = new Intent(Settings_B.this, Settings_Menu.class);
        startActivity(i);
    }

    public boolean Check(){
        if(!oldPassword.getText().toString().isEmpty()&&!oldPassword.getText().toString().equals(" ")&&!newPassword.getText().toString().isEmpty()&&!newPassword.getText().toString().equals(" ")&&!confirmPassword.getText().toString().isEmpty()&&!confirmPassword.getText().toString().equals(" ")){
            if(newPassword.getText().toString().equals(confirmPassword.getText().toString())){
                return true;
            }else{
                Toast.makeText(this,"Password not the same.", Toast.LENGTH_LONG).show();
            }
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
