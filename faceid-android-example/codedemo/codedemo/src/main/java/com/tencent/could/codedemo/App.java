package com.tencent.could.codedemo;

import android.app.Application;
import com.tencent.could.huiyansdk.overseas.HuiYanOsApi;

/**
 * demo应用的app对象
 *
 * @author jerrydong
 * @since 2020/8/10
 */
public class App extends Application {

    // 单例App对象
    private static App instance;

    /**
     * 获取当前的app对象
     *
     * @return Application对象
     */
    public static App getApp() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        HuiYanOsApi.init(getApp());
    }

}
