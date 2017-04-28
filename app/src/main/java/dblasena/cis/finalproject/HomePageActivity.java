package dblasena.cis.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;

public class HomePageActivity extends AppCompatActivity {

    Button buttonLogOut;
    TextView textviewUser;
    String Email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        buttonLogOut = (Button) findViewById(R.id.buttonLogOut);
        textviewUser = (TextView) findViewById(R.id.textviewUser);


        Bundle extras = getIntent().getExtras();
        Email = extras.getString("Email");
        textviewUser.setText("You are now logged as " + Email);


        buttonLogOut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("CIS3334", "normal Log Out ");
                finish();



            }
        });
    }



    public static JSONObject getJSONObjectFromURL(String urlString) throws IOException, JSONException {

        HttpURLConnection urlConnection = null;

        URL url = new URL("https://na1.api.riotgames.com/lol/platform/v3/champions?freeToPlay=zac&api_key=RGAPI-e939ea0b-87b0-4e55-8103-f716a44fb6c5");

        urlConnection = (HttpURLConnection) url.openConnection();

        urlConnection.setRequestMethod("GET");
        //urlConnection.setReadTimeout(10000 /* milliseconds */);
        //urlConnection.setConnectTimeout(15000 /* milliseconds */);

        urlConnection.setDoOutput(true);

        urlConnection.connect();

        BufferedReader br=new BufferedReader(new InputStreamReader(url.openStream()));

        char[] buffer = new char[1024];

        String jsonString = new String();

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line+"\n");
        }
        br.close();

        jsonString = sb.toString();

        System.out.println("JSON: " + jsonString);

        return new JSONObject(jsonString);
    }


    public void finish() {
        Intent intent = new Intent();

        setResult(RESULT_OK, intent);

        super.finish();
    }
}
