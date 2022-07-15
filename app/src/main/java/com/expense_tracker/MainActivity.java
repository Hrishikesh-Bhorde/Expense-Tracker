package com.expense_tracker;

import android.app.ProgressDialog;
import android.support.v4.app.RemoteActionCompatParcelizer;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {


    private EditText email, pass;
    private ProgressDialog mdialoge;

    //FireBase

    private FirebaseAuth authuser;


    private void LoginDetails(){
        email = findViewById(R.id.emai_login);
        pass = findViewById(R.id.password_login);
        Button btnLogin = findViewById(R.id.btn_login);



        TextView forgetPass = findViewById(R.id.forgot_pass);
        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Badam Kha ki jara",Toast.LENGTH_SHORT).show();

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = email.getText().toString().trim();
                String Password = pass.getText().toString().trim();

                if(TextUtils.isEmpty(Email)){
                    email.setError("Email Required");
                    return;
                }


                if(TextUtils.isEmpty(Password)){
                    pass.setError("Password Required");
                    return;
                }

                mdialoge.setMessage("Processing..");
                mdialoge.show();

                authuser.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"LOGIN SUCCESSFUL",Toast.LENGTH_SHORT).show();
                            mdialoge.dismiss();
                            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                        }else{
                            mdialoge.dismiss();
                            Toast.makeText(getApplicationContext(),"Login Failed...",Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });





    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        authuser=FirebaseAuth.getInstance();
        mdialoge = new ProgressDialog(this);

//         if (authuser.getCurrentUser()!=null){
//             startActivity(new Intent(getApplicationContext(),HomeActivity.class));
//         }

        LoginDetails();





        TextView regtext =(TextView)findViewById(R.id.signup);
        regtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent RegIntent = new Intent(MainActivity.this,RegPage.class);
                startActivity(RegIntent);
            }
        });


    }

        
}

