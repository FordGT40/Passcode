package com.wisdom.passcode.base.weakreferences;

import android.view.View;

import java.lang.ref.WeakReference;

/**
 * View可用性检测器
 * View弱引用。工具类，避免强制引用，造成系统资源过分占用
 * @author longway
 * 
 */
public class WeakReferenceView {
	private WeakReference<View> mReference;

	public WeakReferenceView(View view) {
		mReference = new WeakReference<View>(view);
	}

	public View getView() {
		View view = null;
		if (mReference == null) {
			return view;
		}
		return mReference.get();
	}

	public boolean checkViewAvailable() {
		View view = getView();
		if (view == null) {
			return false;
		}
		return true;
	}
}
