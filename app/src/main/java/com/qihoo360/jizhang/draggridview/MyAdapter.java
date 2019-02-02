package com.qihoo360.jizhang.draggridview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qihoo360.jizhang.draggridview.helper.ItemTouchHelperAdapter;
import com.qihoo360.jizhang.draggridview.helper.OnStartDragListener;

import java.util.ArrayList;
import java.util.Collections;

/**
 * +----------------------------------------------------------------------
 * | 功能描述:
 * +----------------------------------------------------------------------
 * | 时　　间: 2018/7/18.
 * +----------------------------------------------------------------------
 * | 代码创建: 张云鹏
 * +----------------------------------------------------------------------
 * | 版本信息: V1.0.0
 * +----------------------------------------------------------------------
 **/
public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ItemTouchHelperAdapter {

    private Context mContext;
    private ArrayList<String> mItems;
    private final OnStartDragListener mDragStartListener;
    private RecyclerView mRecyclerView;

    public MyAdapter(RecyclerView recyclerView, Context context, ArrayList<String> arrayList, OnStartDragListener dragStartListener) {
        mContext = context;
        mItems = arrayList;
        mRecyclerView = recyclerView;
        mDragStartListener = dragStartListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 11 == 0) {
            return 1;
        } else {
            return 0;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == 0){
            return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item, viewGroup, false));
        } else {
            return new MyViewHolder2(LayoutInflater.from(mContext).inflate(R.layout.item2, viewGroup, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder myViewHolder, int i) {
        if (myViewHolder instanceof MyViewHolder){
            MyViewHolder v = (MyViewHolder) myViewHolder;
            v.mTextView.setText(mItems.get(i% 11 == 0 ? i - i/11 : i - i / 11 - 1));
        } else {
            MyViewHolder2 v2 = (MyViewHolder2) myViewHolder;
            int m = i / 10 + 1;
            v2.mTextView2.setText("第"+m+"页");
        }

        myViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mDragStartListener.onStartDrag(myViewHolder);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public void onItemDismiss(int position) {
        mItems.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {

        Log.e("zhangyunpeng", (fromPosition% 11 == 0 ? fromPosition - fromPosition/11 : fromPosition - fromPosition / 11 - 1)
                + "#######" + (toPosition% 11 == 0 ? toPosition - toPosition/11 : toPosition - toPosition / 11 - 1));

        int start = (fromPosition% 11 == 0 ? fromPosition - fromPosition/11 : fromPosition - fromPosition / 11 - 1);
        int end = (toPosition% 11 == 0 ? toPosition - toPosition/11 : toPosition - toPosition / 11 - 1);

        if (fromPosition < toPosition) {
            for (int i = start; i < end; i++) {
                Collections.swap(mItems, i, i + 1);
            }
        } else {
            for (int i = start; i > end; i--) {
                Collections.swap(mItems, i, i - 1);
            }
        }

        notifyItemMoved(fromPosition, toPosition);
        return true;
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mTextView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.textview);
        }
    }

    public class MyViewHolder2 extends RecyclerView.ViewHolder {
        TextView mTextView2;
        public MyViewHolder2(@NonNull View itemView) {
            super(itemView);
            mTextView2 = itemView.findViewById(R.id.page_text);
        }
    }
}
