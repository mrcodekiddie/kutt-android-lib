package com.kutt.it.kutt_android;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class HTTPRequestSender extends AsyncTask<Call,Void,String>
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
            String responseFailed = responseMessage.errorBody().string();


            Log.v("watha", ""+isResponseSuccess.toString() + responseSuccess + responseFailed);
            if (isResponseSuccess) {
                result += responseSuccess;
            } else {
                result += responseFailed;
            }


            response.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Log.v("watha",response.body());
                    try {
                        Log.v("watha_err",response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });
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
