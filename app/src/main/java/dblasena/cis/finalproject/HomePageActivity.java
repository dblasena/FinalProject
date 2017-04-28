package dblasena.cis.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class HomePageActivity extends AppCompatActivity {

    Button buttonLogOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        buttonLogOut = (Button) findViewById(R.id.buttonLogOut);


        buttonLogOut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("CIS3334", "normal Log Out ");
                finish();



            }
        });
    }
    public void finish() {
        Intent intent = new Intent();

        setResult(RESULT_OK, intent);

        super.finish();
    }
}
