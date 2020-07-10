package com.example.zaseki;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

/**
 * Created by Tomoya on 2018/11/23.
 */
public class DialogSeat extends DialogFragment {
    int seatNo;

    public void setSeatNo(int n){
        seatNo = n;
    }

    // ダイアログが生成された時に呼ばれるメソッド ※必須
    public Dialog onCreateDialog(Bundle savedInstanceState){
        // ダイアログ生成  AlertDialogのBuilderクラスを指定してインスタンス化します
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        // タイトル設定
        dialogBuilder.setTitle("　" + seatNo +"番");
        // 表示する文章設定
        dialogBuilder.setMessage("があなたの座席です");

        // dialogBulderを返す
        return dialogBuilder.create();
    }
}
