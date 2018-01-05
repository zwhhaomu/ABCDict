package com.example.abcdict;

import java.util.Currency;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class WordDBHelper extends SQLiteOpenHelper {
	SQLiteDatabase db;
	private static final String CREATE_SQLWordTbl="create table wordtable(_id integer primary key autoincrement,wordid text,word text,detail text)";
	private static final String CREATE_SQLuser="create table usertable" +
			"(_id integer primary key autoincrement," +
			"username text," +
			"password text," +
			"ssex text," +
			"marrage text," +
			"favorite text," +
			"position text)";
	public WordDBHelper(Context context) {
		super(context, "word.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		this.db=db;
		db.execSQL(CREATE_SQLWordTbl);
		db.execSQL(CREATE_SQLuser);

	}

	public int insertWord(ContentValues values){
		int i=0;
		db=getWritableDatabase();
		i=(int) db.insert("wordtable", null, values);
		return i;
	}


	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	public Cursor query() {
		db=getWritableDatabase();
		Cursor c=db.query("wordtable", null, null, null, null, null, "_id asc");
		return c;
	}
	public int insertUser(String username,
			String password,String ssex,String marrage,
			String favorite,String position){
		int i=0;
		db=getWritableDatabase();

		ContentValues cv=new ContentValues();
		cv.put("username",username);
		cv.put("password",password);
		cv.put("ssex",ssex);
		cv.put("marrage",marrage);
		cv.put("favorite",favorite);
		cv.put("position",position);
		i=(int) db.insert("usertable", null, cv);

		return i;

	}

	public boolean userLogin(String username,String password){
		boolean flag=false;
		db=getWritableDatabase();
		Cursor c=db.query("usertable", null, "username=? and password=?", new String[]{username,password}, null, null,null);
		if(c.getCount()!=0)
			flag=true;
		return flag;		
	}
	//ÓÃ»§µÇÂ¼

	public Cursor getWordByid(String _id) {
		db=getReadableDatabase();
		Cursor c=db.query("wordtable", null, "_id=?", new String[]{_id}, null, null, null);

		return c;
	}
	public Cursor getWordByWordID(String wordid) {
		db=getReadableDatabase();
		Cursor c=db.query("wordtable", null, "wordid=?", new String[]{wordid}, null, null, null);
		return c;
	}

}
