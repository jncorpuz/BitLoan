package com.mayors.loditech.bitloan;

import android.content.Intent;
import android.content.res.Resources;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mayors.loditech.bitloan.Data.RetrofitClient;
import com.mayors.loditech.bitloan.Merchant.Home_Menu_Merchant;
import com.mayors.loditech.bitloan.Models.Account;
import com.mayors.loditech.bitloan.Models.MerchantAccount;
import com.mayors.loditech.bitloan.Models.Transaction;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mayors.loditech.bitloan.Models.Account.account;

public class Login extends AppCompatActivity {
    EditText username,password;
    Button cmdLogin;
    ConstraintLayout constraintLayout;
    int ScreenHeight, ScreenWidth, defHeight = 566, defWidth = 383;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.txtUsername);
        password = (EditText) findViewById(R.id.txtPassword);
        cmdLogin = (Button) findViewById(R.id.cmdLogin);
        constraintLayout = (ConstraintLayout) findViewById(R.id.constraintLayout);


    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        ScreenHeight = PxToDp(constraintLayout.getHeight());
        ScreenWidth = PxToDp(constraintLayout.getWidth());

        ConstraintLayout.LayoutParams usernameParams = (ConstraintLayout.LayoutParams)username.getLayoutParams();
        usernameParams.setMargins(54,Scale(303, ScreenHeight, defHeight),54,0);
        username.setLayoutParams(usernameParams);
    }



    public int PxToDp(int a){
        a = (int)(a/ Resources.getSystem().getDisplayMetrics().density);
        return a;
    }

    public int Scale(float margin, float size, float scale){
        margin = size*(margin/scale);
        margin = (int)(margin * Resources.getSystem().getDisplayMetrics().density);
        return (int)margin;
    }

    public void Login(View v){
        if(Check()){
            Call<Account> call = RetrofitClient.getInstance().getAPI().Login(username.getText().toString(), password.getText().toString());
            call.enqueue(new Callback<Account>() {
                @Override
                public void onResponse(Call<Account> call, Response<Account> response) {
                    try {
                        account = response.body();
                        if (account != null) {
                            Intent i;
                            switch (account.Role) {
                                case "0": //Manager
                                    //Toast.makeText(Login.this, "Manager", Toast.LENGTH_SHORT).show();
                                    break;
                                case "1": //Client
                                    //Toast.makeText(Login.this, "Client", Toast.LENGTH_SHORT).show();
                                    i = new Intent(Login.this, Home_Menu.class);
                                    startActivity(i);
                                    break;
                                case "2": //Merchant
                                    //Toast.makeText(Login.this, "Merchant", Toast.LENGTH_SHORT).show();
                                    i = new Intent(Login.this, Home_Menu_Merchant.class); //fix
                                    startActivity(i);
                                    break;
                                default:
                                    Toast.makeText(Login.this, "Failed", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }
                        else {
                            Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_SHORT).show();
                            username.getText().clear();
                            password.getText().clear();
                        }
                    }
                    catch (Exception ex) {
                        Toast.makeText(Login.this, "An Error has Occured. Please contact developer", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Account> call, Throwable t) {

                }
            });

            /*
            Call<User> call = RetrofitClient.getInstance().getAPI().LoginUser(username.getText().toString(),password.getText().toString());
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    try{
                        User user = response.body();
                        if(user!=null) {
                            Account.account = user.Account;
                            Wallet.wallet = user.Wallet;
                            Rewards.rewards = user.Rewards;
                            Intent i = new Intent(Login.this, Home_Menu.class);
                            startActivity(i);

                        }else{
                            Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_SHORT).show();
                            username.getText().clear();
                            password.getText().clear();
                        }
                    }catch (Exception e){
                        Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        username.getText().clear();
                        password.getText().clear();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    username.getText().clear();
                    password.getText().clear();
                }
            });
            */
        }else{
            Toast.makeText(this,"Fill the required fields.", Toast.LENGTH_LONG).show();
        }
    }



    public void getMerchant(){
        for(int i=0; i<Transaction.transaction.size(); i++) {
            Call<MerchantAccount> call = RetrofitClient.getInstance().getAPI().GetMerchantAccount(Transaction.transaction.get(i).getReceiverID());
            call.enqueue(new Callback<MerchantAccount>() {
                @Override
                public void onResponse(Call<MerchantAccount> call, Response<MerchantAccount> response) {
                    try {
                        Transaction.EnterpriseNames.add(response.body().getEnterpriseName());
                    } catch (Exception e) {
                    }
                }

                @Override
                public void onFailure(Call<MerchantAccount> call, Throwable t) {
                }
            });
        }
    }

    public boolean Check(){
        if(!username.getText().toString().isEmpty()&&!username.getText().toString().equals(" ")&&!password.getText().toString().isEmpty()&&!password.getText().toString().equals(" ")){
            return true;
        }
        return false;
    }

    public void SignUp(View v){
        Intent i = new Intent(Login.this, SignUp_A.class);
        startActivity(i);
    }
}
