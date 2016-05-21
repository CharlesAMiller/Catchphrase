package xyz.charliemiller.catchphrase.catchphrase;

import android.content.SharedPreferences;

import java.util.ArrayList;

/**
 * Created by charlie on 5/4/16.
 */
public class PreferenceHelper
{
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

        int numberOfCategories = prefs.getInt(settingNumberOfCategories, 0);

        for(int i = 0; i < numberOfCategories; i++)
        {
            String curCategory = prefs.getString(settingsCategory, null);
            if(curCategory != null)
            {
                toReturn.add(curCategory);
            }
            else
            {
                throw new Error("Category saved to Preferences as NULL"); // Should be try/catch?
            }
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

        int numberOfTerms = prefs.getInt(termLength + category, 0);

        for(int i = 0; i < numberOfTerms; i++)
        {
            String tempKey = termsPrefix + category + i;
            String curTerm = prefs.getString(tempKey, null);

            if(curTerm != null)
            {
                toReturn.add(curTerm);
            }
            else
            {
                throw new Error("Term saved to Preferences as NULL");
            }
        }

        return toReturn;
    }

}
