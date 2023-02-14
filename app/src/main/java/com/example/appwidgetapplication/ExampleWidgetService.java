package com.example.appwidgetapplication;

import static com.example.appwidgetapplication.ExampleAppWidgetProvider.EXTRA_ITEM_POSITION;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

public class ExampleWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ExampleWidgetItemFactory(getApplicationContext(),intent);
    }


    class ExampleWidgetItemFactory implements RemoteViewsFactory {

        private Context context;
        private int appWidgetId;
        private String[] exampleData = {
                "one","two","three","four","five","six","seven","eight","nine","ten"
        };

        public ExampleWidgetItemFactory(Context context, Intent intent) {
            this.context = context;
            this.appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        @Override
        public void onCreate() {

            // connect to data source
            SystemClock.sleep(3000);

        }

        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {

            // close data source

        }

        @Override
        public int getCount() {
            return exampleData.length;
        }

        @Override
        public RemoteViews getViewAt(int i) {

            RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.example_widget_item);
            views.setTextViewText(R.id.example_widget_item_text,exampleData[i]);
            Intent fillIntent = new Intent();
            fillIntent.putExtra(EXTRA_ITEM_POSITION,i);
            views.setOnClickFillInIntent(R.id.example_widget_item_text,fillIntent);
            SystemClock.sleep(500);
            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}

