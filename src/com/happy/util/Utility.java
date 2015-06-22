package com.happy.util;

import android.text.TextUtils;

import com.happy.biz.CocoWeatherDB;
import com.happy.entity.City;
import com.happy.entity.County;
import com.happy.entity.Province;

public class Utility {

	/**
	 * 解析和处理服务器返回的省级数据
	 */
	public synchronized static boolean handleProvincesResponse(
			CocoWeatherDB cocoWeatherDB, String reponse) {
		if (!TextUtils.isEmpty(reponse)) {
			String[] allProvinces = reponse.split(",");
			if (allProvinces != null && allProvinces.length > 0) {
				for (String p : allProvinces) {
					String[] array = p.split("\\|");
					Province province = new Province();
					province.setProvinceCode(array[0]);
					province.setProvinceName(array[1]);
					// 解析出来的数据存储到Province表
					cocoWeatherDB.saveProvince(province);
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * 解析和处理服务器返回的市级数据
	 */
	public static boolean handleCitiesResponse(CocoWeatherDB cocoWeatherDB,
			String reponse, int provinceId) {
		if (!TextUtils.isEmpty(reponse)) {
			String[] allCities = reponse.split(",");
			if (allCities != null && allCities.length > 0) {
				for (String c : allCities) {
					String[] array = c.split("\\|");
					City city = new City();
					city.setCityCode(array[0]);
					city.setCityName(array[1]);
					city.setProvinceId(provinceId);
					// 解析出来的数据存储到City表
					cocoWeatherDB.saveCity(city);
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * 解析和处理数据库返回的县级数据
	 */
	public static boolean handleCountiesResponse(CocoWeatherDB cocoWeatherDB,
			String reponse, int cityId) {
		if (!TextUtils.isEmpty(reponse)) {
			LogUtil.i("reponse", "county="+reponse);
			String[] allCounties = reponse.split(",");
			if (allCounties != null && allCounties.length > 0) {
				for(String c : allCounties){
					String[] array = c.split("\\|");
					County county = new County();
					county.setCountyCode(array[0]);
					county.setCountyName(array[1]);
					county.setCityId(cityId);
					//解析出来的数据存储到County表
					cocoWeatherDB.saveCounty(county);
				}
				return true;
			}
		}
		return false;
	}

}
