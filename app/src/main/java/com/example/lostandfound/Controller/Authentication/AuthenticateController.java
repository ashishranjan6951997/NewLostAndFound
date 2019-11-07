package com.example.lostandfound.Controller.Authentication;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AuthenticateController
{
    FirebaseAuth mAuth;
    Activity activity;
    String email,password;

    public AuthenticateController(Activity activity,String email,String password)
    {
        mAuth = FirebaseAuth.getInstance();
        this.activity = activity;
        this.email = email;
        this.password = password;
    }

    public void login()
    {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful())
                {
                    Toast.makeText(activity, task.getException().toString(), Toast.LENGTH_LONG).show();
                }
                else {
//                    IntentDemo demo = new IntentDemo(activity,destActivity);
//                    demo.transfer();

//                    Intent intent = new Intent(activity,ChooseActivity.this);
//                    activity.startActivity(intent);
//                    activity.finish();
                }
            }
        });

    }

    public void signUp()
    {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(activity, task.getException().toString(), Toast.LENGTH_LONG).show();
                }
                else
                {
//                    saveData(name);
//                    Intent intent = new Intent(activity,ChooseActivity.this);
//                    activity.startActivity(intent);
//                    activity.finish();
                }
            }
        });
    }
}
