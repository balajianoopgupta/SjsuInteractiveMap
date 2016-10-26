package balajianoopgupta.projects.com.sjsuinteractivemap;


//import com.squareup.*;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by rahilvora on 10/25/16.
 */
public class GetLocation extends AsyncTask<String, Void, String> {
    String key = "AIzaSyB8kqDkrqZ3ScAi7s6a-fT_Dw9OKx6Zqdw";
    String urlBase = "http://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins=";
    @Override
    protected String doInBackground(String... params) {
        String lato = params[0];
        String lngo = params[1];
        String latd = params[2];
        String lngd = params[3];

        urlBase = urlBase+lato+","+lngo+"&destinations="+latd+","+lngd+"&mode=walking";
        HttpURLConnection mUrlConnection = null;
        StringBuilder mJsonResults = new StringBuilder();
//        OkHttpClient client = new OkHttpClient();
//
//        Request request = new Request.Builder()
//                .url(urlBase)
//                .build();
//        try{
//            Response response = client.newCall(request).execute();
//            Log.i("output","sdf");
//        }
//        catch (IOException e){
//            Log.i("Exception",e.toString());
//        }
        try{
            URL url = new URL(urlBase);
            mUrlConnection = (HttpURLConnection) url.openConnection();
            mUrlConnection.setRequestMethod("GET");
            mUrlConnection.connect();
            InputStream in = new BufferedInputStream(mUrlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = reader.readLine()) != null) {
                mJsonResults.append(line);
            }
        }
        catch (MalformedURLException e){

        }
        catch (IOException e){

        }
        Log.i("output",mJsonResults.toString());
        return mJsonResults.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
