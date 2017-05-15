package com.mr.http.log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.mr.http.util.LogManager;

/**
 * Json Log
 */
public class MR_JsonLog {

    public static void printJson(String tag, String msg, String headString) {

        String message;

        tag = LogManager.PREFIX + tag;
        
        try {
            if (msg.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(msg);
                message = jsonObject.toString(LogManager.JSON_INDENT);
            } else if (msg.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(msg);
                message = jsonArray.toString(LogManager.JSON_INDENT);
            } else {
                message = msg;
            }
        } catch (JSONException e) {
            message = msg;
        }

        MR_PrintUtil.printLine(tag, true);
        message = headString + LogManager.LINE_SEPARATOR + message;
        String[] lines = message.split(LogManager.LINE_SEPARATOR);
        for (String line : lines) {
        	   Log.e(tag, "║ " + line);
        }
        MR_PrintUtil.printLine(tag, false);
    }
}
