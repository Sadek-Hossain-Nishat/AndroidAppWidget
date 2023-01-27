package com.example.appwidgetapplication;

import static com.example.appwidgetapplication.ExampleAppWidgetConfig.KEY_BUTTON_TEXT;
import static com.example.appwidgetapplication.ExampleAppWidgetConfig.SHARED_PRES;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;

public class ExampleAppWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context,
                         AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {

        for (int appWidgetId : appWidgetIds) {
            Intent intent = new Intent(context,MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);

            SharedPreferences prefs = context.getSharedPreferences(SHARED_PRES,Context.MODE_PRIVATE);

            String buttonText = prefs.getString(KEY_BUTTON_TEXT+appWidgetId,"Press me");

            Log.i("debug", "onUpdate: called when widget is updated "+appWidgetIds.length
                    );

            Log.i("debug", "onUpdate: called when widget is updated"+
                    prefs.getString(KEY_BUTTON_TEXT+appWidgetId,"Press me"));
            Log.i("debug", "onUpdate: called when widget is updated and appwidgetid"+
                    appWidgetId);
            Log.i("debug", "onUpdate: called when widget is updated and buttontext"+
                    buttonText);




            RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.example_widget);

            views.setOnClickPendingIntent(R.id.id_example_widget_button,pendingIntent);

            views.setCharSequence(R.id.id_example_widget_button,"setText",buttonText);

            appWidgetManager.updateAppWidget(appWidgetId,views);

        }

    }
}
