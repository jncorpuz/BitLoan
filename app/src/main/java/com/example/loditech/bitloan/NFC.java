package com.example.loditech.bitloan;

import android.annotation.TargetApi;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loditech.bitloan.Data.RetrofitClient;
import com.example.loditech.bitloan.Models.Account;
import com.example.loditech.bitloan.Models.MerchantAccount;
import com.example.loditech.bitloan.Models.Rewards;
import com.example.loditech.bitloan.Models.Transaction;
import com.example.loditech.bitloan.Models.User;
import com.example.loditech.bitloan.Models.Wallet;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.nfc.NdefRecord.createMime;
@TargetApi(16)
public class NFC extends BaseDrawerActivity implements CreateNdefMessageCallback {
    NfcAdapter mNfcAdapter;

    ImageView btnBack,imageView;
    TextView txtBalance6, txtSeller, txtTransAmount;
    Button btnConfirm, btnCancel2;

    String referenceID;

    MerchantAccount merchantAccount;
    Transaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.nfc, frameLayout);

        btnBack = (ImageView) findViewById(R.id.btnBack);
        imageView = (ImageView) findViewById(R.id.imageView);
        txtBalance6 = (TextView) findViewById(R.id.txtBalance6) ;
        txtSeller = (TextView) findViewById(R.id.txtSeller);
        txtTransAmount = (TextView) findViewById(R.id.txtTransAmount);
        btnConfirm = (Button) findViewById(R.id.btnConfirm);
        btnCancel2 = (Button) findViewById(R.id.btnCancel2);
        GetUser();
        txtBalance6.setText(Double.toString(Wallet.wallet.getAmount()));
        imageView.setVisibility(View.INVISIBLE);
        txtSeller.setVisibility(View.INVISIBLE);
        txtTransAmount.setVisibility(View.INVISIBLE);
        btnConfirm.setVisibility(View.INVISIBLE);
        btnCancel2.setVisibility(View.INVISIBLE);

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mNfcAdapter == null) {
            Toast.makeText(this, "NFC is not available", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        // Register callback
        mNfcAdapter.setNdefPushMessageCallback(this, this);
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

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        ScreenHeight = PxToDp(drawerLayout.getHeight());
        ScreenWidth = PxToDp(drawerLayout.getWidth());

        ConstraintLayout.LayoutParams balanceParams = (ConstraintLayout.LayoutParams)txtBalance6.getLayoutParams();
        balanceParams.setMargins(0,Scale(40, ScreenHeight, defHeight),Scale(16, ScreenWidth, defWidth),0);
        txtBalance6.setLayoutParams(balanceParams);

        ConstraintLayout.LayoutParams btnbackParams = (ConstraintLayout.LayoutParams)btnBack.getLayoutParams();
        btnbackParams.setMargins(Scale(16, ScreenWidth, defWidth),Scale(32, ScreenHeight, defHeight),0,0);
        btnBack.setLayoutParams(btnbackParams);

        ConstraintLayout.LayoutParams imageviewParams = (ConstraintLayout.LayoutParams)imageView.getLayoutParams();
        imageviewParams.setMargins(27,Scale(232, ScreenHeight, defHeight),27,0);
        imageView.setLayoutParams(imageviewParams);

        ConstraintLayout.LayoutParams txtsellerParams = (ConstraintLayout.LayoutParams)txtSeller.getLayoutParams();
        txtsellerParams.setMargins(8,Scale(384, ScreenHeight, defHeight),8,0);
        txtSeller.setLayoutParams(txtsellerParams);

        ConstraintLayout.LayoutParams txttransamountParams = (ConstraintLayout.LayoutParams)txtTransAmount.getLayoutParams();
        txttransamountParams.setMargins(8,Scale(300, ScreenHeight, defHeight),8,0);

        ConstraintLayout.LayoutParams btnconfirmParams = (ConstraintLayout.LayoutParams)btnConfirm.getLayoutParams();
        btnconfirmParams.setMargins(Scale(52, ScreenWidth, defWidth),0,0,Scale(44, ScreenHeight,defHeight));
        btnConfirm.setLayoutParams(btnconfirmParams);

        ConstraintLayout.LayoutParams btncancel2Params = (ConstraintLayout.LayoutParams)btnCancel2.getLayoutParams();
        btncancel2Params.setMargins(0,0,Scale(52, ScreenWidth, defWidth),Scale(44,ScreenHeight,defHeight));
        btnCancel2.setLayoutParams(btncancel2Params);




    }

    @Override
    public void onResume() {
        super.onResume();
        navigationView.getMenu().getItem(1).setChecked(true);
        // Check to see that the Activity started due to an Android Beam
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
                processIntent(getIntent());
        }
    }

    void processIntent(Intent intent) {
        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(
                NfcAdapter.EXTRA_NDEF_MESSAGES);
        // only one message sent during the beam
        NdefMessage msg = (NdefMessage) rawMsgs[0];
        // record 0 contains the MIME type, record 1 is the AAR, if present
        referenceID = new String(msg.getRecords()[0].getPayload());
        if(!referenceID.equals("")) {
            Call<Transaction> call = RetrofitClient.getInstance().getAPI().GetTransaction(referenceID);
            call.enqueue(new Callback<Transaction>() {
                @Override
                public void onResponse(Call<Transaction> call, Response<Transaction> response) {
                    try {
                         transaction = response.body();
                        if (transaction != null) {
                            imageView.setVisibility(View.VISIBLE);
                            txtSeller.setVisibility(View.VISIBLE);
                            txtTransAmount.setVisibility(View.VISIBLE);
                            btnConfirm.setVisibility(View.INVISIBLE);
                            btnCancel2.setVisibility(View.INVISIBLE);
                            txtTransAmount.setText(Double.toString(transaction.getAmount()));
                            getMerchant(transaction.getReceiverID());
                            txtSeller.setText(merchantAccount.getEnterpriseName());
                            GetUser();
                            txtBalance6.setText(Double.toString(Wallet.wallet.getAmount()));

                        }else{
                            Toast.makeText(NFC.this,"Insufficient Balance.",Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(NFC.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Transaction> call, Throwable t) {
                    Toast.makeText(NFC.this, "ERROR", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public void cmdConfirm(View v){
        Call<Transaction> call = RetrofitClient.getInstance().getAPI().SendMoney(Account.account.ID, referenceID);
        call.enqueue(new Callback<Transaction>() {
            @Override
            public void onResponse(Call<Transaction> call, Response<Transaction> response) {
                try {
                    transaction = response.body();
                    if (transaction != null) {
                        Toast.makeText(NFC.this,"Successful",Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(NFC.this, e.toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Transaction> call, Throwable t) {
                Toast.makeText(NFC.this, "ERROR", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void cmdCancel(View v){
        Intent i = new Intent(NFC.this, Home_Menu.class);
        startActivity(i);
    }

    public void getMerchant(final int receiverID){
        Call<MerchantAccount> call = RetrofitClient.getInstance().getAPI().GetMerchantAccount(receiverID);
        call.enqueue(new Callback<MerchantAccount>() {
            @Override
            public void onResponse(Call<MerchantAccount> call, Response<MerchantAccount> response) {
                try{
                    merchantAccount = response.body();
                }catch (Exception e){
                    Toast.makeText(NFC.this,e.toString(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MerchantAccount> call, Throwable t) {
                Toast.makeText(NFC.this,"ERROR",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public NdefMessage createNdefMessage(NfcEvent event) {
        return null;
    }
}
