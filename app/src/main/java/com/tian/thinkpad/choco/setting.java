package com.tian.thinkpad.choco;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class setting extends Fragment {
    private TextView textView;
    private Button button;
    private ListView setting_lv;
    private String[] str={"设置1","设置2","设置3"};
    private TextView userName;
    private TextView userState;
    private TextView tv6;
    private CardView cv6;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_setting,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        userName=getActivity().findViewById(R.id.setting_tv1);

        final SharedPreferences sharedPreferences=getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        String userName2=sharedPreferences.getString("userName","--未登录--");
        userName.setText(userName2);
        tv6=getActivity().findViewById(R.id.setting_tv6);
        cv6=getActivity().findViewById(R.id.cardview6);
        if(sharedPreferences.contains("userName")){
            tv6.setText("注销当前账户");
            cv6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.clear();
                    editor.commit();
                    tv6.setText("未登录");
                    Toast.makeText(getActivity(),"注销账户",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getActivity(),Main2Activity.class));
                }
            });
        }else{
            tv6.setText("未登录");
        }


    }
}
