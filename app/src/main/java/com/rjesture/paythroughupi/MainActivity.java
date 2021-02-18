package com.rjesture.paythroughupi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.rjesture.myupiclient.UpiPortal;

import static model.UpiConstants.UpiAmount;
import static model.UpiConstants.UpiId;
import static model.UpiConstants.UpiName;
import static model.UpiConstants.UpiRemarkNote;
import static model.UpiConstants.UpiResultMessage;

public class MainActivity extends AppCompatActivity {

    TextView tv_tvDisplay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn_Click;
        tv_tvDisplay= findViewById(R.id.tv_tvDisplay);
        btn_Click= findViewById(R.id.btn_Click);
        btn_Click.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, UpiPortal.class);
            intent.putExtra(UpiId,"9044870700@okbizaxis");
            intent.putExtra(UpiName,"Rajat");
            intent.putExtra(UpiRemarkNote,"Rajat Test");
            intent.putExtra(UpiAmount,"100");
            startActivityForResult(intent,5);
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==RESULT_OK)
        {
            String message=data.getStringExtra(UpiResultMessage);
            tv_tvDisplay.setText(message);
        }else {
            String message=data.getStringExtra(UpiResultMessage);
            tv_tvDisplay.setText(message);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}