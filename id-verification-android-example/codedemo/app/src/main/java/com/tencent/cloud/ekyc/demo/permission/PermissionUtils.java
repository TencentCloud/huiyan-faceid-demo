package com.tencent.cloud.ekyc.demo.permission;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import androidx.appcompat.app.AlertDialog.Builder;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;
import java.util.List;

/**
 * 权限申请工具类
 *
 * @author jerrydong
 * @since 2020/8/12
 */
public class PermissionUtils {

    // Android 6.0 的系统版本号
    private static final int ANDROID_M = 23;

    // 权限权限
    private static final String TAG = "PermissionUtils";

    // 是否打开系统设置
    private static boolean showSystemSetting = true;

    // 不再提示权限时的展示对话框
    private Dialog mPermissionDialog;

    //权限请求码
    private final int mRequestCode = 119;

    // 用来接受回调
    private IPermissionsResult mPermissionsResult;

    /**
     * 构造方法
     */
    private PermissionUtils() {
    }

    /**
     * 单例内部类，主要处理静态内部类
     */
    private static final class PermissionUtilsHolder {

        private static final PermissionUtils INSTANCE = new PermissionUtils();
    }

    /**
     * 获取单例对象
     *
     * @return 单例对象
     */
    public static PermissionUtils getInstance() {
        return PermissionUtilsHolder.INSTANCE;
    }

    /**
     * 请求权限
     *
     * @param context 请求入口的上下文
     * @param permissions 需要请求的权限列表
     * @param permissionsResult
     */
    public void requestPermission(Activity context, String[] permissions, IPermissionsResult permissionsResult) {
        // 6.0才用动态权限
        if (Build.VERSION.SDK_INT < ANDROID_M) {
            permissionsResult.passPermissions();
            return;
        }
        mPermissionsResult = permissionsResult;
        // 创建一个mPermissionList，逐个判断哪些权限未授予，未授予的权限存储到mPerrrmissionList中
        List<String> permissionList = new ArrayList<>();
        // 逐个判断你要的权限是否已经通过
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(context, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permissions[i]); //添加还未授予的权限
            }
        }
        String[] realNeedRequest = permissionList.toArray(new String[permissionList.size()]);
        // 申请权限
        if (realNeedRequest.length > 0) { // 有权限没有通过，需要申请
            ActivityCompat.requestPermissions(context, realNeedRequest, mRequestCode);
        } else {
            // 说明权限都已经通过
            permissionsResult.passPermissions();
            return;
        }
    }

    /**
     * 处理权限请求结果
     *
     * @param context 上下文信息
     * @param requestCode 请求码
     * @param permissions 请求的权限结果
     * @param grantResults 请求结果
     */
    public void onRequestPermissionsResult(Activity context, int requestCode, String[] permissions,
            int[] grantResults) {
        if (requestCode != mRequestCode) {
            return;
        }
        // 处理权限请求结果
        boolean hasPermissionDismiss = false; // 有权限没有通过
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] == -1) {
                hasPermissionDismiss = true;
            }
        }
        //如果有权限没有被允许
        if (hasPermissionDismiss) {
            if (showSystemSetting) {
                // 跳转到系统设置权限页面，或者直接关闭页面，不让他继续访问
                showSystemPermissionsSettingDialog(context);
            } else {
                mPermissionsResult.forbidPermissions();
                cancelPermissionDialog();
                // 清理持有
                cleanPermissionsResult();
            }
        } else {
            if (mPermissionsResult != null) {
                //全部权限通过，可以进行下一步操作。。。
                mPermissionsResult.passPermissions();
                cancelPermissionDialog();
                // 清理持有
                cleanPermissionsResult();
            }
        }
    }

    /**
     * 打开权限提示Text的内容
     *
     * @param context 上下文信息
     */
    private void showSystemPermissionsSettingDialog(final Activity context) {
        final String packName = context.getPackageName();
        if (mPermissionDialog == null) {
            Builder builder = new Builder(context)
                    .setMessage("您已拒绝或禁用权限，请手动授予允许访问")
                    .setPositiveButton("设置", new OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            cancelPermissionDialog();
                            // 清理持有
                            cleanPermissionsResult();
                            Uri packageURI = Uri.parse("package:" + packName);
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                            context.startActivity(intent);
                            context.finish();
                        }
                    })
                    .setNeutralButton("取消", new OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
//                            //关闭页面或者做其他操作
                            cancelPermissionDialog();
                            mPermissionsResult.forbidPermissions();
                            // 清理持有
                            cleanPermissionsResult();
                        }
                    })
                    .setCancelable(true);
            mPermissionDialog = builder.create();
        }
        mPermissionDialog.show();
    }

    /**
     * 关闭对话框
     */
    private void cancelPermissionDialog() {
        if (mPermissionDialog != null) {
            mPermissionDialog.cancel();
            mPermissionDialog = null;
        }
    }

    /**
     * 清理持有
     */
    private void cleanPermissionsResult() {
        if (mPermissionsResult != null) {
            mPermissionsResult = null;
        }
    }
}
