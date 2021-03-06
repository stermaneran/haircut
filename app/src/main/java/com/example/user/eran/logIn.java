package com.example.user.eran;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * logIn to the application
 */
public class logIn extends AppCompatActivity {

    private TextInputLayout emailField;
    private TextInputLayout passwordField;
    private View btnLogin;
    private View btnSignUp;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;

    private FirebaseAnalytics mFirebaseAnalytics;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.log_in);
        emailField = (TextInputLayout) findViewById(R.id.email_field);
        passwordField = (TextInputLayout) findViewById(R.id.password_field);
        btnLogin = findViewById(R.id.login);
        btnSignUp = findViewById(R.id.sign_up);

        //Get Firebase auth instance
        mAuth = FirebaseAuth.getInstance();

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        //checks if the user is already registered in the system
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Intent intent = new Intent(logIn.this, basePage.class);
                    startActivity(intent);
                    finish();

                } else {
                    // User is signed out
                }
            }
        };

        //start registrationPage activity
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(logIn.this, registrationPage.class);
                startActivity(intent);
            }
        });

        //validates the input and passes valid users to authenticateUser
        //and returns the correct message if not
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Utils.hasText(emailField)) {
                    Utils.showToast(logIn.this, "Please input your email");
                } else if (!Utils.hasText(passwordField)) {
                    Utils.showToast(logIn.this, "Please input your password");
                } else {
                    //requesting Firebase server
                    showProcessDialog();
                    authenticateUser(Utils.getText(emailField), Utils.getText(passwordField));

                }
            }
        });
    }

    /**
     * authenticate User with Firebase
     *
     * @param email
     * @param password start basePage activity upon success
     */
    private void authenticateUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(logIn.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // When login failed
                        if (!task.isSuccessful()) {
                            Utils.showToast(logIn.this, "Login error!");
                            progressDialog.dismiss();
                        } else {
                            //When login successful, redirect user to main activity
                            Intent intent = new Intent(logIn.this, basePage.class);
                            startActivity(intent);
                            progressDialog.dismiss();
                            finish();
                        }
                    }
                });
    }

    /**
     * Process Dialog for Firebase logIn
     */
    private void showProcessDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Login");
        progressDialog.setMessage("Logging in Firebase server...");
        progressDialog.show();
    }

    /**
     * checks if the user has not loged out
     */
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    /**
     * saves current user's authentication state
     */
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

}
