package com.unimib.eden.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * A utility class for managing shared preferences in the application.
 * This class provides methods for setting and retrieving various data types from shared preferences,
 * such as authentication tokens, user IDs, and last update timestamps.
 */
public class SharedPreferencesProvider {
    private final Application mApplication;
    private final SharedPreferences sharedPref;

    /**
     * Constructs a new SharedPreferencesProvider with the specified application context.
     *
     * @param application The application context
     */
    public SharedPreferencesProvider(Application application)
    {
        this.mApplication = application;
        sharedPref = mApplication.getSharedPreferences(Constants.SHARED_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
    }

    /**
     * Sets the authentication token in shared preferences.
     *
     * @param token The authentication token to set
     */
    public void setAuthenticationToken(String token)
    {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(Constants.AUTHENTICATION_TOKEN, token);
        editor.apply();
    }

    /**
     * Sets the user ID in shared preferences.
     *
     * @param userId The user ID to set
     */
    public void setUserId(String userId)
    {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(Constants.USER_ID, userId);
        editor.apply();
    }

    /**
     * Deletes all data from shared preferences.
     */
    public void deleteAll()
    {
        sharedPref.edit().clear().apply();
    }

    /**
     * Sets the last update timestamp in shared preferences.
     *
     * @param lastUpdate The timestamp of the last update
     */
    public void setLastUpdate(long lastUpdate)
    {
        SharedPreferences sharedPref = mApplication.getSharedPreferences(Constants.SHARED_PREFERENCES_FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(Constants.LAST_UPDATE, lastUpdate);
        editor.apply();
    }

    /**
     * Retrieves the last update timestamp from shared preferences.
     *
     * @return The last update timestamp, or 0 if not found
     */
    public long getLastUpdate()
    {
        SharedPreferences sharedPref = mApplication.getSharedPreferences(Constants.SHARED_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);

        return sharedPref.getLong(Constants.LAST_UPDATE, 0);
    }
}
