package com.example.zaseki;

import android.app.DialogFragment;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import static com.example.zaseki.MainActivity.clear;

/**
 * Created by Tomoya on 2018/11/23.
 */


public class DialogStart extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("初期化しますが、よろしいですか？")
                .setPositiveButton("はい", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        clear = 1;
                    }
                })
                .setNegativeButton("いいえ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        clear = 0;
                    }
                });

        return builder.create();
    }
}
