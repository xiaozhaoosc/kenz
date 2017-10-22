package site.kenz.db.api.kenzdbapi.utils;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 字符串操作类
 */
public class StringUtil {

	public static List<String[]> splitToList(String orgStr, String splitStr1, String splitStr2) {
		List<String[]> list = new ArrayList<String[]>();

		String[] newStr = removeLastStr(orgStr, splitStr1).split(splitStr1);
		for (String tmpStr : newStr) {
			list.add(removeLastStr(tmpStr, splitStr2).split(splitStr2));
		}
		return list;
	}

	public static String[] splitToArray(String orgStr, String splitStr) {
		return removeLastStr(orgStr, splitStr).split(splitStr);
	}

	public static String removeLastStr(String orgStr, String lastStr) {
		if (orgStr.endsWith(lastStr)) {
			orgStr = orgStr.substring(0, orgStr.lastIndexOf(lastStr));
		}
		return orgStr;
	}

	public static String toString(Object obj) {
		if (obj == null) {
			return "";
		} else {
			return obj.toString();
		}
	}

	/**
	 * 替换字符串
	 * 
	 * @param str
	 * @param map
	 * @param tag
	 * @return
	 */
	public static String replaceString(String str, final Map<String, Object> map, final String tag) {
		Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry<String, Object> entry = it.next();
			String key = entry.getKey();
			Object value = entry.getValue();

			String replaceStr = tag + key + tag;
			if (str.contains(replaceStr)) {
				str = str.replaceAll(replaceStr, toString(value));
			}
		}
		return str;
	}

	/**
	 * 替换字符串
	 * 
	 * @param str
	 * @param map
	 * @return
	 */
	public static String replaceString(String str, final Map<String, Object> map) {
		return replaceString(str, map, "%");
	}


	/**
	 * 非空判断
	 * 
	 * @param @param
	 *            obj
	 * @param @return
	 * @return boolean
	 * @author gdl
	 */
	public static boolean checkNotNull(Object obj) {
		boolean isTrue = false;
		if (obj != null && !obj.equals("")) {
			isTrue = true;
		}
		return isTrue;

	}

	/**
	 * 判断传入的字符串是否是数字,这个很常用的用了能减少很多恶意的bug @Title: isNumber @param @param
	 * value @param @return 设定文件 @return boolean 返回类型 @author gdl @date
	 */
	public static boolean isNumber(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}
