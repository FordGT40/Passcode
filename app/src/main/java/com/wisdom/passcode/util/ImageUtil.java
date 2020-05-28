package com.wisdom.passcode.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.baidu.tts.loopj.Base64;
import com.wisdom.passcode.faceId.faceAli.CameraView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

import Decoder.BASE64Decoder;

/**
 * @author HanXueFeng
 * @ProjectName project： hrbzwt
 * @class package：com.wisdom.hrbzwt.util
 * @class describe：
 * @time 2019/5/30 15:38
 * @change
 */
public class ImageUtil {

    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {

        int i;

        int j;

        if (bmp.getHeight() > bmp.getWidth()) {

            i = bmp.getWidth();

            j = bmp.getWidth();

        } else {

            i = bmp.getHeight();

            j = bmp.getHeight();

        }

        Bitmap localBitmap = Bitmap.createBitmap(i, j, Bitmap.Config.RGB_565);

        Canvas localCanvas = new Canvas(localBitmap);

        while (true) {

            localCanvas.drawBitmap(bmp, new Rect(0, 0, i, j), new Rect(0, 0, i, j), null);

            if (needRecycle)

                bmp.recycle();

            ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();

            localBitmap.compress(Bitmap.CompressFormat.JPEG, 100,

                    localByteArrayOutputStream);

            localBitmap.recycle();

            byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();

            try {

                localByteArrayOutputStream.close();

                return arrayOfByte;

            } catch (Exception e) {

                // F.out(e);

            }

            i = bmp.getHeight();

            j = bmp.getHeight();

        }

    }


    public static byte[] bmpToByteArray(Bitmap bmp) {
        int bytes = bmp.getByteCount();

        ByteBuffer buf = ByteBuffer.allocate(bytes);
        bmp.copyPixelsToBuffer(buf);

        byte[] byteArray = buf.array();
        return byteArray;
    }

    /**
     * @param imgStr base64编码字符串
     * @param path   图片路径-具体到文件
     * @return
     * @Description: 将base64编码字符串转换为图片
     * @Author:
     * @CreateTime:
     */
    public static boolean generateImage(String imgStr, String path) {
        if (imgStr == null)
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
// 解密
            byte[] b = decoder.decodeBuffer(imgStr);
// 处理数据
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            OutputStream out = new FileOutputStream(path);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * @return
     * @Description: 根据图片地址转换为base64编码字符串
     * @Author:
     * @CreateTime:
     */
    public static String getImageStr(String imgFile) {
        InputStream inputStream = null;
        byte[] data = null;
        try {
            inputStream = new FileInputStream(imgFile);
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("ImgeUtil", "getImageStr: " + e);
        }
        // 返回Base64编码过的字节数组字符串
        String result = Base64.encodeToString(data, Base64.NO_WRAP);
        return result;
    }

    /* 本地图片转换Base64的方法
     * @param imgPath
     */
    public static String ImageToBase64(String imgPath) {
        InputStream in = null;
        byte[] data = null;
// 读取图片字节数组
        try {
            in = new FileInputStream(imgPath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
// 返回Base64编码过的字节数组字符串
        String result = Base64.encodeToString(data, Base64.NO_WRAP);
        return result;
    }


    /*
     * bitmap转base64
     * */
    public static String bitmapToBase64(Bitmap bitmap) {
        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.NO_WRAP);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //暂时注释掉，扫脸登录之后保存照片到本地的操作
//        try {
//            Boolean isSuccess=saveMyBitmap(bitmap,"a.jpg");
//            Log.i("****", "bitmapToBase64: "+isSuccess);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return result;
    }

    public static boolean saveMyBitmap(Bitmap bmp, String bitName) throws IOException {
        boolean flag = false;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

// 获得存储卡的路径
            String sdpath = Environment.getExternalStorageDirectory() + "/";
            File f = new File(sdpath, bitName);
            f.createNewFile();
            FileOutputStream fOut = null;
            try {
                fOut = new FileOutputStream(f);
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                flag = true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                fOut.flush();
                fOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        return flag;
    }


    /**
     * 将view转换为图片
     * @param v
     * @return
     */
    public static Bitmap getViewBitmap(View v) {
        v.clearFocus();
        v.setPressed(false);
        boolean willNotCache = v.willNotCacheDrawing();
        v.setWillNotCacheDrawing(false);
        int color = v.getDrawingCacheBackgroundColor();
        v.setDrawingCacheBackgroundColor(0);
        if (color != 0) {
            v.destroyDrawingCache();
        }
        v.buildDrawingCache();
        Bitmap cacheBitmap = v.getDrawingCache();
        if (cacheBitmap == null) {
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);
        v.destroyDrawingCache();
        v.setWillNotCacheDrawing(willNotCache);
        v.setDrawingCacheBackgroundColor(color);
        return bitmap;
    }


    /**
     * 定制方法，获得人脸识别后的图片 （扫脸登录专用）
     * @param mCameraView
     * @param data
     * @return
     */
    public static Bitmap getBitmapView(CameraView mCameraView, byte[] data) {
        //获取bitmap
        Camera.Size previewSize = mCameraView.getPreviewSize();//获取尺寸,格式转换的时候要用到
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;
        YuvImage yuvimage = new YuvImage(data, ImageFormat.NV21, previewSize.width, previewSize.height,
                null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        yuvimage.compressToJpeg(new Rect(0, 0, previewSize.width, previewSize.height), 100, baos);// 80--JPG图片的质量[0-100],100最高
        byte[] rawImage = baos.toByteArray();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        return BitmapFactory.decodeByteArray(rawImage, 0, rawImage.length, options);
    }


    /**
     * 旋转变换
     *
     * @param origin 原图
     * @param alpha  旋转角度，可正可负
     * @return 旋转后的图片
     */
    public  static Bitmap rotateBitmap(Bitmap origin, float alpha) {
        if (origin == null) {
            return null;
        }
        int width = origin.getWidth();
        int height = origin.getHeight();
        Matrix matrix = new Matrix();
        matrix.setRotate(alpha);
        // 围绕原地进行旋转
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        if (newBM.equals(origin)) {
            return newBM;
        }
        origin.recycle();
        return newBM;
    }




}

