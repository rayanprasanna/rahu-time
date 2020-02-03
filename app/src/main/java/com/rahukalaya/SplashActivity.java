package com.rahukalaya;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.NetworkError;
import com.android.volley.error.NoConnectionError;
import com.android.volley.error.ParseError;
import com.android.volley.error.ServerError;
import com.android.volley.error.TimeoutError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.rahukalaya.common.Common;
import com.rahukalaya.common.JSONParser;
import com.rahukalaya.model.AppController;
import com.rahukalaya.model.Result;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Vector;

public class SplashActivity extends Activity {
    List<Result> resultList = new Vector<>();
    String URL = "http://universlsoftware.com/appsadmin/appspanel/index.php/astro_rahu/feed";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		startMain();
	}

	int progressBarStatus = 0;

	private void startMain() {
		if (Common.checkNetwork(SplashActivity.this)) {
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
					StrictMode.setThreadPolicy(policy);
					ResultAsynTask task = new ResultAsynTask(SplashActivity.this);
					task.execute(URL);
					//getData();
				}
			}, 4000);

		} else {
			dataAlert();
			new Thread(new Runnable() {
				@Override
				public void run() {

					while (progressBarStatus < 10) {

						if (Common.checkNetwork(SplashActivity.this)) {
							progressBarStatus++;
						}

						if (progressBarStatus == 10) {
							StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
							StrictMode.setThreadPolicy(policy);
							ResultAsynTask task = new ResultAsynTask(SplashActivity.this);
							task.execute(URL);
							//getData();
						}

					}

				}
			}).start();
		}

	}

	public void dataAlert() {

		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
		dialogBuilder.setTitle("Rahu Kalaya");
		dialogBuilder.setMessage("An app wants to turn on Mobile Data.");
		dialogBuilder.setCancelable(false);
		dialogBuilder.setNegativeButton("Deny", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				SplashActivity.this.finish();
				System.exit(0);

			}
		});
		dialogBuilder.setPositiveButton("Allow", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				//boolean res = Common.dataEnabled(SplashActivity.this, true);
			}
		});

		dialogBuilder.create().show();
	}

	@SuppressLint("StaticFieldLeak")
	public class ResultAsynTask extends AsyncTask<String, Void, List<Result>> {
		private Activity context;

		ResultAsynTask(Activity context) {
			this.context = context;
		}

		@Override
		protected void onPreExecute() {

			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(List<Result> resultList) {
			if (resultList == null) {
				Toast.makeText(context, "Check internet connection!", Toast.LENGTH_LONG).show();
				finish();
			} else {
				Common.resultList=resultList;
				Intent mainIntent = new Intent(SplashActivity.this, RahuMainActivity.class);
				startActivity(mainIntent);
				SplashActivity.this.finish();
			}
		}

		@Override
		protected List<Result> doInBackground(String... urls) {
			List<Result> resultList = new Vector<>();
			String response;

			for (String url : urls) {
				try {
					response = JSONParser.getResponseFromUrl(url);

					JSONObject jsonRootObject = new JSONObject(response);
					// Get the instance of JSONArray that contains
					// JSONObjects
					JSONArray jsonArray = jsonRootObject.optJSONArray("results");
					// Iterate the jsonArray and print the info of
					// JSONObjects
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject = jsonArray.getJSONObject(i);

						Result result = new Result();
						result.setDay(jsonObject.optString("day"));
						result.setDate(jsonObject.optString("date"));
						result.setSunRaise(jsonObject.optString("sun_raise_time"));
						result.setSunfall(jsonObject.optString("sun_fall_time"));
						result.setDivaSita(jsonObject.optString("day_rahu_start_time"));
						result.setDivaDakva(jsonObject.optString("day_rahu_end_time"));
						result.setRathreeSita(jsonObject.optString("night_rahu_start_time"));
						result.setRathreeDakva(jsonObject.optString("night_rahu_end_time"));
						result.setSubaDisava(jsonObject.optInt("subha_dishawa"));
						result.setMaruDisava(jsonObject.optInt("maru_dishawa"));
						resultList.add(result);
					}

					System.err.println(">>>>>>>>>>>>>>>>>>>>>>>>>> Data Size : <<<<<<<<<<<<<<<<<<<<<<<<<<<<" + resultList.size());
				} catch (Exception e) {
					//finish();
					e.printStackTrace();
				}

			}

			return resultList;
		}
	}
	public void getData(){
		final ProgressDialog progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Loading...");
		progressDialog.show();
        //private final int SPLASH_DISPLAY_LENGTH = 3000;
        StringRequest stringRequest = new StringRequest(
				Request.Method.GET,
                URL,
				new Response.Listener<String>() {
					@SuppressLint("SetTextI18n")
					@Override
					public void onResponse(String response) {
						//Here is main response object
						JSONObject json;
						try {
							json = new JSONObject(response);
							JSONArray rahu_results = json.getJSONArray("results");

							if(rahu_results.length ()> 0){

								for(int countItem = 0; countItem < rahu_results.length(); countItem++){

									JSONObject rahuTimeObject = rahu_results.getJSONObject(countItem);

									Result rahuTime = new Result();

									rahuTime.setDay(rahuTimeObject.isNull("day")?"":rahuTimeObject.optString("day"));
									rahuTime.setDate(rahuTimeObject.isNull("date")?"":rahuTimeObject.optString("date"));
									rahuTime.setSunRaise(rahuTimeObject.isNull("sun_raise_time")?"":rahuTimeObject.optString("sun_raise_time"));
									rahuTime.setSunfall(rahuTimeObject.isNull("sun_fall_time")?"":rahuTimeObject.optString("sun_fall_time"));
									rahuTime.setDivaSita(rahuTimeObject.isNull("day_rahu_start_time")?"":rahuTimeObject.optString("day_rahu_start_time"));
									rahuTime.setDivaDakva(rahuTimeObject.isNull("day_rahu_end_time")?"":rahuTimeObject.optString("day_rahu_end_time"));
									rahuTime.setRathreeSita(rahuTimeObject.isNull("night_rahu_start_time")?"":rahuTimeObject.optString("night_rahu_start_time"));
									rahuTime.setRathreeDakva(rahuTimeObject.isNull("night_rahu_end_time")?"":rahuTimeObject.optString("night_rahu_end_time"));
									rahuTime.setSubaDisava(rahuTimeObject.optInt("suba_dishawa"));
									rahuTime.setMaruDisava(rahuTimeObject.optInt("maru_dishawa"));
									resultList.add(rahuTime);
								}
                                if (resultList == null) {
                                    Toast.makeText(SplashActivity.this, "Check internet connection!", Toast.LENGTH_LONG).show();
                                    finish();
                                } else {
                                    Common.resultList=resultList;
                                    Intent mainIntent = new Intent(SplashActivity.this, RahuMainActivity.class);
                                    startActivity(mainIntent);
                                    SplashActivity.this.finish();
                                }
							}
						} catch (JSONException e) {
							e.printStackTrace();
							progressDialog.dismiss();
						}
						progressDialog.dismiss();
					}
				},
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						// Do something when get error
						AppController.getInstance().getRequestQueue().stop();
						Log.e("Volley", error.toString());
						progressDialog.dismiss();
						if (error instanceof TimeoutError || error instanceof NoConnectionError) {
							System.out.println("Request Type: " + "TimeoutError");
						} else if (error instanceof AuthFailureError) {
							System.out.println("Request Type: " + "AuthFailureError");
						} else if (error instanceof ServerError) {
							System.out.println("Request Type: " + "ServerError");
						} else if (error instanceof NetworkError) {
							System.out.println("Request Type: " + "NetworkError");
						} else if (error instanceof ParseError) {
							System.out.println("Request Type: " + "ParseError");
						}
					}
				}
		);

		// Add StringRequest to the RequestQueue
		stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		AppController.getInstance().getRequestQueue();
		AppController.getInstance().addToRequestQueue(stringRequest);
	}
}
