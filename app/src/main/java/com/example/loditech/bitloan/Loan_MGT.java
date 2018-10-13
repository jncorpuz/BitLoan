package com.example.loditech.bitloan;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loditech.bitloan.Data.RetrofitClient;
import com.example.loditech.bitloan.Models.Account;
import com.example.loditech.bitloan.Models.LoanPayment;
import com.example.loditech.bitloan.Models.Rewards;
import com.example.loditech.bitloan.Models.Transaction;
import com.example.loditech.bitloan.Models.User;
import com.example.loditech.bitloan.Models.Wallet;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Loan_MGT extends BaseDrawerActivity {

    TextView txtBalance5;
    ImageView btnBack3;
    TableLayout tableLayout2;
    Button btnApply;
    List<LoanPayment> loanPayments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.loan_mgt, frameLayout);
        GetUser();
        txtBalance5 = (TextView) findViewById(R.id.txtBalance5);
        btnBack3 = (ImageView) findViewById(R.id.btnBack3);
        tableLayout2 = (TableLayout) findViewById(R.id.tableLayout2);
        btnApply = (Button) findViewById(R.id.btnApply);

        txtBalance5.setText(Double.toString(Wallet.wallet.getAmount()));
        GetAllBillsPaid();
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

    public void GetAllBillsPaid(){
        Call<List<LoanPayment>> call = RetrofitClient.getInstance().getAPI().GetBillsPaid(Account.account.getID());
        call.enqueue(new Callback<List<LoanPayment>>() {
            @Override
            public void onResponse(Call<List<LoanPayment>> call, Response<List<LoanPayment>> response) {
                try{
                    loanPayments = response.body();
                    if(loanPayments!=null)
                        CreateTableRow();
                }catch (Exception e){
                    Toast.makeText(Loan_MGT.this, e.toString(), Toast.LENGTH_LONG).show();
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
            TextView paid = new TextView(this);
            paid.setId(i);
            paid.setText("   Paid PHP"+Double.toString(loanPayments.get(i).getAmount())+" - "+loanPayments.get(i).PaymentDate);
            tableRow1.addView(paid);
            tableRow1.addView(date);
            setTableRowColumnProperty(tableRow1);
            tableLayout2.addView(tableRow1);
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

        ConstraintLayout.LayoutParams btnbackParams = (ConstraintLayout.LayoutParams)btnBack3.getLayoutParams();
        btnbackParams.setMargins(Scale(16, ScreenWidth, defWidth),Scale(32, ScreenHeight, defHeight),0,0);
        btnBack3.setLayoutParams(btnbackParams);

        ConstraintLayout.LayoutParams balanceParams = (ConstraintLayout.LayoutParams)txtBalance5.getLayoutParams();
        balanceParams.setMargins(0,Scale(40, ScreenHeight, defHeight),Scale(16, ScreenWidth, defWidth),0);
        txtBalance5.setLayoutParams(balanceParams);

        ConstraintLayout.LayoutParams tablelayout2Params = (ConstraintLayout.LayoutParams)tableLayout2.getLayoutParams();
        tablelayout2Params.setMargins(8,Scale(144, ScreenHeight, defHeight),8,0);
        tableLayout2.setLayoutParams(tablelayout2Params);

    }

    @Override
    protected void onResume() {
        super.onResume();
        navigationView.getMenu().getItem(2).setChecked(true);
    }

    public void cmdApplyForLoan(View v){
        Intent i = new Intent(Loan_MGT.this, Loan_Appilication.class);
        startActivity(i);
    }
}
