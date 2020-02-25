package com.asadeveloper.submissionempat.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.asadeveloper.submissionempat.R;
import com.asadeveloper.submissionempat.db.DatabaseContract;
import com.asadeveloper.submissionempat.db.WidgetMovies;
import com.asadeveloper.submissionempat.model.Favorite;
import com.asadeveloper.submissionempat.model.MovieItems;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.asadeveloper.submissionempat.db.DatabaseContract.CONTENT_URI;
import static com.asadeveloper.submissionempat.widget.FavoriteFilmWidget.EXTRA_ITEM;

public class StackRemoteViewsFactory implements  RemoteViewsService.RemoteViewsFactory {
        private Context mContext;
        int mAppWidgetId;
        private Cursor cursor;
        MovieItems movieResult;

        public StackRemoteViewsFactory(Context applicationContext, Intent intent) {
                mContext = applicationContext;
                mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }


        @Override
        public void onCreate() {
                cursor = mContext.getContentResolver().query(CONTENT_URI, null, null, null, null);
        }

        @Override
        public void onDataSetChanged() {
                final long token = Binder.clearCallingIdentity();
                cursor = mContext.getContentResolver().query(CONTENT_URI, null, null, null, null);
                Binder.restoreCallingIdentity(token);
        }

        @Override
        public void onDestroy() {
        }

        @Override
        public int getCount() {
                return cursor.getCount();
        }

        @Override
        public RemoteViews getViewAt(int position) {
                RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.item_widget);
                if (cursor.moveToPosition(position)) {
                        movieResult = new MovieItems(cursor);
                        Bitmap bmp;
                        try {
                                bmp = Glide.with(mContext)
                                        .asBitmap()
                                        .load(movieResult.getPoster_path())
                                        .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                                        .get();
                                rv.setImageViewBitmap(R.id.imgView_widget, bmp);
                                rv.setTextViewText(R.id.tv_widget_title, movieResult.getOriginal_title());
                        } catch (InterruptedException | ExecutionException e) {
                                Log.d("Widget Load Error", "error");
                        }
                }

                Bundle extras = new Bundle();
                extras.putInt(EXTRA_ITEM, position);
                Intent fillInIntent = new Intent();
                fillInIntent.putExtras(extras);

                rv.setOnClickFillInIntent(R.id.imgView_widget, fillInIntent);
                return rv;
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
        public long getItemId(int position) {
                return 0;
        }

        @Override
        public boolean hasStableIds() {
                return false;
        }
}
