package com.qihoo360.jizhang.draggridview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.qihoo360.jizhang.draggridview.helper.MyDefaultItemAnimator;
import com.qihoo360.jizhang.draggridview.helper.OnStartDragListener;
import com.qihoo360.jizhang.draggridview.helper.SimpleItemTouchHelperCallback;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  implements OnStartDragListener {

    private ItemTouchHelper mItemTouchHelper;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            arrayList.add("第" + i + "个item");
        }

        int m;
        if (arrayList.size() % 10 == 0){
            m = arrayList.size() / 10;
        } else {
            m = arrayList.size() / 10 + 1;
        }
        arrayList.addAll(arrayList.subList(arrayList.size() - m, arrayList.size()));

        recyclerView = findViewById(R.id.recyclerview);
        GridLayoutManager layoutManager = new GridLayoutManager(MainActivity.this, 5);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position %11 == 0){
                    return 5;
                } else {
                    return 1;
                }
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        MyAdapter adapter = new MyAdapter(recyclerView, MainActivity.this, arrayList, this);
        recyclerView.setAdapter(adapter);


        /*********************拖动**********************/
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setItemAnimator(new MyDefaultItemAnimator());
        /*********************拖动**********************/

    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }
}
