package com.expense_tracker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.customtabs.*;
import android.support.v4.app.RemoteActionCompatParcelizer;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class RegPage extends AppCompatActivity {
    private EditText mEmail;
    private EditText mpass;
    private EditText mpass2;
    Button btnregister;

    private ProgressDialog mdialogue;

    //FireBase

    private FirebaseAuth mAuth;




    private void registration() {
        mEmail = findViewById(R.id.email_reg);
        mpass = findViewById(R.id.password_reg);
        mpass2 = findViewById(R.id.repassword_reg);
        btnregister = findViewById(R.id.btn_reg);

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = mEmail.getText().toString().trim();
                String Pass = mpass.getText().toString().trim();
                String re_pass = mpass2.getText().toString().trim();


                if(TextUtils.isEmpty(Email))
                {
                    mEmail.setError("Email Required");
                    return;
                }

                if(TextUtils.isEmpty(Pass))
                {
                    mpass.setError("Password Required");
                    return;
                }

                if(TextUtils.isEmpty(re_pass))
                {
                    mpass2.setError("Re-Enter Password");
                    return;
                }

                if (!Pass.equals(re_pass)){
                    mpass2.setError("Password should match");
                    return;
                }

                mdialogue.setMessage("Processing..");
                mdialogue.show();

                mAuth.createUserWithEmailAndPassword(Email,Pass).addOnCompleteListener(new OnCompleteListener<com.google.firebase.auth.AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<com.google.firebase.auth.AuthResult> task) {
                       if (task.isSuccessful()){

                           mdialogue.dismiss();
                           Toast.makeText(getApplicationContext(),"Registration Complete", Toast.LENGTH_SHORT).show();
                           startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                       }else{
                           mdialogue.dismiss();
                           Toast.makeText(getApplicationContext(),"Registration Failed",Toast.LENGTH_SHORT).show();
                       }
                    }
                });

            }
        });



    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_page);
        mAuth=FirebaseAuth.getInstance();

        mdialogue=new ProgressDialog(this);


        registration();

        TextView txt = findViewById(R.id.login_redirect);
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(RegPage.this,MainActivity.class);
                startActivity(intent2);
            }
        });

        Button reset = (Button) findViewById(R.id.btn_reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEmail.setText("");
                mpass.setText("");
                mpass2.setText("");
            }
        });



    }
}