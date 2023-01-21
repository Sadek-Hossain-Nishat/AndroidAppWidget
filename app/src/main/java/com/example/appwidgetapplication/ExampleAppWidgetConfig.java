package com.example.appwidgetapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ExampleAppWidgetConfig extends AppCompatActivity {

    public static final String SHARED_PRESS = "prefs";
    public static final String KEY_BUTTON_TEXT = "keyButtonText";

    private int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    private EditText editTextButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example_app_widget_config);

        Intent configIntent = getIntent();

        Bundle extras = configIntent.getExtras();
        if (extras!=null) {
            appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {

            finish();

        }

        editTextButton = findViewById(R.id.id_edit_text_button);
    }

    public void confirmConfiguration(View view) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
    Intent intent = new Intent(this,MainActivity.class);
    PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);


    }
}