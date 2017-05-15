package kechuang.mr.com.kcjh.utils;

import android.text.TextUtils;

/**
 * 字符串工具类
 */
public class StringUtils {

	public static final String EMPTY = "";
	public static final int INDEX_NOT_FOUND = -1;

	/**
	 * null 转为""
	 * 
	 * @param v
	 * @return
	 */
	public static String string(Object v) {
		if (v == null || "null".equals(v))
			return "";
		String value = "";
		try {
			value = String.valueOf(v);
		} catch (Exception e) {
		}
		return value;
	}

	/**
	 * 为空判断
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	/**
	 * 为 " "
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if ((Character.isWhitespace(str.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 不为 " "
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotBlank(String str) {
		return !StringUtils.isBlank(str);
	}

	/**
	 * 获取含 * 的手机号
	 */
	public static String getHidePhoneNum(String phoneNum) {
		if (phoneNum == null || phoneNum.length() != 11) {
			return "";
		}
		return phoneNum.substring(0, 3) + "****" + phoneNum.substring(phoneNum.length() - 4, phoneNum.length());
	}

	/**
	 * 身份证号加*号展示
	 * 
	 * @param mobileNo
	 *            身份证号
	 * @return
	 */
	public static String getHideIDCardNo(String IDCardNo) {

		String XH = "";
		String XIDcardNum = "";
		if (IDCardNo == null || StringUtils.isEmpty(IDCardNo)) {
			return "";
		}
		if (IDCardNo.length() >= 15) {
			for (int i = 0; i < IDCardNo.length() - 8; i++) {
				XH += "*";
			}
			XIDcardNum = IDCardNo.substring(0, 4) + XH + IDCardNo.substring(IDCardNo.length() - 4, IDCardNo.length());
		} else {
			XIDcardNum = IDCardNo;
		}
		return XIDcardNum;
	}

	/**
	 * 获取含 * 的银行卡号
	 * 
	 * @param bankCardNum
	 * @return
	 */
	public static String getHideBankCardNum(String bankCardNum) {

		String XH = "";
		String XBnkNum = "";
		if (bankCardNum == null || StringUtils.isEmpty(bankCardNum)) {
			return "";
		}
		if (bankCardNum.length() >= 16) {
			for (int i = 0; i < bankCardNum.length() - 10; i++) {
				XH += "*";
			}
			XBnkNum = bankCardNum.substring(0, 6) + XH + bankCardNum.substring(bankCardNum.length() - 4, bankCardNum.length());
		} else {
			XBnkNum = bankCardNum;
		}
		return XBnkNum;

	}

	public static boolean isFloat(String str) {
		try {
			Float.parseFloat(str);
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		return true;
	}
	
	/**卡号每隔四位加空格*/
	public static String convert2BlankNum(String num) {
		StringBuilder showNum = new StringBuilder("");
		int i = 0;
		if (TextUtils.isEmpty(num)) {
			return "";
		}
		if (num.length() >= 4) {
			for (; i < (num.length() / 4) * 4; i += 4) {
				String s = num.substring(i, i + 4);
				showNum.append(s);
				showNum.append(" ");
				//showNum = showNum + s + " " ;
			}
			String s = num.substring(i, num.length());
			showNum.append(s);
			return showNum.toString();
		} else {
			return num;
		}
	}

	/** str转为444格式 */
	public static String get443FormatStr(String str) {
		if (TextUtils.isEmpty(str)) {
			return "";
		}
		StringBuffer sb = new StringBuffer(str);
		int len = str.length() / 4;
		for (int i = 0; i < len; i++)
			sb.insert(4 + 5 * i, " ");
		return sb.toString();
	}
}
