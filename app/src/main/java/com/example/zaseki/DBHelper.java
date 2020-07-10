package com.example.zaseki;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    // コンストラクタ
    public DBHelper(Context c) {
        //最後の引数はバージョン。更新されるとonUpgradeが走る
        super(c, "Seat.db", null, 11);
    }

    @Override
//初起動の時に、Create文を処理。
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE Seat" +
                        "(id INTEGER PRIMARY KEY AUTOINCREMENT," +  //連番管理
                        " seatNo INTEGER,"+
                        " sumiF boolean );");
    }

    @Override
//テーブル構造を変わってるときだけ処理される。
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // テーブルの破棄と再作成
        db.execSQL("drop table Seat;");
        onCreate(db);
    }

}
