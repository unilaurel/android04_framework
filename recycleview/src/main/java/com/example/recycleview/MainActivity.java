package com.example.recycleview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private MyRecyclerViewAdapter mAdaper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recycler_view);

        //LinerLayout
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //横向きに並べる
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        //逆向きに表示
        linearLayoutManager.setReverseLayout(true);

        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdaper = new MyRecyclerViewAdapter(this,mRecyclerView);
        mRecyclerView.setAdapter(mAdaper);

        mAdaper.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(MainActivity.this,"第"+position+"数据被点击",Toast.LENGTH_SHORT).show();
            }
        });
    }

    //dataを増加する
    public void onAddDataClick(View v) {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            String s = "第" + i + "条数据";
            data.add(s);
        }

        mAdaper.setDataSource(data);
    }


    //Layoutを変える
    public void onChangeLayoutC(View v) {
        //liner-->grid
        if (mRecyclerView.getLayoutManager().getClass() == LinearLayoutManager.class) {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
            mRecyclerView.setLayoutManager(gridLayoutManager);
        }else if(mRecyclerView.getLayoutManager().getClass()==GridLayoutManager.class){
            //grid--stagger
            StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        }else{
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(linearLayoutManager);
        }
    }

    /**
     * 一つのdataを増加する
     * @param v
     */
    public void onInsertDataClick (View v) {
       mAdaper.addData(1);
    }

    /**
     * 一つのdataを削除する
     * @param v
     */
    public void onRemoveDataClick (View v) {
        mAdaper.removeData(1);
    }
}