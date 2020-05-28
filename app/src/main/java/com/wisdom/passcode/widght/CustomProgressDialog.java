package com.wisdom.passcode.widght;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.wisdom.passcode.R;

/**
 * @author HanXueFeng
 * @ProjectName project： TVTestApplication
 * @class package：com.wisdom.hrbtv.ui
 * @class describe：
 * @time 2020/3/25 0025 11:59
 * @change
 */
public class CustomProgressDialog extends Dialog {

    public TextView messageTv;

    public CustomProgressDialog(Context context) {
        this(context, R.style.MyDialogStyle, "");
    }

    public CustomProgressDialog(Context context, String string) {
        this(context, R.style.MyDialogStyle, string);
    }

    public CustomProgressDialog(Context context, int theme, String string) {
        super(context, theme);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.item_custom_progress_dialog);
        messageTv = (TextView) findViewById(R.id.tv_message);
        messageTv.setText(string);
        getWindow().getAttributes().gravity = Gravity.CENTER;
        getWindow().getAttributes().dimAmount = 0f;
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {

        return super.onKeyDown(keyCode, event);
    }
    //    public void setMessage(String msg){
//        messageTv.setText(msg);
//    }
}
