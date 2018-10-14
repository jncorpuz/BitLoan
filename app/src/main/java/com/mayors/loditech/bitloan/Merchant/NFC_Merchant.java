package com.mayors.loditech.bitloan.Merchant;

import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Bundle;

import com.mayors.loditech.bitloan.R;

public class NFC_Merchant extends BaseDrawerActivityMerchant implements NfcAdapter.CreateNdefMessageCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.nfc_merchant, frameLayout);
        setTitle("NFC");
    }

    @Override
    public NdefMessage createNdefMessage(NfcEvent nfcEvent) {
        return null;
    }
}
