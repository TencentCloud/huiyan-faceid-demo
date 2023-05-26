package com.tencent.cloud.ekyc.demo;

import android.os.Bundle;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.tencent.cloud.ekyc.base.enums.LanguageStyle;
import com.tencent.cloud.ekyc.ekycsdk.api.EkycHySdk;
import com.tencent.cloud.ekyc.ekycsdk.callback.EkycHyCallBack;
import com.tencent.cloud.ekyc.ekycsdk.entity.EkycHyConfig;
import com.tencent.cloud.ekyc.ekycsdk.entity.EkycHyResult;
import com.tencent.cloud.ekyc.ekycsdk.entity.OcrUiConfig;

public class HomeActivity  extends AppCompatActivity {
    private static final String TAG = HomeActivity.class.getSimpleName();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        findViewById(R.id.btn_open).setOnClickListener(v -> {
            startEkycCheck();
        });
    }

    /**
     * 开始EKYC的检测
     */
    private void startEkycCheck() {
        EkycHyConfig ekycHyConfig = new EkycHyConfig();
        ekycHyConfig.setLicenseName(/** todo lic文件名(assets目录)*/);
        ekycHyConfig.setOcrType(/** todo 卡证检测类型OcrRegionType 枚举类型*/);
        OcrUiConfig config = new OcrUiConfig();
        config.setRemoveFlash(false);
        config.setRemoveAlbum(false);
        ekycHyConfig.setLanguageStyle(LanguageStyle.AUTO);
        ekycHyConfig.setOcrUiConfig(config);
        EkycHySdk.startEkycCheck(/**todo 设置token*/, ekycHyConfig, new EkycHyCallBack() {
            @Override
            public void onSuccess(EkycHyResult result) {
                Log.e(TAG, "result: " + result.toString());
            }

            @Override
            public void onFail(int errorCode, String errorMsg, String ekycToken) {
                Log.e(TAG, "code: " + errorCode + " msg: " + errorMsg + " token: " + ekycToken);
            }
        });
    }
}
