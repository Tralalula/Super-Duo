package it.jaschke.alexandria;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class Utilities {
    public static final int ISBN_10_LENGTH = 10;
    public static final int ISBN_13_LENGTH = 13;
    public static final String ISBN_13_FIRST_THREE_NUMBERS = "978";
    public static final String EAN_KEY = "EAN";
    public static final String EAN_CONTENT = "EAN_CONTENT";

    private static Utilities mInstance = new Utilities();
    private static Context mContext;
    private ConnectivityManager mConnectivityManager;
    private boolean mConnected = false;

    public static Utilities getInstance(Context context) {
        mContext = context.getApplicationContext();
        return mInstance;
    }

    public boolean isOnline() {
        try {
            mConnectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = mConnectivityManager.getActiveNetworkInfo();
            mConnected = networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
            return mConnected;

        } catch (Exception e) {
            Log.e(getResString(mContext, R.string.exception_check_connectivity) + " ", e.getMessage());
        }
        return mConnected;
    }

    public static String getResString(Context context, int id) {
        return context.getResources().getString(id);
    }
}
