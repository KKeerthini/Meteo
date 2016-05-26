package com.widget.cloudwired.weatherwidget.urlconnector;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

/**
 * Created by KEERTHINI on 5/25/2016.
 */
public class URLConnector {

    URL url = null;
    Context context;

    BufferedReader readInputStream = null;

    public URLConnector(Context context) {
        this.context = context;

    }

    public String openHttpConnection(String urlStr, String methodType) {
        BufferedInputStream inputStream = null;
        String result="";
        int resCode = -1;

        try {
            URL url = new URL(urlStr);
            URLConnection urlConn = url.openConnection();

            if (!(urlConn instanceof HttpURLConnection)) {
                throw new IOException("URL is not an Http URL");
            }
            HttpURLConnection httpConn = (HttpURLConnection) urlConn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod(methodType);
            httpConn.connect();
            resCode = httpConn.getResponseCode();

            if (resCode == HttpURLConnection.HTTP_OK) {

                inputStream = new BufferedInputStream(httpConn.getInputStream());
                String response = inputStream.toString();
                Log.d("HTTPResponse", response);
               /* int apiVersion= Build.VERSION.SDK_INT;
                if(apiVersion < 19)
                {*/
                readInputStream = new BufferedReader(new InputStreamReader(inputStream));//StandardCharsets.UTF_8));
                //   }
               /* else
                {
                    readInputStream=new BufferedReader(new InputStreamReader(inputStream ,StandardCharsets.UTF_8));
                }*/

                String str = null;
                StringBuilder sb = new StringBuilder(8192);
                while ((str = readInputStream.readLine()) != null) {
                    sb.append(str);

                }
                Log.d("result", "httpResponse" + sb.toString());
                result=sb.toString();
                readInputStream.close();

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}
