package kechuang.mr.com.kcjh.ui.thread;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import kechuang.mr.com.kcjh.impl.HandlerImpl;


public class UIHandler extends Handler {

	/** 回调接口，消息传递给注册者 */
	private HandlerImpl handler;
	
//    WeakReference<Activity> mWeakReference;
//    public UIHandler(Looper looper , Activity activity){
//    	super(looper);
//        mWeakReference = new WeakReference<Activity>(activity);
//    }


	public UIHandler(Looper looper) {
		super(looper);
	}

	public UIHandler(Looper looper, HandlerImpl handler) {
		super(looper);
		this.handler = handler;
	}

	public void setHandler(HandlerImpl handler) {
		this.handler = handler;
	}

	@Override
	public void handleMessage(Message msg) {
		super.handleMessage(msg);
        //final Activity activity = mWeakReference.get();
        //if(activity!=null)
        //{
    		if (handler != null) {
    			/** 有消息，就传递 */
    			handler.handleMessage(msg);
    		}
       // }
	}
}
