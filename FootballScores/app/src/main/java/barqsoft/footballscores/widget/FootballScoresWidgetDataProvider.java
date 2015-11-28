// I used the guide created by Dharmang Soni to help create my collection widget
// http://dharmangsoni.blogspot.dk/2014/03/collection-widget-with-event-handling.html

package barqsoft.footballscores.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.text.SimpleDateFormat;
import java.util.Date;

import barqsoft.footballscores.R;
import barqsoft.footballscores.Utilities;
import barqsoft.footballscores.data.FootballScoresDatabaseContract;

@SuppressWarnings("NewApi")
public class FootballScoresWidgetDataProvider implements RemoteViewsService.RemoteViewsFactory {
    private Utilities mUtilities;
    private Context mContext = null;
    private Cursor mCursor = null;

    public FootballScoresWidgetDataProvider(Context context, Intent intent) {
        mContext = context;
        mUtilities = new Utilities(mContext);
    }

    @Override
    public int getCount() {
        return mCursor == null ? 0 : mCursor.getCount();
    }

    @Override
    public long getItemId(int position) {
        if (mCursor.moveToPosition(position)) {
            return mCursor.getLong(Utilities.DATABASE_MATCH_ID_COL);
        }
        return position;
    }

    @Override
    public RemoteViews getLoadingView() {
        return new RemoteViews(mContext.getPackageName(), R.layout.widget_list);
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (position == AdapterView.INVALID_POSITION || mCursor == null ||
                !mCursor.moveToPosition(position)) {
            return null;
        }
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_list);

        String homeTeamName = mCursor.getString(Utilities.DATABASE_HOME_COL);
        String awayTeamName = mCursor.getString(Utilities.DATABASE_AWAY_COL);

        remoteViews.setTextViewText(R.id.home_name, homeTeamName);
        remoteViews.setTextViewText(R.id.away_name, awayTeamName);

        String score = mUtilities.getScores(
                mCursor.getInt(Utilities.DATABASE_HOME_GOALS_COL),
                mCursor.getInt(Utilities.DATABASE_AWAY_GOALS_COL)
        );
        remoteViews.setTextViewText(R.id.score_textview, score);

        remoteViews.setTextViewText(R.id.data_textview, mCursor.getString(Utilities.DATABASE_TIME_COL));

        remoteViews.setImageViewResource(
                R.id.home_crest,
                mUtilities.getTeamCrestByTeamName(homeTeamName)
        );
        remoteViews.setImageViewResource(
                R.id.away_crest,
                mUtilities.getTeamCrestByTeamName(awayTeamName)
        );

        return remoteViews;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void onCreate() {
        initData();
    }

    @Override
    public void onDataSetChanged() {
        initData();
    }

    private void initData() {
        // Try/finally to avoid permission denial crash when trying to connect to database
        // http://stackoverflow.com/questions/21275898/securityexception-with-granturipermission-when-sharing-a-file-with-fileprovider
        // http://stackoverflow.com/questions/13187284/android-permission-denial-in-widget-remoteviewsfactory-for-content
        final long token = Binder.clearCallingIdentity();
        try {
            if (mCursor != null) {
                mCursor.close();
            }

            Date date = new Date(System.currentTimeMillis());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

            mCursor = mContext.getContentResolver().query(
                    FootballScoresDatabaseContract.ScoresTable.buildScoreWithDate(),
                    Utilities.DATABASE_PROJECTION,
                    null,
                    new String[]{simpleDateFormat.format(date)},
                    null
            );

        } finally {
            Binder.restoreCallingIdentity(token);
        }
    }

    @Override
    public void onDestroy() {
        if (mCursor != null) {
            mCursor.close();
            mCursor = null;
        }
    }
}
