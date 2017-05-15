package kechuang.mr.com.kcjh.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Bitmap 工具类
 */
public class BitmapUtils {

	/**
	 * 将 Bitmap 转换成 字节数组
	 * 
	 * @param b
	 */
	public static byte[] bitmapToByte(Bitmap b) {
		if (b == null) {
			return null;
		}

		ByteArrayOutputStream o = new ByteArrayOutputStream();
		b.compress(Bitmap.CompressFormat.PNG, 100, o);
		return o.toByteArray();
	}

	/**
	 * 将 字节数组 转换成 Bitmap
	 * 
	 * @param b
	 */
	public static Bitmap byteToBitmap(byte[] b) {
		return (b == null || b.length == 0) ? null : BitmapFactory.decodeByteArray(b, 0, b.length);
	}

	/**
	 * 将 Drawable 转换成 Bitmap
	 * 
	 * @param d
	 */
	public static Bitmap drawableToBitmap(Drawable d) {
		Bitmap b = null;
		if (d != null) {
			int w = d.getIntrinsicWidth();
			int h = d.getIntrinsicHeight();
			Config config = d.getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888 : Config.RGB_565;
			b = Bitmap.createBitmap(w, h, config);
			// 注意，下面三行代码要用到，否在在View或者surfaceview里的canvas.drawBitmap会看不到图
			Canvas canvas = new Canvas(b);
			d.setBounds(0, 0, w, h);
			d.draw(canvas);
		}

		return d == null ? null : b;
	}

	/**
	 * 将 Bitmap 转换成 Drawable
	 *
	 * @param b
	 */
	@SuppressWarnings("deprecation")
	public static Drawable bitmapToDrawable(Bitmap b) {
		return b == null ? null : new BitmapDrawable(b);
	}

	/**
	 * 将 Drawable 转换成 字节数组
	 *
	 * @param d
	 */
	public static byte[] drawableToByte(Drawable d) {
		return bitmapToByte(drawableToBitmap(d));
	}

	/**
	 * 将 字节数组 转换成 Drawable
	 *
	 * @param b
	 * @return
	 */
	public static Drawable byteToDrawable(byte[] b) {
		return bitmapToDrawable(byteToBitmap(b));
	}

	public static Bitmap stringtoBitmap(String string) {
		Bitmap bitmap = null;
		try {
			byte[] bitmapArray = Base64.decode(string, 0);
			bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bitmap;
	}

	public static String bitmaptoString(Bitmap bitmap) {
		String string = null;
		ByteArrayOutputStream bStream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, bStream);
		byte[] bytes = bStream.toByteArray();
		string = Base64.encodeToString(bytes, 0);
		return string;
	}

