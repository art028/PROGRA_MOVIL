package com.example.app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class claseBD  extends SQLiteOpenHelper {

    private static final String nombre_bd="taller.bd";
    private static int  version_bd=2;
    private static final String tabla_prod="CREATE TABLE PRODUCTOS(CODIGO TEXT PRIMARY KEY, " +
            "PRODUCTO TEXT, CANTIDAD INT)";

    public claseBD(@Nullable Context context) {
        super(context, nombre_bd, null, version_bd);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(tabla_prod);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ tabla_prod);
        db.execSQL(tabla_prod);
    }

    public void agregarProd(String codigo, String producto, Integer cantidad){
        SQLiteDatabase bd=getWritableDatabase();
        if(bd!=null){
            bd.execSQL("INSERT INTO PRODUCTOS VALUES('"+codigo+"','"+producto+"','"+cantidad+"')");
            bd.close();
        }
    }
}
