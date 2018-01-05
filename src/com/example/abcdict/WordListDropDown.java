package com.example.abcdict;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.util.HttpUtil;
import com.example.util.StreamTool;



import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class WordListDropDown extends Activity {
	private static final String TAG = "ASYNC_TASK";
	private ListView listView;
	private Button mybtn;
	ListView mytestView;
	List<HashMap<String, Object>> list;
	String category="С4";
	private final int WORDCOUNT = 20; 					
	private int startno=1;
	WordDBHelper worddbhelper;
	private MyTask mTask;
	private ProgressBar progressBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		setContentView(R.layout.activity_wordlistdropdown);
		worddbhelper=new WordDBHelper(getApplication());
		mybtn=(Button) findViewById(R.id.mybtn);
		progressBar = (ProgressBar) findViewById(R.id.progress_bar);
		list=new ArrayList<HashMap<String,Object>>();
		mybtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//refreshdata();
				
				
				mTask = new MyTask();
				String path=HttpUtil.BASE_URL+"GetWordListByCategoryServlet?category"+category+"+&count="+20+"&startWidStr="+0+"&format=json";		
				mTask.execute(path);
				Toast.makeText(getApplication(), "����ˢ�³ɹ���", Toast.LENGTH_SHORT).show();
				

			}
		});
		listView = (ListView) findViewById(R.id.mylistviewtest);
		listView.setCacheColorHint(Color.TRANSPARENT);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {	

				Intent intent=new Intent(getApplicationContext(), WordShowActivity.class);
				intent.putExtra("_id", position);
				startActivity(intent);


			}
		});
		//refreshdata();

	}

	private void refreshdata() {
		
		list=getWordData(category,WORDCOUNT,startno);
		String[] from = { "id","word", "detail"};
		int[] to = {R.id.textid, R.id.textword, R.id.textdetail};
		SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),
				list, R.layout.wordlist_row, from, to);
		listView.setAdapter(adapter);	
	}

	private List<HashMap<String, Object>> getWordData(String category,int count,int startnum) {
		String path=HttpUtil.BASE_URL+"GetWordListByCategoryServlet?category"+category+"+&count="+count+"&startWidStr="+startnum+"&format=json";		
		URL url;
		try {
			url = new URL(path);
			URLConnection conn=url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			InputStream inStream=conn.getInputStream();
			byte[] data=StreamTool.read(inStream);
			String json=new String(data);
			JSONObject jsonObject = new JSONObject(json);
			int retCode = jsonObject.getInt("ret");
			if (retCode == 0) {
				JSONObject dataObj = jsonObject.getJSONObject("data");

				int totalNum = dataObj.getInt("totalnum");
				if (totalNum > 0) {

					JSONArray wordList = dataObj.getJSONArray("wordlist");

					for (int i = 0; i < wordList.length(); i++) {
						JSONObject word = (JSONObject) wordList
								.opt(i);
						HashMap<String, Object> hashMap = new HashMap<String, Object>();
						hashMap.put("id", word.getInt("id"));
						hashMap.put("word", word.getString("word"));
						hashMap.put("detail", word.getString("detail"));
						hashMap.put("ping", word.getString("ping"));

						list.add(hashMap);

						ContentValues values=new ContentValues();
						values.put("wordid", word.getInt("id"));
						values.put("word", word.getString("word"));
						values.put("detail", word.getString("detail"));
						worddbhelper.insertWord(values);
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	private class MyTask extends AsyncTask<String, Integer, String> {
		//onPreExecute����������ִ�к�̨����ǰ��һЩUI����
		@Override
		protected void onPreExecute() {
			Log.i(TAG, "onPreExecute() called");
			//textView.setText("loading...");
		}

		//doInBackground�����ڲ�ִ�к�̨����,�����ڴ˷������޸�UI
		@Override
		protected String doInBackground(String... params) {		
			Log.i(TAG, "doInBackground(Params... params) called");
			try {
				HttpClient client = new DefaultHttpClient();
				HttpGet get = new HttpGet(params[0]);
				HttpResponse response = client.execute(get);
				
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					HttpEntity entity = response.getEntity();
					InputStream is = entity.getContent();
					long total = entity.getContentLength();
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					byte[] buf = new byte[1024];
					int count = 0;
					int length = -1;
					//StreamTool.read(is);
					while ((length = is.read(buf)) != -1) {
						baos.write(buf, 0, length);
						count += length;
						//����publishProgress��������,���onProgressUpdate��������ִ��
						publishProgress((int) ((count / total) ));
						//Ϊ����ʾ����,����500����
						Thread.sleep(500);
					}
					return new String(baos.toByteArray(), "utf-8");
				}
			} catch (Exception e) {
				Log.e(TAG, e.getMessage());
			}
			return null;
		}

		//onProgressUpdate�������ڸ��½�����Ϣ
		@Override
		protected void onProgressUpdate(Integer... progresses) {
			Log.i(TAG, "onProgressUpdate(Progress... progresses) called");
			progressBar.setProgress(progresses[0]);
			mybtn.setText("loading..." + progresses[0] + "%");
		}

		//onPostExecute����������ִ�����̨��������UI,��ʾ���
		@Override
		protected void onPostExecute(String data) {
			Log.i(TAG, "onPostExecute(Result result) called");
			String json=new String(data);
			JSONObject jsonObject;
			try {
				jsonObject = new JSONObject(json);
				int retCode = jsonObject.getInt("ret");
				if (retCode == 0) {
					JSONObject dataObj = jsonObject.getJSONObject("data");

					int totalNum = dataObj.getInt("totalnum");
					if (totalNum > 0) {

						JSONArray wordList = dataObj.getJSONArray("wordlist");

						for (int i = 0; i < wordList.length(); i++) {
							JSONObject word = (JSONObject) wordList
									.opt(i);
							ContentValues values=new ContentValues();
							values.put("wordid", word.getInt("id"));
							values.put("word", word.getString("word"));
							values.put("detail", word.getString("detail"));
							worddbhelper.insertWord(values);
							
							HashMap<String, Object> hashMap = new HashMap<String, Object>();
							hashMap.put("id", word.getInt("id"));
							hashMap.put("word", word.getString("word"));
							hashMap.put("detail", word.getString("detail"));
							hashMap.put("ping", word.getString("ping"));

							list.add(hashMap);	
						}
						String[] from = { "id","word", "detail"};
						int[] to = {R.id.textid, R.id.textword, R.id.textdetail};
						SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),
								list, R.layout.wordlist_row, from, to);
						
						listView.setAdapter(adapter);
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mybtn.setText("�������");
			
		}

		//onCancelled����������ȡ��ִ���е�����ʱ����UI
		@Override
		protected void onCancelled() {
			Log.i(TAG, "onCancelled() called");
			mybtn.setText("cancelled");
			progressBar.setProgress(0);
		}
	}
}
