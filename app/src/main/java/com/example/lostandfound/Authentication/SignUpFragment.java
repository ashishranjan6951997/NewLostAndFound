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

public class SignUpFragment extends Fragment {
    View rootView;
    EditText editemail;
    EditText editpass;
    EditText editConfirmPass;
    Button btnlogin;
    Button btnSignup;
    CallBackInterface callBackInterface;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView=inflater.inflate(R.id.sign_up,container,false);
        initUi();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void initUi() {
        editemail=rootView.findViewById(R.id.email);
        editpass=rootView.findViewById(R.id.password);
        editConfirmPass=rootView.findViewById(R.id.ConfirmPassword);

        btnlogin=rootView.findViewById(R.id.login);
        btnSignup=rootView.findViewById(R.id.SignUp);
        btnlogin.setOnClickListener(new View.OnClickListener() {
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
