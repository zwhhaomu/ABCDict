/**
 *
 */
package com.example.abcdict;

import android.net.Uri;
import android.provider.BaseColumns;


public final class Words
{
	// 授权常量1.调用Dict工程的Provider
	//public static final String AUTHORITY = "com.example.dict.wordProvider";
	//授权常量1.调用ContentProvider工程的Provider
	public static final String AUTHORITY = "com.siit.dict";
	// 定义一个静态内部类，定义该ContentProvider所包的数据列的列名
	public static final class Word implements BaseColumns
	{
		public static final String WORDS_TableTbl = "wordtable";
		// 定义Content所允许操作的3个数据列
		public final static String _ID = "_id";
		public final static String WORD = "word";
		public final static String DETAIL = "detail";
		// 定义该Content提供服务的两个Uri
		public final static Uri CONTENT_URI = Uri
				.parse("content://" + AUTHORITY + "/word1");
	}
	public static final class User implements BaseColumns
	{
		public static final String WORDS_TableTbl = "usertable";
		// 定义Content所允许操作的3个数据列
		public final static String _ID = "_id";
		public final static String USERNAME = "username";
		public final static String PASSWORD = "password";
		public final static String GENDER = "gender";
		public final static String FAVORITE = "favorite";
		public final static String MARRAGE = "marrage";
		public final static String POSITION = "position";
		// 定义该Content提供服务的两个Uri
		public final static Uri CONTENT_URI = Uri
				.parse("content://" + AUTHORITY + "/user1");
	}

}
