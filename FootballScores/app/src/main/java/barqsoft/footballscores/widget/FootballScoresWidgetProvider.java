package barqsoft.footballscores.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;
import java.util.Date;

import barqsoft.footballscores.DatabaseContract;
import barqsoft.footballscores.MainActivity;
import barqsoft.footballscores.R;

public class FootballScoresWidgetProvider extends AppWidgetProvider {
    private static final String ACTION_REFRESH = "barqsoft.footballscores.widget.action.REFRESH";

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Cursor cursor = context.getContentResolver().query(
                DatabaseContract.scores_table.buildScoreWithDate(),
                null,
                null,
                new String[]{simpleDateFormat.format(date)},
                null
        );

        int numOfMatchesToday = 0;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                numOfMatchesToday++;
            } while (cursor.moveToNext());
        }
        cursor.close();

        int artResourceId = R.drawable.ic_launcher;
        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

            views.setImageViewResource(R.id.widget_icon, artResourceId);

            views.setTextViewText(R.id.widget_number_of_matches, numOfMatchesToday + " matches today");

            Intent launchIntent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, launchIntent, 0);
            views.setOnClickPendingIntent(R.id.widget, pendingIntent);

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    public void onReceive(@NonNull Context context, @NonNull Intent intent) {
        super.onReceive(context, intent);
        if (ACTION_REFRESH.equals(intent.getAction())) {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            this.onUpdate(context, appWidgetManager, appWidgetManager.getAppWidgetIds(
                    new ComponentName(context, FootballScoresWidgetProvider.class)));
        }
    }

    public static Intent receiveBroadcast(Context context) {
        return new Intent(ACTION_REFRESH)
                .setComponent(new ComponentName(context, FootballScoresWidgetProvider.class));
    }
}