	/**
	 * 获取一个指定大小的bitmap
	 *
	 * @param res
	 *            Resources
	 * @param resId
	 *            图片ID
	 * @param reqWidth
	 *            目标宽度
	 * @param reqHeight
	 *            目标高度
	 */
	public static Bitmap bitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
		InputStream is = res.openRawResource(resId);
		return bitmapFromStream(is, null, reqWidth, reqHeight);
	}

	/**
	 * 获取一个指定大小的bitmap
	 *
	 * @param reqWidth
	 *            目标宽度
	 * @param reqHeight
	 *            目标高度
	 */
	public static Bitmap bitmapFromFile(String pathName, int reqWidth, int reqHeight) {
		if (reqHeight == 0 || reqWidth == 0) {
			try {
				return BitmapFactory.decodeFile(pathName);
			} catch (OutOfMemoryError e) {
			}
		}
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(pathName, options);
		options = BitmapUtils.calculateInSampleSize(options, reqWidth, reqHeight);
		return BitmapFactory.decodeFile(pathName, options);
	}

	/**
	 * 获取一个指定大小的bitmap
	 *
	 * @param data
	 *            Bitmap的byte数组
	 * @param offset
	 *            image从byte数组创建的起始位置
	 * @param length
	 *            the number of bytes, 从offset处开始的长度
	 * @param reqWidth
	 *            目标宽度
	 * @param reqHeight
	 *            目标高度
	 */
	public static Bitmap bitmapFromByteArray(byte[] data, int offset, int length, int reqWidth, int reqHeight) {
		if (reqHeight == 0 || reqWidth == 0) {
			try {
				return BitmapFactory.decodeByteArray(data, offset, length);
			} catch (OutOfMemoryError e) {
			}
		}
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		options.inPurgeable = true;
		BitmapFactory.decodeByteArray(data, offset, length, options);
		options = BitmapUtils.calculateInSampleSize(options, reqWidth, reqHeight);
		return BitmapFactory.decodeByteArray(data, offset, length, options);
	}


	/**
	 * 获取一个指定大小的bitmap
	 *
	 * @param is
	 *            从输入流中读取Bitmap
	 * @param outPadding
	 *            If not null, return the padding rect for the bitmap if it
	 *            exists, otherwise set padding to [-1,-1,-1,-1]. If no bitmap
	 *            is returned (null) then padding is unchanged.
	 * @param reqWidth
	 *            目标宽度
	 * @param reqHeight
	 *            目标高度
	 */
	public static Bitmap bitmapFromStream(InputStream is, Rect outPadding, int reqWidth, int reqHeight) {
		Bitmap bmp = null;
		if (reqHeight == 0 || reqWidth == 0) {
			try {
				return BitmapFactory.decodeStream(is);
			} catch (OutOfMemoryError e) {
			}
		}
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		options.inPurgeable = true;
		BitmapFactory.decodeStream(is, outPadding, options);
		options = BitmapUtils.calculateInSampleSize(options, reqWidth, reqHeight);
		bmp = BitmapFactory.decodeStream(is, outPadding, options);
		return bmp;
	}

	/**
	 * 图片压缩处理（使用Options的方法）
	 *
	 * <br>
	 * <b>说明</b> 使用方法：
	 * 首先你要将Options的inJustDecodeBounds属性设置为true，BitmapFactory.decode一次图片 。
	 * 然后将Options连同期望的宽度和高度一起传递到到本方法中。
	 * 之后再使用本方法的返回值做参数调用BitmapFactory.decode创建图片。
	 *
	 * <br>
	 * <b>说明</b> BitmapFactory创建bitmap会尝试为已经构建的bitmap分配内存
	 * ，这时就会很容易导致OOM出现。为此每一种创建方法都提供了一个可选的Options参数
	 * ，将这个参数的inJustDecodeBounds属性设置为true就可以让解析方法禁止为bitmap分配内存
	 * ，返回值也不再是一个Bitmap对象， 而是null。虽然Bitmap是null了，但是Options的outWidth、
	 * outHeight和outMimeType属性都会被赋值。
	 *
	 * @param reqWidth
	 *            目标宽度,这里的宽高只是阀值，实际显示的图片将小于等于这个值
	 * @param reqHeight
	 *            目标高度,这里的宽高只是阀值，实际显示的图片将小于等于这个值
	 */
	public static BitmapFactory.Options calculateInSampleSize(final BitmapFactory.Options options, final int reqWidth, final int reqHeight) {
		// 源图片的高度和宽度
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;
		if (height > reqHeight || width > reqWidth) {
			// 计算出实际宽高和目标宽高的比率
			final int heightRatio = Math.round((float) height / (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			// 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
			// 一定都会大于等于目标的宽和高。
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		// 设置压缩比例
		options.inSampleSize = inSampleSize;
		options.inJustDecodeBounds = false;
		return options;
	}

	/**
	 * 图片压缩方法：（使用compress的方法） <br>
	 *
	 * <b>注意</b> bitmap实际并没有被回收，如果你不需要，请手动置空 <br>
	 * <b>说明</b> 如果bitmap本身的大小小于maxSize，则不作处理
	 *
	 * @param bitmap
	 *            要压缩的图片
	 * @param maxSize
	 *            压缩后的大小，单位kb
	 */
	public static Bitmap imageZoom(Bitmap bitmap, double maxSize) {
		// 将bitmap放至数组中，意在获得bitmap的大小（与实际读取的原文件要大）
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// 格式、质量、输出流
		bitmap.compress(Bitmap.CompressFormat.PNG, 70, baos);
		byte[] b = baos.toByteArray();
		// 将字节换成KB
		double mid = b.length / 1024;
		// 获取bitmap大小 是允许最大大小的多少倍
		double i = mid / maxSize;
		// 判断bitmap占用空间是否大于允许最大空间 如果大于则压缩 小于则不压缩
		if (i > 1) {
			// 缩放图片 此处用到平方根 将宽带和高度压缩掉对应的平方根倍
			// （保持宽高不变，缩放后也达到了最大占用空间的大小）
			bitmap = scaleWithWH(bitmap, bitmap.getWidth() / Math.sqrt(i), bitmap.getHeight() / Math.sqrt(i));
		}
		return bitmap;
	}

	/***
	 * 图片的缩放方法,如果参数宽高为0,则不处理<br>
	 *
	 * <b>注意</b> src实际并没有被回收，如果你不需要，请手动置空
	 *
	 * @param src
	 *            ：源图片资源
	 * @param w
	 *            ：缩放后宽度
	 * @param h
	 *            ：缩放后高度
	 */
	public static Bitmap scaleWithWH(Bitmap src, double w, double h) {
		if (w == 0 || h == 0 || src == null) {
			return src;
		} else {
			// 记录src的宽高
			int width = src.getWidth();
			int height = src.getHeight();
			// 创建一个matrix容器
			Matrix matrix = new Matrix();
			// 计算缩放比例
			float scaleWidth = (float) (w / width);
			float scaleHeight = (float) (h / height);
			// 开始缩放
			matrix.postScale(scaleWidth, scaleHeight);
			// 创建缩放后的图片
			return Bitmap.createBitmap(src, 0, 0, width, height, matrix, true);
		}
	}

	/**
	 * 图片的缩放方法<br>
	 *
	 * <b>注意</b> src实际并没有被回收，如果你不需要，请手动置空
	 *
	 * @param src
	 *            ：源图片资源
	 * @param scaleMatrix
	 *            ：缩放规则
	 */
	public static Bitmap scaleWithMatrix(Bitmap src, Matrix scaleMatrix) {
		return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), scaleMatrix, true);
	}

	/**
	 * 图片的缩放方法<br>
	 *
	 * <b>注意</b> src实际并没有被回收，如果你不需要，请手动置空
	 *
	 * @param src
	 *            ：源图片资源
	 * @param scaleX
	 *            ：横向缩放比例
	 * @param scaleY
	 *            ：纵向缩放比例
	 */
	public static Bitmap scaleWithXY(Bitmap src, float scaleX, float scaleY) {
		Matrix matrix = new Matrix();
		matrix.postScale(scaleX, scaleY);
		return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
	}

	/**
	 * 图片的缩放方法<br>
	 *
	 * <b>注意</b> src实际并没有被回收，如果你不需要，请手动置空
	 *
	 * @param src
	 *            ：源图片资源
	 * @param scaleXY
	 *            ：缩放比例
	 */
	public static Bitmap scaleWithXY(Bitmap src, float scaleXY) {
		return scaleWithXY(src, scaleXY, scaleXY);
	}

	/**
     * 图片反转
     *
     * @param bm
     * @param flag
     *            0为水平反转，1为垂直反转
     * @return
     */
    public static Bitmap reverseBitmap(Bitmap bmp, int flag) {
        float[] floats = null;
        switch (flag) {
        case 0: // 水平反转
            floats = new float[] { -1f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 1f };
            break;
        case 1: // 垂直反转
            floats = new float[] { 1f, 0f, 0f, 0f, -1f, 0f, 0f, 0f, 1f };
            break;
        }

        if (floats != null) {
            Matrix matrix = new Matrix();
            matrix.setValues(floats);
            return Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
        }

        return null;
    }

	/**
	 * 旋转图片<br>
	 *
	 * <b>注意</b> bitmap实际并没有被回收，如果你不需要，请手动置空
	 *
	 * @param angle
	 *            旋转角度
	 * @param bitmap
	 *            要旋转的图片
	 * @return 旋转后的图片
	 */
	public static Bitmap rotate(int angle, Bitmap bitmap) {
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
	}

	/**
	 * 获取图片类型
	 *
	 * @param file
	 * @return
	 */
	public static String getImageType(File file) {
		if (file == null || !file.exists()) {
			return null;
		}
		InputStream in = null;
		try {
			in = new FileInputStream(file);
			return getImageType(in);
		} catch (IOException e) {
			return null;
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				// ingore
			}
		}
	}

	/**
	 * 获取图片的类型信息
	 *
	 * @param in
	 * @return
	 */
	public static String getImageType(InputStream in) {
		if (in == null) {
			return null;
		}
		try {
			byte[] bytes = new byte[8];
			in.read(bytes);
			return getImageType(bytes);
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * 获取图片的类型信息
	 *
	 * @param bytes
	 * @return
	 */
	public static String getImageType(byte[] bytes) {
		if (isJPEG(bytes)) {
			return "image/jpeg";
		}
		if (isGIF(bytes)) {
			return "image/gif";
		}
		if (isPNG(bytes)) {
			return "image/png";
		}
		if (isBMP(bytes)) {
			return "application/x-bmp";
		}
		return null;
	}

	/**
	 * 图片去色,返回灰度图片
	 *
	 * @param bmpOriginal
	 *            传入的图片
	 * @return 去色后的图片
	 */
	public static Bitmap toGrayscale(Bitmap bmpOriginal) {
		int width, height;
		height = bmpOriginal.getHeight();
		width = bmpOriginal.getWidth();
		Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Config.RGB_565);
		Canvas c = new Canvas(bmpGrayscale);
		Paint paint = new Paint();
		ColorMatrix cm = new ColorMatrix();
		cm.setSaturation(0);
		ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
		paint.setColorFilter(f);
		c.drawBitmap(bmpOriginal, 0, 0, paint);
		return bmpGrayscale;
	}

	/**
	 * 去色同时加圆角
	 *
	 * @param bmpOriginal
	 *            原图
	 * @param pixels
	 *            圆角弧度
	 * @return 修改后的图片
	 */
	public static Bitmap toGrayscale(Bitmap bmpOriginal, int pixels) {
		return toRoundCorner(toGrayscale(bmpOriginal), pixels);
	}

	/**
	 * 把图片变成圆角
	 *
	 * @param bitmap
	 *            需要修改的图片
	 * @param pixels
	 *            圆角的弧度
	 * @return 圆角图片
	 */
	public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {

		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = pixels;
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}

	/**
	 * 获得带倒影的图片方法
	 *
	 * @param bitmap
	 * @return
	 */
	public static Bitmap createReflectionImageWithOrigin(Bitmap bitmap) {
		final int reflectionGap = 4;
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);
		Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, height / 2, width, height / 2, matrix, false);

		Bitmap bitmapWithReflection = Bitmap.createBitmap(width, (height + height / 2), Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmapWithReflection);
		canvas.drawBitmap(bitmap, 0, 0, null);
		Paint deafalutPaint = new Paint();
		canvas.drawRect(0, height, width, height + reflectionGap, deafalutPaint);
		canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);
		Paint paint = new Paint();
		LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0, bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff, 0x00ffffff, TileMode.CLAMP);
		paint.setShader(shader);
		// Set the Transfer mode to be porter duff and destination in
		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		// Draw a rectangle using the paint with our linear gradient
		canvas.drawRect(0, height, width, bitmapWithReflection.getHeight() + reflectionGap, paint);

		return bitmapWithReflection;
	}

	/**
	 * 获得图片的倒影，同时倒影渐变效果
	 *
	 * @param srcbitmap
	 * @return
	 */
	public static Bitmap createMirro(Bitmap srcbitmap) {

		int width = srcbitmap.getWidth();
		int height = srcbitmap.getHeight();
		int shadow_height = 15;
		int[] pixels = new int[width * height];
		srcbitmap.getPixels(pixels, 0, width, 0, 0, width, height);
		// shadow effect
		int alpha = 0x00000000;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int index = y * width + x;
				int r = (pixels[index] >> 16) & 0xff;
				int g = (pixels[index] >> 8) & 0xff;
				int b = pixels[index] & 0xff;
				pixels[index] = alpha | (r << 16) | (g << 8) | b;
			}
			if (y >= (height - shadow_height)) {
				alpha = alpha + 0x1F000000;
			}
		}
		// invert effect
		Bitmap bm = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		for (int y = 0; y < height; y++) {
			bm.setPixels(pixels, y * width, width, 0, height - y - 1, width, 1);
		}
		return Bitmap.createBitmap(bm, 0, 0, width, shadow_height);
	}

	/**
	 * 回收一个未被回收的Bitmap
	 * 
	 * @param bitmap
	 */
	public static void doRecycledIfNot(Bitmap bitmap) {
		if (!bitmap.isRecycled()) {
			bitmap.recycle();
		}
	}

	private static boolean isJPEG(byte[] b) {
		if (b.length < 2) {
			return false;
		}
		return (b[0] == (byte) 0xFF) && (b[1] == (byte) 0xD8);
	}

	private static boolean isGIF(byte[] b) {
		if (b.length < 6) {
			return false;
		}
		return b[0] == 'G' && b[1] == 'I' && b[2] == 'F' && b[3] == '8' && (b[4] == '7' || b[4] == '9') && b[5] == 'a';
	}

	private static boolean isPNG(byte[] b) {
		if (b.length < 8) {
			return false;
		}
		return (b[0] == (byte) 137 && b[1] == (byte) 80 && b[2] == (byte) 78 && b[3] == (byte) 71 && b[4] == (byte) 13 && b[5] == (byte) 10 && b[6] == (byte) 26 && b[7] == (byte) 10);
	}

	private static boolean isBMP(byte[] b) {
		if (b.length < 2) {
			return false;
		}
		return (b[0] == 0x42) && (b[1] == 0x4d);
	}
}
