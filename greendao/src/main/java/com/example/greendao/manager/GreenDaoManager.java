package com.example.greendao.manager;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


import com.example.greendao.MyApplication;
import com.example.greendao.model.GoodsModel;
import com.example.greendao.model.GoodsModelDao;
import com.example.greendao.utils.DataUtils;

import org.greenrobot.greendao.query.QueryBuilder;
import org.json.JSONException;

import java.util.List;

public class GreenDaoManager {

    private Context mContext;
    private GoodsModelDao mGoodsModelDao;

    public GreenDaoManager (Context context) {
        this.mContext = context;
//        获取DAO实例
        mGoodsModelDao = MyApplication.mSession.getGoodsModelDao();
    }

    /**
     * 添加所有的数据到数据库
     */
    public void insertGoods () {
        String json = DataUtils.getJson("goods.json", mContext);
//        如果不想因为重复添加数据而导致崩溃,可以使用insertOrReplaceInTx API
//        mGoodsModelDao.insertInTx(DataUtils.getGoodsModels(json));
        mGoodsModelDao.insertOrReplaceInTx(DataUtils.getGoodsModels(json));
    }

    /**
     * 查询所有的数据
     * @return
     */
    public List<GoodsModel> queryGoods () {
        QueryBuilder<GoodsModel> result = mGoodsModelDao.queryBuilder();
        result = result.orderAsc(GoodsModelDao.Properties.GoodsId);
        return result.list();
    }

    /**
     * 查询水果的数据
     * @return
     */
    public List<GoodsModel> queryFruits () {
        QueryBuilder<GoodsModel> result = mGoodsModelDao.queryBuilder();
        /**
         * 借助Property属性类提供的筛选方法
         */
        result = result.where(GoodsModelDao.Properties.Type.eq("0")).orderAsc(GoodsModelDao.Properties.GoodsId);
        return result.list();
    }

    /**
     * 查询零食的数据
     * @return
     */
    public List<GoodsModel> querySnacks () {
        QueryBuilder<GoodsModel> result = mGoodsModelDao.queryBuilder();
        /**
         * 借助Property属性类提供的筛选方法
         */
        result = result.where(GoodsModelDao.Properties.Type.eq("1")).orderAsc(GoodsModelDao.Properties.GoodsId);
        return result.list();
    }

    /**
     * 修改指定商品的商品信息
     * @param model
     */
    public void updateGoodsInfo (GoodsModel model) {
        mGoodsModelDao.update(model);
        mGoodsModelDao.updateInTx();
    }

    /**
     * 删除指定商品的商品信息
     * @param model
     */
    public void deleteGoodsInfo (GoodsModel model) {
        mGoodsModelDao.deleteByKey(model.getId());
    }
}
