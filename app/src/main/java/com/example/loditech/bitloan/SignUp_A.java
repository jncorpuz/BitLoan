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
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.loditech.bitloan.Models.Account;

public class SignUp_A extends AppCompatActivity {
    EditText fName, mName, lName, email, address, city, province;
    DrawerLayout drawerLayout;
    int ScreenHeight, ScreenWidth, defHeight = 566, defWidth = 383;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_a);

        fName = (EditText) findViewById(R.id.txtFname);
        mName = (EditText) findViewById(R.id.txtMname);
        lName = (EditText) findViewById(R.id.txtLname);
        email = (EditText) findViewById(R.id.txtEmail);
        address = (EditText) findViewById(R.id.txtAddress);
        city = (EditText) findViewById(R.id.txtCity);
        province = (EditText) findViewById(R.id.txtProvince);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        ScreenHeight = PxToDp(drawerLayout.getHeight());
        ScreenWidth = PxToDp(drawerLayout.getWidth());

        ConstraintLayout.LayoutParams fNameParams = (ConstraintLayout.LayoutParams)fName.getLayoutParams();
        fNameParams.setMargins(39,Scale(114, ScreenHeight, defHeight),39,0);
        fName.setLayoutParams(fNameParams);
    }

    public int PxToDp(int a){
        a = (int)(a/ Resources.getSystem().getDisplayMetrics().density);
        return a;
    }

    public int Scale(float margin, float size, float scale){
        margin = size*(margin/scale);
        margin = (int)(margin * Resources.getSystem().getDisplayMetrics().density);
        Toast.makeText(SignUp_A.this, String.valueOf(margin), Toast.LENGTH_SHORT).show();
        return (int)margin;
    }

    public void Login(View v){
        Intent i = new Intent(SignUp_A.this, Login.class);
        startActivity(i);
    }

    public void NextStep(View v){
        if(Check()) {
            Account account = new Account(0, "", "", fName.getText().toString(), mName.getText().toString(), lName.getText().toString(), email.getText().toString(),address.getText().toString()+", "+city.getText().toString()+", "+province.getText().toString(), "CLIENT");
            Account.account = account;
            Intent i = new Intent(SignUp_A.this, SignUp_B.class);
            startActivity(i);
        }else{
            Toast.makeText(this,"Fill the required fields.", Toast.LENGTH_LONG).show();
        }
    }

    public boolean Check(){
        if(!fName.getText().toString().isEmpty()&&!fName.getText().toString().equals(" ")&&!lName.getText().toString().isEmpty()&&!lName.getText().toString().equals(" ")&&!email.getText().toString().isEmpty()&&!email.getText().toString().equals(" ")&&!address.getText().toString().isEmpty()&&!address.getText().toString().equals(" ")&&!city.getText().toString().isEmpty()&&!city.getText().toString().equals(" ")&&!province.getText().toString().isEmpty()&&!province.getText().toString().equals(" ")){
            return true;
        }
        return false;
    }
}
