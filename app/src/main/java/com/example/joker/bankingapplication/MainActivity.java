package com.example.joker.bankingapplication;

import android.app.ProgressDialog;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

import java.io.InputStreamReader;
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


    private void result(final List<TransactionInfo> transactionInfoList){
        Log.d("INSIDE RESULT","");
        if(progressBar.isShowing()){
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
            InputStreamReader reader=null;
            try{
                reader=new InputStreamReader(MainActivity.this.getResources().openRawResource(R.raw.sms));
            }catch(Exception e){
                Log.d("INSIDE EXCEPTION","");
            }
            if(reader==null){
                return null;
            }
            SMSModel smsModel=gson.fromJson(reader, SMSModel.class);
            for(IndividualMessage messages:smsModel.getSmsList()){
                if(messages.getAddress().contains("PNBSMS")
                        && messages.getBody().contains("XXXXXXXX00004252")
                        || messages.getBody().contains("XXXXXXXX4252")
                ){
                    String[] words=messages.getBody().split(" ");
                    finalListOfMessages.add(getDataFromMessage(words));
                }
            }
            return finalListOfMessages;
        }


        private TransactionInfo getDataFromMessage(String[] words){
            boolean amountIsSet=false;
            TransactionInfo transactionInfo=new TransactionInfo();

            for(String word:words){
                if(word.matches("\\d{2}-\\d{2}-\\d{2}")){
                    transactionInfo.setDate(word);
                }else if(word.matches("\\d{2}-\\d{2}-\\d{4}")){
                    transactionInfo.setDate(this.changeDateFormat(word));
                }else if(word.toLowerCase().equals("debited")) {
                    transactionInfo.setType(TransactionInfo.BankingType.debited);
                }else if(word.toLowerCase().equals("credited")){
                    transactionInfo.setType(TransactionInfo.BankingType.credited);
                }else if(!amountIsSet){
                    if(word.matches("\\d{0,9}(\\.\\d{1,2})")){
                        transactionInfo.setAmountDeductedOrCredited(word);
                        amountIsSet=true;
                    }else if(word.contains("Rs")) {
                        if (word.length() > 3) {
                            String moneyWithRs=word.substring(3);
                            if(moneyWithRs.contains(",")) {
                                String[] semiWords = moneyWithRs.split(",");
                                transactionInfo.setAmountDeductedOrCredited(semiWords[0]);
                                amountIsSet = true;
                                if (transactionInfo.getDate() == null) {
                                    transactionInfo.setDate(this.changeDateFormat(semiWords[1]));
                                }
                            }else {
                                transactionInfo.setAmountDeductedOrCredited(moneyWithRs);
                                amountIsSet = true;
                            }
                        }
                    }
                }
            }


            return transactionInfo;
        }

        private String changeDateFormat(String date){
           String[] dateMonthYear= date.split("-");
           return dateMonthYear[0]+"-"+dateMonthYear[1]+"-"+dateMonthYear[2].charAt(2)+dateMonthYear[2].charAt(3);
        }


        private String getTransactedAmount(String fullAmountWithRs){
            String[] rupees=fullAmountWithRs.split("\\.");
            return rupees[1]+"."+rupees[2];
        }


        @Override
        public void run() {
            MainActivity.this.result(convertJsonIntoList());
        }
    }
}
