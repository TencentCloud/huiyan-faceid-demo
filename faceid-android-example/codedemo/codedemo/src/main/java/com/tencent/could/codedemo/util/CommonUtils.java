package com.tencent.could.codedemo.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore.Images.Media;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import com.tencent.could.codedemo.App;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author jerrydong
 * @since 2021/4/12
 */
public class CommonUtils {

    private static final String TAG = "CommonUtils";

    /**
     * base64加密
     *
     * @param input
     * @return
     */
    public static String base64EncodeToString(final String input) {
        byte[] encode = Base64.encode(input.getBytes(), Base64.NO_WRAP);
        return new String(encode);
    }

    /**
     * base64解密
     *
     * @param input
     * @return base64 解密的结果
     */
    public static String base64DecodeToString(final String input) {
        byte[] decode = Base64.decode(input, Base64.NO_WRAP);
        return new String(decode);
    }

    /**
     * 将BitMap存储到相册图库里
     *
     * @param bestImgEncryptedBase64
     * @param needDecode 是否需要主动解密
     * @return 相册里的Uri
     */
    public static Uri decryptBestImgBase64(String bestImgEncryptedBase64, boolean needDecode) {
        // 如果是null则不需要打开界面
        if (TextUtils.isEmpty(bestImgEncryptedBase64)) {
            return null;
        }
        byte[] jpg;
        try {
            // 需要解密信息
            if (needDecode) {
                byte[] keyBytes = "huiyansdkaeskeydemotestt".getBytes("UTF-8");
                byte[] bestImgEncryptedBytes = Base64.decode(bestImgEncryptedBase64
                        .getBytes("UTF-8"), Base64.DEFAULT);
                // PC 上需要, Android 不需要
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding");
                SecretKey keySpec = new SecretKeySpec(keyBytes, "AES");
                cipher.init(Cipher.DECRYPT_MODE, keySpec);
                byte[] jpgBase64Bytes = cipher.doFinal(bestImgEncryptedBytes);
                jpg = Base64.decode(jpgBase64Bytes, Base64.DEFAULT);
            } else {
                jpg = Base64.decode(bestImgEncryptedBase64, Base64.NO_WRAP);
            }
        } catch (Exception e) {
            Log.e(TAG, "Decrypt fail." + e.getLocalizedMessage());
            return null;
        }
        // 保存 jpg 图片到媒体库
        Bitmap bitmap = BitmapFactory.decodeByteArray(jpg, 0, jpg.length);
        if (bitmap == null) {
            Log.w(TAG, "Decode jpg bytes fail.");
            return null;
        }
        // 存储BitMap到相册
        Context applicationContext = App.getApp();
        String imgFileName = "HuiYan_bestImage"
                + "_" + System.currentTimeMillis();
        String imgUrl = Media.insertImage(applicationContext.getContentResolver(), bitmap, imgFileName, "desc");
        // uri = content://media/external/images/media/155676
        return Uri.parse(imgUrl);
    }
}
