package com.example.greendao;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.greendao.manager.GreenDaoManager;
import com.example.greendao.model.GoodsModel;


public class GoodsDetailActivity extends AppCompatActivity {

    private GreenDaoManager mDbManager;
    private EditText mEtInfo;
    private GoodsModel mGoodsModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);

        mDbManager = new GreenDaoManager(this);
        mGoodsModel = getIntent().getParcelableExtra("goodsModel");

        mEtInfo = findViewById(R.id.et_info);
        mEtInfo.setText(mGoodsModel.getInfo());
    }

    /**
     * 保存编辑点击事件
     * @param v
     */
    public void onEditClick(View v) {
        String info = mEtInfo.getText().toString();
        mGoodsModel.setInfo(info);
        mDbManager.updateGoodsInfo(mGoodsModel);
        onBackPressed();
    }

    /**
     * 删除商品
     * @param v
     */
    public void onDelClick(View v) {
        String info = mEtInfo.getText().toString();
        mGoodsModel.setInfo(info);
        mDbManager.deleteGoodsInfo(mGoodsModel);
        onBackPressed();
    }
}
