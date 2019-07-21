package com.kutt.it.android;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.kutt.it.kutt_android.Kutt;
import com.kutt.it.kutt_android.KuttBuilder;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Kutt kutt=new KuttBuilder().setApi_key("Put API key here").setDomain("https://kutt.it").build();

        String res="";
        try
        {
            //res=kutt.submit("https://onezero.medium.com/what-will-happen-when-robots-store-all-our-memories-20ca1ddaf4d0","lllllll",null,null);
//res=kutt.deleteUrl("sWucZH","links.fossfreaks.com");
  //res=kutt.stats("lw6",null);          Log.v("llll",res);

            res=kutt.deleteUrl("l",null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        new AlertDialog.Builder(this)
                .setTitle("boww")
                .setMessage(res)
                .show();
    }
}
