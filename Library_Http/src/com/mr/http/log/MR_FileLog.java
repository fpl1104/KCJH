package com.mr.http.log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Random;

import android.util.Log;

import com.mr.http.util.LogManager;

/**
 * File Log
 */
public class MR_FileLog {

	public static void printFile(String tag, File targetDirectory, String fileName, String headString, String msg) {
		
		tag = LogManager.PREFIX + tag;
		
		fileName = (fileName == null) ? getFileName() : fileName;
		if (save(targetDirectory, fileName, msg)) {
			Log.d(tag, headString + " save log success ! location is >>>" + targetDirectory.getAbsolutePath() + "/" + fileName);
		} else {
			Log.e(tag, headString + "save log fails !");
		}
	}

	private static boolean save(File dic, String fileName, String msg) {

		File file = new File(dic, fileName);

		try {
			OutputStream outputStream = new FileOutputStream(file);
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
			outputStreamWriter.write(msg);
			outputStreamWriter.flush();
			outputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	private static String getFileName() {
		Random random = new Random();
		return "Log_" + Long.toString(System.currentTimeMillis() + random.nextInt(10000)).substring(4) + ".txt";
	}

}