package com.tian.thinkpad.choco;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.concurrent.CopyOnWriteArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link sort.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link sort#newInstance} factory method to
 * create an instance of this fragment.
 */
public class sort extends Fragment {
    private TextView textView;
    private Button button;
    private RecyclerView sort_rv;
    private ListView lv_sort;
    private MyCardAdatper myCardAdatper;
    private StaggeredGridLayoutManager manager;

    private WebViewData model;
    private CopyOnWriteArrayList<WebViewData> list_lv= new CopyOnWriteArrayList<WebViewData>();
    private CopyOnWriteArrayList<String> dataList2= new CopyOnWriteArrayList<>();
    private Button bt;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_sort,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sort_rv=getActivity().findViewById(R.id.sort_rv);
        bt=getActivity().findViewById(R.id.searchBt);
        initView();
        initListener();
       bt.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               EditText searchContent=getActivity().findViewById(R.id.searchContent);
               String str=searchContent.getText().toString();
               initView(str);
               initListener(str);
           }
       });
    }


    private void initView() {
        /*for (int i = 0; i < 10; i++) {
            dataList.add("今日分享 | " + i);
        }*/
        SQLdm s = new SQLdm();
        SQLiteDatabase db = s.openDatabase(getContext());
        Cursor cursor = db.rawQuery("select * from webView",null);
        while(cursor.moveToNext()){
            model=new WebViewData();
            model.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            model.setPhoto(cursor.getString(cursor.getColumnIndex("ballball")));
            list_lv.add(model);
        }
        cursor.close();
        manager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        sort_rv.setLayoutManager(manager);
        myCardAdatper = new MyCardAdatper(getActivity(), list_lv);
        sort_rv.setAdapter(myCardAdatper);
    }
    private void initView(String newText) {
        /*for (int i = 0; i < 10; i++) {
            dataList.add("今日分享 | " + i);
        }*/
        SQLdm s = new SQLdm();
        SQLiteDatabase db = s.openDatabase(getContext());
        Cursor cursor = db.rawQuery("select * from webView where title like '%"+newText+"%'",null);
        while(cursor.moveToNext()){
            model=new WebViewData();
            model.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            model.setPhoto(cursor.getString(cursor.getColumnIndex("ballball")));
            list_lv=new CopyOnWriteArrayList<>();
            list_lv.add(model);
        }
        cursor.close();
        manager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        sort_rv.setLayoutManager(manager);
        myCardAdatper = new MyCardAdatper(getActivity(), list_lv);
        sort_rv.setAdapter(myCardAdatper);
    }

    //show webView
    private void initListener() {
        myCardAdatper.setOnItemClickListener(new MyCardAdatper.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                // Toast.makeText(.this, dataList.get(position), Toast.LENGTH_LONG).show();
                //Toast.makeText(getActivity(),dataList.get(position),Toast.LENGTH_LONG).show();
                SQLdm s = new SQLdm();
                SQLiteDatabase db = s.openDatabase(getContext());
                Cursor cursor = db.rawQuery("select * from webView",null);
                while(cursor.moveToNext()){
                    String link = cursor.getString(cursor.getColumnIndex("link"));
                    dataList2.add(link);
                }
                Intent i=new Intent(getActivity(),webViewActivity.class);
                i.putExtra("a",dataList2.get(position));
                startActivity(i);
            }
        });
    }
    private void initListener(final String newText) {
        myCardAdatper.setOnItemClickListener(new MyCardAdatper.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                // Toast.makeText(.this, dataList.get(position), Toast.LENGTH_LONG).show();
                //Toast.makeText(getActivity(),dataList.get(position),Toast.LENGTH_LONG).show();
                SQLdm s = new SQLdm();
                SQLiteDatabase db = s.openDatabase(getContext());
                Cursor cursor = db.rawQuery("select * from webView where title like '%"+newText+"%'",null);
                while(cursor.moveToNext()){
                    String link = cursor.getString(cursor.getColumnIndex("link"));
                    dataList2=new CopyOnWriteArrayList<>();
                    dataList2.add(link);
                }
                Intent i=new Intent(getActivity(),webViewActivity.class);
                i.putExtra("a",dataList2.get(position));
                startActivity(i);
            }
        });
    }
}
