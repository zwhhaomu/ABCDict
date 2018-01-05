package com.example.abcdict;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class WordMainActivity extends Activity {
	private Button btninsert,btnsearch,btnupdate,btndelete;
	private EditText word,detail;
	WordDBHelper worddbhelper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_word);
		btninsert=(Button) findViewById(R.id.btninsert);
		btnsearch=(Button) findViewById(R.id.btnserach);
		btnupdate=(Button) findViewById(R.id.btnupdate);
		btndelete=(Button) findViewById(R.id.btndelete);
		word=(EditText) findViewById(R.id.word);
		detail=(EditText) findViewById(R.id.detail);
		worddbhelper=new WordDBHelper(this);

		btninsert.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String wordstr=word.getText().toString();
				String detailstr=detail.getText().toString();
				ContentValues values=new ContentValues();
				values.put("word", wordstr);
				values.put("detail", detailstr);
				int i=worddbhelper.insertWord(values);
				if(i!=0)
					Toast.makeText(getApplicationContext(), "单词录入成功！"+"\n单词："+wordstr+"\n解析："+detailstr, Toast.LENGTH_LONG).show();
				else
					Toast.makeText(getApplicationContext(), "单词录入失败！", Toast.LENGTH_LONG).show();
			}
		});

		btnsearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String wordstr=word.getText().toString();
				String detailstr=detail.getText().toString();
				Intent intent=new Intent(WordMainActivity.this,WordShowActivity.class);
				Bundle b=new Bundle();
				b.putString("word", wordstr);
				b.putString("detail", detailstr);
				intent.putExtra("data", b);
				startActivity(intent);
				
			}
		});

		btnupdate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String wordstr=word.getText().toString();
				String detailstr=detail.getText().toString();
				Toast.makeText(getApplicationContext(), "单词更新成功！"+"\n单词："+wordstr+"\n解析："+detailstr, Toast.LENGTH_LONG).show();
			}
		});

		btndelete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String wordstr=word.getText().toString();
				Toast.makeText(getApplicationContext(), wordstr+"已成功删除！", Toast.LENGTH_LONG).show();
			}
		});

	}





}
