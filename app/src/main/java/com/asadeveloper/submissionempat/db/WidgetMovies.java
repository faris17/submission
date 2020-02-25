package com.asadeveloper.submissionempat.db;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Build;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.asadeveloper.submissionempat.MainActivity;
import com.asadeveloper.submissionempat.R;
import com.asadeveloper.submissionempat.model.MovieItems;
import com.asadeveloper.submissionempat.widget.StackWidgetService;

import java.util.ArrayList;
import java.util.Objects;

import static android.provider.BaseColumns._ID;
import static android.provider.ContactsContract.CommonDataKinds.Organization.TITLE;
import static com.asadeveloper.submissionempat.db.DatabaseContract.NoteColumns.DATE;
import static com.asadeveloper.submissionempat.db.DatabaseContract.NoteColumns.OVERVIEW;
import static com.asadeveloper.submissionempat.db.DatabaseContract.NoteColumns.POSTER;
import static com.asadeveloper.submissionempat.db.DatabaseContract.TABLE_NAME;
import static com.asadeveloper.submissionempat.widget.FavoriteFilmWidget.EXTRA_ITEM;
import static com.asadeveloper.submissionempat.widget.FavoriteFilmWidget.TOAST_ACTION;

public class WidgetMovies extends AppWidgetProvider {
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Intent intent = new Intent(context, StackWidgetService.class);

        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.favorite_film_widget);

        views.setRemoteAdapter(R.id.stack_view, intent);

        Intent toastIntent = new Intent(context, MainActivity.class);

        toastIntent.setAction(TOAST_ACTION);
        toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.stack_view, toastPendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {

    }

    @Override
    public void onDisabled(Context context) {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager.getInstance(context);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Objects.requireNonNull(intent.getAction()).equals(TOAST_ACTION)) {
                intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                        AppWidgetManager.INVALID_APPWIDGET_ID);
                int viewIndex = intent.getIntExtra(EXTRA_ITEM, 0);
                Toast.makeText(context, "Touched view " + viewIndex, Toast.LENGTH_SHORT).show();
            }
        }
        super.onReceive(context, intent);
    }
}
