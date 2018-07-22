package com.example.mindy.simplymatch3;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.media.Image;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;

//public class Img {
//
//    private Image pic;
//    private int pieceID;

    //constructors
//    public Img() {}
//    public Img( Image pic, int pieceID) {
//        this.pic = pic;
//        this.pieceID = pieceID;
//    }

    //getters and setters
//    public void setPieceID( int pieceID) {
//        this.pieceID = pieceID;
//    }
//    public int getPieceID() {
//        return this.pieceID;
//    }
//    public void setPic( Image pic) {
//        this.pic = pic;
//    }
//    public Image getPic() {
//        return this.pic;
//    }

//}

public class MyDBHandler extends SQLiteOpenHelper {
   private static final int DATABASE_VERSION = 1;
   private static final String DATABASE_NAME = "imgDB.db";
   public static final String TABLE_NAME = "Images";
   public static final String COLUMN_ID = "PieceID";
   public static final String COLUMN_NAME = "Image";

    //@Override
    //public void onCreate(SQLiteDatabase db) {}
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {}
    //public String loadHandler() {}
    //public void addHandler(Img img) {}
    //public Img findHandler(Image pic) {}
    //public boolean deleteHandler(int pieceID) {}
    //public boolean updateHandler(int pieceID, Image pic) {}

    public MyDBHandler(Context context, String name,
                       SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    public void queryData( String sql) {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public void insertData (byte[] image ){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO IMAGES (NULL, ?)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindBlob(1, image);

        statement.executeInsert();
    }

    public Cursor getData(String sql) {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql,null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //String CREATE_TABLE = "CREATE TABLE" + TABLE_NAME + "(" + COLUMN_ID +
        //        "INTEGER PRIMARY KEY," + COLUMN_NAME + "TEXT )";
        String CREATE_TABLE = "CREATE TABLE IMAGES(Id INTEGER PRIMARY KEY AUTOINCREMENT, image BLOG)";
        db.execSQL(CREATE_TABLE);
    }



}




