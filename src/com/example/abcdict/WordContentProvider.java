package com.example.abcdict;

import java.util.HashMap;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class WordContentProvider extends ContentProvider {

	WordDBHelper dbhelper;
	// Uri工具类
	private static final UriMatcher sUriMatcher;
	// 查询、更新条件
	private static final int WORDS = 1;
	private static final int WORDS_ID = 2;
	private static final int USERS = 3;
	private static final int USERS_ID = 4;
	// 查询列集合
	private static HashMap<String, String> wordProjectionMap;

	static {
		// Uri匹配工具类
		sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		sUriMatcher.addURI(Words.AUTHORITY, "word", WORDS);
		sUriMatcher.addURI(Words.AUTHORITY, "word/#", WORDS_ID);

		sUriMatcher.addURI(Words.AUTHORITY, "user", USERS);
		sUriMatcher.addURI(Words.AUTHORITY, "user/#", USERS_ID);
		// 实例化查询列集合
		wordProjectionMap = new HashMap<String, String>();
		// 添加查询列
		wordProjectionMap.put(Words.Word._ID, Words.Word._ID);
		wordProjectionMap.put(Words.Word.WORD, Words.Word.WORD);
		wordProjectionMap.put(Words.Word.DETAIL, Words.Word.DETAIL);
		wordProjectionMap.put(Words.User._ID, Words.Word._ID);
		wordProjectionMap.put(Words.User.USERNAME, Words.User.USERNAME);
		wordProjectionMap.put(Words.User.PASSWORD, Words.User.PASSWORD);
		wordProjectionMap.put(Words.User.FAVORITE, Words.User.FAVORITE);
		wordProjectionMap.put(Words.User.GENDER, Words.User.GENDER);
		wordProjectionMap.put(Words.User.POSITION, Words.User.POSITION);

	}
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int count=0;
		SQLiteDatabase db=dbhelper.getWritableDatabase();
		// 获得数据库实例
		switch(sUriMatcher.match(uri)){
		case WORDS:
			count=db.delete(Words.Word.WORDS_TableTbl, selection, selectionArgs);
		case USERS:
			count=db.delete("usertable", selection, selectionArgs);
		}
		return count;
	}

	@Override
	public String getType(Uri arg0) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db=dbhelper.getWritableDatabase();
		long rowid;
		Uri wordUri=null;
		switch(sUriMatcher.match(uri)){
		case WORDS:
			rowid=db.insert("wordtable", null, values);
			wordUri=ContentUris.withAppendedId(uri, rowid);
			getContext().getContentResolver().notifyChange(wordUri, null);	
			break;
		case USERS:
			rowid=db.insert("usertable", null, values);
			wordUri=ContentUris.withAppendedId(uri, rowid);
			getContext().getContentResolver().notifyChange(wordUri, null);	
			break;
		}
		return wordUri;
	}

	@Override
	public boolean onCreate() {
		dbhelper=new WordDBHelper(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String where, String[] whereArgs,
			String sortArgs) {
		SQLiteDatabase db=dbhelper.getWritableDatabase();
		Cursor c=db.query("wordtable", projection, where, whereArgs, null, null, sortArgs);
		return c;
	}

	@Override
	public int update(Uri arg0, ContentValues values, String where, String[] whereArgs) {
		int count=0;
		SQLiteDatabase db=dbhelper.getWritableDatabase();
		count=db.update("wordtable", values, where, whereArgs);
		return count;
	}

}
