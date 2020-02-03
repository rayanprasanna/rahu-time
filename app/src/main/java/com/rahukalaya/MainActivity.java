package com.rahukalaya;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.rahukalaya.common.Common;
import com.rahukalaya.model.Result;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
	

	private AdView adView;
	//private InterstitialAd interstitial;

	private ImageButton mondayButton;
	private ImageButton tuesdayButton;
	private ImageButton wednesdayButton;
	private ImageButton thursdayButton;
	private ImageButton fridayButton;
	private ImageButton saturdayButton;
	private ImageButton sundayButton;
	private ImageButton adaButton;
	private TextView dateText;
	private TextView iraudawaText;
	private TextView irabasimaText;
	private TextView divaSitaText;
	private TextView divaDakvaText;
	private TextView rathreeSitaText;
	private TextView rathreeDakvaText;
	private ImageView subaImageView;
	private ImageView maruImageView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		adView = (AdView) findViewById(R.id.adView);
		adaButton = (ImageButton) findViewById(R.id.adaButton);
		mondayButton = (ImageButton) findViewById(R.id.mondayButton);
		tuesdayButton = (ImageButton) findViewById(R.id.tuesdayButton);
		wednesdayButton = (ImageButton) findViewById(R.id.wednesdayButton);
		thursdayButton = (ImageButton) findViewById(R.id.thursdayButton);
		fridayButton = (ImageButton) findViewById(R.id.fridayButton);
		saturdayButton = (ImageButton) findViewById(R.id.saturdayButton);
		sundayButton = (ImageButton) findViewById(R.id.sundayButton);
		dateText = (TextView) findViewById(R.id.dateText);
		iraudawaText = (TextView) findViewById(R.id.iraudawaText);
		irabasimaText = (TextView) findViewById(R.id.irabasimaText);
		divaSitaText = (TextView) findViewById(R.id.divaSitaText);
		divaDakvaText = (TextView) findViewById(R.id.divaDakvaText);
		rathreeSitaText = (TextView) findViewById(R.id.rathreeSitaText);
		rathreeDakvaText = (TextView) findViewById(R.id.rathreeDakvaText);
		subaImageView = (ImageView) findViewById(R.id.subaImageView);
		maruImageView = (ImageView) findViewById(R.id.maruImageView);

		setTitle("");
		getSupportActionBar().setBackgroundDrawable(
				new BitmapDrawable(BitmapFactory.decodeResource(getResources(), R.drawable.app_title)));
		actionMethods();
		
		loadAda();
		
		com.rahukalaya.common.MessageManager.smsNotify(this);
		initAds();
		

	}

	private void loadAda() {
		Date date= new Date();
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
		String dateStr=dateFormat.format(date);
		for(int i=0;i<Common.resultList.size();i++){
			Result r=Common.resultList.get(i);
			if(dateStr.equals(r.getDate())){
				setData(i);
				break;
			}
		}
		
	}

	private void actionMethods() {
		adaButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				loadAda();
				/*if (interstitial.isLoaded()) {
					interstitial.show();
				}*/
			}

		});
		
		mondayButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				setData(0);
				
			}

		});
		
		tuesdayButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				setData(1);

			}

		});
		
		wednesdayButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				setData(2);

			}

		});
		
		thursdayButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				setData(3);

			}

		});
		
		fridayButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				setData(4);

			}

		});
		
		saturdayButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				setData(5);

			}

		});
		
		sundayButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				setData(6);

			}

		});
		
	

	}

	private SimpleDateFormat timeFormat=new SimpleDateFormat("HH:mm");
	private void setData(int i) {
		if(Common.resultList!=null){
			Result result= Common.resultList.get(i);
			dateText.setText(result.getDate());
			try {
				iraudawaText.setText(timeFormat.format(timeFormat.parse(result.getSunRaise())));
				irabasimaText.setText(timeFormat.format(timeFormat.parse(result.getSunfall())));
				divaSitaText.setText(timeFormat.format(timeFormat.parse(result.getDivaSita())));
				divaDakvaText.setText(timeFormat.format(timeFormat.parse(result.getDivaDakva())));
				rathreeSitaText.setText(timeFormat.format(timeFormat.parse(result.getRathreeSita())));
				rathreeDakvaText.setText(timeFormat.format(timeFormat.parse(result.getRathreeDakva())));
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//subaImageView.setImageResource(Common.getDishaRes(result.getSubaDisava()));
			//maruImageView.setImageResource(Common.getDishaRes(result.getMaruDisava()));
		}

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case R.id.action_close:
			this.finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void initAds() {
		AdRequest adRequest = new AdRequest.Builder().build();
		adView.loadAd(adRequest);

		/*interstitial = new InterstitialAd(MainActivity.this);
		interstitial.setAdUnitId(getResources().getString(R.string.ad_unit_inst));
		AdRequest adInsRequest = new AdRequest.Builder().build();
		interstitial.loadAd(adInsRequest);*/

	}
	

	@Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        switch (requestCode) {

            case com.rahukalaya.common.MessageManager.ACTION_CALL_CODE:
                startActivity(com.rahukalaya.common.MessageManager.getUSSDIntent());
                break;
            
        }
    }

}
