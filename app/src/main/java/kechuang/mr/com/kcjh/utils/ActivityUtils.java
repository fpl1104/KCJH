package kechuang.mr.com.kcjh.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;

import java.util.List;

/**
 * Activity 工具类
 */
public class ActivityUtils {

	/**
	 * 判断 Activity 是不是在栈的最上面
	 * 
	 * @param topTask
	 * @param packageName
	 * @param activityName
	 * @return
	 */
	public static boolean isTopActivity(ActivityManager.RunningTaskInfo topTask,
			String packageName, String activityName) {
		if (topTask != null) {
			ComponentName topActivity = topTask.topActivity;
			if (topActivity.getPackageName().equals(packageName)
					&& topActivity.getClassName().equals(activityName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取 TopTask
	 * 
	 * @param context
	 * @return
	 */
	public static ActivityManager.RunningTaskInfo getTopTask(Context context) {
		ActivityManager mActivityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningTaskInfo> tasks = mActivityManager.getRunningTasks(1);
		if (tasks != null && !tasks.isEmpty()) {
			return tasks.get(0);
		}
		return null;
	}
}
