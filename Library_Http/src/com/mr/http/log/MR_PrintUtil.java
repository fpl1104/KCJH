package com.mr.http.log;

import android.text.TextUtils;
import android.util.Log;

import com.mr.http.util.LogManager;

/**
 */
public class MR_PrintUtil {

    public static boolean isEmpty(String line) {
        return TextUtils.isEmpty(line) || line.equals("\n") || line.equals("\t") || TextUtils.isEmpty(line.trim());
    }

    public static void printLine(String tag, boolean isTop) {
        if (isTop) {
            Log.e(tag, "╔═══════════════════════════════════════════════════════");
        } else {
            Log.e(tag, "╚═══════════════════════════════════════════════════════");
        }
    }

}
