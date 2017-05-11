package dblasena.cis.finalproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * Default activity that is shown.
 * Has the user either log in or create an acount. These two features will interact with firebase and if the user selects the create account function,
 * they will be directed to the createAccountActivity. The methods in this are the basic firebase required methods for signing in and a return method for when the user logs out and returns the intent.
 */
public class MainActivity extends AppCompatActivity {

    private static final int CIS3334_REQUEST_CODE = 1001;
    String Email;

    EditText editTextEmail;
    EditText editTextPassword;

    TextView textViewStatus;

    Button buttonLogin;
    Button buttonCreateAccount;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    /**
     * OnCreate will create the create the connections to the xml file for this activity. This will also set up the firebase connection and create the button handlers.
     * It will also set up the neccessary intents in the button handlers.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);

        textViewStatus = (TextView) findViewById(R.id.textViewStatus);

        buttonCreateAccount = (Button) findViewById(R.id.buttonCreateAccount);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);


        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("CIS3334", "onAuthStateChanged:signed_in:" + user.getUid());


                } else {
                    // User is signed out
                    Log.d("CIS3334", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };


        buttonLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("CIS3334", "normal login ");
                signIn(editTextEmail.getText().toString(), editTextPassword.getText().toString());


            }
        });


        buttonCreateAccount.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("CIS3334", "normal login ");
                Intent createaccountIntent = new Intent(MainActivity.this, CreateAccountActivity.class);
                startActivity(createaccountIntent);



            }
        });
    }

    /**
     * Adds an authentication listener for the firebase connection. This is a standard method for all of firebase.
     */
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    /**
     * removes the authentication state listener from the firebase listener. Standard firebase method.
     */
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    /**
     * This method will use the signinwithEmailandPassword firebase built in method to check the user name and password that the user has entered,
     * If it is correct then they will be signed in and taken to the HomePageActivity. If it is not then they will be given an error message instead
     * @param email String that is obtained from the button listeners
     * @param password String that is obtained from the button listener
     */
    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (!task.isSuccessful()) {
                            editTextEmail.setText("");
                            editTextPassword.setText("");
                            textViewStatus.setText("Error: incorrect username or password!");

                        } else {
                            Intent signInIntent = new Intent(MainActivity.this, HomePageActivity.class);
                            Email = editTextEmail.getText().toString();
                            signInIntent.putExtra("Email", Email);
                            startActivity(signInIntent);
                            editTextEmail.setText("");
                            editTextPassword.setText("");
                        }
                    }
                });


    }

    /**
     * When this activity is returned to it will be when the user has logged out.
     * It will display a toast message saying that they are now logged out.
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

            Toast.makeText(getApplicationContext(), "Message to display", Toast.LENGTH_LONG)
                    .show();

    }
}




