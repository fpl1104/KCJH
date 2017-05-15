package kechuang.mr.com.kcjh.utils;

import android.content.Context;

import java.util.Map;
import java.util.Set;

/**
 * SharePref 持久化工具类
 */
public class PreferencesUtils {

	private static String PREFERENCE_NAME = "security_prefs";

	/**
	 * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
	 * 
	 * @param context
	 * @param key
	 * @param object
	 */
	public static void put(Context context, String key, Object object, String preferencesName) {

		SecuritySharedPreference securitySharedPreference = new SecuritySharedPreference(context, preferencesName, Context.MODE_PRIVATE);
		SecuritySharedPreference.SecurityEditor securityEditor = securitySharedPreference.edit();

		if (object instanceof String) {
			securityEditor.putString(key, (String) object);
		} else if (object instanceof Integer) {
			securityEditor.putInt(key, (Integer) object);
		} else if (object instanceof Boolean) {
			securityEditor.putBoolean(key, (Boolean) object);
		} else if (object instanceof Float) {
			securityEditor.putFloat(key, (Float) object);
		} else if (object instanceof Long) {
			securityEditor.putLong(key, (Long) object);
		} else if (object instanceof Set) {
			securityEditor.putStringSet(key, (Set<String>) object);
		} else {
			securityEditor.putString(key, StringUtils.string(object));
		}
		securityEditor.apply();
	}

	/**
	 * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
	 * 
	 * @param context
	 * @param key
	 * @param object
	 */
	public static void put(Context context, String key, Object object) {
		put(context, key, object, PREFERENCE_NAME);
	}

	/**
	 * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
	 * 
	 * @param context
	 * @param key
	 * @param defaultObject
	 * @return
	 */
	public static Object get(Context context, String key, Object defaultObject, String preferencesName) {

		SecuritySharedPreference securitySharedPreference = new SecuritySharedPreference(context, preferencesName, Context.MODE_PRIVATE);
		if (defaultObject instanceof String) {
			return securitySharedPreference.getString(key, (String) defaultObject);
		} else if (defaultObject instanceof Integer) {
			return securitySharedPreference.getInt(key, (Integer) defaultObject);
		} else if (defaultObject instanceof Boolean) {
			return securitySharedPreference.getBoolean(key, (Boolean) defaultObject);
		} else if (defaultObject instanceof Float) {
			return securitySharedPreference.getFloat(key, (Float) defaultObject);
		} else if (defaultObject instanceof Long) {
			return securitySharedPreference.getLong(key, (Long) defaultObject);
		} else if (defaultObject instanceof Set) {
			return securitySharedPreference.getStringSet(key, (Set<String>) defaultObject);
		}

		return null;
	}
	
	/**
	 * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
	 * 
	 * @param context
	 * @param key
	 * @param defaultObject
	 * @return
	 */
	public static Object get(Context context, String key, Object defaultObject) {
		
		return get(context, key, defaultObject, PREFERENCE_NAME);
	}


	/**
	 * 移除某个key值已经对应的值
	 * 
	 * @param context
	 * @param key
	 */
	public static void remove(Context context, String key, String preferencesName) {
		SecuritySharedPreference securitySharedPreference = new SecuritySharedPreference(context, preferencesName, Context.MODE_PRIVATE);
		SecuritySharedPreference.SecurityEditor securityEditor = securitySharedPreference.edit();
		securityEditor.remove(key);
		securityEditor.apply();
	}
	
	/**
	 * 移除某个key值已经对应的值
	 * 
	 * @param context
	 * @param key
	 */
	public static void remove(Context context, String key) {
		remove(context, key, PREFERENCE_NAME);
	}

	/**
	 * 清除所有数据
	 * 
	 * @param context
	 */
	public static void clear(Context context, String preferencesName) {
		SecuritySharedPreference securitySharedPreference = new SecuritySharedPreference(context, preferencesName, Context.MODE_PRIVATE);
		SecuritySharedPreference.SecurityEditor securityEditor = securitySharedPreference.edit();
		securityEditor.clear();
		securityEditor.apply();
	}
	
	/**
	 * 清除所有数据
	 * 
	 * @param context
	 */
	public static void clear(Context context) {
		clear(context,PREFERENCE_NAME);
	}

	/**
	 * 查询某个key是否已经存在
	 * 
	 * @param context
	 * @param key
	 * @return
	 */
	public static boolean contains(Context context, String key, String preferencesName) {
		SecuritySharedPreference securitySharedPreference = new SecuritySharedPreference(context, preferencesName, Context.MODE_PRIVATE);
		return securitySharedPreference.contains(key);
	}
	
	/**
	 * 查询某个key是否已经存在
	 * 
	 * @param context
	 * @param key
	 * @return
	 */
	public static boolean contains(Context context, String key) {
		return contains(context, key,PREFERENCE_NAME);
	}

	/**
	 * 返回所有的键值对
	 * 
	 * @param context
	 * @return
	 */
	public static Map<String, ?> getAll(Context context, String preferencesName) {
		SecuritySharedPreference securitySharedPreference = new SecuritySharedPreference(context, preferencesName, Context.MODE_PRIVATE);
		return securitySharedPreference.getAll();
	}
	
	/**
	 * 返回所有的键值对
	 * 
	 * @param context
	 * @return
	 */
	public static Map<String, ?> getAll(Context context) {
		SecuritySharedPreference securitySharedPreference = new SecuritySharedPreference(context, PREFERENCE_NAME, Context.MODE_PRIVATE);
		return securitySharedPreference.getAll();
	}
}
