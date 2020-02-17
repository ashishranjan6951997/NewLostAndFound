package com.example.lostandfound.View.Authentication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.lostandfound.Controller.Authentication.AuthenticateController;
import com.example.lostandfound.R;
import com.example.lostandfound.View.Authentication.CallBackInterface;

import static com.example.lostandfound.NameClass.signUp;

public class LoginFragment extends Fragment
{
    CallBackInterface callBackInterface;
    View rootView;
    EditText editemail;
    EditText editpass;
    Button btnlogin;
    Button btnSignup;
    TextView btnForgetPassword;
    AuthenticateController controller;
    String emailAddress;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.login_fragment,container,false);
            initUI();
            return rootView;
    }
    public void setCallBackInterface(CallBackInterface callBackInterface)
    {
        this.callBackInterface=callBackInterface;
    }
    private void initUI()
    {
        emailAddress="";
        editemail=rootView.findViewById(R.id.email);
        editpass=rootView.findViewById(R.id.password);
        btnlogin=rootView.findViewById(R.id.login);
        btnSignup=rootView.findViewById(R.id.SignUp);
        btnForgetPassword = rootView.findViewById(R.id.forget_password);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(callBackInterface!=null)
                {
                    callBackInterface.callBackMethod(signUp);
                }
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailText = editemail.getText().toString();
                String passwordText = editpass.getText().toString();



                String cred[] = {emailText,passwordText};

                controller = new AuthenticateController(getActivity(),cred);
                controller.login();
            }
        });

        btnForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                emailAddress = editemail.getText().toString();

                if(emailAddress.equals((""))){
                    Toast.makeText(getActivity(),"Please add email id",Toast.LENGTH_LONG).show();
                }
                else
                {
                    controller = new AuthenticateController(getActivity(),emailAddress);
                    controller.forgetPassword();
                }
            }
        });
    }
}