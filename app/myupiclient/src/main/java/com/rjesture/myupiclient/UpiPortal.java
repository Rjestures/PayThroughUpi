package com.rjesture.myupiclient;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import model.UpiMaker;

import static model.UpiConstants.UpiAmount;
import static model.UpiConstants.UpiId;
import static model.UpiConstants.UpiName;
import static model.UpiConstants.UpiRemarkNote;
import static model.UpiConstants.UpiResultMessage;
import static model.UpiConstants.defaultErrorMessage;
import static model.UpiConstants.fieldErrorMessage;
import static model.UpiConstants.noUpiAppMessage;

public class UpiPortal extends AppCompatActivity {
    private static final int UPI_PAYMENT = 100;
    UpiMaker upiMaker;

    public static boolean isConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnected()
                    && netInfo.isConnectedOrConnecting()
                    && netInfo.isAvailable();
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upi_portal);
        initialize();

    }

    private void initialize() {
        Intent intent = getIntent();
        try {
            if (intent.hasExtra(UpiId) && intent.hasExtra(UpiName) && intent.hasExtra(UpiRemarkNote)
                    && intent.hasExtra(UpiAmount)) {
                String upiId = intent.getStringExtra(UpiId);
                String upiName = intent.getStringExtra(UpiName);
                String upiRemarkNote = intent.getStringExtra(UpiRemarkNote);
                String upiAmount = intent.getStringExtra(UpiAmount);
                if (upiId.isEmpty() || upiName.isEmpty() || upiRemarkNote.isEmpty() || upiAmount.isEmpty()) {
                    intent.putExtra(UpiResultMessage, fieldErrorMessage);
                    setResult(RESULT_CANCELED, intent);
                    finish();
                } else {
                    upiMaker = new UpiMaker(upiId, upiName, upiRemarkNote, upiAmount);
                    payUsingUpi();
                }
            } else {
                intent.putExtra(UpiResultMessage, fieldErrorMessage);
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        } catch (Exception e) {
            intent.putExtra(UpiResultMessage, defaultErrorMessage);
            setResult(RESULT_CANCELED, intent);
            finish();
        }
    }

    void payUsingUpi() {
        Intent intent = getIntent();
        try {
            Uri uri = Uri.parse("upi://pay").buildUpon()
                    .appendQueryParameter("pa", upiMaker.getUpiId())
                    .appendQueryParameter("pn", upiMaker.getUpiName())
                    .appendQueryParameter("tn", upiMaker.getUpiRemarkNote())
                    .appendQueryParameter("am", upiMaker.getUpiAmount())
                    .appendQueryParameter("cu", "INR")
                    .build();
            Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
            upiPayIntent.setData(uri);
            // will always show a dialog to user to choose an app
            Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");
            // check if intent resolves
            if (null != chooser.resolveActivity(getPackageManager())) {
                startActivityForResult(upiPayIntent, UPI_PAYMENT);
            } else {
                intent.putExtra(UpiResultMessage, noUpiAppMessage);
                setResult(RESULT_CANCELED, intent);
                finish();
            }

        } catch (ActivityNotFoundException e) {
            intent.putExtra(UpiResultMessage, noUpiAppMessage);
            setResult(RESULT_CANCELED, intent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
            intent.putExtra(UpiResultMessage, defaultErrorMessage);
            setResult(RESULT_CANCELED, intent);
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Intent intent = getIntent();
        try {
            Bundle bundle = data.getExtras();
            intent.putExtra(UpiResultMessage, bundle);
            setResult(RESULT_OK, intent);
            finish();
            //finishing activity

        } catch (Exception e) {
            intent.putExtra(UpiResultMessage, defaultErrorMessage);
            setResult(RESULT_CANCELED, intent);
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
       /*
       E/main: response -1
       E/UPI: onActivityResult: txnId=AXI4a3428ee58654a938811812c72c0df45&responseCode=00&Status=SUCCESS&txnRef=922118921612
       E/UPIPAY: upiPaymentDataOperation: txnId=AXI4a3428ee58654a938811812c72c0df45&responseCode=00&Status=SUCCESS&txnRef=922118921612
       E/UPI: payment successfull: 922118921612
         */
/*
        switch (requestCode) {
            case UPI_PAYMENT:
                if ((RESULT_OK == resultCode) || (resultCode == 11)) {
                    if (data != null) {
                        String trxt = data.getStringExtra("response");
                        Log.e("UPI", "onActivityResult: " + trxt);
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add(trxt);
                        upiPaymentDataOperation(dataList);
                    } else {
                        Log.e("UPI", "onActivityResult: " + "Return data is null");
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add("nothing");
                        upiPaymentDataOperation(dataList);
                    }
                } else {
                    //when user simply back without payment
                    Log.e("UPI", "onActivityResult: " + "Return data is null");
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("nothing");
                    upiPaymentDataOperation(dataList);
                }
                break;
        }
*/
    }


}