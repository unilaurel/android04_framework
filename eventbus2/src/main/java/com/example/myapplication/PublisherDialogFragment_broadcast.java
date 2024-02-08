package com.example.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class PublisherDialogFragment_broadcast extends DialogFragment {
    private static final String TAG = "PublisherDialogFragment";


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Publisher");
        final String[] items = {"Success", "Failure"};
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        //success
                    {
                        Intent intent = new Intent();
                        intent.setAction(MainActivity_broadcast.HANDLE_EVENT_ACTION);
                        intent.putExtra(MainActivity_broadcast.STATUS_KEY, true);
                        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                    }

                        break;
                    case 1:
                        //fail
                    {
                        Intent intent = new Intent();
                        intent.setAction(MainActivity_broadcast.HANDLE_EVENT_ACTION);
                        intent.putExtra(MainActivity_broadcast.STATUS_KEY, false);
                        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                    }

                        break;
                }
            }
        });
        return builder.create();
    }
}
