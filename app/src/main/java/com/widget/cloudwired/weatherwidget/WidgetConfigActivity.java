package com.widget.cloudwired.weatherwidget;

import android.annotation.TargetApi;
import android.os.Build;
import android.preference.PreferenceActivity;

import com.widget.cloudwired.weatherwidget.R;

import java.util.List;

/**
 * Created by CIPL0310 on 5/25/2016.
 */
public class WidgetConfigActivity extends PreferenceActivity {

    private static final String PREFERENCES_PACKAGE_NAME = "com.widget.cloudwired";

    @Override
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.preference_header, target);
    }

    @Override
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected boolean isValidFragment(String fragmentName) {
        if (fragmentName.startsWith(PREFERENCES_PACKAGE_NAME)) {
           return true;
       }
        return super.isValidFragment(fragmentName);
    }
}
