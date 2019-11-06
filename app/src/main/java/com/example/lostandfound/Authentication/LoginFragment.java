package com.example.lostandfound.Authentication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.lostandfound.R;

public class LoginFragment extends Fragment {
    CallBackInterface callBackInterface;
    View rootView;
    EditText editemail;
    EditText editpass;
    Button btnlogin;
    Button btnSignup;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView=inflater.inflate(R.id.login_fragment,container,false);
            initUI();
            return rootView;
    }
    public void setCallBackInterface(CallBackInterface callBackInterface)
    {
        this.callBackInterface=callBackInterface;
    }
    private void initUI() {
        editemail=rootView.findViewById(R.id.email);
        editpass=rootView.findViewById(R.id.password);
        btnlogin=rootView.findViewById(R.id.login);
        btnSignup=rootView.findViewById(R.id.SignUp);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(callBackInterface!=null)
                {
                    callBackInterface.callBackMethod();
                }
            }
        });
    }
}
