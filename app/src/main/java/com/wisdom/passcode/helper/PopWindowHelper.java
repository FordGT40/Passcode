package com.wisdom.passcode.helper;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.wisdom.passcode.ConstantString;
import com.wisdom.passcode.R;
import com.wisdom.passcode.util.LogUtil;
import com.wisdom.passcode.util.ToastUtil;

import java.io.File;

import static cn.bertsir.zbar.QrConfig.REQUEST_CAMERA;


/**
 * Created by Administrator on 2017/5/24.
 */

public class PopWindowHelper implements View.OnClickListener {
    private static PopupWindow window;
    private Context context;

    public PopWindowHelper(Context context) {
        this.context = context;
    }

    /**
     * 弹出选择上传方式菜单
     *
     * @param context
     */
    public void showUploadPop(final Context context) {
        WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
        lp.alpha = Float.parseFloat("0.5"); //0.0-1.0
        ((Activity) context).getWindow().setAttributes(lp);
        // 一个自定义的布局，作为显示的内容
        View view = LayoutInflater.from(context).inflate(
                R.layout.pop_select_upload, null);
        LinearLayout ll_upload_img = (LinearLayout) view.findViewById(R.id.ll_upload_img);
        LinearLayout ll_upload_camaro = (LinearLayout) view.findViewById(R.id.ll_upload_camaro);
        LinearLayout ll_upload_file = (LinearLayout) view.findViewById(R.id.ll_upload_file);
        LinearLayout ll_cancel = (LinearLayout) view.findViewById(R.id.ll_cancel);
        ll_cancel.setOnClickListener(this);
        ll_upload_file.setOnClickListener(this);
        ll_upload_camaro.setOnClickListener(this);
        ll_upload_img.setOnClickListener(this);
        window = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(0xffffffff));
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);
        // 设置popWindow的显示和消失动画
        window.setAnimationStyle(R.style.mypopwindow_anim_style);
        // 在底部显示
        window.showAtLocation(((Activity) context).findViewById(R.id.ll_parent),
                Gravity.BOTTOM, 0, 0);
        //popWindow消失监听方法
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
                lp.alpha = 1; //0.0-1.0
                ((Activity) context).getWindow().setAttributes(lp);
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_cancel: {//取消按钮
                if (window != null) {
                    window.dismiss();
                }
            }
            break;
            case R.id.ll_upload_img: {//从相册选择
//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
////              intent.setType("text/plain");
////                Intent intent = new Intent(Intent.ACTION_GET_CONTENT,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                intent.setType("image/*");
//                intent.addCategory(Intent.CATEGORY_OPENABLE);
                Intent intent;
                if (Build.VERSION.SDK_INT < 19) {
                    intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                } else {
                    intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                }
                try {
                    ((Activity) context).startActivityForResult(Intent.createChooser(intent, "选择上传的照片"), ConstantString.ALBUM_SELECT_CODE);
                    window.dismiss();
                } catch (android.content.ActivityNotFoundException ex) {
                    ToastUtil.Companion.showToast("请先安装文件浏览器！");
                }
            }
            break;
            case R.id.ll_upload_camaro: {//打开相机
                //申请运行时权限
                if (ContextCompat.checkSelfPermission(context,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) context,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            0x113);
                }
                String filePath = Environment.getExternalStorageDirectory() + "/passcode/" + System.currentTimeMillis() + ".jpg";
                File outputFile = new File(filePath);
                if (!outputFile.getParentFile().exists()) {
                    outputFile.getParentFile().mkdir();
                }
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Uri contentUri = FileProvider.getUriForFile(context, "com.wisdom.passcode.myprovider.contentprovider", outputFile);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
                } else {
                    Uri photoUri = Uri.fromFile(outputFile); // 传递路径
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);// 更改系统默认存储路径
                }
                ConstantString.Companion.setPIC_LOCATE(filePath);
                LogUtil.getInstance().e("filePath:"+filePath);
                LogUtil.getInstance().e("ConstantString.Companion:"+ConstantString.Companion.getPIC_LOCATE());
                ((Activity) context).startActivityForResult(intent, REQUEST_CAMERA);
                window.dismiss();
            }
            break;
            case R.id.ll_upload_file: {//选择文件
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
//                intent.setType("img/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                try {
                    ((Activity) context).startActivityForResult(Intent.createChooser(intent, "选择上传的文件"), ConstantString.FILE_SELECT_CODE);
                    window.dismiss();
                } catch (android.content.ActivityNotFoundException ex) {
                    ToastUtil.Companion.showToast("请先安装文件浏览器！");
                }
            }
            break;
        }
    }


}
