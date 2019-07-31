package com.kutt.it.android;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kutt.it.kutt_android.Kutt;
import com.kutt.it.kutt_android.KuttBuilder;

import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import static android.app.PendingIntent.getActivity;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences.Editor credentialsEditor;
    private SharedPreferences credentials;
    private CheckBox customURLCheckBox,passwordCheckBox;
    private EditText customURLEditText,passwordEdittext,inputEditText;
    private TextView customURLPrefixTextView;
    private  String customURl=null;
    private String password=null;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//avl=findViewById(R.id.avl);
//avl.setIndicatorColor(R.color.colorAccent);

        customURLCheckBox=findViewById(R.id.checkbox_customurl);
        passwordCheckBox=findViewById(R.id.checkbox_password);
        customURLEditText=findViewById(R.id.et_customurl);
        passwordEdittext=findViewById(R.id.et_password);
        customURLPrefixTextView=findViewById(R.id.tv_customurl_prefix);
        inputEditText=findViewById(R.id.et_url);


        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        // when the other app Shares text it is placed as a text/plan mime type
        // on the intent so we can then retrieve that text off the incoming intent

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
                if (sharedText != null)
                {
                    inputEditText.setText(sharedText);
                }
            }
        }

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
                public void onClick(View v)
                {
                    if(!domainEditText.getText().toString().contains("http"))
                    {
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("Error")
                                .setMessage("Enter a valid domain")
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .show();
                    }
                    else
                    {
                        SharedPreferences.Editor credentialsEditor;
                        SharedPreferences credentials;

                        credentials = getSharedPreferences("credentials", Context.MODE_PRIVATE);
                        credentialsEditor = credentials.edit();
                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

                        SharedPreferences.Editor editor = prefs.edit();

                        editor.putString(getString(R.string.api_key),apiEditText.getText().toString());
                        editor.putString(getString(R.string.domain),domainEditText.getText().toString());
                        editor.commit();
                        startActivity(new Intent(MainActivity.this,MainActivity.class));


                    }
                }
            });





        }
        else
        {
            final Kutt kutt=new KuttBuilder().setApi_key(apiKey).setDomain(domain).build();

            String res="";

            String[] domainPrefix=domain.split("//");
            customURLPrefixTextView.setText(domainPrefix[1]+"/");

            ImageView sendImageView=findViewById(R.id.iv_send);


            try
            {



                sendImageView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
//avl.setVisibility(View.VISIBLE);

                        Log.v("taskState","started");
                      //  avl.smoothToShow();
                        try {

                            String res=kutt.submit(inputEditText.getText().toString(),customURl,password,null);

                            JSONObject jsonObject=new JSONObject(res);

                            if(res.contains("error"))

                            {
                                Log.v("taskStatus","finished");

                                new AlertDialog.Builder(MainActivity.this)
                                        .setTitle("Error")
                                        .setMessage(jsonObject.get("error").toString())
                                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        })

                                        .show();
                            }

                            else
                            {
                                Log.v("taskStatus","finished");


                                final String shortUrl=jsonObject.get("shortUrl").toString();
                                new AlertDialog.Builder(MainActivity.this)
                                        .setTitle("Success")
                                        .setMessage(shortUrl)
                                        .setPositiveButton("copy", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                final android.content.ClipboardManager clipboardManager = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
                                                ClipData clipData = ClipData.newPlainText("Source Text", shortUrl);
                                                clipboardManager.setPrimaryClip(clipData);
                                            }
                                        })


                                        .show();
                            }


                        } catch (Exception e) {
                            new AlertDialog.Builder(MainActivity.this)
                                    .setTitle("Error")
                                    .setMessage(e.getMessage())
                                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }

                                    })
                                    .setCancelable(false)
                                    .show();
                        }
                    }
                });



            }
            catch (Exception e)
            {
                e.printStackTrace();
            }


        }

        customURLCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    customURLPrefixTextView.setVisibility(View.VISIBLE);
                    customURLEditText.setVisibility(View.VISIBLE);
                    customURLEditText.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                            customURl=s.toString();
                            Log.v("customURL",customURl);

                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                }
                else
                {
                    customURLPrefixTextView.setVisibility(View.GONE);
                    customURLEditText.setVisibility(View.GONE);
                    customURLEditText.getEditableText().clear();
                    customURl = null;
                }
            }

        });

        passwordCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {

                    passwordEdittext.setVisibility(View.VISIBLE);
                    passwordEdittext.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
password=s.toString();
Log.v("custom",password);
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });

                }
                else
                {
                    passwordEdittext.setVisibility(View.GONE);
                    password = null;
                }
            }

        });

    }


}
