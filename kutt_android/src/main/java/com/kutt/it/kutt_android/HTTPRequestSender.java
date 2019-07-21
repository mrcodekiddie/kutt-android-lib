package com.kutt.it.kutt_android;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class  HTTPRequestSender extends AsyncTask<Call,Void,String>
{


    String result=" ";


    @Override
    protected String doInBackground(Call... args)
    {

        Call<String> response=args[0];
        try {
            Response<String> responseMessage = response.execute();




            Boolean isResponseSuccess = responseMessage.isSuccessful();
            String responseSuccess = responseMessage.body();
            Integer responseCode=responseMessage.code();

            Log.v("response"," "+responseSuccess +" "+responseCode.toString());

            if(responseCode==200)
            {
                result+=responseSuccess;

            }

            if(responseCode==400)
            {
                result+=responseMessage.errorBody().string();
            }




        }
      catch (IOException ex)
      {
         result+=ex.getMessage();
      }

        catch (NullPointerException nex)
        {
            Log.e("exceptionn",nex.getMessage());
        }
        return  result;
    }
}
