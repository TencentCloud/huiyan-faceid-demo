#### 1、依赖环境

当前Android端慧眼SDK适用于API 19 (Android 4.4) 及以上版本。

#### 2、SDK接入步骤

1. 将**huiyansdk_android_overseas_1.0.9.6_release.aar**具体版本号以官网下载为准) 和 **tencent-ai-sdk-youtu-base-1.0.1.39-release.aar**、**tencent-ai-sdk-common-1.1.36-release.aar**、**tencent-ai-sdk-aicamera-1.0.22-release.aar**(具体版本号以最终提供为准) 添加到您工程的libs目录下。

![](https://ai-sdk-release-1254418846.cos.ap-guangzhou.myqcloud.com/SDK%E6%96%87%E6%A1%A3%E5%9B%BE%E5%BA%8A/tuyong/oversea_lib.png)

2. 在您工程的**build.gradle**中进行如下配置：

```groovy
// 设置ndk so架构过滤(以armeabi-v7a为例)
ndk {
    abiFilters 'armeabi-v7a'
}

dependencies {
    // 引入慧眼SDK
    implementation files("libs/huiyansdk_android_overseas_1.0.9.5_release.aar")
    // 慧眼通用算法SDK
    implementation files("libs/tencent-ai-sdk-youtu-base-1.0.1.32-release.aar")
    // 通用能力组件库
    implementation files("libs/tencent-ai-sdk-common-1.1.27-release.aar")
    implementation files("libs/tencent-ai-sdk-aicamera-1.0.18-release.aar")

  	// 慧眼SDK需要依赖的第三方库
    // gson
    implementation 'com.google.code.gson:gson:2.8.5'
}
```

3. 同时需要在AndroidManifest.xml文件中进行必要的权限声明

```xml
<!-- 摄像头权限 -->
<uses-permission android:name="android.permission.CAMERA" />
<uses-feature
    android:name="android.hardware.camera"
    android:required="true" />
<uses-feature android:name="android.hardware.camera.autofocus" />

<!-- SDK需要的权限 -->
<uses-permission android:name="android.permission.INTERNET" />
<!-- SDK可选需要的权限 -->
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
```

   对于需要兼容Android 6.0以上的用户，以上权限除了需要在AndroidManifest.xml文件中声明权以外，还需使用代码动态申请权限。

#### 3、初始化接口

	在您APP初始化的时候调用，推荐在Application中调用，主要是进行一些SDK的初始化操作

```java
@Override
public void onCreate() {
    super.onCreate();
    instance = this;
    // SDK需要在Application初始化时进行初始化
    HuiYanOsApi.init(getApp());
}
```

#### 4、启动核身检测接口

```java
// HuiYanOs的相关参数
HuiYanOsConfig huiYanOsConfig = new HuiYanOsConfig();
// 此license文件存放在assets下
huiYanOsConfig.setAuthLicense("YTFaceSDK.license");
if (compatCheckBox.isChecked()) {
    huiYanOsConfig.setPageColorStyle(PageColorStyle.Dark);
}
// 是否需要返回最佳帧
if (needBestImageCB.isChecked()) {
    huiYanOsConfig.setNeedBestImage(true);
}
// 启动方法，开始的活体，currentToken为后台下发数据
HuiYanOsApi.startHuiYanAuth(currentToken, huiYanOsConfig, new HuiYanOsAuthCallBack() {
    @Override
    public void onSuccess(HuiYanOsAuthResult authResult) {
        showToast("活体通过！");
        if (!TextUtils.isEmpty(authResult.getBestImage())) {
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
```

[HuiYanOsAuthResult](https://iwiki.woa.com/pages/viewpage.action?pageId=4007948264)为活体成功的返回结果, 最终的核身结果可以通过token，访问[GetFaceldResultIntl](//todo:正式的官网API接口)获取。

**注意：**当前的 **"YTFaceSDK.license"**文件是需要您主动申请的，暂时您可以联系客服人员进行license申请。将申请完成后的license文件放到assets文件下。

![](https://ai-sdk-release-1254418846.cos.ap-guangzhou.myqcloud.com/huiyan/image/license%E5%AD%98%E6%94%BE%E8%B7%AF%E5%BE%84.png)

#### 5、SDK资源释放

在您APP退出使用的时候，可以调用SDK资源释放接口

```java
@Override
protected void onDestroy() {
    super.onDestroy();
    // 退出时做资源释放
    HuiYanOsApi.release();
}
```

#### 6、混淆规则配置

  如果您的应用开启了混淆功能，为确保SDK的正常使用，请把以下部分添加到您的混淆文件中。

```java
#慧眼SDK的混淆包含
-keep class com.tencent.could.huiyansdk.** {*;}
-keep class com.tencent.could.aicamare.** {*;}
-keep class com.tencent.could.component.** {*;}
-keep class com.tencent.youtu.** {*;}
-keep class com.tenpay.utils.SMUtils {*;}
```