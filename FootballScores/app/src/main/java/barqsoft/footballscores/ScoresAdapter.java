package barqsoft.footballscores;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class ScoresAdapter extends CursorAdapter {
    public double detailMatchId = 0;

    private Utilities mUtilities;

    public ScoresAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);
        mUtilities = new Utilities(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_football_scores_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);

        return view;
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        final ViewHolder viewHolder = (ViewHolder) view.getTag();
        viewHolder.homeName.setText(cursor.getString(Utilities.DATABASE_HOME_COL));
        viewHolder.awayName.setText(cursor.getString(Utilities.DATABASE_AWAY_COL));
        viewHolder.date.setText(cursor.getString(Utilities.DATABASE_TIME_COL));
        viewHolder.score.setText(
                mUtilities.getScores(
                        cursor.getInt(Utilities.DATABASE_HOME_GOALS_COL),
                        cursor.getInt(Utilities.DATABASE_AWAY_GOALS_COL)
                )
        );
        viewHolder.match_id = cursor.getDouble(Utilities.DATABASE_MATCH_ID_COL);

        viewHolder.homeCrest.setImageResource(
                mUtilities.getTeamCrestByTeamName(cursor.getString(Utilities.DATABASE_HOME_COL))
        );
        viewHolder.awayCrest.setImageResource(
                mUtilities.getTeamCrestByTeamName(cursor.getString(Utilities.DATABASE_AWAY_COL))
        );

        LayoutInflater layoutInflater = (LayoutInflater) context.getApplicationContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View v = layoutInflater.inflate(R.layout.fragment_detail, null);
        ViewGroup container = (ViewGroup) view.findViewById(R.id.details_fragment_container);

        if (viewHolder.match_id == detailMatchId) {
            container.addView(v, 0,
                    new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                    )
            );

            TextView matchDay = (TextView) v.findViewById(R.id.matchday_textview);
            matchDay.setText(
                    mUtilities.getMatchDay(
                            cursor.getInt(Utilities.DATABASE_MATCH_DAY_COL),
                            cursor.getInt(Utilities.DATABASE_LEAGUE_COL)
                    )
            );
            TextView league = (TextView) v.findViewById(R.id.league_textview);
            league.setText(mUtilities.getLeague(cursor.getInt(Utilities.DATABASE_LEAGUE_COL)));

            Button shareButton = (Button) v.findViewById(R.id.share_button);
            shareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //add Share Action
                    context.startActivity(
                            createShareForecastIntent(
                                    viewHolder.homeName.getText() + " " +
                                            viewHolder.score.getText() + " " +
                                            viewHolder.awayName.getText() + " "
                            )
                    );
                }
            });
        } else {
            container.removeAllViews();
        }

    }

    @SuppressWarnings("deprecation")
    public Intent createShareForecastIntent(String ShareText) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(
                Intent.EXTRA_TEXT,
                ShareText + mContext.getResources().getString(R.string.share_hashtag)
        );
        return shareIntent;
    }

}
