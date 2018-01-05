package com.example.abcdict;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;



public class GridViewActivity extends Activity {

	private List<Map<String, Object>> data_list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gridview);
		initView();
	}


	private void initView() {
		// TODO Auto-generated method stub
		GridView gridview=(GridView)this.findViewById(R.id.mainlist);
		String [] from = {"image","text"};
		int [] to = {R.id.image,R.id.text};
		data_list = new ArrayList<Map<String,Object>>();
		data_list = getData();
		SimpleAdapter sim_adapter = new SimpleAdapter(getApplicationContext(),data_list,R.layout.gridview_item,from,to);

		gridview.setAdapter(sim_adapter);
		gridview.setOnItemClickListener(new OnItemClickListener(){


			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent;
				switch(position){
				case 0:
					intent = new Intent(getApplicationContext(),WordMainActivity.class);
					startActivity(intent);

					break;
				case 1:
					intent = new Intent(getApplicationContext(),WordListShow.class);
					startActivity(intent);
					break;

				case 2:
					intent = new Intent(getApplicationContext(),WordListDropDown.class);
					startActivity(intent);
					break;
				}
			}
		});

	}

	public List<Map<String, Object>> getData(){	
		int[] icon = { R.drawable.ringtone,R.drawable.clock,R.drawable.weather,R.drawable.youtube};
		String[] iconName = { "单词管理", "查看单词", "单词下载刷新","学习日历"};
		//icon和iconName的长度是相同的，这里任选其一都可以
		for(int i=0;i<icon.length;i++){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("image", icon[i]);
			map.put("text", iconName[i]);
			data_list.add(map);
		}

		return data_list;
	}

}