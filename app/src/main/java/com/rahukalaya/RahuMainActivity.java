package com.rahukalaya;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.rahukalaya.common.Common;
import com.rahukalaya.common.MessageManager;
import com.rahukalaya.model.Result;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class RahuMainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    Boolean isBlue = false,isYellow = false,isGreen = false,isPurple = false,isRed = false,isNormal = true;
    RadioButton blue,dark_yellow,red,green,purple,default_color;
    private AdView adView;
    LinearLayout rahu_layout;
    TextView sunriseTextView,sunsetTextView,dateTextView,goodDirection,badDirection,rahuTimeRange;
    Button dayButton,nightButton,todayButton,mondayButton,tuesdayButton,wednesdayButton,thursdayButton,fridayButton,saturdayButton,sundayButton;
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rahu_main);

        sunriseTextView = findViewById(R.id.sun_raise_time);  dayButton = findViewById(R.id.day);             fridayButton = findViewById(R.id.friday);
        sunsetTextView = findViewById(R.id.sun_fall_time);    nightButton = findViewById(R.id.night);         saturdayButton = findViewById(R.id.saturday);
        dateTextView = findViewById(R.id.date_month);         todayButton = findViewById(R.id.today);         sundayButton = findViewById(R.id.sunday);
        goodDirection = findViewById(R.id.good_direction);    mondayButton = findViewById(R.id.monday);       adView = findViewById(R.id.adView);
        badDirection = findViewById(R.id.bad_direction);      tuesdayButton = findViewById(R.id.tuesday);     rahu_layout = findViewById(R.id.rahu_layout);
        rahuTimeRange = findViewById(R.id.rahu_time_range);   wednesdayButton = findViewById(R.id.wednesday);
        Toolbar toolbar = findViewById(R.id.search_bar);      thursdayButton = findViewById(R.id.thursday);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color='#ffffff'><b>රාහු කාලය</b></font>"));

        actionMethods();

        loadAda();

        com.rahukalaya.common.MessageManager.smsNotify(this);
        initAds();

    }
    @TargetApi(Build.VERSION_CODES.KITKAT)

    private void popupWindow(){
        final Dialog dialogs = new Dialog(RahuMainActivity.this);
        dialogs.setContentView(R.layout.theme_popup);
        TextView imgCross = dialogs.findViewById(R.id.close);

        red = dialogs.findViewById(R.id.red_ray);
        red.setOnCheckedChangeListener(this);

        blue = dialogs.findViewById(R.id.blue_ray);
        blue.setOnCheckedChangeListener(this);

        dark_yellow = dialogs.findViewById(R.id.yellow_ray);
        dark_yellow.setOnCheckedChangeListener(this);

        green = dialogs.findViewById(R.id.green_ray);
        green.setOnCheckedChangeListener(this);

        purple = dialogs.findViewById(R.id.purple_ray);
        purple.setOnCheckedChangeListener(this);

        default_color = dialogs.findViewById(R.id.default_ray);
        default_color.setOnCheckedChangeListener(this);

        imgCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogs.dismiss();
            }
        });
        dialogs.show();
    }
    private void loadAda() {
        Date date= new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        String dateStr=dateFormat.format(date);
        for(int i = 0; i< Common.resultList.size(); i++){
            Result r=Common.resultList.get(i);
            if(dateStr.equals(r.getDate())){
                setData(i,true);
                final int finalI = i;
                dayButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nightButton.setTextColor(Color.BLACK);
                        dayButton.setTextColor(Color.WHITE);
                        if (isNormal){
                            rahu_layout.setBackgroundResource(R.drawable.day_time);
                        }else if (isRed){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.theme_6);
                        }else if (isYellow){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.rahu_theme_1);
                        }else if (isGreen){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.rahu_theme_2);
                        }else if (isBlue){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.rahu_theme_3);
                        }else if (isPurple){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.rahu_theme_4);
                        }
                        dayButton.setBackgroundColor(Color.parseColor("#90000000"));
                        nightButton.setBackgroundColor(Color.parseColor("#90ffffff"));
                        setData(finalI,true);
                    }
                });
                final int finalI1 = i;
                nightButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nightButton.setTextColor(Color.WHITE);
                        dayButton.setTextColor(Color.BLACK);
                        if (isNormal){
                            rahu_layout.setBackgroundResource(R.drawable.night_time);
                        }else if (isRed){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.theme_6);
                        }else if (isYellow){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.rahu_theme_1);
                        }else if (isGreen){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.rahu_theme_2);
                        }else if (isBlue){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.rahu_theme_3);
                        }else if (isPurple){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.rahu_theme_4);
                        }
                        dayButton.setBackgroundColor(Color.parseColor("#90ffffff"));
                        nightButton.setBackgroundColor(Color.parseColor("#90000000"));
                        setData(finalI1,false);
                    }
                });
                break;
            }
        }

    }

    private void actionMethods() {
        todayButton.setOnClickListener(new View.OnClickListener() {

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
                setData(0,true);
                dayButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nightButton.setTextColor(Color.BLACK);
                        dayButton.setTextColor(Color.WHITE);
                        if (isNormal){
                            rahu_layout.setBackgroundResource(R.drawable.day_time);
                        }else if (isRed){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.theme_6);
                        }else if (isYellow){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.rahu_theme_1);
                        }else if (isGreen){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.rahu_theme_2);
                        }else if (isBlue){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.rahu_theme_3);
                        }else if (isPurple){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.rahu_theme_4);
                        }
                        dayButton.setBackgroundColor(Color.parseColor("#90000000"));
                        nightButton.setBackgroundColor(Color.parseColor("#90ffffff"));
                        setData(0,true);
                    }
                });
                nightButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nightButton.setTextColor(Color.WHITE);
                        dayButton.setTextColor(Color.BLACK);
                        if (isNormal){
                            rahu_layout.setBackgroundResource(R.drawable.night_time);
                        }else if (isRed){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.theme_6);
                        }else if (isYellow){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.rahu_theme_1);
                        }else if (isGreen){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.rahu_theme_2);
                        }else if (isBlue){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.rahu_theme_3);
                        }else if (isPurple){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.rahu_theme_4);
                        }
                        dayButton.setBackgroundColor(Color.parseColor("#90ffffff"));
                        nightButton.setBackgroundColor(Color.parseColor("#90000000"));
                        setData(0,false);
                    }
                });
            }

        });

        tuesdayButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setData(1,true);
                dayButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nightButton.setTextColor(Color.BLACK);
                        dayButton.setTextColor(Color.WHITE);
                        if (isNormal){
                            rahu_layout.setBackgroundResource(R.drawable.day_time);
                        }else if (isRed){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.theme_6);
                        }else if (isYellow){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.rahu_theme_1);
                        }else if (isGreen){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.rahu_theme_2);
                        }else if (isBlue){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.rahu_theme_3);
                        }else if (isPurple){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.rahu_theme_4);
                        }
                        dayButton.setBackgroundColor(Color.parseColor("#90000000"));
                        nightButton.setBackgroundColor(Color.parseColor("#90ffffff"));
                        setData(1,true);
                    }
                });
                nightButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nightButton.setTextColor(Color.WHITE);
                        dayButton.setTextColor(Color.BLACK);
                        if (isNormal){
                            rahu_layout.setBackgroundResource(R.drawable.night_time);
                        }else if (isRed){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.theme_6);
                        }else if (isYellow){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.rahu_theme_1);
                        }else if (isGreen){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.rahu_theme_2);
                        }else if (isBlue){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.rahu_theme_3);
                        }else if (isPurple){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.rahu_theme_4);
                        }
                        dayButton.setBackgroundColor(Color.parseColor("#90ffffff"));
                        nightButton.setBackgroundColor(Color.parseColor("#90000000"));
                        setData(1,false);
                    }
                });
            }

        });

        wednesdayButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setData(2,true);
                dayButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nightButton.setTextColor(Color.BLACK);
                        dayButton.setTextColor(Color.WHITE);
                        if (isNormal){
                            rahu_layout.setBackgroundResource(R.drawable.day_time);
                        }else if (isRed){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.theme_6);
                        }else if (isYellow){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.rahu_theme_1);
                        }else if (isGreen){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.rahu_theme_2);
                        }else if (isBlue){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.rahu_theme_3);
                        }else if (isPurple){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.rahu_theme_4);
                        }
                        dayButton.setBackgroundColor(Color.parseColor("#90000000"));
                        nightButton.setBackgroundColor(Color.parseColor("#90ffffff"));
                        setData(2,true);
                    }
                });
                nightButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nightButton.setTextColor(Color.WHITE);
                        dayButton.setTextColor(Color.BLACK);
                        if (isNormal){
                            rahu_layout.setBackgroundResource(R.drawable.night_time);
                        }else if (isRed){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.theme_6);
                        }else if (isYellow){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.rahu_theme_1);
                        }else if (isGreen){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.rahu_theme_2);
                        }else if (isBlue){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.rahu_theme_3);
                        }else if (isPurple){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.rahu_theme_4);
                        }
                        dayButton.setBackgroundColor(Color.parseColor("#90ffffff"));
                        nightButton.setBackgroundColor(Color.parseColor("#90000000"));
                        setData(2,false);
                    }
                });
            }

        });

        thursdayButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setData(3,true);
                dayButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nightButton.setTextColor(Color.BLACK);
                        dayButton.setTextColor(Color.WHITE);
                        if (isNormal){
                            rahu_layout.setBackgroundResource(R.drawable.day_time);
                        }else if (isRed){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.theme_6);
                        }else if (isYellow){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.rahu_theme_1);
                        }else if (isGreen){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.rahu_theme_2);
                        }else if (isBlue){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.rahu_theme_3);
                        }else if (isPurple){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.rahu_theme_4);
                        }
                        dayButton.setBackgroundColor(Color.parseColor("#90000000"));
                        nightButton.setBackgroundColor(Color.parseColor("#90ffffff"));
                        setData(3,true);
                    }
                });
                nightButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nightButton.setTextColor(Color.WHITE);
                        dayButton.setTextColor(Color.BLACK);
                        if (isNormal){
                            rahu_layout.setBackgroundResource(R.drawable.night_time);
                        }else if (isRed){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.theme_6);
                        }else if (isYellow){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.rahu_theme_1);
                        }else if (isGreen){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.rahu_theme_2);
                        }else if (isBlue){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.rahu_theme_3);
                        }else if (isPurple){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.rahu_theme_4);
                        }
                        dayButton.setBackgroundColor(Color.parseColor("#90ffffff"));
                        nightButton.setBackgroundColor(Color.parseColor("#90000000"));
                        setData(3,false);
                    }
                });
            }

        });

        fridayButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setData(4,true);
                dayButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nightButton.setTextColor(Color.BLACK);
                        dayButton.setTextColor(Color.WHITE);
                        if (isNormal){
                            rahu_layout.setBackgroundResource(R.drawable.day_time);
                        }else if (isRed){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.theme_6);
                        }else if (isYellow){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.rahu_theme_1);
                        }else if (isGreen){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.rahu_theme_2);
                        }else if (isBlue){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.rahu_theme_3);
                        }else if (isPurple){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.rahu_theme_4);
                        }
                        dayButton.setBackgroundColor(Color.parseColor("#90000000"));
                        nightButton.setBackgroundColor(Color.parseColor("#90ffffff"));
                        setData(4,true);
                    }
                });
                nightButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nightButton.setTextColor(Color.WHITE);
                        dayButton.setTextColor(Color.BLACK);
                        if (isNormal){
                            rahu_layout.setBackgroundResource(R.drawable.night_time);
                        }else if (isRed){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.theme_6);
                        }else if (isYellow){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.rahu_theme_1);
                        }else if (isGreen){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.rahu_theme_2);
                        }else if (isBlue){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.rahu_theme_3);
                        }else if (isPurple){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.rahu_theme_4);
                        }
                        dayButton.setBackgroundColor(Color.parseColor("#90ffffff"));
                        nightButton.setBackgroundColor(Color.parseColor("#90000000"));
                        setData(4,false);
                    }
                });
            }

        });

        saturdayButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setData(5,true);
                dayButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nightButton.setTextColor(Color.BLACK);
                        dayButton.setTextColor(Color.WHITE);
                        if (isNormal){
                            rahu_layout.setBackgroundResource(R.drawable.day_time);
                        }else if (isRed){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.theme_6);
                        }else if (isYellow){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.rahu_theme_1);
                        }else if (isGreen){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.rahu_theme_2);
                        }else if (isBlue){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.rahu_theme_3);
                        }else if (isPurple){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.rahu_theme_4);
                        }
                        dayButton.setBackgroundColor(Color.parseColor("#90000000"));
                        nightButton.setBackgroundColor(Color.parseColor("#90ffffff"));
                        setData(5,true);
                    }
                });
                nightButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nightButton.setTextColor(Color.WHITE);
                        dayButton.setTextColor(Color.BLACK);
                        if (isNormal){
                            rahu_layout.setBackgroundResource(R.drawable.night_time);
                        }else if (isRed){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.theme_6);
                        }else if (isYellow){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.rahu_theme_1);
                        }else if (isGreen){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.rahu_theme_2);
                        }else if (isBlue){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.rahu_theme_3);
                        }else if (isPurple){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.rahu_theme_4);
                        }
                        dayButton.setBackgroundColor(Color.parseColor("#90ffffff"));
                        nightButton.setBackgroundColor(Color.parseColor("#90000000"));
                        setData(5,false);
                    }
                });
            }

        });

        sundayButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setData(6,true);
                dayButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nightButton.setTextColor(Color.BLACK);
                        dayButton.setTextColor(Color.WHITE);
                        if (isNormal){
                            rahu_layout.setBackgroundResource(R.drawable.day_time);
                        }else if (isRed){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.theme_6);
                        }else if (isYellow){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.rahu_theme_1);
                        }else if (isGreen){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.rahu_theme_2);
                        }else if (isBlue){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.rahu_theme_3);
                        }else if (isPurple){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.rahu_theme_4);
                        }
                        dayButton.setBackgroundColor(Color.parseColor("#90000000"));
                        nightButton.setBackgroundColor(Color.parseColor("#90ffffff"));
                        setData(6,true);
                    }
                });
                nightButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nightButton.setTextColor(Color.WHITE);
                        dayButton.setTextColor(Color.BLACK);
                        if (isNormal){
                            rahu_layout.setBackgroundResource(R.drawable.night_time);
                        }else if (isRed){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.theme_6);
                        }else if (isYellow){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.rahu_theme_1);
                        }else if (isGreen){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.rahu_theme_2);
                        }else if (isBlue){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.rahu_theme_3);
                        }else if (isPurple){
                            goodDirection.setTextColor(Color.WHITE);
                            badDirection.setTextColor(Color.WHITE);
                            rahuTimeRange.setTextColor(Color.WHITE);
                            rahu_layout.setBackgroundResource(R.drawable.rahu_theme_4);
                        }
                        dayButton.setBackgroundColor(Color.parseColor("#90ffffff"));
                        nightButton.setBackgroundColor(Color.parseColor("#90000000"));
                        setData(6,false);
                    }
                });
            }

        });



    }

    @SuppressLint("SimpleDateFormat")
    private SimpleDateFormat timeFormat=new SimpleDateFormat("HH:mm");
    @SuppressLint("SetTextI18n")
    private void setData(int i, final Boolean isDay) {
        if(Common.resultList!=null){
            Result result= Common.resultList.get(i);
            Date date = new Date();
            @SuppressLint("SimpleDateFormat") DateFormat monthFormat = new SimpleDateFormat("MMMM");
            @SuppressLint("SimpleDateFormat") DateFormat yearFormat = new SimpleDateFormat("yyyy");
            String[] day_month_text = Common.resultList.get(i).getDate().split("-");

            dateTextView.setText(result.getDay() + " , " + day_month_text[2] + " " + monthFormat.format(date) + " " + yearFormat.format(date));
            try {
                sunriseTextView.setText(timeFormat.format(timeFormat.parse(result.getSunRaise())));
                sunsetTextView.setText(timeFormat.format(timeFormat.parse(result.getSunfall())));
                if (isDay){
                    rahuTimeRange.setText("AM " + timeFormat.format(timeFormat.parse(result.getDivaSita())) + " සිට " + " AM " + timeFormat.format(timeFormat.parse(result.getDivaDakva())));
                }else {
                    rahuTimeRange.setText("PM " + timeFormat.format(timeFormat.parse(result.getRathreeSita())) + " සිට " + " PM " + timeFormat.format(timeFormat.parse(result.getRathreeDakva())));
                }
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            goodDirection.setText("සුභ දිශාව - " + Common.getDishaRes(result.getSubaDisava()));
            badDirection.setText("මරු සිටින දිශාව - " +Common.getDishaRes(result.getMaruDisava()));
        }

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.rahu_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.power) {
            logout();
            return true;
        }
        if (id == R.id.theme){
            popupWindow();
        }
        return super.onOptionsItemSelected(item);
    }
    private void logout() {
        System.exit(0);
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == MessageManager.ACTION_CALL_CODE) {
            startActivity(MessageManager.getUSSDIntent());
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            if (buttonView.getId() == R.id.red_ray){
                blue.setChecked(false);
                green.setChecked(false);
                purple.setChecked(false);
                dark_yellow.setChecked(false);
                default_color.setChecked(false);

                isRed = true;isYellow = false;isGreen = false;isBlue = false;isPurple = false;isNormal = false;
                rahu_layout.setBackgroundResource(R.drawable.theme_6);
                goodDirection.setTextColor(Color.WHITE);
                badDirection.setTextColor(Color.WHITE);
                rahuTimeRange.setTextColor(Color.WHITE);
            }
            if (buttonView.getId() == R.id.yellow_ray) {
                blue.setChecked(false);
                green.setChecked(false);
                purple.setChecked(false);
                default_color.setChecked(false);
                red.setChecked(false);

                isRed = false;isYellow = true;isGreen = false;isBlue = false;isPurple = false;isNormal = false;
                rahu_layout.setBackgroundResource(R.drawable.rahu_theme_1);
                goodDirection.setTextColor(Color.WHITE);
                badDirection.setTextColor(Color.WHITE);
                rahuTimeRange.setTextColor(Color.WHITE);
            }
            if (buttonView.getId() == R.id.green_ray) {
                blue.setChecked(false);
                dark_yellow.setChecked(false);
                purple.setChecked(false);
                default_color.setChecked(false);
                red.setChecked(false);

                isRed = false;isYellow = false;isGreen = true;isBlue = false;isPurple = false;isNormal = false;
                rahu_layout.setBackgroundResource(R.drawable.rahu_theme_2);
                goodDirection.setTextColor(Color.WHITE);
                badDirection.setTextColor(Color.WHITE);
                rahuTimeRange.setTextColor(Color.WHITE);
            }
            if (buttonView.getId() == R.id.blue_ray) {
                dark_yellow.setChecked(false);
                green.setChecked(false);
                purple.setChecked(false);
                default_color.setChecked(false);
                red.setChecked(false);

                isRed = false;isYellow = false;isGreen = false;isBlue = true;isPurple = false;isNormal = false;
                rahu_layout.setBackgroundResource(R.drawable.rahu_theme_3);
                goodDirection.setTextColor(Color.WHITE);
                badDirection.setTextColor(Color.WHITE);
                rahuTimeRange.setTextColor(Color.WHITE);
            }
            if (buttonView.getId() == R.id.purple_ray) {
                dark_yellow.setChecked(false);
                green.setChecked(false);
                blue.setChecked(false);
                default_color.setChecked(false);
                red.setChecked(false);

                isRed = false;isYellow = false;isGreen = false;isBlue = false;isPurple = true;isNormal = false;
                rahu_layout.setBackgroundResource(R.drawable.rahu_theme_4);
                goodDirection.setTextColor(Color.WHITE);
                badDirection.setTextColor(Color.WHITE);
                rahuTimeRange.setTextColor(Color.WHITE);
            }
            if (buttonView.getId() == R.id.default_ray) {
                dark_yellow.setChecked(false);
                green.setChecked(false);
                blue.setChecked(false);
                isRed = false;
                default_color.setChecked(false);

                isRed = false;isYellow = false;isGreen = false;isBlue = false;isPurple = false;isNormal = true;
                rahu_layout.setBackgroundResource(R.drawable.day_time);
                goodDirection.setTextColor(Color.parseColor("#ff5555"));
                badDirection.setTextColor(Color.parseColor("#ff5555"));
                rahuTimeRange.setTextColor(Color.parseColor("#ff5555"));
            }
        }
    }
}
