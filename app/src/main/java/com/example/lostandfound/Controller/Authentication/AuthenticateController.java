package com.example.lostandfound.Controller.Authentication;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AuthenticateController {
    FirebaseAuth mAuth;
    Activity activity;
    String email, password, confirmPassword;

    public AuthenticateController(Activity activity, String[] cred) {
        mAuth = FirebaseAuth.getInstance();
        this.activity = activity;
        this.email = cred[0];
        this.password = cred[1];
        if (cred.length == 3) {
            this.confirmPassword = cred[2];
        }
    }

    public void login() {
        if (!email.isEmpty() || !password.isEmpty()) {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(activity, task.getException().toString(), Toast.LENGTH_LONG).show();
                    } else {
                        if (mAuth.getCurrentUser().isEmailVerified()) {
                            Toast.makeText(activity, "Your email is verified", Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(activity, "Please verify your email", Toast.LENGTH_LONG).show();
                        }
//                    IntentDemo demo = new IntentDemo(activity,destActivity);
//                    demo.transfer();

//                    Intent intent = new Intent(activity,ChooseActivity.this);
//                    activity.startActivity(intent);
//                    activity.finish();

//                        Toast.makeText(activity, "Log in Successful", Toast.LENGTH_LONG).show();
                    }
                }
            });
        } else {

            Toast.makeText(activity, "Email or password not present in log in", Toast.LENGTH_LONG).show();
        }
    }

    public void signUp() {
        if (!email.isEmpty() || !password.isEmpty()) {
            if (!password.equals(confirmPassword)) {
                Toast.makeText(activity, "Password and Confirm Password are not same", Toast.LENGTH_LONG).show();
            } else {
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(activity, task.getException().toString(), Toast.LENGTH_LONG).show();
                        } else {
//                    saveData(name);
//                    Intent intent = new Intent(activity,ChooseActivity.this);
//                    activity.startActivity(intent);
//                    activity.finish();

                            sendVerification();
                            Toast.makeText(activity, "Sign Up Successful", Toast.LENGTH_LONG).show();
                            //Intent intent=new Intent()
                        }
                    }
                });
            }
        } else {
            Toast.makeText(activity, "Email or password not present in sign up", Toast.LENGTH_LONG).show();
        }
    }

    private void sendVerification() {
        Log.v("EMAIL VERIFICATION", "Sending verification");
        mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(activity, "Registered successfully. Please Check your email", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(activity, String.valueOf(task.getException()), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}