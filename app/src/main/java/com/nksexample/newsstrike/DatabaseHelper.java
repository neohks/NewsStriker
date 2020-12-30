package com.nksexample.newsstrike;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.nksexample.newsstrike.model.ArticleModel;
import com.nksexample.newsstrike.model.FavModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    //Database version
    private static final int DATABASE_VERSION = 1;

    //Database name
    private static final String DATABASE_NAME = "newsapp.db";

    //Table NAme
    private static final String TABLE_FAV = "favouritetable";

    //Table Fields
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "webname";
    private static final String COLUMN_URL = "url";

    SQLiteDatabase database;

    public DatabaseHelper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        database = getWritableDatabase();

    }

    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);


    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE "+ TABLE_FAV + " ( " + COLUMN_ID + " INTEGER PRIMARY KEY, " + COLUMN_NAME + " TEXT, " + COLUMN_URL + " TEXT)" );

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_FAV);
        onCreate(sqLiteDatabase);
    }

    //Get All data from database
    public ArrayList<FavModel> listALLFavItems()
    {
        ArrayList<FavModel> favModelList = new ArrayList<>();

        String queryString = "SELECT * FROM " + TABLE_FAV;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString,null);

        if(cursor.moveToFirst())
        {
            //loop result
            do
            {
                int favID = cursor.getInt(0);
                String publisherName = cursor.getString(1);
                String url = cursor.getString(2);

                FavModel newFav = new FavModel(favID, publisherName, url);
                favModelList.add(newFav);

            }
            while (cursor.moveToNext());
        }
        else
        {
            // fail dont add anything to list
            Log.d("FAIL", "Please check showALLData method in DBHelper");
        }

        cursor.close();
        db.close();
        return  favModelList;
    }

    //Insert INTO database
    public boolean insertONEFavItem(FavModel favModel) {

        SQLiteDatabase dbs = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID, favModel.getFavID());
        contentValues.put(COLUMN_NAME, favModel.getPublisherName());
        contentValues.put(COLUMN_URL, favModel.getUrl());

        long insert = dbs.insert(TABLE_FAV, null, contentValues);

        if (insert == -1)
            return false;
        else
            return true;
    }

    //Delete FROM database
    public boolean deleteONEFavItem(FavModel favModel)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + TABLE_FAV + " WHERE " + COLUMN_ID +" = " + favModel.getFavID() ;
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst())
        {
            return true;
        }
        else
        {
            return false;
        }
    }


}
