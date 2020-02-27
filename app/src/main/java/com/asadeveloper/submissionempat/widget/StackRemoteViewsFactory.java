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
import com.asadeveloper.submissionempat.db.NoteHelper;
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
        private final List<Bitmap> widgetItems = new ArrayList<>();
        private NoteHelper helper;
        private ArrayList<MovieItems> list;
        private Cursor cursor;

        public StackRemoteViewsFactory(Context applicationContext, Intent intent) {
                this.mContext = applicationContext;
                mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        private Favorite getFav(int position) {
                if (!cursor.moveToPosition(position)) {
                        throw new IllegalStateException("Position invalid!");
                }

                return new Favorite(cursor.getInt(cursor.getColumnIndexOrThrow(
                        DatabaseContract.NoteColumns._ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.NoteColumns.TITLE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.NoteColumns.OVERVIEW)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.NoteColumns.DATE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.NoteColumns.POSTER)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.NoteColumns.VOTE)), "");
        }


        @Override
        public void onCreate() {

//                cursor = mContext.getContentResolver().query(
//                        DatabaseContract.CONTENT_URI,
//                        null,
//                        null,
//                        null,
//                        null
//                );

        }

        @Override
        public void onDataSetChanged() {
                helper = new NoteHelper(mContext);
                helper.open();
                list = helper.getAllMovie();
//                for (int i = 0; i < list.size(); i++) {
//                        Bitmap bitmap = null;
//                        try {
//                                bitmap = Glide.with(context)
//                                        .asBitmap()
//                                        .load(list.get(i).getPhoto())
//                                        .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
//                        } catch (Exception e) {
//                                e.getMessage();
//                        }
//                        widgetItems.add(bitmap);
//                }
//                if (cursor != null) {
//                        cursor.close();
//                }
//                final long identityToken = Binder.clearCallingIdentity();
//                cursor = mContext.getContentResolver().query(
//                        DatabaseContract.CONTENT_URI, null, null, null, null);
//                Binder.restoreCallingIdentity(identityToken);
        }

        @Override
        public void onDestroy() {
                if (cursor != null) {
                        cursor.close();
                }
        }

        @Override
        public int getCount() {
                return list.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
//                Favorite movieFavorite = getFav(position);
                RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.item_widget);

                Log.e("Widgetku",list.get(position).getOriginal_title());

                Bitmap bmp = null;
                try {
                        bmp = Glide.with(mContext)
                                .asBitmap()
                                .load(list.get(position).getPoster_path())
                                .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                                .get();
                        rv.setImageViewBitmap(R.id.imgView_widget,bmp);
                        rv.setTextViewText(R.id.tv_widget_title, list.get(position).getOriginal_title());
                        Log.d("Widgetku","Yessh");
                }catch (InterruptedException | ExecutionException e){
                        Log.d("Widget Load Error","error");
                }
                Bundle extras = new Bundle();
                extras.putInt(FavoriteFilmWidget.EXTRA_ITEM, position);
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
//                return cursor.moveToPosition(position) ? cursor.getLong(0) : position;
                return 0;
        }

        @Override
        public boolean hasStableIds() {
                return false;
        }
}
