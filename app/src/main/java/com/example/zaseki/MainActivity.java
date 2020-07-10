package com.example.zaseki;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Random;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    //初期化確認フラグ
    static int clear = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ボタンの定義
        Button button1;
        button1 = (Button) findViewById(R.id.button1);
        Button button2;
        button2 = (Button) findViewById(R.id.button2);
        Button button3;
        button3 = (Button) findViewById(R.id.button3);

        //初期化ボタン
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //初期化確認
                DialogStart dialog = new DialogStart();
                dialog.show(getFragmentManager(), "sample");
            }
        });

        //開始ボタン
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //入力エリアから参加人数取得
                EditText name;
                name = (EditText) findViewById(R.id.ninzu);
                SpannableStringBuilder tmp;
                tmp = (SpannableStringBuilder) name.getText();

                //参加人数入力確認
                if (tmp.length()==0){
                    Toast.makeText(getBaseContext(), "参加人数を設定してください", Toast.LENGTH_LONG).show();
                } else {
                    //初期化OK状態なら、初期化する
                    if (clear == 1) {
                        //DB準備
                        SQLiteDatabase db;
                        DBHelper hlpr = new DBHelper(getApplicationContext());
                        db = hlpr.getWritableDatabase();
                        //レコード初期化
                        db.execSQL("delete from Seat");
                        //参加人数分の座席情報レコード作成
                        int maxninzu = Integer.parseInt(tmp.toString());
                        for (int i = 1; i <= maxninzu; i++) {
                            String qry = "Insert into Seat(seatNo,sumiF) VALUES ('" + i + "',0);";
                            db.execSQL(qry);
                        }
                        Toast.makeText(getBaseContext(), "設定しました。抽選を開始します", Toast.LENGTH_LONG).show();
                        //初期化NG
                    } else {
                        Toast.makeText(getBaseContext(), "抽選中のため、初期化できません", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        //抽選ボタン
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                clear = 0;
                SQLiteDatabase db;
                Random r = new Random();
                DialogSeat dialog = new DialogSeat();
                //空席取得
                DBHelper hlpr = new DBHelper(getApplicationContext());
                db = hlpr.getReadableDatabase();
                Cursor cr = db.query(
                        "Seat", new String[]{"seatNo", "sumiF","id"},
                        "sumiF=0", null, null, null, null);
                //デバッグメッセージ
                while(cr.moveToNext()) {
                    Log.d("座席NO", cr.getString(0) + "フラグ"+ cr.getString(1)+ "ID"+ cr.getString(2));
                }

                if(cr.getCount()==0){
                    //済フラグOFFのデータがない場合
                    Toast.makeText(getBaseContext(), "全員きました", Toast.LENGTH_LONG).show();
                    clear=1;
                } else {
                    //ランダムチョイス
                    int n = r.nextInt(cr.getCount());
                    cr.moveToPosition(n);

                    // 座席指定ダイアログ表示
                    dialog.setSeatNo(cr.getInt(0));
                    dialog.show(getFragmentManager(), "sample");

                    //表示済の席をDB記録
                    db = hlpr.getWritableDatabase();
                    String qry = "Update seat SET sumiF = '1' where id = " + cr.getString(2) + ";";
                    db.execSQL(qry);
                }

            }
        });
    }
}
