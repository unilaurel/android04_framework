package com.example.greendao;



import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.greendao.manager.GreenDaoManager;
import com.example.greendao.model.GoodsModel;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private GreenDaoManager mDBManager;
    private GoodsAdapter mAdapter;
    private RecyclerView mRv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        查询所有的商品
        notifyAdapter(mDBManager.queryGoods());
    }

    private void init () {
        mDBManager = new GreenDaoManager(this);
        initView();
    }

    private void initView () {
        mRv = findViewById(R.id.rv);
        mAdapter = new GoodsAdapter(this);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.setAdapter(mAdapter);
    }

    /**
     * 进货按钮的点击事件
     * @param v
     */
    public void onAddGoodsClick (View v) {
        mDBManager.insertGoods();
    }

    /**
     * 查询全部商品
     * @param v
     */
    public void onQueryAllClick (View v) {
        List<GoodsModel> dataSource = mDBManager.queryGoods();
        notifyAdapter(dataSource);
    }

    /**
     * 筛选-水果
     * @param v
     */
    public void onQueryFruitsClick (View v) {
        notifyAdapter(mDBManager.queryFruits());
    }

    /**
     * 筛选-零食
     * @param v
     */
    public void onQuerySnacksClick (View v) {
        notifyAdapter(mDBManager.querySnacks());
    }

    /**
     * 改变展示数据
     */
    private void notifyAdapter (List<GoodsModel> dataSource) {
        mAdapter.setDataSource(dataSource);
    }

}
