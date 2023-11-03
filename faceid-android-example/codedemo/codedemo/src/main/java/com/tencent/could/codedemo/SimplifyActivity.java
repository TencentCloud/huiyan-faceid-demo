package com.tencent.could.codedemo;

import android.Manifest.permission;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import com.google.gson.Gson;
import com.tencent.could.codedemo.permission.IPermissionsResult;
import com.tencent.could.codedemo.permission.PermissionUtils;
import com.tencent.could.codedemo.util.CommonUtils;
import com.tencent.could.huiyansdk.entity.HuiYanOsAuthResult;
import com.tencent.could.huiyansdk.enums.PageColorStyle;
import com.tencent.could.huiyansdk.overseas.HuiYanOsApi;
import com.tencent.could.huiyansdk.overseas.HuiYanOsAuthCallBack;
import com.tencent.could.huiyansdk.overseas.HuiYanOsConfig;
import java.io.IOException;


/**
 * 精简版本的Activity
 *
 * @author jerrydong
 * @since 2022/6/13
 */
public class SimplifyActivity extends AppCompatActivity {

    // 需要请求的危险权限
    private String[] requestPermissions = new String[]{
            permission.READ_EXTERNAL_STORAGE, permission.WRITE_EXTERNAL_STORAGE, // 文件的读写权限
            permission.CAMERA,    // 相机权限
    };

    private static final String TAG = "SimplifyActivity";

    // 默认的toekn
    private static final String DEFAULT_FACE_TOKEN = "123";

    // 当前的Token
    private String currentToken = DEFAULT_FACE_TOKEN;

    // 选择框信息
    private AppCompatCheckBox compatCheckBox;

    // 是否返回最佳帧
    private AppCompatCheckBox needBestImageCB;


    // 设置自定义Token的按钮
    private EditText editText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simplify_main);
        initView();
        initHuiYanCheckEvent();
    }

    /**
     * 界面相关的初始化
     */
    private void initView() {
        // 添加自定义设置faceIdToken的方法
        compatCheckBox = findViewById(R.id.use_dark_style);
        needBestImageCB = findViewById(R.id.is_need_best_image_check);
        editText = findViewById(R.id.edit_text_ed);
        findViewById(R.id.set_token_btn).setOnClickListener(v -> {
            currentToken = editText.getText().toString();
            Toast.makeText(SimplifyActivity.this, "设置FaceIdToken为:" + currentToken, Toast.LENGTH_SHORT).show();
        });
    }


    /**
     * 慧眼启动的流程
     */
    private void initHuiYanCheckEvent() {
        findViewById(R.id.start_auth_check_btn).setOnClickListener(v -> {
            if (TextUtils.isEmpty(currentToken)) {
                Toast.makeText(App.getApp(), "token 不能为空", Toast.LENGTH_SHORT).show();
                return;
            }

            // HuiYanOs的相关参数
            HuiYanOsConfig huiYanOsConfig = new HuiYanOsConfig();
            // 此license文件存放在assets下【此工程没有需要联系客服进行神奇】
            huiYanOsConfig.setAuthLicense("YTFaceSDK.license");
            if (compatCheckBox.isChecked()) {
                huiYanOsConfig.setPageColorStyle(PageColorStyle.Dark);
            }
            // 是否需要返回最佳帧
            if (needBestImageCB.isChecked()) {
                huiYanOsConfig.setNeedBestImage(true);
            }
            // 启动核身方法，开始的活体，currentToken为后台下发数据
            HuiYanOsApi.startHuiYanAuth(currentToken, huiYanOsConfig, new HuiYanOsAuthCallBack() {
                @Override
                public void onSuccess(HuiYanOsAuthResult authResult) {
                    showToast("活体通过！");
                    if (!TextUtils.isEmpty(authResult.getBestImage())) {
                        // 存最佳帧到相册，（可以自行使用）
                        CommonUtils.decryptBestImgBase64(authResult.getBestImage(), false);
                    }
                }

                @Override
                public void onFail(int errorCode, String errorMsg, String token) {
                    String msg = "活体失败 " + "code: " + errorCode + " msg: " + errorMsg + " token: " + token;
                    Log.e(TAG, "onFail" + msg);
                    showToast(msg);
                }
            });
        });
        // 权限申请
        PermissionUtils.getInstance().requestPermission(this, requestPermissions, new IPermissionsResult() {
            @Override
            public void passPermissions() {
                Log.e(TAG, "权限授权完成");
            }

            @Override
            public void forbidPermissions() {
                Log.e(TAG, "权限授权未完成！");
            }
        });
    }


    /**
     * 显示的内容
     *
     * @param showMsg
     */
    private void showToast(final String showMsg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(SimplifyActivity.this, showMsg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtils.getInstance().onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

}
