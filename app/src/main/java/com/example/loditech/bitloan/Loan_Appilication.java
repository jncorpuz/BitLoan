package com.example.loditech.bitloan;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loditech.bitloan.Data.RetrofitClient;
import com.example.loditech.bitloan.Models.Account;
import com.example.loditech.bitloan.Models.Loans;
import com.example.loditech.bitloan.Models.Rewards;
import com.example.loditech.bitloan.Models.User;
import com.example.loditech.bitloan.Models.Wallet;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;

public class Loan_Appilication extends BaseDrawerActivity {

    TextView balance;
    EditText loanAmount, loanReason;
    ImageView btnBackz;
    Button btnSubmit, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.loan_application, frameLayout);
        GetUser();
        balance = (TextView) findViewById(R.id.balance);
        loanAmount = (EditText) findViewById(R.id.loanAmount);
        loanReason = (EditText) findViewById(R.id.loanReason);
        btnBackz= (ImageView) findViewById(R.id.btnBackz);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        balance.setText(Double.toString(Wallet.wallet.getAmount()));
    }

    public void GetUser(){
        Call<User> call = RetrofitClient.getInstance().getAPI().GetUser(Account.account.getID());
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                Wallet.wallet = user.Wallet;
                Rewards.rewards = user.Rewards;
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    public void cmdApplyLoan(View v){
        if(Check()){
            Loans loans = new Loans(0, Account.account.getID(),"",Double.parseDouble(loanAmount.getText().toString()),0,0,loanReason.getText().toString(),false,false,0);
            Call<Boolean> call = RetrofitClient.getInstance().getAPI().ApplyLoan(loans);

            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if(response.body()){
                        try {
                            if(response.body()) {
                                loanAmount.getText().clear();
                                loanReason.getText().clear();
                                Toast.makeText(Loan_Appilication.this, "Loan has been requested.", Toast.LENGTH_LONG).show();
                            }
                        }catch (Exception e){
                            Toast.makeText(Loan_Appilication.this,e.toString(),Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(Loan_Appilication.this,"ERROR",Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {

                }
            });
        }
    }

    public boolean Check(){
        if(!loanAmount.getText().toString().isEmpty()&&!loanAmount.getText().toString().equals(" ")&&!loanReason.getText().toString().isEmpty()&&!loanReason.getText().toString().equals(" ")){
            return true;
        }
        return  false;
    }

    public void Cancel(View v){
        Intent i = new Intent(Loan_Appilication.this, Home_Menu.class);
        startActivity(i);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        ScreenHeight = PxToDp(drawerLayout.getHeight());
        ScreenWidth = PxToDp(drawerLayout.getWidth());

        ConstraintLayout.LayoutParams balanceParams = (ConstraintLayout.LayoutParams)balance.getLayoutParams();
        balanceParams.setMargins(0,Scale(40, ScreenHeight, defHeight),Scale(16, ScreenWidth, defWidth),0);
        balance.setLayoutParams(balanceParams);

        ConstraintLayout.LayoutParams loanamountParams = (ConstraintLayout.LayoutParams)loanAmount.getLayoutParams();
        loanamountParams.setMargins(29,Scale(192, ScreenHeight, defHeight),29,0);
        loanAmount.setLayoutParams(loanamountParams);

        ConstraintLayout.LayoutParams loanreasonParams = (ConstraintLayout.LayoutParams)loanReason.getLayoutParams();
        loanreasonParams.setMargins(29,0,29,Scale(156, ScreenHeight, defHeight));
        loanReason.setLayoutParams(loanreasonParams);

        ConstraintLayout.LayoutParams btnsubmitParams = (ConstraintLayout.LayoutParams)btnSubmit.getLayoutParams();
        btnsubmitParams.setMargins(52,0,0,Scale(76, ScreenHeight, defHeight));
        btnSubmit.setLayoutParams(btnsubmitParams);

        ConstraintLayout.LayoutParams btncancelParams = (ConstraintLayout.LayoutParams)btnCancel.getLayoutParams();
        btncancelParams.setMargins(0,0,52,Scale(76, ScreenHeight, defHeight));
        btnCancel.setLayoutParams(btncancelParams);

        ConstraintLayout.LayoutParams btnbackzParams = (ConstraintLayout.LayoutParams)btnBackz.getLayoutParams();
        btnbackzParams.setMargins(Scale(16, ScreenWidth, defWidth),Scale(32, ScreenHeight, defHeight),0,0);
        btnBackz.setLayoutParams(btnbackzParams);
    }

    @Override
    protected void onResume() {
        super.onResume();
        navigationView.getMenu().getItem(2).setChecked(true);
    }
}
