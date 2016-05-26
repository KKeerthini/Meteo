package com.widget.cloudwired.weatherwidget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.util.Log;
import android.util.StringBuilderPrinter;
import android.widget.RemoteViews;

import com.widget.cloudwired.weatherwidget.asynctask.CommonAsyncTask;
import com.widget.cloudwired.weatherwidget.asynctask.CommonAsynctaskInterface;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Implementation of App Widget functionality.
 */
public class WeatherWidgetProvider extends AppWidgetProvider {

    private String city, mainWeather;
    double temperature;
    private RemoteViews views;


    public void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        views = new RemoteViews(context.getPackageName(), R.layout.weather_widget);
        retrieveWeatherDataAndUI(context, appWidgetManager, appWidgetId);


    }

    private void retrieveWeatherDataAndUI(Context context, final AppWidgetManager appWidgetManager, final int appWidgetId) {
        String url = Constants.WEATHER_URL + "q=" + Constants.CITY + "&" + Constants.APPID + "=" + Constants.APP_KEY;
        CommonAsyncTask commonAsyncTask = new CommonAsyncTask(context, url, Constants.REQUEST_GET, new CommonAsynctaskInterface() {
            @Override
            public void retrieveResponseString(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject != null) {

                        JSONArray weatherJSONArray = jsonObject.optJSONArray("weather");
                        for (int i = 0; i < weatherJSONArray.length(); i++) {
                            mainWeather = weatherJSONArray.optJSONObject(i).optString("main");
                        }

                        JSONObject tempJsonObj = jsonObject.optJSONObject("main");
                        temperature = Double.parseDouble(tempJsonObj.optString("temp"));
                        temperature = temperature - 273.15;

                        city = jsonObject.optString("name");

                        CharSequence widgetText = city;
                        views.setTextViewText(R.id.appwidget_city, widgetText);
                        CharSequence widgetTemp = String.valueOf(temperature) + " °C";
                        views.setTextViewText(R.id.weather_temp, widgetTemp);
                        Log.d("Temp","Temp" +widgetTemp);
                        Log.d("Weather","Mainweather"+mainWeather);
                        switch (mainWeather) {

                            case "Rain":
                                views.setImageViewResource(R.id.weather_image, R.drawable.rainyclouds);
                                break;
                            case "Clear":
                                views.setImageViewResource(R.id.weather_image, R.drawable.clearclouds);
                                break;
                            case "Clouds":
                                views.setImageViewResource(R.id.weather_image, R.drawable.partlycloud);
                                break;
                            default:
                                views.setImageViewResource(R.id.weather_image, R.drawable.scatteredshowers);

                        }

                        // Instruct the widget manager to update the widget
                        appWidgetManager.updateAppWidget(appWidgetId, views);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        commonAsyncTask.execute();
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created

    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }


}

