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




import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * This class will run an async task to accesw the RiotGames api and return will the requested information. It will take the information parse the JSON and display it to the user.
 * To get this to run the user must enter a valid summoner name of the player and click the searc button. This will beiggin the process of retrieving parsing and displaying the data.
 */
public class HomePageActivity extends AppCompatActivity {

    Button buttonLogOut;
    TextView textviewUser;
    String Email;
    TextView textviewName;

    Button btnHit;
    TextView txtJson;
    ProgressDialog pd;

    /**
     * This method creates the connections to the xml file along with creating all of the button handlers.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        buttonLogOut = (Button) findViewById(R.id.buttonLogOut);
        textviewUser = (TextView) findViewById(R.id.textviewUser);
        textviewName = (TextView) findViewById(R.id.editTextName);


        Bundle extras = getIntent().getExtras();
        Email = extras.getString("Email");
        textviewUser.setText(R.string.LoggedInAs + Email);

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


    /**
     * This class is the async task that is ran to retrieve the information on the selected player and return it to be displayed.
     * In this method the commented out sections are used to run this to return the information as a JSONObject rather than a string. I am not sure which one is better to use.
     * This make a call to the api and return the information.
     */
    private class JsonTask extends AsyncTask<String, String, String> {
    //private class JsonTask extends AsyncTask<Void, Void, JSONObject> {

        /**
         * When the task is first started it will create a progress dialog that will keep track of how lojng it takes for this to run.
         */
        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(HomePageActivity.this);

            pd.setCancelable(false);
            pd.show();
        }

        /**
         * This method is where most of the work is done. It will create the connection with the web and retrieve the information from the url.
         * It will then read each line of the JSON and for a string out of it that is able to be displayed.
         * The connection to the internet and the reader are then closed when completed.
         * @param params
         * @return This will either be a string of the JSON or if one of the exceptions are thrown then there will not be a returned value.
         */
        protected String doInBackground(String... params) {
           // protected JSONObject doInBackground(Void... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

               // String url2 = "https://na.api.riotgames.com/api/lol/NA/v1.3/stats/by-summoner/36583485/summary?season=SEASON3&api_key=RGAPI-e939ea0b-87b0-4e55-8103-f716a44fb6c5";

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

        /**
         * This method will be ran when there is a returned value from the doinBackground.
         * It will remove the progress display and set a text field with the information on the searched name.
         * @param result This is the string information that is sent from the doinBackground method
         */
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

    /**
     * This method is called when the user wants to log out. They will log out of the Firebase and be returned to the mainActivity with login and create account options.
     */
    public void finish() {
        Intent intent = new Intent();

        setResult(RESULT_OK, intent);

        super.finish();
    }
}
