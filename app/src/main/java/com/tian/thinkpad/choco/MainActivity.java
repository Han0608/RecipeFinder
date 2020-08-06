package com.tian.thinkpad.choco;



import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.ViewFlipper;

public class MainActivity extends AppCompatActivity {

    private SearchView sv;
    private ListView lv;
    private String[] str={"1876","2654"};

    private ViewFlipper vf;
    private int[] resId = { R.drawable.cake, R.drawable.ice,R.drawable.cake2};
   // private GestureDetector gestureDetector=null;
 //   private Activity activity=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sv=findViewById(R.id.sv);
        lv=findViewById(R.id.lv);
        lv.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,str));
        lv.setTextFilterEnabled(true);

        //viewflipper roll pic

        vf=findViewById(R.id.vf);

        for(int i=0;i<resId.length;i++){
            vf.addView(getImageView(resId[i]));
        }
        vf.setInAnimation(this,R.anim.push_left_in);
        vf.setOutAnimation(this,R.anim.push_left_out);
        vf.setFlipInterval(5000);
        vf.startFlipping();

        //set text listener for search view
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                sv.clearFocus();
                //sv.onActionViewCollapsed();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(!TextUtils.isEmpty(newText)){
                    lv.setFilterText(newText);
                }else{
                    lv.clearTextFilter();
                }
                sv.setVisibility(View.INVISIBLE);
                //sv.onActionViewCollapsed();
                return false;
            }
        });
    }
    private ImageView getImageView(int resId){
        ImageView image=new ImageView(this);
        image.setBackgroundResource(resId);
        return image;
    }
}
