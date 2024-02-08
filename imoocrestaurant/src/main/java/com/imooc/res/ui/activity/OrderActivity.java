package com.imooc.res.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.imooc.res.R;
import com.imooc.res.UserInfoHolder;
import com.imooc.res.bean.Order;
import com.imooc.res.bean.User;
import com.imooc.res.biz.OrderBiz;
import com.imooc.res.net.CommonCallback;
import com.imooc.res.ui.adapter.OrderAdapter;
import com.imooc.res.ui.view.CircleTransform;
import com.imooc.res.ui.view.refresh.SwipeRefresh;
import com.imooc.res.ui.view.refresh.SwipeRefreshLayout;
import com.imooc.res.utils.T;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends BaseActivity {


    private Button mBtnTakeOrder;
    private ImageView mIvIcon;
    private TextView mTvName;

    private RecyclerView mRecyclerView;
    private OrderAdapter mAdapter;
    private List<Order> mDatas = new ArrayList<>();

    private SwipeRefreshLayout mRefreshLayout;

    private OrderBiz mOrderBiz = new OrderBiz();
    private int mCurrentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);


//        mDatas.add(new Product(
//                "鸿洋餐厅",
//                "5星级大饭店,就是好",
//                "5星级大饭店,就是好",
//                188.8f,
//                "http://img.mukewang.com/55403cbd0001f88806000338-240-135.jpg"
//        ));


        initViews();
        initEvents();

    }

    @Override
    protected void onResume() {
        super.onResume();

        startLoadingProgress();
        loadData();

    }

    private void loadData() {
        mOrderBiz.listByPage(0, new CommonCallback<List<Order>>() {
            @Override
            public void onError(Exception e) {
                stopLoadingProgress();
                T.showToast(e.getMessage());
                if (mRefreshLayout.isRefreshing()) {
                    mRefreshLayout.setRefreshing(false);
                }
                if (e.getMessage().contains("用户未登录")) {
                    toLoginActivity();
                }

            }

            @Override
            public void onSuccess(List<Order> response) {
                stopLoadingProgress();
                T.showToast("更新订单成功~~~");
                mDatas.clear();
                mDatas.addAll(response);
                mAdapter.notifyDataSetChanged();
                if (mRefreshLayout.isRefreshing()) {
                    mRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    private void initEvents() {
        mBtnTakeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toProductListActivity();
            }


        });
    }

    private void toProductListActivity() {
        Intent intent = new Intent(this, ProductListActivity.class);
        startActivity(intent);
    }

    private void initViews() {

        mBtnTakeOrder = (Button) findViewById(R.id.id_btn_take_order);
        mIvIcon = (ImageView) findViewById(R.id.id_iv_icon);
        mTvName = (TextView) findViewById(R.id.id_tv_name);
        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.id_refresh_layout);

        User user = UserInfoHolder.getInstance().getUser();
        if (user != null) {
            mTvName.setText(user.getUsername());
        } else {
            toLoginActivity();
            return;
        }

        //设置开关
        mRefreshLayout.setMode(SwipeRefresh.Mode.BOTH);
        //设置颜色
        mRefreshLayout.setColorSchemeColors(Color.RED, Color.BLACK, Color.BLUE, Color.GRAY);
        mRefreshLayout.setOnRefreshListener(new SwipeRefresh.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
        mRefreshLayout.setOnPullUpRefreshListener(new SwipeRefreshLayout.OnPullUpRefreshListener() {
            @Override
            public void onPullUpRefresh() {
                loadMore();
            }
        });

        mAdapter = new OrderAdapter(this, mDatas);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        Picasso.with(this).load(R.drawable.icon).placeholder(R.drawable.pictures_no).transform(new CircleTransform()).into(mIvIcon);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            home.addCategory(Intent.CATEGORY_HOME);
            startActivity(home);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void loadMore() {
        mOrderBiz.listByPage(++mCurrentPage, new CommonCallback<List<Order>>() {
            @Override
            public void onError(Exception e) {
                stopLoadingProgress();
                T.showToast(e.getMessage());
                mRefreshLayout.setPullUpRefreshing(false);
                mCurrentPage--;

                String message = e.getMessage();
                if (message.contains("用户未登录")) {
                    toLoginActivity();
                }

            }

            @Override
            public void onSuccess(List<Order> response) {
                stopLoadingProgress();
                if (response.size() == 0) {
                    T.showToast("木有历史订单啦~~~");
                    mRefreshLayout.setPullUpRefreshing(false);
                    return;
                }
                T.showToast("更新订单成功~~~");
                mDatas.addAll(response);
                mAdapter.notifyDataSetChanged();
                mRefreshLayout.setPullUpRefreshing(false);
            }
        });
    }
}
