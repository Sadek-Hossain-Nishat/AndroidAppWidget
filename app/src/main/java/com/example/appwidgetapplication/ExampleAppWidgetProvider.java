package com.example.appwidgetapplication;

import static com.example.appwidgetapplication.ExampleAppWidgetConfig.KEY_BUTTON_TEXT;
import static com.example.appwidgetapplication.ExampleAppWidgetConfig.SHARED_PRES;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

public class ExampleAppWidgetProvider extends AppWidgetProvider {

    public static final String ACTION_REFRESH = "actionRefresh";



    @Override
    public void onUpdate(Context context,
                         AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {

        for (int appWidgetId : appWidgetIds) {

            Toast.makeText(context, "onUpdated", Toast.LENGTH_SHORT).show();
            Intent buttonIntent = new Intent(context,MainActivity.class);
            PendingIntent buttonPendingIntent = PendingIntent.getActivity(context,0,buttonIntent,0);

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

            // it is responsible service intent to create adapter click view

            Intent serviceIntent = new Intent(context,ExampleWidgetService.class);
            serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetId);

            serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME)));


            Intent clickIntent = new Intent(context,ExampleAppWidgetProvider.class);
            clickIntent.setAction(ACTION_REFRESH);
            PendingIntent clickPendingIntent = PendingIntent.getBroadcast(context,
                    0,clickIntent,0);




            RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.example_widget);

            views.setOnClickPendingIntent(R.id.id_example_widget_button,buttonPendingIntent);

            views.setCharSequence(R.id.id_example_widget_button,"setText",buttonText);

            views.setRemoteAdapter(R.id.example_widget_stack_view,serviceIntent);
            views.setEmptyView(R.id.example_widget_stack_view,R.id.example_widget_empty_view);

            views.setPendingIntentTemplate(R.id.example_widget_stack_view,clickPendingIntent);

            Bundle appWidgetOptions = appWidgetManager.getAppWidgetOptions(appWidgetId);

            resizeWidget(appWidgetOptions,views);

            appWidgetManager.updateAppWidget(appWidgetId,views);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId,R.id.example_widget_stack_view);

        }

    }


    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        RemoteViews views = new RemoteViews(
                context.getPackageName(),
                R.layout.example_widget


        );


        resizeWidget(newOptions,views);







        appWidgetManager.updateAppWidget(appWidgetId,views);
    }


    private void resizeWidget(Bundle appWidgetOptions, RemoteViews views) {

        int minWidth = appWidgetOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);
        int maxWidth = appWidgetOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_WIDTH);
        int minHeight = appWidgetOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT);
        int maxHeight = appWidgetOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT);
        if (maxHeight > 100) {

            views.setViewVisibility(R.id.id_example_widget_text, View.VISIBLE);
            views.setViewVisibility(R.id.id_example_widget_button, View.VISIBLE);
        } else {
            views.setViewVisibility(R.id.id_example_widget_text,View.GONE);
            views.setViewVisibility(R.id.id_example_widget_button,View.GONE);
        }

    }


    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        Toast.makeText(context, "onDeleted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEnabled(Context context) {
        Toast.makeText(context, "onEnabled", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onDisabled(Context context) {
        Toast.makeText(context, "onDisabled", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onReceive(Context context, Intent intent) {

        if (ACTION_REFRESH.equals(intent.getAction())) {

            int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);


            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId,R.id.example_widget_stack_view);

        }
        super.onReceive(context, intent);
    }
}
