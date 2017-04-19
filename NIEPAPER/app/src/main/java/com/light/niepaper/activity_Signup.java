package com.light.niepaper;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class activity_Signup extends AppCompatActivity {
    protected EditText name;
    protected EditText email;
    protected EditText passwd;
    protected FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__signup);
    }

    public void onRegister(View view){
        name = (EditText) findViewById(R.id.et_name);
        email = (EditText) findViewById(R.id.et_email);
        passwd = (EditText) findViewById(R.id.et_passwd);

        firebaseAuth = FirebaseAuth.getInstance();

        String email_ = email.getText().toString();
        String passwd_ = passwd.getText().toString();

        email_ =email_.trim();
        passwd_ = passwd_.trim();

        if(email_.isEmpty() || passwd_.isEmpty()){
            AlertDialog.Builder builder = new AlertDialog.Builder(activity_Signup.this);
            builder.setMessage(R.string.signup_error)
                    .setTitle(R.string.login_error_title)
                    .setPositiveButton(android.R.string.ok, null);
            AlertDialog dialog = builder.create();
            dialog.show();
        }else{
            firebaseAuth.createUserWithEmailAndPassword(email_,passwd_)
                    .addOnCompleteListener(activity_Signup.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(activity_Signup.this, profileActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(activity_Signup.this);
                                builder.setMessage(task.getException().getMessage())
                                        .setTitle(R.string.login_error_title)
                                        .setPositiveButton(android.R.string.ok, null);
                                AlertDialog dialog = builder.create();
                                dialog.show();

                            }
                        }
                    });

        }
    }
}
