package com.wisdom.passcode.base.weakreferences;

import android.app.Activity;

import java.lang.ref.WeakReference;

/**
 * Activity可用性检测器
 * 弱引用，避免强制引用Activity造成系统资源过分占用，工具类
 * @author longway
 * 
 */
public class 	WeakReferenceActivity {
	private WeakReference<Activity> mReference;

	public WeakReferenceActivity(Activity activity) {
		mReference = new WeakReference<Activity>(activity);
	}

	public Activity getActivity() {
		Activity activity = null;
		if (mReference == null) {
			return activity;
		}
		return mReference.get();
	}

	public boolean checkActivityAvailable() {
		Activity activity = getActivity();
		if (activity == null || activity.isFinishing()) {
			return false;
		}
		return true;
	}
}
