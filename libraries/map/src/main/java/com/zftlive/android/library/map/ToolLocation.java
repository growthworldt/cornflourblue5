/*
 *     Android基础开发个人积累、沉淀、封装、整理共通
 *     Copyright (c) 2016. 曾繁添 <zftlive@163.com>
 *     Github：https://github.com/zengfantian || http://git.oschina.net/zftlive
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package com.zftlive.android.library.map;

import android.app.Application;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

/**
 * 地理位置相关工具类（百度地图API）
 * 
 * @author 曾繁添
 * 
 */
public class ToolLocation {
	
	// 须在主线程中声明,Context需要时全进程有效的context
//	public static LocationClient mLocClient = null;

	private static ToolLocation instance;

	private Context mContext;

	private ToolLocation(){

	}

	public static ToolLocation getInstance(Application mContext){
		if(null == instance){
			instance = new ToolLocation();
//			mLocClient = new LocationClient(mContext);
		}
		return instance;
	}

	/**
	 * 判断GPS是否打开
	 * 
	 * @return
	 */
	public static boolean isGpsOPen(Context mContext) {
		LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
		// 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
		boolean isGpsOkay = locationManager
				.isProviderEnabled(LocationManager.GPS_PROVIDER);
		if (isGpsOkay) {
			return true;
		} else {
			return false;
		}
	}

	/**
     * 强制打开GPS
     * @param context
     */ 
    public static void forceOpenGPS(Context context) {
    	//4.0++
    	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
    		Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE"); 
    	    intent.putExtra("enabled", true); 
    	    context.sendBroadcast(intent); 
    	 
    	   String provider = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED); 
    	   if(!provider.contains("gps")){ //if gps is disabled  
    	       final Intent poke = new Intent(); 
    	       poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");  
    	       poke.addCategory(Intent.CATEGORY_ALTERNATIVE); 
    	       poke.setData(Uri.parse("3"));  
    	       context.sendBroadcast(poke); 
    	   } 
    	}else{
            Intent GPSIntent = new Intent(); 
            GPSIntent.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider"); 
            GPSIntent.addCategory("android.intent.category.ALTERNATIVE"); 
            GPSIntent.setData(Uri.parse("custom:3")); 
            try { 
                PendingIntent.getBroadcast(context, 0, GPSIntent, 0).send(); 
            } catch (CanceledException e) { 
            } 
    	}
    }
	
//	/**
//	 * 请求定位，附带地址信息
//	 * @param callback 定位成功回调函数
//	 * @param isLocOnce 是否只定位一次，true的情况执行完回调函数之后，将不再监听定位，反之，一直监听定位，需要手动停止定位
//	 * 示例代码如下：<br>
//	 * <per>
//		protected void onDestroy() {
//			ToolLocation.stopLocation();
//			super.onDestroy();
//		}
//	 * </per>
//	 */
//	public static void requestLocation(Context mContext,final InterfaceBDLocation callback,
//			final boolean isLocOnce) {
//		mLocClient.registerLocationListener(new BDLocationListener() {
//			@Override
//			public void onReceiveLocation(BDLocation location) {
//				if (null == location || null == callback)
//					return;
//				callback.onLocationSuccess(location);
//				if (isLocOnce)
//					mLocClient.stop();
//			}
//		});
//		LocationClientOption options = configOptions(mContext);
//		mLocClient.setLocOption(options);
//		mLocClient.start();
//	}
//
//	/**
//	 * 停止请求
//	 */
//	public static void stopLocation(){
//		if(mLocClient.isStarted()){
//			ToolLocation.mLocClient.stop();
//		}
//	}
//
//	private static LocationClientOption configOptions(Context mContext) {
//		// 定位相关参数
//		LocationClientOption option = new LocationClientOption();
//		// 设置定位模式
//		/**
//		 * 定位模式分为： 高精度定位模式-->这种定位模式下，会同时使用网络定位和GPS定位，优先返回最高精度的定位结果；
//		 * 低功耗定位模-->这种定位模式下，不会使用GPS，只会使用网络定位（Wi-Fi和基站定位）
//		 * 仅设备定位模式-->这种定位模式下，不需要连接网络，只使用GPS进行定位，这种模式下不支持室内环境的定位
//		 */
//		if (isGpsOPen(mContext)) {
//			option.setLocationMode(LocationMode.Hight_Accuracy);
//		} else {
//			if (isNetworkConnected(mContext)) {
//				option.setLocationMode(LocationMode.Battery_Saving);
//			}
//		}
//		// 设置是否需要地址信息，默认为无地址
//		option.setIsNeedAddress(true);
//		// 设置是否打开gps，使用gps前提是用户硬件打开gps，默认是不打开gps的
//		if (isGpsOPen(mContext)) {
//			option.setOpenGps(true);
//		}
//		/**
//		 * 设置坐标类型：
//		 * 我们支持返回若干种坐标系，包括国测局坐标系、百度坐标系，需要更多坐标系请联系我们，需要深度合作。目前这些参数的代码为。因此需要在请求时指定类型
//		 * ，如果不指定，默认返回百度坐标系。注意当仅输入IP时，不会返回坐标。目前这些参数的代码为
//		 * 返回国测局经纬度坐标系 coor=gcj02
//		 * 返回百度墨卡托坐标系 coor=bd09
//		 * 返回百度经纬度坐标系 coor=bd09ll
//		 * 百度手机地图对外接口中的坐标系默认是bd09ll，如果配合百度地图产品的话，需要注意坐标系对应问题。
//		 */
//		option.setCoorType("bd09ll");
//		// 设置定时定位的时间间隔(单位ms)
//		option.setScanSpan(1000);
//		// 设置超时间隔，单位是毫秒
//		option.setTimeOut(60 * 60 * 1000);
//
//		return option;
//	}

	private static boolean isNetworkConnected(Context context) {
		if(context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
			if(mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}

		return false;
	}

//	/**
//	 * 定位成功接口
//	 *
//	 */
//	public interface InterfaceBDLocation {
//		public void onLocationSuccess(BDLocation location);
//	}
}
