package kechuang.mr.com.kcjh.utils;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.Set;

public class JsonTool {
	

	
	/**
	 * map 转 json
	 * @param map
	 * @return
	 */
	public static String simpleMapToJsonStr( Map<String ,String > map){
        if(map==null||map.isEmpty()){  
            return "null";  
        }  
        String jsonStr = "{";
        Set<?> keySet = map.keySet();
        for (Object key : keySet) {
            jsonStr += "\'"+key+"\':\'"+map.get(key)+"\',";       
        }  
        jsonStr = jsonStr.substring(0, jsonStr.length()-1);
        jsonStr += "}";  
        return jsonStr;  
    } 
	
	/**
	 * 从String 获取 String ，为空则返回默认值 defaultValue
	 * 
	 * @param jsonData
	 * @param key
	 * @param defaultValue
	 */
	public static String getString(String jsonData, String key, String defaultValue) {
		if (TextUtils.isEmpty(jsonData)) {
			return defaultValue;
		}

		try {
			JSONObject jsonObject = new JSONObject(jsonData);
			return getString(jsonObject, key, defaultValue);
		} catch (JSONException e) {
				e.printStackTrace();
			return defaultValue;
		}
	}
	
	/**
	 * 从JSONObject 获取 String ，为空则返回默认值 defaultValue
	 * 
	 * @param jsonObject
	 * @param key
	 * @param defaultValue
	 */
	public static String getString(JSONObject jsonObject, String key, String defaultValue) {
		if (jsonObject == null || TextUtils.isEmpty(key)) {
			return defaultValue;
		}

		try {
			return jsonObject.getString(key);
		} catch (JSONException e) {
				e.printStackTrace();
			return defaultValue;
		}
	}
}
