package com.example.abcdict;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

public class RegistActivity extends Activity {
	private Button register,cancel;
	private ToggleButton marriged;
	private RadioButton male,female;
	private EditText username,password;
	private Spinner position;
	private Checkable reading,swimming,running;
	WordDBHelper dbhelper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		dbhelper=new WordDBHelper(this);
		setContentView(R.layout.activity_regist);
		username=(EditText) findViewById(R.id.username);
		password=(EditText) findViewById(R.id.password);
		register=(Button) findViewById(R.id.register);
		cancel=(Button) findViewById(R.id.cancle);
		marriged=(ToggleButton) findViewById(R.id.marriged);
		male=(RadioButton) findViewById(R.id.male);
		female=(RadioButton) findViewById(R.id.female);
		position=(Spinner) findViewById(R.id.position);
		String[] str={"CEO","CFO","PM"};
		ArrayAdapter aa=new ArrayAdapter(this, android.R.layout.simple_spinner_item,str);
		position.setAdapter(aa);
		reading=(Checkable) findViewById(R.id.reading);
		swimming=(Checkable) findViewById(R.id.swimming);
		running=(Checkable) findViewById(R.id.running);


		register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Bundle b=new Bundle();
				b.putString("username", username.getText().toString());
				b.putString("password", password.getText().toString());
				if(male.isChecked()){
					b.putString("gender", "男");
				}else{
					b.putString("gender", "女");
				}
				String temp="\n爱好：";
				if(reading.isChecked()){
					temp+="阅读";
				}
				if(swimming.isChecked()){
					temp+="、游泳";
				}
				if(running.isChecked()){
					temp+="、跑步";
				}
				b.putString("hobby", temp);
				if(marriged.isChecked()){
					b.putSerializable("marriged", "已婚");
				}else{
					b.putSerializable("marriged", "未婚");
				}
				b.putString("position", "\n职位："+position.getSelectedItem().toString());
				Toast.makeText(getApplicationContext(), "恭喜您，注册成功！\n您的注册信息如下："
						+b.getString("username")+b.getString("password")
						+b.getString("gender")+b.getString("hobby")
						+b.getString("marriged")+b.getString("position"),Toast.LENGTH_LONG).show();
				int  i=dbhelper.insertUser(b.getString("username"), b.getString("password"), b.getString("gender"), b.getString("marriged"), b.getString("hobby"), b.getString("position"));
				if(i!=0){
					Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
					//intent.putExtra("data", b);
					startActivity(intent);
				}
				else 
					Toast.makeText(getApplicationContext(), "失败", Toast.LENGTH_SHORT).show();
			}
		});
	}



}
