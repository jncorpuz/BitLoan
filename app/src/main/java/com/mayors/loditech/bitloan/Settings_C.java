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

import retrofit2.Callback;
import retrofit2.Response;

public class Settings_C extends BaseDrawerActivity {
    TextView balance;
    EditText fName, mName, lName, address, city, province;
    Button btnUpdate2, btnCancel5;
    ImageView btnBack9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.settings_c,frameLayout);

        balance = (TextView) findViewById(R.id.txtBalance4);
        fName = (EditText) findViewById(R.id.txtfName);
        mName = (EditText) findViewById(R.id.txtmName);
        lName = (EditText) findViewById(R.id.txtlName);
        address = (EditText) findViewById(R.id.txtAddress1);
        city = (EditText) findViewById(R.id.txtCity1);
        province = (EditText) findViewById(R.id.txtProvince1);
        btnUpdate2 = (Button) findViewById(R.id.btnUpdate2);
        btnCancel5 = (Button) findViewById(R.id.btnCancel5);
        btnBack9 = (ImageView) findViewById(R.id.btnBack9);

        balance.setText(Double.toString(Wallet.wallet.getAmount()));
        fName.setText(Account.account.getFirstName());
        mName.setText(Account.account.getMiddleName());
        lName.setText(Account.account.getLastName());
        String[] address1 = Account.account.getAddress().split(", ");
        address.setText(address1[0]);
        city.setText(address1[1]);
        province.setText(address1[2]);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        ScreenHeight = PxToDp(drawerLayout.getHeight());
        ScreenWidth = PxToDp(drawerLayout.getWidth());

        ConstraintLayout.LayoutParams balanceParams = (ConstraintLayout.LayoutParams)balance.getLayoutParams();
        balanceParams.setMargins(0,Scale(40, ScreenHeight, defHeight),Scale(16, ScreenWidth, defWidth),0);
        balance.setLayoutParams(balanceParams);

        ConstraintLayout.LayoutParams btnbackParams = (ConstraintLayout.LayoutParams)btnBack9.getLayoutParams();
        btnbackParams.setMargins(Scale(16, ScreenWidth, defWidth),Scale(32, ScreenHeight, defHeight),0,0);
        btnBack9.setLayoutParams(btnbackParams);

        ConstraintLayout.LayoutParams fnameParams = (ConstraintLayout.LayoutParams)fName.getLayoutParams();
        fnameParams.setMargins(29,Scale(184,ScreenHeight,defHeight),29,0);
        fName.setLayoutParams(fnameParams);

        ConstraintLayout.LayoutParams mnameParams = (ConstraintLayout.LayoutParams)mName.getLayoutParams();
        mnameParams.setMargins(Scale(44, ScreenWidth, defWidth),Scale(260, ScreenHeight, defHeight),0,0);
        mName.setLayoutParams(mnameParams);

        ConstraintLayout.LayoutParams lnameParams = (ConstraintLayout.LayoutParams)lName.getLayoutParams();
        lnameParams.setMargins(0,Scale(260, ScreenHeight, defHeight),Scale(56, ScreenWidth, defWidth),0);
        lName.setLayoutParams(lnameParams);

        ConstraintLayout.LayoutParams addressParams = (ConstraintLayout.LayoutParams)address.getLayoutParams();
        addressParams.setMargins(29,0,29,Scale(184, ScreenHeight, defHeight));
        address.setLayoutParams(addressParams);

        ConstraintLayout.LayoutParams cityParams = (ConstraintLayout.LayoutParams)city.getLayoutParams();
        cityParams.setMargins(Scale(56, ScreenWidth, defWidth),0,0,Scale(116, ScreenHeight, defHeight));
        city.setLayoutParams(cityParams);

        ConstraintLayout.LayoutParams provinceParams = (ConstraintLayout.LayoutParams)province.getLayoutParams();
        provinceParams.setMargins(0,0,Scale(56, ScreenWidth,defWidth),Scale(116, ScreenHeight, defHeight));
        province.setLayoutParams(provinceParams);

        ConstraintLayout.LayoutParams btnupdate2Params = (ConstraintLayout.LayoutParams)btnUpdate2.getLayoutParams();
        btnupdate2Params.setMargins(0,0,0,Scale(56, ScreenHeight, defHeight));
        btnUpdate2.setLayoutParams(btnupdate2Params);

        ConstraintLayout.LayoutParams btncancel5Params = (ConstraintLayout.LayoutParams)btnCancel5.getLayoutParams();
        btncancel5Params.setMargins(0,0,Scale(56, ScreenWidth, defWidth),Scale(56, ScreenHeight, defHeight));
        btnCancel5.setLayoutParams(btncancel5Params);

    }

    public void cmdUpdate(View v){
        if(Check()){
            Account account = new Account(Account.account.getID(),"","",fName.getText().toString(),mName.getText().toString(),lName.getText().toString(),"",address.getText().toString()+", "+city.getText().toString()+", "+province.getText().toString(),Account.account.Role);
            retrofit2.Call<Boolean> call = RetrofitClient.getInstance().getAPI().UpdateInformation(account);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(retrofit2.Call<Boolean> call, Response<Boolean> response) {
                    try{
                        if(response.body()){
                            Account.account.setFirstName(fName.getText().toString());
                            Account.account.setMiddleName(mName.getText().toString());
                            Account.account.setLastName(lName.getText().toString());
                            Account.account.setAddress(address.getText().toString()+", "+city.getText().toString()+", "+province.getText().toString());
                        }
                    }catch (Exception e){
                        Toast.makeText(Settings_C.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(retrofit2.Call<Boolean> call, Throwable t) {
                    Toast.makeText(Settings_C.this, "ERROR", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public void cmdCancel(View v){
        Intent i = new Intent(Settings_C.this, Settings_Menu.class);
        startActivity(i);
    }

    public boolean Check(){
        if(!fName.getText().toString().isEmpty()&&!fName.getText().toString().equals(" ")&&!lName.getText().toString().isEmpty()&&!lName.getText().toString().equals(" ")&&!address.getText().toString().isEmpty()&&!address.getText().toString().equals(" ")&&!city.getText().toString().isEmpty()&&!city.getText().toString().equals(" ")&&!province.getText().toString().isEmpty()&&!province.getText().toString().equals(" ")){
            return true;
        }
        Toast.makeText(this, "Fill the required fields", Toast.LENGTH_LONG).show();
        return false;
    }

    protected void onResume() {
        super.onResume();
        navigationView.getMenu().getItem(6).setChecked(true);
    }


}
