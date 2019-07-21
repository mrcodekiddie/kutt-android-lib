package com.kutt.it.kutt_android;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Kutt
{
    private API api;
    private Call<String> getURLsCall;
    private Call<String> submitCall;
    private Call<String> deleteCall;
    private Call<String> statsCall;

    JSONObject jsonObject;
    String return_response=",,,";



 protected static   String domain;
 protected static String auth_api_key;


    protected Kutt(String domain,String auth_api_key)
    {
        this.domain=domain;
        this.auth_api_key=auth_api_key;
        api=ServiceGenerator.createService(API.class);

    }



    public String getUrls() throws  ExecutionException, InterruptedException
    {

        getURLsCall=api.geturls();

        String result=new HTTPRequestSender().execute(getURLsCall).get();

        return result;

    }


    public String submit(@NonNull String target,@Nullable String customUrl,@Nullable String password,@Nullable String reuse) throws Exception
    {
        JSONObject postData=new JSONObject();

        postData.put("target",target);

        if(customUrl!=null)
        {
            postData.put("customurl",customUrl);
        }
        if(password!=null)
        {
            postData.put("password",password);
        }
        if(reuse!=null)
        {
            postData.put("reuse",reuse);
        }

        Log.v("postdata",postData.toString());
        submitCall=api.submit("application/json",postData.toString());

        String result=new HTTPRequestSender().execute(submitCall).get();

        return result;
    }


    public String deleteUrl(@NonNull String id,@Nullable String domain) throws  Exception
    {
        JSONObject postData=new JSONObject();

        postData.put("id",id);

        if(domain!=null)
        {
            postData.put("domain",domain);
        }
        deleteCall=api.deleteUrl("application/json",postData.toString());

        String result=new HTTPRequestSender().execute(deleteCall).get();

        return result;
    }

    public String stats(@NonNull  String id,@Nullable String domain) throws Exception
    {
        if(domain==null)
        {
            statsCall=api.stats(id);

        }
        else
        {
            statsCall=api.stats(id,domain);
        }




       String result= new HTTPRequestSender().execute(statsCall).get();

        return result;
    }
}

