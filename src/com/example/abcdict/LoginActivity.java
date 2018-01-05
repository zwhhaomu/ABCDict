package com.example.abcdict;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity implements SensorEventListener  {
	private Button loginBtn,regBtn;
	private EditText username,password;
	WordDBHelper dbhelper;
	//1.获取传感器服务
	//在LoginActivity声明SensorManager 对象：
	private SensorManager sensorManager;
	//onCreate方法中添加：


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		loginBtn=(Button) findViewById(R.id.loginButton);
		regBtn=(Button) findViewById(R.id.regButton);
		username=(EditText) findViewById(R.id.username);
		password=(EditText) findViewById(R.id.password);
		dbhelper=new WordDBHelper(getBaseContext());
		sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
		/*
		 * ctrl+1错误提示
		 * alt+/ 代码提示
		 * */


		loginBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				islogin();
				
			}

		});
		regBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "即将进入注册页面！", Toast.LENGTH_LONG).show();	
				Intent intent=new Intent(getApplicationContext(),RegistActivity.class);
				startActivity(intent);
			}
		});
	}
	@Override
	protected void onResume() {
		sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY),SensorManager.SENSOR_DELAY_UI);
		super.onResume();
	}


	protected void islogin() {
		String usernamestr=username.getText().toString();
		String passwordstr=password.getText().toString();
		if(dbhelper.userLogin(usernamestr, passwordstr)){
			Toast.makeText(getApplicationContext(), "恭喜你登录成功！", Toast.LENGTH_LONG).show();
			Intent intent=new Intent();
			intent.setClass(LoginActivity.this, GridViewActivity.class);
			startActivity(intent);
		}
		else
			Toast.makeText(getApplicationContext(), "登录失败！", Toast.LENGTH_LONG).show();


	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		sensorManager.unregisterListener(this);
		super.onDestroy();
		
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		sensorManager.unregisterListener(this);
		super.onPause();
	}
	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub

	}
	@Override
	public void onSensorChanged(SensorEvent event) {
		int instance=(int) event.values[0];
		if(instance==0)
			islogin();

	}


}
