package dblasena.cis.finalproject;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

/**
 * This activity will let the user create an account if they did not previously have one. It will use friebase to add a new username and password combination to the database.
 * When the account is created the user will then be logged in. if the user no lnger wishes to create an account they can use the back button to go back to the start up page.
 */

public class CreateAccountActivity extends AppCompatActivity {

    EditText edittextCreateEmail;
    EditText editTextCreatePassword;
    EditText editTextCreatePassword2;

    TextView textviewStatus;

    private FirebaseAuth mAuth;


    Button buttonCreateAccount;
    Button buttonBack;

    /**
     * This will establish connections with the xml file along with creating a connection with Firebase database..
     * This method will also set up the button Listeners.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        edittextCreateEmail = (EditText) findViewById(R.id.editTextCreateEmail);
        editTextCreatePassword = (EditText) findViewById(R.id.editTextCreatePassword);
        editTextCreatePassword2 = (EditText) findViewById(R.id.editTextCreatePassword2);

        textviewStatus = (TextView) findViewById(R.id.textViewStatus);

        buttonCreateAccount = (Button) findViewById(R.id.buttonCreateAccount);
        buttonBack = (Button) findViewById(R.id.buttonback);



        mAuth = FirebaseAuth.getInstance();
        FirebaseAuth.AuthStateListener mAuthListener = new FirebaseAuth.AuthStateListener() {
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
                //
            }
        };


        buttonCreateAccount.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            public void onClick(View v) {
                Log.d("CIS3334", "normal login ");

                if(Objects.equals(editTextCreatePassword.toString(), editTextCreatePassword2.toString()))

                createAccount(edittextCreateEmail.getText().toString(), editTextCreatePassword.getText().toString());

                else{
                    textviewStatus.setText(R.string.PasswordMatch);
                    editTextCreatePassword.setText("");
                    editTextCreatePassword2.setText("");

                }


            }
        });
        buttonBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();


            }
        });
    }


    /**
     * This method will use the Firebase built in method of createUserWithEmailandPassword. It will take the new username and password that are passed in from the button handler.
     * If the account is created the user will then be able to log in on future uses. Also, they will be directed to the HomePageActivity and fully logged in.
     * @param email
     * @param password
     */
    private void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if (!task.isSuccessful()) {
                            textviewStatus.setText(R.string.LogError);


                        } else {
                            Intent signInIntent = new Intent(CreateAccountActivity.this, HomePageActivity.class);
                           String Email = edittextCreateEmail.getText().toString();
                            signInIntent.putExtra("Email", Email);
                            startActivity(signInIntent);
                            edittextCreateEmail.setText("");
                            editTextCreatePassword.setText("");
                        }


                    }
                });
    }

    /**
     * This method is called when the user wants to log out. It will end the connection with firebase and return the user to Man Activity where they can log in again.
     */


    public void finish() {
        Intent intent = new Intent();

        setResult(RESULT_OK, intent);

        super.finish();
    }
}
