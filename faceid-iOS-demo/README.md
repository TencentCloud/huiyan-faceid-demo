

# 一、开发准备

1. 注册腾讯云账号，点击进入eKYC的控制台，即可开通相应服务。
2. 从eKYC SDK下载链接中下载对应版本的SDK，并集成到本地。
3. 联系官网的商务或者客服人员，获取对应版本的license文件。


# 二、接入流程

## 2.1 依赖环境

1. 开发环境 Xcode 12.0 及以上版本，推荐使用最新的版本。
2. SDK 适用于iOS11.0及以上版本。

## 2.2 接入步骤

导入相关库及文件

### 1）导入SDK Framework
Link Binary With Libraries导入相关Framework SDK依赖的库如下：

```
├── HuiYanEKYCVerification.framework
├── tnn.framework
├── tnnliveness.framework
├── YTCommonLiveness.framework
├── YTFaceAlignmentTinyLiveness.framework
├── YTFaceDetectorLiveness.framework
├── YTFaceLiveReflect.framework
├── YTFaceTrackerLiveness.framework
├── YTPoseDetector.framework
├── YtSDKKitActionLiveness.framework
├── YtSDKKitFramework.framework
├── YtSDKKitOcrVideoIdent.framework
├── YtSDKKitReflectLiveness.framework
├── YtSDKKitSilentLiveness.framework
├── HKOCRSDK.framework
├── tiny_opencv2.framework
├── HuiYanOverseasSDK.framework
├── IdVerification.framework
├── OcrSDKKit.framework
├── TXYCommonDevice.framework
├── TXYCommonNetworking.framework
├── TXYCommonUtils.framework
├── YTCv.framework
├── YTImageRefiner.framework
├── YtSDKKitFrameworkTool.framework
└── YTSm.framework
```
### 2）导入系统Framework
Link Binary With Libraries导入系统Framework

```
├── libc++.tbd
├── Accelerate.framework
└── CoreML.framework
```

### 3）导入bundle
Copy Bundle Resources中导入bundle文件

```
├── face-tracker-v003.bundle
├── huiyan_verification.bundle
├── HuiYanSDKUI.bundle
├── idverificationres.bundle
├── ocr-v001.bundle
├── OcrSDK.bundle
└── ytsdkviidres.bundle
```
### 4）Build Phases设置
1. Other Linker Flags 新增 **-ObjC**
2. 接入.m文件 设置后缀为.mm 或文件更改Type：Objective-C++ Source

      <img src="https://ai-sdk-release-1254418846.cos.ap-guangzhou.myqcloud.com/EKYC/%E5%9B%BE%E5%BA%8A/setOC%2B%2B.png" style="zoom:50%;" />

### 5）权限设置
​	SDK需要摄像头使用权限，请添加对应的权限声明。在主项目info.plist 配置中添加下面key-value值

```XML
<key>Privacy - Camera Usage Description</key>
	<string>eKYC SDK 需要访问您的相机权限</string>
<key>Privacy - Photo Library Usage Description</key>
	<string>eKYC SDK 需要访问您的相册权限</string>
```



# 三、接口使用说明

## 3.1 初始化

​	在您APP初始化的时候调用，主要是进行一些SDK的初始化操作

```objective-c
#import <HuiYanEKYCVerification/VerificationKit.h>
- (void)viewDidLoad {
    [[VerificationKit sharedInstance] initWithViewController:self];
}
```

## 3.2 启动身份认证eKYC检测流程

​	当您需要启动身份认证eKYC检测流程的时候，只需要调用startVerifiWithConfig方法，配置ekycToken，和一些自定义配置。

```objective-c
- (IBAction)startVerificationEvent:(id)sender {
    NSLog(@"startVerificationEvent");
    VerificationConfig *config = [[VerificationConfig alloc] init];
    config.licPath = [[NSBundle mainBundle] pathForResource:@"" ofType:nil];
    config.languageType = HY_EKYC_EN;
    config.verAutoTimeOut = 30000;//鉴伪超时时间设置
    config.hyFaceTimeOut = 15000;//人脸单动作超时设置
    config.ekycToken = @"";
    [[VerificationKit sharedInstance] startVerifiWithConfig:config withSuccCallback:^(int errorCode, id  _Nonnull resultInfo, id  _Nullable reserved) {
        NSLog(@"ErrCode:%d msg:%@",errorCode,resultInfo);
    } withFialCallback:^(int errorCode, NSString * _Nonnull errorMsg, id  _Nullable reserved) {
        NSLog(@"ErrCode:%d msg:%@ extra:%@",errorCode,errorMsg,reserved);
    }];
}
```

**ekycToken** 为从服务器兑换的本次身份认证流程的唯一凭证。

**注意：** **"eKYC_license.lic"**文件是需要联系商务或者客服人员进行license申请。将申请完成后的license文件放到Copy Bundle Resources下。

<img src="https://ai-sdk-release-1254418846.cos.ap-guangzhou.myqcloud.com/EKYC/%E5%9B%BE%E5%BA%8A/eKYCLicResources.png" style="zoom:50%;" />



## 3.3 SDK资源释放

​	在您调用完SDK不在使用时，可以调用SDK资源释放接口

```objective-c
- (void)dealloc {
    [VerificationKit clearInstance];
}
```