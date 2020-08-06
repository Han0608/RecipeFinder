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
import android.widget.ImageView;
import android.widget.ViewFlipper;

import java.util.concurrent.CopyOnWriteArrayList;


public class index extends Fragment {

    RecyclerView mRecyclerView;
    private CopyOnWriteArrayList<WebViewData> dataList = new CopyOnWriteArrayList<WebViewData>();
    private CopyOnWriteArrayList<String> dataList1= new CopyOnWriteArrayList<>();

    private MyCardAdatper myCardAdatper;
    private StaggeredGridLayoutManager manager;

    private WebViewData model;
    private ViewFlipper vf1;
    private int[] resId = { R.drawable.cake, R.drawable.beer,R.drawable.toast,R.drawable.baguette};

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_index,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mRecyclerView=getActivity().findViewById(R.id.cardview_list);

        vf1= getActivity().findViewById(R.id.vf1);

        for(int i=0;i<resId.length;i++){
            vf1.addView(getImageView(resId[i]));
        }
        vf1.setInAnimation(getActivity(),R.anim.push_left_in);
        vf1.setOutAnimation(getActivity(),R.anim.push_left_out);
        vf1.setFlipInterval(5000);
        vf1.startFlipping();

        initView();
        initListener();
       /* mCardView = getActivity().findViewById(R.id.cardview);
        mCardView.setRadius(20);              // 设置卡片圆角的半径大小
        mCardView.setCardBackgroundColor(Color.WHITE);  // 设置卡片背景的颜色
        mCardView.setCardElevation(10);               // 设置阴影部分大小
        mCardView.setContentPadding(10, 10, 10, 10); // 设置卡片距离阴影大小*/
        /*cardView1=getActivity().findViewById(R.id.cardView1);
        cardView1.setRadius(20);*/
    }

    //viewflipper getImage
    private ImageView getImageView(int resId){
        ImageView image=new ImageView(getActivity());
        image.setBackgroundResource(resId);
        return image;
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
            dataList.add(model);
        }
        cursor.close();
        manager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        myCardAdatper = new MyCardAdatper(getActivity(), dataList);
        mRecyclerView.setAdapter(myCardAdatper);
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
                    dataList1.add(link);
                }
                Intent i=new Intent(getActivity(),webViewActivity.class);
                i.putExtra("a",dataList1.get(position));
                startActivity(i);
            }
        });
    }
}
