package com.example.loditech.bitloan;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loditech.bitloan.Data.RetrofitClient;
import com.example.loditech.bitloan.Models.Account;
import com.example.loditech.bitloan.Models.LoanPayment;
import com.example.loditech.bitloan.Models.Rewards;
import com.example.loditech.bitloan.Models.User;
import com.example.loditech.bitloan.Models.Wallet;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillsDue extends BaseDrawerActivity {

    TextView balance;
    ImageView btnBack11;
    TableLayout billDue;
    List<LoanPayment> loanPayments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.bills_due, frameLayout);
        GetUser();
        balance = (TextView) findViewById(R.id.balance);
        btnBack11 = (ImageView) findViewById(R.id.btnBack11);
        billDue = (TableLayout) findViewById(R.id.BillsDue_Layout);

        balance.setText(Double.toString(Wallet.wallet.getAmount()));
        GetAllBillsDue();

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

    public void GetAllBillsDue(){
        Call<List<LoanPayment>> call = RetrofitClient.getInstance().getAPI().GetBillsDue(Account.account.getID());
        call.enqueue(new Callback<List<LoanPayment>>() {
            @Override
            public void onResponse(Call<List<LoanPayment>> call, Response<List<LoanPayment>> response) {
                try{
                    loanPayments = response.body();
                    if(loanPayments!=null)
                        CreateTableRow();
                }catch (Exception e){
                    Toast.makeText(BillsDue.this, e.toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<LoanPayment>> call, Throwable t) {

            }
        });
    }

    public void CreateTableRow(){
        for(int i=1; i<loanPayments.size(); i++) {
            TableRow tableRow1 = new TableRow(this);
            TextView date = new TextView(this);
            date.setId(i);
            date.setText(loanPayments.get(i).getDueDate());
            tableRow1.addView(date);
            TextView paid = new TextView(this);
            paid.setId(i);
            paid.setText("   Pay PHP"+Double.toString(loanPayments.get(i).getAmount()));
            tableRow1.addView(paid);
            setTableRowColumnProperty(tableRow1);
            billDue.addView(tableRow1);
        }

    }

    private void setTableRowColumnProperty(TableRow tableRow) {
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(70, 40);
        tableRow.setLayoutParams(layoutParams);
    }

    @Override
    protected void onResume() {
        super.onResume();
        navigationView.getMenu().getItem(4).setChecked(true);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        ScreenHeight = PxToDp(drawerLayout.getHeight());
        ScreenWidth = PxToDp(drawerLayout.getWidth());

        ConstraintLayout.LayoutParams balanceParams = (ConstraintLayout.LayoutParams)balance.getLayoutParams();
        balanceParams.setMargins(0,Scale(40, ScreenHeight, defHeight),Scale(16, ScreenWidth, defWidth),0);
        balance.setLayoutParams(balanceParams);

        ConstraintLayout.LayoutParams btnback11Params = (ConstraintLayout.LayoutParams)btnBack11.getLayoutParams();
        btnback11Params.setMargins(Scale(16, ScreenWidth, defWidth),Scale(32, ScreenHeight, defHeight),0,0);
        btnBack11.setLayoutParams(btnback11Params);

        ConstraintLayout.LayoutParams billdueParams = (ConstraintLayout.LayoutParams)billDue.getLayoutParams();
        billdueParams.setMargins(0,Scale(148, ScreenHeight, defHeight),8,8);
        billDue.setLayoutParams(billdueParams);

    }
}
