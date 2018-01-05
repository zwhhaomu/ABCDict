package com.example.abcdict;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class WordListShow extends ListActivity {
	private WordDBHelper dbhelper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ListView listView=getListView();
		dbhelper=new WordDBHelper(this);
		Cursor c=dbhelper.query();
		String[] from={"wordid","word","detail"};
		int[] to={R.id.textid,R.id.textword,R.id.textdetail};
		SimpleCursorAdapter adapter=new SimpleCursorAdapter(getApplicationContext(),R.layout.list_item,c,from,to);
		listView.setAdapter(adapter);
		dbhelper.close();
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long arg3) {
				Intent intent=new Intent(getApplicationContext(), WordShowActivity.class);
				intent.putExtra("_id", position);
				startActivity(intent);
				
			}
		});
		

	}

}
