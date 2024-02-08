package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onSuccessEvent(SuccessEvent event) {
        setImageSrc(R.drawable.ic_happy);
    }

    @Subscribe
    public void onFailEvent(FailureEvent event) {
        setImageSrc(R.drawable.ic_sad);
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onPostingEvent(PostingEvent event) {
        String threadInfo = Thread.currentThread().toString();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setPublisherThreadInfo(event.threadInfo);
                setSubscribeThreadInfo(threadInfo);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEvent(MainEvent event) {
        String threadInfo = Thread.currentThread().toString();
        setSubscribeThreadInfo(threadInfo);
        setPublisherThreadInfo(event.threadInfo);
    }

    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
    public void onMainOrderedEvent(MainOrderedEvent event) {
        Log.d(TAG, "onMainOrderedEvent: enter @" + SystemClock.uptimeMillis());
        String threadInfo = Thread.currentThread().toString();
        setSubscribeThreadInfo(threadInfo);
        setPublisherThreadInfo(event.threadInfo);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "onMainOrderedEvent: exit @" + SystemClock.uptimeMillis());

    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onBackgroundEvent(BackgroundEvent event) {
        String threadInfo = Thread.currentThread().toString();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setPublisherThreadInfo(event.threadInfo);
                setSubscribeThreadInfo(threadInfo);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onAsyncEvent(AsyncEvent event) {
        String threadInfo = Thread.currentThread().toString();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setPublisherThreadInfo(event.threadInfo);
                setSubscribeThreadInfo(threadInfo);
            }
        });
    }

    public void setPublisherThreadInfo(String threadInfo) {
        setTextView(R.id.publisherThreadTextView, threadInfo);
    }

    public void setSubscribeThreadInfo(String threadInfo) {
        setTextView(R.id.subscriberThreadTextView, threadInfo);
    }

    private void setTextView(int resId, String text) {
        final TextView textView = findViewById(resId);
        textView.setText(text);
        textView.setAlpha(.5f);
        textView.animate().alpha(1).start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Subscriber");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PublisherDialogFragment fragment = new PublisherDialogFragment();
                fragment.show(getSupportFragmentManager(), "publisher");
            }
        });

    }

    private void setImageSrc(int resId) {
        ImageView imageView = findViewById(R.id.emotionImageView);
        imageView.setImageResource(resId);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_sticky) {
            Intent intent = new Intent(this, StickyActivity.class);
            EventBus.getDefault().postSticky(new StickyEvent("sticky-message-content"));
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        return NavigationUI.navigateUp(navController, appBarConfiguration)
//                || super.onSupportNavigateUp();
//    }
}