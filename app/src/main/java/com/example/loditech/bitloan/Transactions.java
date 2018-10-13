package com.example.loditech.bitloan;

import android.graphics.Color;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loditech.bitloan.Data.RetrofitClient;
import com.example.loditech.bitloan.Models.Account;
import com.example.loditech.bitloan.Models.MerchantAccount;
import com.example.loditech.bitloan.Models.Rewards;
import com.example.loditech.bitloan.Models.Transaction;
import com.example.loditech.bitloan.Models.User;
import com.example.loditech.bitloan.Models.Wallet;
import com.google.gson.annotations.Expose;

import org.w3c.dom.Text;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Transactions extends BaseDrawerActivity {

    ImageView btnBack10;
    TextView txtBalance8;
    TableLayout transaction_layout;
    List<Transaction> transactions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.transactions, frameLayout);
        btnBack10 = (ImageView) findViewById(R.id.btnBack10);
        txtBalance8 =  (TextView) findViewById(R.id.txtBalance8);
        transaction_layout = (TableLayout) findViewById(R.id.Transaction_Layout);
        GetUser();
        txtBalance8.setText(Double.toString(Wallet.wallet.getAmount()));
        GetAllTransaction();
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

    public void CreateTableRow(){
        try {
            for (int i = 0; i < transactions.size(); i++) {
                TableRow tableRow1 = new TableRow(this);
                tableRow1.setBackgroundColor(Color.WHITE);

                TextView date = new TextView(this);
                date.setId(i);
                date.setText(transactions.get(i).getTransactionDateTime());

                TextView storeName = new TextView(this);
                storeName.setId(i);
                storeName.setText("  Purchased - ");

                TextView amount = new TextView(this);
                amount.setId(i);
                amount.setText("PHP"+Double.toString(transactions.get(i).getAmount()));

                tableRow1.addView(date);
                tableRow1.addView(storeName);
                tableRow1.addView(amount);
                setTableRowColumnProperty(tableRow1);
                transaction_layout.addView(tableRow1);
            }
        }catch (Exception e){
            Toast.makeText(Transactions.this,e.toString(),Toast.LENGTH_LONG).show();
        }
    }

    public void GetAllTransaction(){
        Call<List<Transaction>> call = RetrofitClient.getInstance().getAPI().GetAllTransactionsByAccountID(Account.account.getID());
        call.enqueue(new Callback<List<Transaction>>() {
            @Override
            public void onResponse(Call<List<Transaction>> call, Response<List<Transaction>> response) {
                try {
                    transactions = response.body();
                    if(transactions!=null)
                        CreateTableRow();
                } catch (Exception e) {
                    Toast.makeText(Transactions.this, e.toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Transaction>> call, Throwable t) {
                transactions = null;
            }
        });
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

        ConstraintLayout.LayoutParams btnbackParams = (ConstraintLayout.LayoutParams)btnBack10.getLayoutParams();
        btnbackParams.setMargins(Scale(16, ScreenWidth, defWidth),Scale(32, ScreenHeight, defHeight),0,0);
        btnBack10.setLayoutParams(btnbackParams);

        ConstraintLayout.LayoutParams balanceParams = (ConstraintLayout.LayoutParams)txtBalance8.getLayoutParams();
        balanceParams.setMargins(0,Scale(40, ScreenHeight, defHeight),Scale(16, ScreenWidth, defWidth),0);
        txtBalance8.setLayoutParams(balanceParams);

        ConstraintLayout.LayoutParams transactionlayoutParams = (ConstraintLayout.LayoutParams)transaction_layout.getLayoutParams();
        transactionlayoutParams.setMargins(0,Scale(144, ScreenHeight, defHeight),0,0);
        transaction_layout.setLayoutParams(transactionlayoutParams);

    }

    @Override
    protected void onResume() {
        super.onResume();
        navigationView.getMenu().getItem(3).setChecked(true);
    }
}
