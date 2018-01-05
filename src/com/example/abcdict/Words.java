/**
 *
 */
package com.example.abcdict;

import android.net.Uri;
import android.provider.BaseColumns;


public final class Words
{
	// ��Ȩ����1.����Dict���̵�Provider
	//public static final String AUTHORITY = "com.example.dict.wordProvider";
	//��Ȩ����1.����ContentProvider���̵�Provider
	public static final String AUTHORITY = "com.siit.dict";
	// ����һ����̬�ڲ��࣬�����ContentProvider�����������е�����
	public static final class Word implements BaseColumns
	{
		public static final String WORDS_TableTbl = "wordtable";
		// ����Content�����������3��������
		public final static String _ID = "_id";
		public final static String WORD = "word";
		public final static String DETAIL = "detail";
		// �����Content�ṩ���������Uri
		public final static Uri CONTENT_URI = Uri
				.parse("content://" + AUTHORITY + "/word1");
	}
	public static final class User implements BaseColumns
	{
		public static final String WORDS_TableTbl = "usertable";
		// ����Content�����������3��������
		public final static String _ID = "_id";
		public final static String USERNAME = "username";
		public final static String PASSWORD = "password";
		public final static String GENDER = "gender";
		public final static String FAVORITE = "favorite";
		public final static String MARRAGE = "marrage";
		public final static String POSITION = "position";
		// �����Content�ṩ���������Uri
		public final static Uri CONTENT_URI = Uri
				.parse("content://" + AUTHORITY + "/user1");
	}

}
