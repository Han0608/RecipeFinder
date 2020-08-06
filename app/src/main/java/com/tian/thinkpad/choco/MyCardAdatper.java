package com.tian.thinkpad.choco;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.concurrent.CopyOnWriteArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyCardAdatper extends RecyclerView.Adapter<MyCardAdatper.ViewHolder> {

    private Context mContext;
    private CopyOnWriteArrayList<WebViewData> mDataList;

    private OnItemClickListener onItemClickListener;
    private WebViewData model;

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public MyCardAdatper(Context context, CopyOnWriteArrayList<WebViewData> dataList) {
        this.mContext = context;
        this.mDataList = dataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        model=mDataList.get(position);
        holder.itemTitle.setText(model.getTitle());
//        holder.itemImg.setImageURI(Uri.parse(String.valueOf(new File(model.getPhoto()))));
        Glide.with(mContext).load(model.getPhoto()).into(holder.itemImg);
        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.OnItemClick(position);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return mDataList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_img)
        ImageView itemImg;
        @BindView(R.id.item_title)
        TextView itemTitle;
        @BindView(R.id.cardView)
        CardView cardview;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

