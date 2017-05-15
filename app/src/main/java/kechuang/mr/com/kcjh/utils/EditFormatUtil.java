package kechuang.mr.com.kcjh.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class EditFormatUtil {
	/** 控制輸入位數的浮點型edittext */
	public static void setPricePoint(final EditText editText, final int i) {

		editText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// 有小数点，控制小数点后只能输入两位
				if (s.toString().contains(".")) {
					if (s.length() - 1 - s.toString().indexOf(".") > 2) {
						s = s.toString().subSequence(0,
								s.toString().indexOf(".") + 3);
						editText.setText(s);
						editText.setSelection(s.length());
					}
				}
				// 有小数点的，控制小数点前面只能输入7位
				if (s.toString().contains(".")) {
					if (s.toString().indexOf(".") > i) {
						s = s.toString().subSequence(0,
								s.toString().indexOf("."));
						editText.setText(s);
						editText.setSelection(s.length());
					}
				}
				// 没有小数点，控制只能输入7位
				if (!(s.toString().contains("."))) {
					if (s.toString().length() > i) {
						s = s.toString().subSequence(0, i);
						editText.setText(s);
						editText.setSelection(s.length());
					}
				}
				// 直接输入小数点，小数点前面用0补充
				if (s.toString().trim().substring(0).equals(".")) {
					s = "0" + s;
					editText.setText(s);
					editText.setSelection(2);
				}
				// 如果第一个数为0，只能输入小数点，否则不能输入
				if (s.toString().startsWith("0")
						&& s.toString().trim().length() > 1) {
					if (!s.toString().substring(1, 2).equals(".")) {
						editText.setText(s.subSequence(0, 1));
						editText.setSelection(1);
						return;
					}
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {


			}

		});

	}

}
