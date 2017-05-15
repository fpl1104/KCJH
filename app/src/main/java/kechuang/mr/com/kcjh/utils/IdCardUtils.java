package kechuang.mr.com.kcjh.utils;

import android.text.InputFilter;
import android.text.Spanned;
import android.widget.EditText;

import java.util.Arrays;
import java.util.List;

public class IdCardUtils {

	/**
	 *  居民身份证号的组成元素
	 */
    public static String[] IDCARD = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "x", "X", };
    
    /**
     *  设置长度的限制和字符的限制
     * @param idCardEdt
     */
    public static void setEdtInputFilter(final EditText idCardEdt) {
    	final List<String> idCardList = Arrays.asList(IDCARD);
    	InputFilter inputFilter = new InputFilter() {

    	    @Override
    	    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
    	        // 返回空字符串，就代表匹配不成功，返回null代表匹配成功
    	        for (int i = 0; i < source.toString().length(); i++) {
    	            if (!idCardList.contains(String.valueOf(source.charAt(i)))) {
    	                return "";
    	            }
    	            if (idCardEdt.getText().toString().length() < 17) {
    	                if ("x".equals(String.valueOf(source.charAt(i))) || "X".equals(String.valueOf(source.charAt(i)))) {
    	                    return "";
    	                }
    	            }
    	        }
    	        return null;
    	    }
    	};
    	idCardEdt.setFilters(new InputFilter[]{new InputFilter.LengthFilter(18), inputFilter});// 长度的限制和字符的限制
	}
	
    
    /**
     * 比较真实完整的判断身份证号码的工具
     *
     * @param IdCard 用户输入的身份证号码
     * @return [true符合规范, false不符合规范]
     */
    public static boolean isRealIDCard(String IdCard) {
        if (IdCard != null) {
        	
        	String IDCardRegex = "(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x|Y|y)$)";
        	if (IdCard.matches(IDCardRegex)) {
        		int correct = new IDnumDistinguish(IdCard).isCorrect();
                if (0 == correct) {// 符合规范
                    return true;
                }
            }
        }
        return false;
    }
}
