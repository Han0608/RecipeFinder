package com.tian.thinkpad.choco;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.ButterKnife;


public class login extends Fragment {

    private DBOpenHelper mDBOpenHelper;
    private Button mTvLoginActivityRegister;
    private Button mBtLoginactivityLogin;
    private EditText mEtLoginactivityUsername;
    private EditText mEtLoginactivityPassword;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_login,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ButterKnife.bind(getActivity());
        mDBOpenHelper = new DBOpenHelper(getActivity());

        mBtLoginactivityLogin=getActivity().findViewById(R.id.bt_loginactivity_login);
        mTvLoginActivityRegister=getActivity().findViewById(R.id.tv_loginactivity_register);

        mEtLoginactivityUsername=getActivity().findViewById(R.id.et_loginactivity_username);
        mEtLoginactivityPassword=getActivity().findViewById(R.id.et_loginactivity_password);
      mTvLoginActivityRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), RegisterActivity.class));
            }
        });

        mBtLoginactivityLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mEtLoginactivityUsername.getText().toString().trim();
                String password = mEtLoginactivityPassword.getText().toString().trim();
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password)) {
                    ArrayList<User> data = mDBOpenHelper.getAllData();
                    boolean match = false;
                    for(int i=0;i<data.size();i++) {
                        User user = data.get(i);
                        if (name.equals(user.getName()) && password.equals(user.getPassword())){
                            match = true;
                            break;
                        }else{
                            match = false;
                        }
                    }
                    if (match) {
                        Toast.makeText(getActivity(), "登录成功", Toast.LENGTH_SHORT).show();
                        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString("userName",name);
                        editor.commit();
                        Intent intent = new Intent(getActivity(), Main2Activity.class);
                        startActivity(intent);

                    }else {
                        Toast.makeText(getActivity(), "用户名或密码不正确，请重新输入", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "请输入你的用户名或密码", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
