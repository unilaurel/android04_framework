package com.example.glide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private ImageView mIv;

    private String IMAGE_URL = "https://img2.mukewang.com/5b037fb30001534202000199-140-140.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mIv = findViewById(R.id.iv);
    }

    public void onLoadImageClick(View v) {
//        glideLoadImage(IMAGE_URL);
//        glideLoadImage("https://p8.itc.cn/q_70/images03/20210131/e10e003e10c44bfcba57a6605b3287af.jpeg");

        glideAppLoadImage("https://p8.itc.cn/q_70/images03/20210131/e10e003e10c44bfcba57a6605b3287af.jpeg");
    }

    //Glideで画像をloadする
    private void glideLoadImage(String img) {
        //Glide を設置する
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_sad)
                .circleCrop();
        Glide.with(this)
                .load(img)
//                .apply(options)
                .apply(GlideOptionUtils.circleCropOptions())
                .into(mIv);
    }

    private void glideAppLoadImage(String img){
        GlideApp.with(this)
                .load(img)
                .injectOptions()
                .into(mIv);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 200:
                    Bitmap bitmap = (Bitmap) msg.obj;
                    mIv.setImageBitmap(bitmap);
                    break;
                default:
                    mIv.setImageResource(R.drawable.ic_sad);
                    break;
            }
        }
    };

    //net imageをloadする
    private void loadUrlImage(String imgUrl) {
        mIv.setImageResource(R.drawable.ic_add);
/**
 * 1. 画像のアドレスを見つけます。
 * 2. 画像のアドレスに基づいて、ロード可能なオブジェクトに画像を変換します。
 * 3. ImageViewを使用してオブジェクトを表示します。
 */
        new Thread() {
            @Override
            public void run() {
                super.run();
                Message message = new Message();
                try {
                    URL url = new URL(imgUrl);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    int code = conn.getResponseCode();

                    if (code == 200) {
                        InputStream is = conn.getInputStream();
                        //imageviewでload　できるオブジェクト
                        Bitmap bitmap = BitmapFactory.decodeStream(is);
                        message.obj = bitmap;
                        message.what = 200;
                    } else {
                        message.what = code;
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    message.what = -1;
                } catch (IOException e) {
                    e.printStackTrace();
                    message.what = -1;
                } finally {
                    handler.sendMessage(message);
                }
            }
        }.start();

    }


}