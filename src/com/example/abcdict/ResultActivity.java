package com.example.abcdict;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;



public class ResultActivity extends Activity {
	private ListView listview;
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result);
		listview=(ListView) findViewById(R.id.listview);
		Intent intent=this.getIntent();
		Bundle b=intent.getBundleExtra("data");
		List list=new ArrayList();
		list.add(b.getString("username"));
		list.add(b.getString("password"));
		list.add(b.getString("position"));
		list.add(b.getString("gender"));
		list.add(b.getString("hobby"));
		list.add(b.getString("marriged"));
		ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,list);
		listview.setAdapter(adapter);
	}
}
