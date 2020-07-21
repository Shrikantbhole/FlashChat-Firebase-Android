package com.shrikantBhole.flashchatnewfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {

    // TODO: Add member variables here:
    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;

    // TODO: Add firebase member variables

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailView = (AutoCompleteTextView) findViewById(R.id.login_email);
        mPasswordView = (EditText) findViewById(R.id.login_password);

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.integer.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }

        });

        // TODO: Grab an instance of FirebaseAuth

        mAuth = FirebaseAuth.getInstance();

    }

    // Executed when Sign in button pressed
    public void signInExistingUser(View v)   {
        // TODO: Call attemptLogin() here
        attemptLogin();

    }

    // Executed when Register button pressed
    public void registerNewUser(View v) {
        Intent intent = new Intent(this, com.shrikantBhole.flashchatnewfirebase.RegisterActivity.class);
        finish();
        startActivity(intent);
    }

    // TODO: Complete the attemptLogin() method
    private void attemptLogin() {




        String UserName = mEmailView.getText().toString();
        String Password = mPasswordView.getText().toString();

        Log.d("Flatchat","UserName working: " + TextUtils.isEmpty(UserName));
        if(TextUtils.isEmpty(UserName)||TextUtils.isEmpty(Password)) return;

             Toast.makeText(this,"Login in progess",Toast.LENGTH_LONG).show();


        // TODO: Use FirebaseAuth to sign in with email & password


        mAuth.signInWithEmailAndPassword(UserName,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

            Log.d("Flashchat","signInwithEmailSuccessful()" + task.isSuccessful());

            if(!task.isSuccessful()) {
                Log.d("Flashchat","Task not successful" + task.getException());
                showErrorDialog("Error in signing");

            }else{
                Intent myintent = new Intent(LoginActivity.this, MainChatActivity.class);
                finish();
                startActivity(myintent);
            }


            }




        });



    }

    // TODO: Show error on screen with an alert dialog

    private void showErrorDialog(String message){

        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Oops");
        alert.setCancelable(false);
        alert.setMessage(message);
        alert.setPositiveButton(android.R.string.ok, null);
        alert.show();
    }
}