package com.mayors.loditech.bitloan;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.mayors.loditech.bitloan.Data.RetrofitClient;
import com.mayors.loditech.bitloan.Models.Account;
import com.mayors.loditech.bitloan.Models.LoanPayment;
import com.mayors.loditech.bitloan.Models.Rewards;
import com.mayors.loditech.bitloan.Models.Transaction;
import com.mayors.loditech.bitloan.Models.User;
import com.mayors.loditech.bitloan.Models.Wallet;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home_Menu extends BaseDrawerActivity{
    TextView userName, userEmail, balance, points, billsDue, billsTotal;
    ImageView btnBack;
    List<LoanPayment> loanPayments;
    Double totalBill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.home_menu, frameLayout);
        setTitle("Home Menu");

        GetData();

        userName = (TextView) findViewById(R.id.txtUserName);
        userEmail = (TextView) findViewById(R.id.txtUserEmail);
        balance = (TextView) findViewById(R.id.txtBalance);
        points = (TextView) findViewById(R.id.txtRewards);
        billsDue = (TextView) findViewById(R.id.billsDue);
        billsTotal = (TextView) findViewById(R.id.billsTotal);
        btnBack = (ImageView) findViewById(R.id.btnBack2);

        userName.setText(Account.account.FirstName);
        userEmail.setText(Account.account.EmailAddress);
        try {
            balance.setText(Double.toString(Wallet.wallet.getAmount()));
            points.setText(Double.toString(Rewards.rewards.getPoints()));
        }
        catch (Exception ex) {

        }
    }

    public void GetData(){
        Call<Wallet> call = RetrofitClient.getInstance().getAPI().GetWallet(Account.account.ID);
        call.enqueue(new Callback<Wallet>() {
            @Override
            public void onResponse(Call<Wallet> call, Response<Wallet> response) {
                Wallet.wallet = response.body();
                balance.setText(Double.toString(Wallet.wallet.getAmount()));
            }

            @Override
            public void onFailure(Call<Wallet> call, Throwable t) {
                Toast.makeText(Home_Menu.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Call<Rewards> call2 = RetrofitClient.getInstance().getAPI().GetRewards(Account.account.ID);
        call2.enqueue(new Callback<Rewards>() {
            @Override
            public void onResponse(Call<Rewards> call, Response<Rewards> response) {
                Rewards.rewards = response.body();
                points.setText(Double.toString(Rewards.rewards.getPoints()));
            }

            @Override
            public void onFailure(Call<Rewards> call, Throwable t) {
                Toast.makeText(Home_Menu.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        /*
        Call<User> call = RetrofitClient.getInstance().getAPI().GetUser(Account.account.getID());
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                Account.account = user.Account;
                Wallet.wallet = user.Wallet;
                Rewards.rewards = user.Rewards;
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
        */
    }

    public void GetAllBillsDue(){
        Call<List<LoanPayment>> call = RetrofitClient.getInstance().getAPI().GetBillsDue(Account.account.getID());
        call.enqueue(new Callback<List<LoanPayment>>() {
            @Override
            public void onResponse(Call<List<LoanPayment>> call, Response<List<LoanPayment>> response) {
                try{
                    loanPayments = response.body();
                    if(loanPayments!=null)
                        GetTotalBill();
                }catch (Exception e){
                    Toast.makeText(Home_Menu.this, e.toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<LoanPayment>> call, Throwable t) {

            }
        });
    }

    public void GetTotalBill(){
        for(int i=0; i<loanPayments.size(); i++){
            totalBill+=loanPayments.get(i).getAmount();
        }
        billsDue.setText(loanPayments.size());
        billsTotal.setText("PHP"+Double.toString(totalBill));
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        ScreenHeight = PxToDp(drawerLayout.getHeight());
        ScreenWidth = PxToDp(drawerLayout.getWidth());

        //GetAllTransaction();
        //GetAllBillsDue();
        GetData();
        userEmail.setText(Account.account.getEmailAddress());

        ConstraintLayout.LayoutParams userNameParams = (ConstraintLayout.LayoutParams)userName.getLayoutParams();
        userNameParams.setMargins(0,Scale(96, ScreenHeight, defHeight),0,0);
        userName.setLayoutParams(userNameParams);

        ConstraintLayout.LayoutParams useremailParams = (ConstraintLayout.LayoutParams)userEmail.getLayoutParams();
        useremailParams.setMargins(0,Scale(136, ScreenHeight, defHeight),0,0);
        userEmail.setLayoutParams(useremailParams);

        ConstraintLayout.LayoutParams balanceParams = (ConstraintLayout.LayoutParams)balance.getLayoutParams();
        balanceParams.setMargins(0,Scale(180, ScreenHeight, defHeight),0,0);
        balance.setLayoutParams(balanceParams);

        ConstraintLayout.LayoutParams billsdueParams = (ConstraintLayout.LayoutParams)billsDue.getLayoutParams();
        billsdueParams.setMargins(0,0,0,Scale(180, ScreenHeight, defHeight));
        Toast.makeText(this, String.valueOf(billsdueParams.bottomMargin), Toast.LENGTH_SHORT).show();
        billsDue.setLayoutParams(billsdueParams);

        ConstraintLayout.LayoutParams billstotalParams = (ConstraintLayout.LayoutParams)billsTotal.getLayoutParams();
        billstotalParams.setMargins(0, Scale(308, ScreenHeight, defHeight),0,0);
        billsTotal.setLayoutParams(billstotalParams);

        ConstraintLayout.LayoutParams pointsParams = (ConstraintLayout.LayoutParams)points.getLayoutParams();
        pointsParams.setMargins(0,0,0,Scale(80,ScreenHeight, defHeight));
        points.setLayoutParams(pointsParams);

        ConstraintLayout.LayoutParams btnbackParams = (ConstraintLayout.LayoutParams)btnBack.getLayoutParams();
        btnbackParams.setMargins(Scale(16, ScreenWidth, defWidth),Scale(32, ScreenHeight, defHeight),0,0);
        btnBack.setLayoutParams(btnbackParams);
    }

    @Override
    protected void onResume() {
        super.onResume();
        navigationView.getMenu().getItem(0).setChecked(true);
    }
}
