package com.example.okhttp3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {
    private static final String POST_URL = "https://api.github.com/markdown/raw";
    private static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown;charset=utf-8");
    private static final String TAG = "MainActivity";
    private final OkHttpClient mClient = new OkHttpClient();

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.tvContent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuGet) {
            getData();
        } else if(item.getItemId()==R.id.menuResponse){
            response();
        }else if(item.getItemId()==R.id.menuPost){
            post();
        }
        return super.onOptionsItemSelected(item);
    }

    private void post() {
        Request.Builder builder = new Request.Builder();
        builder.url(POST_URL);
        builder.post(RequestBody.create(MEDIA_TYPE_MARKDOWN,"Hello **world**"));
        Request request = builder.build();
        Call call = mClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String content = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mTextView.setText(content);
                        }
                    });

                }
            }
        });
    }

    private void response() {
        Request.Builder builder = new Request.Builder();
        builder.url("https://github.com/square/okhttp/blob/master/README.md");
        Request request = builder.build();
        Call call = mClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d(TAG, "onFailure() called with: call = [" + call + "], e = [" + e + "]");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.d(TAG, "onResponse() called with: call = [" + call + "], response = [" + response + "]");

                int code = response.code();
                Headers headers = response.headers();
                String content = response.body().string();

                final StringBuilder buf = new StringBuilder();
                buf.append("code: " + code);
                buf.append("\nHeaders: " + headers);
                buf.append("\nbody: " + content);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTextView.setText(buf.toString());
                    }
                });

            }
        });
    }

    private void getData() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(new Runnable() {
            @Override
            public void run() {

                Request.Builder builder = new Request.Builder();
                builder.url("https://github.com/square/okhttp/blob/master/README.md");
                Request request = builder.build();
                Log.e(TAG, "run: "+request );

                Call call = mClient.newCall(request);
                try {
                    Response response = call.execute();//同期
                    if (response.isSuccessful()) {
                        String string = response.body().string();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mTextView.setText(string);
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        executor.shutdown();
    }
}