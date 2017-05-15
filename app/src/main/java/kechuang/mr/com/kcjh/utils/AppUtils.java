package kechuang.mr.com.kcjh.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.graphics.Rect;
import android.hardware.Camera;
import android.net.Uri;
import android.provider.MediaStore.Images.ImageColumns;
import android.provider.Settings;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.EditText;

import com.mr.http.util.LogManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author yangqing
 * @version 1
 *          <p/>
 *          应用是否安装判断工具
 *          <p/>
 * @date 2015-12-10
 */
public class AppUtils {

	/**
	 * 描述：判断APP是否安装
	 * 
	 * @param context
	 * @param pacakapgeName
	 * @return1111
	 */
	public static Boolean isNotAppInstall(Context context, String pacakapgeName) {
		PackageInfo packageInfo;
		try {
			packageInfo = context.getPackageManager().getPackageInfo(pacakapgeName, 0);
		} catch (NameNotFoundException e) {
			packageInfo = null;
			e.printStackTrace();
		}
		return packageInfo == null;
	}

	/**
	 * 获取版本号
	 * 
	 * @return
	 */
	public static String getversionName(Context context) {
		PackageInfo info;
		String versionName = "";
		try {
			info = context.getPackageManager().getPackageInfo("com.blemall.okpayment.android", PackageManager.GET_CONFIGURATIONS);
			versionName = info.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionName;
	}

	/**
	 * 验证手机格式
	 */
	public static boolean isMobileNO(String mobiles) {
		/*
		 * 三大运营商最新号段 合作版 移动号段： 134 135 136 137 138 139 147 150 151 152 157 158
		 * 159 178 182 183 184 187 188 联通号段： 130 131 132 145 155 156 171 175 176
		 * 185 186 电信号段： 133 149 153 173 177 180 181 189 虚拟运营商: 170
		 */
		String telRegex = "^1(((3|8)\\d)|(4[579])|(5[^4])|(7[^24]))\\d{8}$";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
		if (TextUtils.isEmpty(mobiles))
			return false;
		else
			return mobiles.matches(telRegex);
	}

	// 校验短信验证码
	public static boolean checkSmsCode(String smsCode) {
		if (TextUtils.isEmpty(smsCode)) {
			return false;
		}
		return smsCode.length() <= 4;
	}

	// 检验身份证号
	public static boolean checkIDCardNum(String IDCardNum) {
		return IdCardUtils.isRealIDCard(IDCardNum);
	}

	// 登录密码规范验证
	public static void checkLoginPwd(short checkId, Context contex) {
		switch (checkId) {
		case 0:
			ToastUtils.show(contex, "合法");
			break;
		case -1:
			ToastUtils.show(contex, "内容为空");
			break;
		case -2:
			ToastUtils.show(contex, "输入小于最小长度");
			break;
		case -3:
			ToastUtils.show(contex, "输入字符不可接受");
			break;
		case -4:
			ToastUtils.show(contex, "简单密码");
			break;
		case -5:
			ToastUtils.show(contex, "输入的密码是字典密码");
			break;
		case -6:
			ToastUtils.show(contex, "错误的校验类型，必须包含数字和字母");
			break;

		default:
			ToastUtils.show(contex, "default错误");
			break;
		}
	}

	/**
	 * 验证QQ格式
	 */
	public static boolean isQQNO(String mobiles) {
		String telRegex = "^\\d[1-9]{5,10}$";
		if (TextUtils.isEmpty(mobiles))
			return false;
		else
			return mobiles.matches(telRegex);
	}

	public static boolean isNumeric(String str, int length) {
		Pattern pattern = Pattern.compile("^\\d{" + length + "}");
		Matcher isNum = pattern.matcher(str);
		return isNum.matches();
	}

	public static boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		return m.matches();
	}

