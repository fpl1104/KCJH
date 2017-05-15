package kechuang.mr.com.kcjh.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据模型处理工具类
 */
public class BeanUtils {


	/**
	 * Object 转换 map
	 * 
	 * @param obj
	 * @return
	 */
	public static Map<String, String> Bean2Map(Object obj) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			Field[] fields = obj.getClass().getFields();
			for (Field f : fields){
				if(f.get(obj) instanceof String)
					map.put(f.getName(), (String) f.get(obj));
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return map;
	}
}
