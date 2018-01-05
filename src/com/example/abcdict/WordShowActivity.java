package com.example.abcdict;




import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;

public class WordShowActivity extends Activity implements SensorEventListener {
	WordDBHelper dbHelper;
	private TextView word,detail;
	private Button btnprior,btnnext,btnedit;
	Cursor c ;
	SensorManager  sensorManger;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wordshow);
		dbHelper=new WordDBHelper(this);
		word=(TextView) findViewById(R.id.word);
		detail=(TextView) findViewById(R.id.detail);
		getWordByid();
		btnprior=(Button) findViewById(R.id.btnprior);
		btnnext=(Button) findViewById(R.id.btnnext);
		btnedit=(Button) findViewById(R.id.editbtn);

		sensorManger=(SensorManager) getSystemService(Context.SENSOR_SERVICE);
		sensorManger.registerListener(this, sensorManger.getDefaultSensor(Sensor.TYPE_PROXIMITY), sensorManger.SENSOR_DELAY_UI);




		btnprior.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(c.moveToPrevious()){
					word.setText(c.getString(c.getColumnIndex("word")));
					detail.setText(c.getString(c.getColumnIndex("detail")));
				}
				else
				{
					Toast.makeText(getApplicationContext(), "已经是第一条，别点了！", 3000).show();
				}
			}
		});
		btnnext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				nextWord();

			}

		});
		btnedit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(WordShowActivity.this,WordMainActivity.class);
				Bundle b=new Bundle();
				b.putString("word", word.getText().toString());
				b.putString("detail", detail.getText().toString());
				intent.putExtra("data",b);
				startActivity(intent);

			}
		});
		dbHelper.close();
	}
	private void getWordByid() {
		Intent intent=this.getIntent();
		int _id=intent.getIntExtra("_id",1);
		c = dbHelper.query();
		if(c.getCount()>0){
			c.moveToPosition(_id);
			word.setText(c.getString(c.getColumnIndex("word")));
			detail.setText(c.getString(c.getColumnIndex("detail")));
		}
		else
			Toast.makeText(getApplicationContext(), "当前数据库没有单词！", 3000).show();

	}
	protected void nextWord() {
		if(c.moveToNext()){
			word.setText(c.getString(c.getColumnIndex("word")));
			detail.setText(c.getString(c.getColumnIndex("detail")));
		}
		else
		{
			Toast.makeText(getApplicationContext(), "已经是最后一条，别点了！", 3000).show();
		}

	}
	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub

	}
	@Override
	public void onSensorChanged(SensorEvent event) {
		int instance;
		instance=(int) event.values[0];
		if(instance==0)
			nextWord();
	}

	@Override
	protected void onPause() {
		sensorManger.unregisterListener(this);
		super.onPause();
	}
	@Override
	protected void onStop() {
		sensorManger.unregisterListener(this);
		super.onStop();
	}

}
