package com.widget.cloudwired.weatherwidget.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import com.widget.cloudwired.weatherwidget.urlconnector.URLConnector;

/**
 * Created by KEERTHINI on 5/25/2016.
 */
public class CommonAsyncTask extends AsyncTask<String, String, String> {
    String url;
    Context context;
    String methodType;
    String responseFromServer;
    CommonAsynctaskInterface commonAsynctaskInterface;


    public CommonAsyncTask(Context context, String url, String methodType, CommonAsynctaskInterface commonAsynctaskInterface) {
        this.context = context;
        this.url = url;
        this.methodType = methodType;
        this.commonAsynctaskInterface = commonAsynctaskInterface;
    }

    @Override
    protected String doInBackground(String... param) {

        URLConnector urlConnection = new URLConnector(context);
        responseFromServer= urlConnection.openHttpConnection(url, methodType);
        return responseFromServer;
    }

    @Override
    protected void onPostExecute(String responseFromServer) {
        super.onPostExecute(responseFromServer);
        commonAsynctaskInterface.retrieveResponseString(responseFromServer);
    }
}