	/**
	 * 格式化手机号为 344
	 * 
	 * @param mEditText
	 */
	public static void phoneNumAddSpace(final EditText mEditText) {
		mEditText.addTextChangedListener(new TextWatcher() {
			int beforeTextLength = 0;
			int onTextLength = 0;
			boolean isChanged = false;

			int location = 0;// 记录光标的位置
			private char[] tempChar;
			private StringBuffer buffer = new StringBuffer();
			int konggeNumberB = 0;

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				beforeTextLength = s.length();
				if (buffer.length() > 0) {
					buffer.delete(0, buffer.length());
				}
				konggeNumberB = 0;
				for (int i = 0; i < s.length(); i++) {
					if (s.charAt(i) == ' ') {
						konggeNumberB++;
					}
				}
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				onTextLength = s.length();
				buffer.append(s.toString());
				if (onTextLength == beforeTextLength || onTextLength <= 3 || isChanged) {
					isChanged = false;
					return;
				}
				isChanged = true;
			}

			@Override
			public void afterTextChanged(Editable s) {
				if (isChanged) {
					location = mEditText.getSelectionEnd();
					int index = 0;
					while (index < buffer.length()) {
						if (buffer.charAt(index) == ' ') {
							buffer.deleteCharAt(index);
						} else {
							index++;
						}
					}

					index = 0;
					int konggeNumberC = 0;
					while (index < buffer.length()) {
						if ((index == 3 || index == 8)) {
							buffer.insert(index, ' ');
							konggeNumberC++;
						}
						index++;
					}

					if (konggeNumberC > konggeNumberB) {
						location += (konggeNumberC - konggeNumberB);
					}

					tempChar = new char[buffer.length()];
					buffer.getChars(0, buffer.length(), tempChar, 0);
					String str = buffer.toString();
					if (location > str.length()) {
						location = str.length();
					} else if (location < 0) {
						location = 0;
					}

					mEditText.setText(str);
					Editable etable = mEditText.getText();
					Selection.setSelection(etable, location);
					isChanged = false;
				}
			}
		});
	}

	/**
	 * 格式化为 44
	 * 
	 * @param editText
	 *            目标editText
	 */
	public static void MobileNumAddSpace(final EditText editText) {
		editText.addTextChangedListener(new TextWatcher() {
			int beforeTextLength = 0;
			int onTextLength = 0;
			boolean isChanged = false;

			int location = 0;// 记录光标的位置
			private char[] tempChar;
			private StringBuffer buffer = new StringBuffer();
			int konggeNumberB = 0;

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				beforeTextLength = s.length();
				if (buffer.length() > 0) {
					buffer.delete(0, buffer.length());
				}
				konggeNumberB = 0;
				for (int i = 0; i < s.length(); i++) {
					if (s.charAt(i) == ' ') {
						konggeNumberB++;
					}
				}
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				onTextLength = s.length();
				buffer.append(s.toString());
				if (onTextLength == beforeTextLength || onTextLength <= 4 || isChanged) {
					isChanged = false;
					return;
				}
				isChanged = true;
			}

			@Override
			public void afterTextChanged(Editable s) {
				if (isChanged) {
					location = editText.getSelectionEnd();
					int index = 0;
					while (index < buffer.length()) {
						if (buffer.charAt(index) == ' ') {
							buffer.deleteCharAt(index);
						} else {
							index++;
						}
					}

					index = 0;
					int konggeNumberC = 0;
					while (index < buffer.length()) {
						if ((index == 4)) {
							buffer.insert(index, ' ');
							konggeNumberC++;
						}
						index++;
					}

					if (konggeNumberC > konggeNumberB) {
						location += (konggeNumberC - konggeNumberB);
					}

					tempChar = new char[buffer.length()];
					buffer.getChars(0, buffer.length(), tempChar, 0);
					String str = buffer.toString();
					if (location > str.length()) {
						location = str.length();
					} else if (location < 0) {
						location = 0;
					}

					editText.setText(str);
					Editable etable = editText.getText();
					Selection.setSelection(etable, location);
					isChanged = false;
				}
			}
		});
	}

	public static int getScreenWidth(Activity activity) {
		WindowManager windowManager = activity.getWindowManager();
		DisplayMetrics dm = new DisplayMetrics();
		windowManager.getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}

	public static int getScreenHeight(Activity activity) {
		WindowManager windowManager = activity.getWindowManager();
		DisplayMetrics dm = new DisplayMetrics();
		windowManager.getDefaultDisplay().getMetrics(dm);
		return dm.heightPixels;
	}

	public static int getScreenBrightness(Context context) {
		int screenBrightness = 255;
		try {
			screenBrightness = Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
		} catch (Exception localException) {
			localException.getMessage();
		}
		return screenBrightness;
	}

	/**
	 * 设置当前屏幕亮度值 0--255
	 */
	public static void saveScreenBrightness(Context context, int paramInt) {
		try {
			Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, paramInt);
			// setScreenBrightness(paramInt);
		} catch (Exception localException) {
			localException.printStackTrace();
		}
	}

	/**
	 * 判断是否开启了自动亮度调节
	 */
	public static boolean isAutoBrightness(Context context) {

		boolean automicBrightness = false;
		try {
			automicBrightness = Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE) == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;

		} catch (Settings.SettingNotFoundException e) {
			e.printStackTrace();
		}
		return automicBrightness;
	}

	/**
	 * 停止自动亮度调节
	 * 
	 * @param activity
	 *            目标activity
	 */
	public static void stopAutoBrightness(Activity activity) {
		Settings.System.putInt(activity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE,
				Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
	}

	/**
	 * 开启亮度自动调节 *
	 * 
	 * @param activity
	 *            目标activity
	 */
	public static void startAutoBrightness(Activity activity) {
		Settings.System.putInt(activity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE,
				Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
	}

	public static void controlKeyboardLayout(final View root, final View editLayout) {
		controlKeyboardLayout(root, editLayout, 0);
	}

	/**
	 * 解决在页面底部置输入框，输入法弹出遮挡部分输入框的问题
	 * 
	 * @param root
	 *            页面根元素
	 * @param editLayout
	 *            被软键盘遮挡的输入框
	 * @param offset
	 *            y-轴偏移量
	 */
	public static void controlKeyboardLayout(final View root, final View editLayout, final int offset) {
		root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

			@Override
			public void onGlobalLayout() {
				Rect rect = new Rect();
				root.getWindowVisibleDisplayFrame(rect);
				// 获取root在窗体的不可视区域高度(被其他View遮挡的区域高度)
				int rootInVisibleHeigh = root.getRootView().getHeight() - rect.bottom;

				int[] location1 = new int[2];
				editLayout.getLocationInWindow(location1);
				// 计算root滚动高度，使editLayout在可见区域
				int srollHeight1 = (location1[1] + editLayout.getHeight()) - rect.bottom;
				if (rootInVisibleHeigh > 100) {
					int[] location = new int[2];
					// 获取editLayout在窗体的坐标
					editLayout.getLocationInWindow(location);
					// 计算root滚动高度，使editLayout在可见区域
					int srollHeight = (location[1] + editLayout.getHeight()) - rect.bottom;
					if (srollHeight > 0)
						root.scrollTo(0, srollHeight + offset);
				} else {
					// 键盘隐藏
					root.scrollTo(0, 0);
				}
			}
		});
	}

	/**
	 * 没办法，魅族XM4的手机就得这么测权限~~
	 * 
	 * @return 是不是有相机权限
	 */
	public static boolean cameraIsCanUse() {
		boolean isCanUse = true;
		Camera mCamera = null;
		try {
			mCamera = Camera.open();
			Camera.Parameters mParameters = mCamera.getParameters(); // 针对魅族手机
			mCamera.setParameters(mParameters);
		} catch (Exception e) {
			isCanUse = false;
		}
		if (mCamera != null) {
			try {
				mCamera.release();
			} catch (Exception e) {
				e.printStackTrace();
				return isCanUse;
			}
		}
		return isCanUse;
	}

	/**
	 * 判断字符串是否为短信验证码 0-合法 1-空字符串 2-长度不够
	 * 
	 * @param str
	 * @return
	 */

	public static int isSmsVerify(String str) {
		return isSmsVerify(str, 4);
	}

	public static int isSmsVerify(String str, int len) {
		return TextUtils.isEmpty(str) ? 1 : str.length() < len ? 2 : 0;
	}

	/** 纯中文字符 */
	public static boolean isUnicodeChar(String str) {
		String regex = "^[\u4e00-\u9fa5·]+";
		return str.matches(regex);
	}

	public static String getRealPathFromURI(Uri uri, Context context) {
		if (null == uri)
			return null;
		final String scheme = uri.getScheme();
		String data = null;
		if (scheme == null)
			data = uri.getPath();
		else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
			data = uri.getPath();
		} else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
			Cursor cursor = context.getContentResolver().query(uri, new String[] { ImageColumns.DATA }, null, null, null);
			if (null != cursor) {
				if (cursor.moveToFirst()) {
					int index = cursor.getColumnIndex(ImageColumns.DATA);
					if (index > -1) {
						data = cursor.getString(index);
					}
				}
				cursor.close();
			}
		}
		return data; 
	}
	
	//key补位
	public static byte[] fillByte(String mPublicRandomKey){
		if ( mPublicRandomKey.length() > 32 )
			return mPublicRandomKey.substring( 0, 32 ).getBytes();
		if (mPublicRandomKey.length() != 32) {
			String c = "" ;
			for (int i = 0; i < (32 - mPublicRandomKey.length()-1); i++) {
			     c+="F";
			}
			mPublicRandomKey = mPublicRandomKey + "|" + c.toString();
			LogManager.i(mPublicRandomKey);
		}
		byte[] key  = mPublicRandomKey.getBytes();// 初始化OCX_KEY
		return key;
	}
}
