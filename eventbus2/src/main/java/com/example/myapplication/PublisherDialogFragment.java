package com.example.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PublisherDialogFragment extends DialogFragment {
    private static final String TAG = "PublisherDialogFragment";


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Publisher");
        final String[] items = {"Success", "Failure", "Posting", "Main", "MainOrdered","Background","Async"};
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        //success
                    {
                        EventBus.getDefault().post(new SuccessEvent());
                    }

                    break;
                    case 1:
                        //fail
                    {
                        EventBus.getDefault().post(new FailureEvent());
                    }

                    break;
                    case 2:
                        //posting
                    {
                        new Thread("posting-002") {
                            @Override
                            public void run() {
                                super.run();
                                EventBus.getDefault().post(new PostingEvent(Thread.currentThread().toString()));
                            }
                        }.start();
                    }
                    break;
                    case 3:
                        //main
                    {
                        new Thread() {
                            @Override
                            public void run() {
                                super.run();
                                EventBus.getDefault().post(new MainEvent(Thread.currentThread().toString()));
                            }
                        }.start();
                    }
                    break;

                    case 4:
                        //mainordered
                    {
                        Log.d(TAG, "onClick: before @ "+ SystemClock.uptimeMillis());

                        EventBus.getDefault().post(new MainOrderedEvent(Thread.currentThread().toString()));
                        Log.d(TAG, "onClick: after @ "+ SystemClock.uptimeMillis());
                    }
                    break;
                    case 5:
                    //background
                    {
                        EventBus.getDefault().post(new BackgroundEvent(Thread.currentThread().toString()));
                    }
                    break;

                    case 6:
                        //async
                    {
                        Executors.newSingleThreadExecutor().submit(new Runnable() {
                            @Override
                            public void run() {
                                EventBus.getDefault().post(new AsyncEvent(Thread.currentThread().toString()));
                            }
                        });
                    }
                    break;
                }
            }
        });
        return builder.create();
    }
}
