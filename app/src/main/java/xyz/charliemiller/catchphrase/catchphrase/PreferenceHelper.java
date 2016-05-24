package xyz.charliemiller.catchphrase.catchphrase;

import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by charlie on 5/4/16.
 */
public class PreferenceHelper
{
    private static final String TAG = "PreferenceHelper";

    private static SharedPreferences prefs;
    private static SharedPreferences.Editor prefsWriter;

    private static final String settingRoundTime = "roundTime";
    private static final long defaultRoundTime = 30000; // 3 min in ms.

    private static final String settingNumberOfPointstoWin = "pointsToWin";
    private static final int defaultNumberOfPointsToWin = 7;

    private static final String settingsCategory = "category_";
    private static final String settingNumberOfCategories = "category";

    private static final String termLength = "termSize_";
    private static final String termsPrefix = "terms_";

    private static final String settingsKey = "key";

    private static final String settingsNumberOfSkips = "skips";
    private static final int defaultNumberOfSkips = 2;

    private static ArrayList<String> defaultCategories;
    private static ArrayList<String> defaultTerms;

    public static void setPreferences(SharedPreferences p)
    {
        if(p == null)
            throw new Error("Null Preferences Passed");

        prefs = p;
        prefsWriter = prefs.edit();
        prefsWriter.apply();

        fillDefaults();
    }

    private static void fillDefaults()
    {
        // You get the idea.

        defaultCategories = new ArrayList<>();
        defaultTerms = new ArrayList<>();

        defaultCategories.add("Misc");
        defaultTerms.add("Keanu Reeves");
        defaultTerms.add("Internet Memes");
        defaultTerms.add("Burn the Witch");
        defaultTerms.add("Andrew Jackson");
    }

    /**
     *
     * @return
     */
    public static long getRoundtime()
    {
        return prefs.getLong(settingRoundTime, defaultRoundTime);
    }

    /**
     *
     * @return
     */
    public static int getNumberOfRoundsToWin()
    {
        return prefs.getInt(settingNumberOfPointstoWin, defaultNumberOfPointsToWin);
    }

    /** Set method for user changed round time preference
     *
     * @param roundTime
     */
    public static void setRoundTime(long roundTime)
    {
        prefsWriter.putLong(settingRoundTime, roundTime);
        prefsWriter.commit();
    }

    /**
     *
     * @param numberOfPoints
     */
    public static void setNumberOfPointsToWin(int numberOfPoints)
    {
        prefsWriter.putInt(settingNumberOfPointstoWin, numberOfPoints);
        prefsWriter.commit();
    }

    /**
     *
     * @return
     */
    public static ArrayList<String> getCategories()
    {
        ArrayList<String> toReturn = new ArrayList<String>();

        Set<String> tempSet = prefs.getStringSet(settingsCategory, null);

        if(tempSet == null)
        {
            toReturn.addAll(defaultCategories);
        }
        else
        {
            toReturn.addAll(tempSet);
        }

        return toReturn;
    }

    public static int getNumberOfSkips()
    {
        return prefs.getInt(settingsNumberOfSkips, defaultNumberOfSkips);
    }

    /**
     *
     * @param category
     * @return
     */
    public static ArrayList<String> getTerms(String category)
    {
        ArrayList<String> toReturn = new ArrayList<String>();

        Set<String> tempTerms = prefs.getStringSet(termsPrefix + category, null);

        if(tempTerms != null)
        {
            toReturn.addAll(tempTerms);
        }
        else
        {
            toReturn.addAll(defaultTerms);
        }

        return toReturn;
    }

    public static String getKey()
    {
        return prefs.getString(settingsKey, null);
    }

    public static void setKey(String key)
    {
        Log.d(TAG, "Key: " + key);
        prefsWriter.putString(settingsKey, key);
        prefsWriter.commit();
    }
}
