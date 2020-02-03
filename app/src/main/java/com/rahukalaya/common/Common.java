package com.rahukalaya.common;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import com.rahukalaya.R;
import com.rahukalaya.model.Result;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;



public class Common {
	public static List<Result> resultList;
	private static  String[] dishaRes={"උතුර","ඊසාන","නැගෙනහිර","ගිණිකොන","දකුණ","නිරිත","බස්නාහිර","වයඹ"};
	public static String getDishaRes(int i){
		return dishaRes[i];
	}

	public static boolean dataEnabled(Context context, boolean enabled) {
		try {
			final ConnectivityManager conman = (ConnectivityManager) context.getApplicationContext()
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			final Class conmanClass = Class.forName(conman.getClass().getName());
			final Field iConnectivityManagerField = conmanClass.getDeclaredField("mService");
			iConnectivityManagerField.setAccessible(true);
			final Object iConnectivityManager = iConnectivityManagerField.get(conman);
			final Class iConnectivityManagerClass = Class.forName(iConnectivityManager.getClass().getName());
			final Method setMobileDataEnabledMethod = iConnectivityManagerClass
					.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
			setMobileDataEnabledMethod.setAccessible(true);
			setMobileDataEnabledMethod.invoke(iConnectivityManager, enabled);

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static  boolean checkNetwork(Context context) {
		ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			return true;
		} else {
			return false;
		}
	}
}
