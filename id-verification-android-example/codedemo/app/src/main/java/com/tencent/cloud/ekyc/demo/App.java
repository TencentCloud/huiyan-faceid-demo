package com.tencent.cloud.ekyc.demo;

import android.app.Application;
import com.tencent.cloud.ekyc.ekycsdk.api.EkycHySdk;


public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        EkycHySdk.init(this);
    }
}
