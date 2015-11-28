package barqsoft.footballscores.service;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.Vector;

import barqsoft.footballscores.data.FootballScoresDatabaseContract;
import barqsoft.footballscores.R;
import barqsoft.footballscores.Utilities;
import barqsoft.footballscores.widget.FootballScoresWidgetProvider;

public class FootballScoresFetchService extends IntentService {
    public static final String LOG_TAG = FootballScoresFetchService.class.getSimpleName();

    public FootballScoresFetchService() {
        super(FootballScoresFetchService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        getData("n2");
        getData("p2");

        return;
    }

    private void getData(String timeFrame) {
        final String BASE_URL = getString(R.string.api_base_url); //Base URL
        final String QUERY_TIME_FRAME = getString(R.string.api_query_time_frame); //Time Frame parameter to determine days

        Uri fetch_build = Uri.parse(BASE_URL).buildUpon().
                appendQueryParameter(QUERY_TIME_FRAME, timeFrame).build();
        HttpURLConnection m_connection = null;
        BufferedReader reader = null;
        String JSON_data = null;

        try {
            URL fetch = new URL(fetch_build.toString());
            m_connection = (HttpURLConnection) fetch.openConnection();
            m_connection.setRequestMethod("GET");
            m_connection.addRequestProperty("X-Auth-Token", getString(R.string.api_key));
            m_connection.connect();

            // Read the input stream into a String
            InputStream inputStream = m_connection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return;
            }
            JSON_data = buffer.toString();
        } catch (Exception e) {
            Log.e(LOG_TAG, getString(R.string.log_myfetchservice_exception_here) + e.getMessage());
        } finally {
            if (m_connection != null) {
                m_connection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(LOG_TAG, getString(R.string.log_myfetchservice_error_closing_stream));
                }
            }
        }
        try {
            if (JSON_data != null) {
                //This bit is to check if the data contains any matches. If not, we call processJson on the dummy data
                JSONArray matches = new JSONObject(JSON_data).getJSONArray(getString(R.string.api_fixtures));
                if (matches.length() == 0) {
                    //if there is no data, call the function on dummy data
                    //this is expected behavior during the off season.
                    processJSONdata(
                            getString(R.string.dummy_data),
                            getApplicationContext(), false
                    );
                    return;
                }


                processJSONdata(JSON_data, getApplicationContext(), true);
            } else {
                //Could not Connect
                Log.e(LOG_TAG, getString(R.string.log_myfetchservice_could_not_connect_to_server));
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
        }
    }

