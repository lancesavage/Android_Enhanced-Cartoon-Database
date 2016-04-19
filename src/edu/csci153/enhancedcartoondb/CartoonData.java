package edu.csci153.enhancedcartoondb;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CartoonData extends SQLiteOpenHelper {
	
	public static final String TABLE_NAME = "CHARACTERS";
	public static final String ID = "_id";
	public static final String CARTOON = "CARTOON";
	public static final String CHARACTER = "CHARACTER";
	public static final String AGE = "AGE";
	public static final String DATABASE_NAME = "cartoons.db";
	public static final int DATABASE_VERSION = 1;  
    
	public CartoonData(Context ctx){
		super (ctx, DATABASE_NAME, null, DATABASE_VERSION);
    }


	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {	
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + TABLE_NAME + " ( " + ID	+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + CARTOON + " TEXT NOT NULL, " + CHARACTER + " TEXT NOT NULL," + AGE + " INTEGER);"); 
	}

}