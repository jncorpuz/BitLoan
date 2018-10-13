package com.example.loditech.bitloan;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loditech.bitloan.Data.RetrofitClient;
import com.example.loditech.bitloan.Models.Account;
import com.example.loditech.bitloan.Models.LoanPayment;
import com.example.loditech.bitloan.Models.User;
import com.example.loditech.bitloan.Models.Voucher;
import com.example.loditech.bitloan.Models.Wallet;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Rewards extends BaseDrawerActivity {

    TextView txtRewards2, txtBalance7;
    ImageView btnBack5;
    ScrollView scrollView;
    TableLayout rewards_layout;

    List<Voucher> vouchers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.rewards, frameLayout);

        txtRewards2 = (TextView) findViewById(R.id.txtRewards2);
        txtBalance7 = (TextView) findViewById(R.id.txtBalance7);
        btnBack5 = (ImageView) findViewById(R.id.btnBack5);
        rewards_layout = (TableLayout) findViewById(R.id.tableLayout3);
        GetUser();
        txtBalance7.setText(Double.toString(Wallet.wallet.getAmount()));
        txtRewards2.setText(Double.toString(com.example.loditech.bitloan.Models.Rewards.rewards.getPoints()));
        GetAllVouchers();

    }

    public void GetUser(){
        Call<User> call = RetrofitClient.getInstance().getAPI().GetUser(Account.account.getID());
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                Wallet.wallet = user.Wallet;
                com.example.loditech.bitloan.Models.Rewards.rewards = user.Rewards;
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    public void GetAllVouchers(){
        Call<List<Voucher>> call = RetrofitClient.getInstance().getAPI().GetAllNotRedeemed(Account.account.getID());
        call.enqueue(new Callback<List<Voucher>>() {
            @Override
            public void onResponse(Call<List<Voucher>> call, Response<List<Voucher>> response) {
                try{
                vouchers = response.body();
                if(vouchers!=null)
                    CreateTableRow();
                }catch (Exception e){
                    Toast.makeText(Rewards.this, e.toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Voucher>> call, Throwable t) {

            }
        });
    }

    public void CreateTableRow(){
        for(int i=1; i<vouchers.size(); i++) {
            TableRow tableRow1 = new TableRow(this);
            TextView percent = new TextView(this);
            percent.setId(i);
            percent.setText(vouchers.get(i).getPercentDiscount()+"% OFF");
            tableRow1.addView(percent);
            TextView description = new TextView(this);
            description.setId(i);
            description.setText("   "+vouchers.get(i).getDescription());
            tableRow1.addView(description);
            Button redeemPoints = new Button(this);
            redeemPoints.setId(vouchers.get(i).getID());
            redeemPoints.setText("   REDEEM for "+vouchers.get(i).getPoints()+"pts");
            redeemPoints.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Call<Boolean> call = RetrofitClient.getInstance().getAPI().RedeemVoucher(Account.account.getID(),v.getId());
                    call.enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                            try{
                                if(response.body()){
                                    Toast.makeText(Rewards.this,"Reward Redeemed.",Toast.LENGTH_LONG).show();
                                }
                            }catch(Exception e){
                                Toast.makeText(Rewards.this,e.toString(),Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {

                        }
                    });
                }
            });
            tableRow1.addView(redeemPoints);
            setTableRowColumnProperty(tableRow1);
            rewards_layout.addView(tableRow1);
        }

    }

    private void setTableRowColumnProperty(TableRow tableRow) {
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(70, 40);
        tableRow.setLayoutParams(layoutParams);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        ScreenHeight = PxToDp(drawerLayout.getHeight());
        ScreenWidth = PxToDp(drawerLayout.getWidth());

        ConstraintLayout.LayoutParams btnbackParams = (ConstraintLayout.LayoutParams)btnBack5.getLayoutParams();
        btnbackParams.setMargins(Scale(16, ScreenWidth, defWidth),Scale(32, ScreenHeight, defHeight),0,0);
        btnBack5.setLayoutParams(btnbackParams);

        ConstraintLayout.LayoutParams balanceParams = (ConstraintLayout.LayoutParams)txtBalance7.getLayoutParams();
        balanceParams.setMargins(0,Scale(40, ScreenHeight, defHeight),Scale(16, ScreenWidth, defWidth),0);
        txtBalance7.setLayoutParams(balanceParams);

        ConstraintLayout.LayoutParams txtrewards2Params = (ConstraintLayout.LayoutParams)txtRewards2.getLayoutParams();
        txtrewards2Params.setMargins(67,Scale(80, ScreenHeight, defHeight),67,0);
        txtRewards2.setLayoutParams(txtrewards2Params);
    }

    @Override
    protected void onResume() {
        super.onResume();
        navigationView.getMenu().getItem(5).setChecked(true);
    }
}
