package dblasena.cis.finalproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class HomePageActivity extends AppCompatActivity {

    Button buttonLogOut;
    TextView textviewUser;
    String Email;
    TextView textviewName;

    Button btnHit;
    TextView txtJson;
    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        buttonLogOut = (Button) findViewById(R.id.buttonLogOut);
        textviewUser = (TextView) findViewById(R.id.textviewUser);
        textviewName = (TextView) findViewById(R.id.editTextName);


        Bundle extras = getIntent().getExtras();
        Email = extras.getString("Email");
        textviewUser.setText("You are now logged as " + Email);

        btnHit = (Button) findViewById(R.id.btnhit);
        txtJson = (TextView) findViewById(R.id.txtjson);


        buttonLogOut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("CIS3334", "normal Log Out ");
                finish();




            }
        });
        btnHit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = textviewName.getText().toString();
                name = name.replace(" ", "");

                 new JsonTask().execute("https://na.api.riotgames.com/api/lol/NA/v1.4/summoner/by-name/" + name +"?api_key=RGAPI-e939ea0b-87b0-4e55-8103-f716a44fb6c5");
                //new JsonTask().execute();
            }
        });
    }
    private class JsonTask extends AsyncTask<String, String, String> {
    //private class JsonTask extends AsyncTask<Void, Void, JSONObject> {

        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(HomePageActivity.this);

            pd.setCancelable(false);
            pd.show();
        }

        protected String doInBackground(String... params) {
           // protected JSONObject doInBackground(Void... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

                String url2 = "https://na.api.riotgames.com/api/lol/NA/v1.3/stats/by-summoner/36583485/summary?season=SEASON3&api_key=RGAPI-e939ea0b-87b0-4e55-8103-f716a44fb6c5";

            //ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

            try {
                URL url = new URL(params[0]);
                //URL url = new URL(url2);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuilder buffer = new StringBuilder();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line).append("\n");


                }

               return buffer.toString();
                //return new JSONObject(buffer.toString());


            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
           // protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);
            if (pd.isShowing()){
                pd.dismiss();
            }
            result = result.replace("{","");
            result = result.replace("}","");
            txtJson.setText(result);
           /* String a = null;
            try {
                a = result.getString("summonerId");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            txtJson.setText(a);
            **/


            textviewName.setText("");
        }


    }








    public void finish() {
        Intent intent = new Intent();

        setResult(RESULT_OK, intent);

        super.finish();
    }
}
