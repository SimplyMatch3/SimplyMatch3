package com.example.mindy.simplymatch3;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.Image;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;

public class Img {

    private Image pic;
    private int pieceID;

    //constructors
    public Img() {}
    public Img( Image pic, int pieceID) {
        this.pic = pic;
        this.pieceID = pieceID;
    }

    //getters
    public void setPieceID( int pieceID) {
        this.pieceID = pieceID;
    }
    public int getPieceID() {
        return this.pieceID;
    }
    public void setPic( Image pic) {
        this.pic = pic;
    }
    public Image getPic() {
        return this.pic;
    }

}

public class MyDBHandler extends SQLiteOpenHelper {
   private static final int DATABASE_VERSION = 1;
   private static final String DATABASE_NAME = "imgDB.db";
   public static final String TABLE_NAME = "Images";
   public static final int COLUMN_ID = "PieceID";
   public static final Image COLUMN_NAME = "Image";

   public MyDBHandler(Context, Stringname, SQLiteDatabase.CursorFactory, intversion) {
       super(context, DATABASE_NAME, factory, DATABASE_VERSION);
   }

    @Override
    public void onCreate(SQLiteDatabase db) {}
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {}
    public String loadHandler() {}
    public void addHandler(Img img) {}
    public Img findHandler(Image pic) {}
    public boolean deleteHandler(int pieceID) {}
    public boolean updateHandler(int pieceID, Image pic) {}

    public MyDBHandler(Context context, Stringname,
                       SQLiteDatabase.CursorFactoryfactory, intversion) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE" + TABLE_NAME + "(" + COLUMN_ID +
                "INTEGER PRIMARYKEY," + COLUMN_NAME + "TEXT )";
        db.execSQL(CREATE_TABLE);
    }

    public String loadHandler() {
        String result = "";
        String query = "Select*FROM" + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            int result_0 = cursor.getInt(0);
            String result_1 = cursor.getString(1);
            result += String.valueOf(result_0) + " " + result_1 +
                    System.getProperty("line.separator");
        }
        cursor.close();
        db.close();
        return result;
    }


    public void addHandler(Img img) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, img.getPieceID());
        values.put(COLUMN_NAME, img.getPic());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public Img findHandler(Image pic) {
        String query = "Select * FROM " + TABLE_NAME + "WHERE" + COLUMN_NAME + " = " + "'" + studentname + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Img img = new Img();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            img.setPieceID(Integer.parseInt(cursor.getInt(0)));
            img.setPic(cursor.getImage(1));
            cursor.close();
        } else {
            img = null;
        }
        db.close();
        return img;
    }

    public boolean deleteHandler(int ID) {
        boolean result = false;
        String query = "Select*FROM" + TABLE_NAME + "WHERE" + COLUMN_ID + "= '" + String.valueOf(ID) + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Img img = new Img();
        if (cursor.moveToFirst()) {
            img.setPieceID(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_NAME, COLUMN_ID + "=?",
                    new String[] {
                String.valueOf(img.getPieceID())
            });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }





}

}


