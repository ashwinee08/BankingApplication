package com.example.joker.bankingapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import adapters.ViewPagerAdapter;
import models.TransactionInfo;
import models.IndividualMessage;
import models.SMSModel;

public class MainActivity extends AppCompatActivity {


    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<TransactionInfo> transactionInfoList;
    private ProgressDialog progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.getHold();
        new Thread(new DataRetriever()).start();
    }


    @Override
    protected void onStart() {
        Log.d("INSIDE START","");
        super.onStart();
        progressBar=new ProgressDialog(this);
        progressBar.setCancelable(false);
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.show();
    }

    private void getHold(){
            tabLayout=findViewById(R.id.tabs);
            viewPager=findViewById(R.id.view_pager);
    }

    private void result(List<TransactionInfo> transactionInfoList){
        Log.d("INSIDE RESULT","");
        if(progressBar.isShowing()){
            progressBar.cancel();
            progressBar.dismiss();
        }
        this.transactionInfoList=transactionInfoList;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(),transactionInfoList));
                tabLayout.setupWithViewPager(viewPager);
                tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimaryDarkB));
                Log.d("EXITING RESULT","");
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d("INSIDE RESUME","");
    }

    private class DataRetriever implements Runnable{

        private List<TransactionInfo> convertJsonIntoList(){
            List<TransactionInfo> finalListOfMessages=new ArrayList<>();
            Gson gson=new Gson();
            Reader reader=new InputStreamReader(MainActivity.this.getResources().openRawResource(R.raw.sms));
            SMSModel smsModel=gson.fromJson(reader, SMSModel.class);
            for(IndividualMessage messages:smsModel.getSmsList()){
                if(messages.getAddress().contains("PNBSMS")){

                    String[] words=messages.getBody().split(" ");
                    finalListOfMessages.add(getDataFromMessage(words));
                }
            }
            return finalListOfMessages;
        }


        private TransactionInfo getDataFromMessage(String[] words){
            TransactionInfo transactionInfo=new TransactionInfo();
             String date=null,amountTransacted=null;
            TransactionInfo.BankingType type=null;
            if(words[0].equals("Your")){
                date=words[10];
                amountTransacted=words[8];
                if (words[5].equals("debited")) {
                        type=TransactionInfo.BankingType.debited;
                    }else if(words[5].equals("credited")){
                    type=TransactionInfo.BankingType.credited;
                    }
            }else if(words[0].equals("Ac")){
                date=words[9];
                amountTransacted=getTransactedAmount(words[4]);
                if (words[2].equals("debited")) {
                    type=TransactionInfo.BankingType.debited;
                }else if(words[2].equals("credited")){
                    type=TransactionInfo.BankingType.credited;
                }
            }
            if(date!=null)
            transactionInfo.setDate(date);

            if(amountTransacted!=null)
            transactionInfo.setAmountDeductedOrCredited(amountTransacted);

            if(type!=null)
            transactionInfo.setType(type);

            return ((date!=null)&&(amountTransacted!=null)&&(type!=null))?transactionInfo:null;
        }

        private String getTransactedAmount(String fullAmountwithRs){
            String[] rupees=fullAmountwithRs.split("\\.");
            return rupees[1]+"."+rupees[2];
        }

        @Override
        public void run() {
            MainActivity.this.result(convertJsonIntoList());
        }
    }
}
