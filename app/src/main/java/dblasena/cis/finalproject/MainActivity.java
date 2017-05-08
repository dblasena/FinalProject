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

               // createAccount(editTextEmail.getText().toString(), editTextPassword.getText().toString());

                //signIn(editTextEmail.getText().toString(), editTextPassword.getText().toString());


            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    /**
    private void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if (!task.isSuccessful()) {
                            textViewStatus.setText("Error: Unable to create account!");

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
     **/

    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (!task.isSuccessful()) {
                            textViewStatus.setText("Error: Unable to login at this time!");

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CIS3334_REQUEST_CODE) {
            Toast.makeText(getApplicationContext(), "Message to display", Toast.LENGTH_LONG)
                    .show();
        }
    }
}