    private void processJSONdata(String JSONdata, Context mContext, boolean isReal) {
        //JSON data
        // This set of league codes is for the 2015/2016 season. In fall of 2016, they will need to
        // be updated. Feel free to use the codes
        final String DUMMY_LEAGUE = Integer.toString(Utilities.DUMMY_LEAGUE);
        final String BUNDESLIGA1 = Integer.toString(Utilities.BUNDESLIGA1);
        final String BUNDESLIGA2 = Integer.toString(Utilities.BUNDESLIGA2);
        final String LIGUE1 = Integer.toString(Utilities.LIGUE1);
        final String LIGUE2 = Integer.toString(Utilities.LIGUE2);
        final String PREMIER_LEAGUE = Integer.toString(Utilities.PREMIER_LEAGUE);
        final String PRIMERA_DIVISION = Integer.toString(Utilities.PRIMERA_DIVISION);
        final String SEGUNDA_DIVISION = Integer.toString(Utilities.SEGUNDA_DIVISION);
        final String SERIE_A = Integer.toString(Utilities.SERIE_A);
        final String PRIMERA_LIGA = Integer.toString(Utilities.PRIMERA_LIGA);
        final String BUNDESLIGA3 = Integer.toString(Utilities.BUNDESLIGA3);
        final String EREDIVISIE = Integer.toString(Utilities.EREDIVISTE);
        final String CHAMPIONS_LEAGUE = Integer.toString(Utilities.CHAMPIONS_LEAGUE);

        final String SEASON_LINK = getString(R.string.api_season_link);
        final String MATCH_LINK = getString(R.string.api_match_link);
        final String FIXTURES = getString(R.string.api_fixtures);
        final String LINKS = getString(R.string.api_links);
        final String SOCCER_SEASON = getString(R.string.api_soccer_season);
        final String SELF = getString(R.string.api_self);
        final String MATCH_DATE = getString(R.string.api_match_date);
        final String HOME_TEAM = getString(R.string.api_home_team);
        final String AWAY_TEAM = getString(R.string.api_away_team);
        final String RESULT = getString(R.string.api_result);
        final String HOME_GOALS = getString(R.string.api_home_goals);
        final String AWAY_GOALS = getString(R.string.api_away_goals);
        final String MATCH_DAY = getString(R.string.api_match_day);

        //Match data
        String league = null;
        String matchDate = null;
        String matchTime = null;
        String home = null;
        String away = null;
        String homeGoals = null;
        String awayGoals = null;
        String matchId = null;
        String matchDay = null;

        try {
            JSONArray matches = new JSONObject(JSONdata).getJSONArray(FIXTURES);

            //ContentValues to be inserted
            Vector<ContentValues> values = new Vector<ContentValues>(matches.length());
            for (int i = 0; i < matches.length(); i++) {

                JSONObject matchData = matches.getJSONObject(i);
                league = matchData.getJSONObject(LINKS)
                        .getJSONObject(SOCCER_SEASON)
                        .getString("href");

                league = league.replace(SEASON_LINK, "");
                //This if statement controls which leagues we're interested in the data from.
                //add leagues here in order to have them be added to the DB.
                // If you are finding no data in the app, check that this contains all the leagues.
                // If it doesn't, that can cause an empty DB, bypassing the dummy data routine.
                if (league.equals(DUMMY_LEAGUE) ||
                        league.equals(PREMIER_LEAGUE) ||
                        league.equals(SERIE_A) ||
                        league.equals(BUNDESLIGA1) ||
                        league.equals(BUNDESLIGA2) ||
                        league.equals(PRIMERA_DIVISION) ||
                        league.equals(SEGUNDA_DIVISION) ||
                        league.equals(LIGUE1) ||
                        league.equals(LIGUE2) ||
                        league.equals(PRIMERA_LIGA) ||
                        league.equals(BUNDESLIGA3) ||
                        league.equals(EREDIVISIE) ||
                        league.equals(CHAMPIONS_LEAGUE)) {

                    matchId = matchData.getJSONObject(LINKS).getJSONObject(SELF).getString("href");
                    matchId = matchId.replace(MATCH_LINK, "");
                    if (!isReal) {
                        //This if statement changes the match ID of the dummy data so that it all goes into the database
                        matchId = matchId + Integer.toString(i);
                    }

                    matchDate = matchData.getString(MATCH_DATE);
                    matchTime = matchDate.substring(
                            matchDate.indexOf("T") + 1,
                            matchDate.indexOf("Z")
                    );
                    matchDate = matchDate.substring(0, matchDate.indexOf("T"));
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
                    simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                    try {
                        Date parsedDate = simpleDateFormat.parse(matchDate + matchTime);
                        SimpleDateFormat newDate = new SimpleDateFormat("yyyy-MM-dd:HH:mm");
                        newDate.setTimeZone(TimeZone.getDefault());
                        matchDate = newDate.format(parsedDate);
                        matchTime = matchDate.substring(matchDate.indexOf(":") + 1);
                        matchDate = matchDate.substring(0, matchDate.indexOf(":"));

                        if (!isReal) {
                            //This if statement changes the dummy data's date to match our current date range.
                            Date fragmentDate = new Date(
                                    System.currentTimeMillis() +
                                    ((i - 2) * Utilities.TWENTY_FOUR_HOURS_IN_MILLISECONDS)
                            );
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            matchDate = dateFormat.format(fragmentDate);
                        }
                    } catch (Exception e) {
                        Log.e(LOG_TAG, e.getMessage());
                    }
                    home = matchData.getString(HOME_TEAM);
                    away = matchData.getString(AWAY_TEAM);
                    homeGoals = matchData.getJSONObject(RESULT).getString(HOME_GOALS);
                    awayGoals = matchData.getJSONObject(RESULT).getString(AWAY_GOALS);
                    matchDay = matchData.getString(MATCH_DAY);
                    ContentValues matchValues = new ContentValues();
                    matchValues.put(Utilities.DATABASE_PROJECTION[Utilities.DATABASE_MATCH_ID_COL], matchId);
                    matchValues.put(Utilities.DATABASE_PROJECTION[Utilities.DATABASE_DATE_COL], matchDate);
                    matchValues.put(Utilities.DATABASE_PROJECTION[Utilities.DATABASE_TIME_COL], matchTime);
                    matchValues.put(Utilities.DATABASE_PROJECTION[Utilities.DATABASE_HOME_COL], home);
                    matchValues.put(Utilities.DATABASE_PROJECTION[Utilities.DATABASE_AWAY_COL], away);
                    matchValues.put(Utilities.DATABASE_PROJECTION[Utilities.DATABASE_HOME_GOALS_COL], homeGoals);
                    matchValues.put(Utilities.DATABASE_PROJECTION[Utilities.DATABASE_AWAY_GOALS_COL], awayGoals);
                    matchValues.put(Utilities.DATABASE_PROJECTION[Utilities.DATABASE_LEAGUE_COL], league);
                    matchValues.put(Utilities.DATABASE_PROJECTION[Utilities.DATABASE_MATCH_DAY_COL], matchDay);

                    values.add(matchValues);
                }
            }
            int insertedData = 0;
            ContentValues[] insertData = new ContentValues[values.size()];
            values.toArray(insertData);
            insertedData = mContext.getContentResolver().bulkInsert(
                    FootballScoresDatabaseContract.BASE_CONTENT_URI,
                    insertData
            );

            sendBroadcast(FootballScoresWidgetProvider.receiveBroadcast(this));

        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage());
        }

    }
}