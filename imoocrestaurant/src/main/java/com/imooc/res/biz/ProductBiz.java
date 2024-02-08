package com.imooc.res.biz;

import com.imooc.res.bean.Product;
import com.imooc.res.config.Config;
import com.imooc.res.net.CommonCallback;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.List;

/**
 * Created by zhy on 16/10/23.
 */
public class ProductBiz {

    public void listByPage(int currentPage, CommonCallback<List<Product>> callback) {
        OkHttpUtils
                .post()
                .url(Config.baseUrl + "product_find")
                .tag(this)
                .addParams("currentPage", currentPage + "")
                .build()
                .execute(callback);
    }

}
