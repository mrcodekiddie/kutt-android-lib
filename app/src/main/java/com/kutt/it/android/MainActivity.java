package com.kutt.it.android;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kutt.it.kutt_android.Kutt;
import com.kutt.it.kutt_android.KuttBuilder;

import java.util.concurrent.ExecutionException;

import static android.app.PendingIntent.getActivity;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences.Editor credentialsEditor;
    private SharedPreferences credentials;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        credentials = getSharedPreferences("credentials", Context.MODE_PRIVATE);
        credentialsEditor = credentials.edit();

        String apiKey = prefs.getString(getString(R.string.api_key), "api");
        String domain= prefs.getString(getString(R.string.domain),"domain");


        assert apiKey != null;

        if (apiKey.equals("api"))
        {
            setContentView(R.layout.layout_register);

            final EditText apiEditText=findViewById(R.id.et_api);
            final EditText domainEditText=findViewById(R.id.et_domain);

            Button setupButton=findViewById(R.id.button_setup);


            setupButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor credentialsEditor;
                    SharedPreferences credentials;

                    credentials = getSharedPreferences("credentials", Context.MODE_PRIVATE);
                    credentialsEditor = credentials.edit();
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

                    SharedPreferences.Editor editor = prefs.edit();

                    editor.putString(getString(R.string.api_key),apiEditText.getText().toString());
                    editor.putString(getString(R.string.domain),domainEditText.getText().toString());
                    editor.commit();






                    setContentView(R.layout.activity_main);
                }
            });





        }
        else
        {
            Kutt kutt=new KuttBuilder().setApi_key(apiKey).setDomain(domain).build();

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


}
