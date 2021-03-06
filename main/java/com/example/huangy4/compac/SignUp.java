package com.example.huangy4.compac;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // Database stuff:
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("");
    DatabaseReference myPref = database.getReference("");
    DatabaseReference myList = database.getReference("");
    private FirebaseAuth firebaseAuth;
    //
    //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // Widget Declarations
    private EditText mEmail_input;
    private EditText mPassword_input;
    private EditText mVerify_input;
    private Spinner mSecurity_input;
    private EditText mAnswer_input;
    private Spinner mStyle_input;
    private Spinner mGender_input;
    private Spinner mPref_gender_input;


    private Button mSubmit_button;
    //
    //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // Variable Declarations
    private static final String TAG = "Sign_up";
    private Spinner security1, security2;
    //
    //-------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        firebaseAuth = FirebaseAuth.getInstance();

        mEmail_input = findViewById(R.id.Email_input);
        mPassword_input = findViewById(R.id.Password_input);
        mVerify_input = findViewById(R.id.Verify_input);
        mSecurity_input = findViewById(R.id.Security_input);
        mAnswer_input = findViewById(R.id.Answer_input);



        addListenerOnButton();
        addListenerSecurityItemSelection();

        //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        // Buttons:


        mSubmit_button = findViewById(R.id.Submit_button);
        mSubmit_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                userRegister();
            }
        });




        //
        //---------------------------------------------------------
    }


    public void addListenerSecurityItemSelection(){
        security1 = findViewById(R.id.Security_input);
    }

    public void addListenerOnButton(){
        security1 = findViewById(R.id.Security_input);
    }


    private void userRegister()
    {
        final String Email = mEmail_input.getText().toString();
        String Password = mPassword_input.getText().toString();
        String Verify = mVerify_input.getText().toString();
        final String Security = mSecurity_input.getSelectedItem().toString();
        final String Answer = mAnswer_input.getText().toString();


        if(TextUtils.isEmpty(Email))
        {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(Password))
        {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(Verify))
        {
            Toast.makeText(this, "Please enter password verification", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(Security))
        {
            Toast.makeText(this, "Please enter security question", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(Answer))
        {
            Toast.makeText(this, "Please enter answer to security question", Toast.LENGTH_LONG).show();
            return;
        }


        //Toast.makeText(this, Email, Toast.LENGTH_LONG).show();
        if(TextUtils.equals(Password, Verify))
        {
            firebaseAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
            {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task)
                {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(SignUp.this, "Successful Registration", Toast.LENGTH_LONG).show();
                        //message.hide();

                        String user = firebaseAuth.getCurrentUser().getUid();

                        myRef = database.getReference("Users").child(user);
                        myRef.child("Email").setValue(Email);
                        myRef.child("Security").setValue(Security);
                        myRef.child("Answer").setValue(Answer);




                        Intent signedup = new Intent(SignUp.this, Login.class);
                        startActivity(signedup);
                    }
                    if(!task.isSuccessful())
                    {
                        Toast.makeText(SignUp.this, "Failed Registration", Toast.LENGTH_LONG).show();
                        //message.hide();
                        return;
                    }
                }
            });
        }
        else
        {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_LONG).show();
            return;
        }


    }
}
