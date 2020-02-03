package com.rahukalaya.common;

import java.util.Arrays;

import com.rahukalaya.R;

import android.R.bool;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.telephony.gsm.SmsManager;
import android.widget.Button;
import android.widget.LinearLayout;

public class MessageManager {
	
	public final static int ACTION_CALL_CODE = 100;

	public static void smsNotify(Activity activity) {

		String msgContent = "Dinapatha rahu kalaya sms magin dana ganeemata kamathida? 1.25 LKR +Tax";
		String s = getServiceProvider(activity);
		String msg = "REG SINASTRO";
		if (s.equalsIgnoreCase("Airtel")|| s.equalsIgnoreCase("Hutch")|| s.equalsIgnoreCase("DIALOG") || s.equalsIgnoreCase("SRI DIALOG") || s.equalsIgnoreCase("413002")) {
			alertUSSD(activity);
		} else if (s.equalsIgnoreCase("Mobitel")) {
			alert(activity,"SMS on Mobitel mobile", msgContent, "2244", msg);
		} 
	}

	public static String getServiceProvider(Activity activity) {

		TelephonyManager manager = (TelephonyManager)
		activity.getSystemService(Context.TELEPHONY_SERVICE);
		String carrierName = manager.getSimOperatorName(); 
		return carrierName;
	}

	private static void alert(final Activity activity ,String title, String msg, final String number, final String regMsg) {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setTitle(title);
		builder.setMessage(msg);

		builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				// Do nothing but close the dialog
				sendSmsByManager(activity,number, regMsg);
				dialog.dismiss();
			}

		});

		builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// Do nothing
				dialog.dismiss();
			}
		});

		AlertDialog alert = builder.create();
		alert.show();
	}

	public static void sendSmsByManager(Activity activity,String number, String regMsg) {
		Uri uri = Uri.parse("smsto:" + number);
        Intent intent = new Intent("android.intent.action.SENDTO", uri);
        intent.putExtra("sms_body", regMsg);
        activity.startActivity(intent);
	}
	
	public static void alertUSSD(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(activity.getResources().getString(R.string.app_name));
        builder.setMessage("Dinapatha rahu kalaya ha sathiye suba nakath sms magin dana ganeemata kamathida? 5LKR+Tax P/D");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (ContextCompat.checkSelfPermission(activity.getApplicationContext(), "android.permission.CALL_PHONE") != 0) {
                    ActivityCompat.requestPermissions(activity, new String[]{"android.permission.CALL_PHONE"}, ACTION_CALL_CODE);
                } else {
                    activity.startActivity(getUSSDIntent());
                }
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        final AlertDialog alert = builder.create();
        alert.setOnShowListener(new DialogInterface.OnShowListener() {
            public void onShow(DialogInterface d) {
                Button posButton = alert.getButton(-1);
                Button negButton = alert.getButton(-2);
                LinearLayout linearLayout = (LinearLayout)posButton.getParent();
                linearLayout.setGravity(1);
                linearLayout.setWeightSum(2.0F);
                int btnwidth = linearLayout.getWidth() / 2 - linearLayout.getWidth() / 20;
                posButton.setWidth(btnwidth);
                negButton.setWidth(btnwidth);
            }
        });
        alert.show();
    }
	
	 public static Intent getUSSDIntent() {
	        Intent callIntent = new Intent(Intent.ACTION_CALL,ussdToCallableUri("#780*942#"));
	        return callIntent;
	    }


	    private static Uri ussdToCallableUri(String ussd) {

	        StringBuilder uriString = new StringBuilder();

	        if (!ussd.startsWith("tel:"))
	            uriString.append("tel:");

	        for (char c : ussd.toCharArray()) {

	            if (c == '#')
	                uriString.append(Uri.encode("#"));
	            else
	                uriString.append(c);
	        }

	        return Uri.parse(uriString.toString());
	    }
}
