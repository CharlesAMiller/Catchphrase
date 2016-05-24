package xyz.charliemiller.catchphrase.catchphrase.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.preference.Preference;

import android.provider.Settings.Secure;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import xyz.charliemiller.catchphrase.catchphrase.PreferenceHelper;

/**
 * Created by Charlie on 5/24/2016.
 */
public class GetKeyTask extends AsyncTask<Context, Void, String>
{

    private static final String dataURL = "http://charliemiller.xyz/catchphrase/key.php";

    @Override
    protected String doInBackground(Context... params)
    {
        String toReturn = null;

        String dataUrlParameters = "mac=";

        String id = Secure.getString(params[0].getContentResolver(),
                Secure.ANDROID_ID);

        dataUrlParameters += id;

        URL url;
        HttpURLConnection connection = null;

        try
        {
            /** Open Connection */
            url = new URL(dataURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length","" + Integer.toString(dataUrlParameters.getBytes().length));
            connection.setRequestProperty("Content-Language", "en-US");
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            /** Make HTTP Post Request */
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(dataUrlParameters);
            wr.flush();
            wr.close();

            /** Await Response */
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while ((line = rd.readLine()) != null)
            {
                response.append(line);
                response.append('\r');
            }
            rd.close();

            String responseStr = response.toString();
            PreferenceHelper.setKey(responseStr);

        } catch (Exception e) { e.printStackTrace(); }

        finally
        {
            if (connection != null)
            {
                connection.disconnect();
            }
        }

        return toReturn;
    }

    @Override
    protected void onPostExecute(String s)
    {
        super.onPostExecute(s);
        PreferenceHelper.setKey(s);
    }
}
