package com.wisdom.passcode.faceId;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * @author HanXueFeng
 * @ProjectName project： hrbzwt
 * @class package：com.wisdom.hrbzwt.idcard_face
 * @class describe：
 * @time 2019/6/28 16:10
 * @change
 */
public class FinishReceiver extends BroadcastReceiver {
    private Activity activity;

    public FinishReceiver(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        activity.finish();
    }
}
