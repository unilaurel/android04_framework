package com.imooc.res.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.imooc.res.R;
import com.imooc.res.ui.adapter.ProductListAdapter;
import com.imooc.res.ui.vo.ProductItem;

import java.util.ArrayList;
import java.util.List;

public class RestaurantActivity extends BaseActivity {


    private RecyclerView mRecyclerView;
    private ProductListAdapter mAdapter;
    private List<ProductItem> mDatas = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        setUpToolbar();
        setTitle("订餐");
        //String name, String label, String desc, float price, String url
        mDatas.add(new ProductItem(
                "鸿洋餐厅",
                "5星级大饭店,就是好",
                "5星级大饭店,就是好",
                188.8f,
                "http://img.mukewang.com/55403cbd0001f88806000338-240-135.jpg"
        ));
        mDatas.add(new ProductItem(
                "鸿洋餐厅",
                "5星级大饭店,就是好",
                "5星级大饭店,就是好",
                188.8f,
                "http://img.mukewang.com/55403cbd0001f88806000338-240-135.jpg"
        ));
        mDatas.add(new ProductItem(
                "鸿洋餐厅",
                "5星级大饭店,就是好",
                "5星级大饭店,就是好",
                188.8f,
                "http://img.mukewang.com/55403cbd0001f88806000338-240-135.jpg"
        ));
        mDatas.add(new ProductItem(
                "鸿洋餐厅",
                "5星级大饭店,就是好",
                "5星级大饭店,就是好",
                188.8f,
                "http://img.mukewang.com/55403cbd0001f88806000338-240-135.jpg"
        ));
        mDatas.add(new ProductItem(
                "鸿洋餐厅",
                "5星级大饭店,就是好",
                "5星级大饭店,就是好",
                188.8f,
                "http://img.mukewang.com/55403cbd0001f88806000338-240-135.jpg"
        ));
        mDatas.add(new ProductItem(
                "鸿洋餐厅",
                "5星级大饭店,就是好",
                "5星级大饭店,就是好",
                188.8f,
                "http://img.mukewang.com/55403cbd0001f88806000338-240-135.jpg"
        ));
        mDatas.add(new ProductItem(
                "鸿洋餐厅",
                "5星级大饭店,就是好",
                "5星级大饭店,就是好",
                188.8f,
                "http://img.mukewang.com/55403cbd0001f88806000338-240-135.jpg"
        ));
        mDatas.add(new ProductItem(
                "鸿洋餐厅",
                "5星级大饭店,就是好",
                "5星级大饭店,就是好",
                188.8f,
                "http://img.mukewang.com/55403cbd0001f88806000338-240-135.jpg"
        ));


        initViews();

    }

    private void initViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);

        mAdapter = new ProductListAdapter(this, mDatas);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }

}
