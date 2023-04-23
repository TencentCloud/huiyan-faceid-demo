#### 1、依赖环境

1. 开发环境 Xcode 11.0 或以上
2. 慧眼iOS SDK 适用于手机iOS9.0及以上版本

#### 2、SDK接入步骤
##### 手动接入方式

1. 导入相关库及文件

Link Binary With Libraries导入相关Framework

2. SDK依赖的库如下

```
├──HuiYanSDK.framework
├──YtSDKKitSilentLiveness.framework
├──YtSDKKitReflectLiveness.framework
├──YtSDKKitActionLiveness.framework
├──YtSDKKitFramework.framework
├──tnnliveness.framework
├──YTFaceAlignmentTinyLiveness.framework
├──YTFaceTrackerLiveness.framework
├──YTFaceDetectorLiveness.framework
├──YTPoseDetector.framework
├──YTCommonLiveness.framework
└──YTFaceLiveReflect.framework
```

3. Link Binary With Libraries导入系统Framework

```
├── AVFoundation.framework
├── libc++.tbd
└── Accelerate.framework
```

4. Copy Bundle Resources中导入模型

```
└── face-tracker-v001.bundle
```

5. Copy Bundle Resources导入资源文件

```
└── HuiYanSDKUI.bundle
```

##### 使用Pod方式接入

1. 在Podfile设置

```ruby
source 'https://github.com/TencentCloud/huiyan-faceid-demo'
target 'HuiYanODemo' do
  pod 'faceidkit'
end
```
or
```ruby
target 'HuiYanODemo' do
  pod 'faceidkit', :source => 'https://github.com/TencentCloud/huiyan-faceid-demo'
end
```

3. pod install --repo-update 更新

>文件层级和具体的设置可以参考Demo

##### Build Phases设置

1. Other Linker Flags 新增 **-ObjC**
2. 接入ViewController.m 设置后缀为.mm(swift 工程添加系统库libc++.tbd)

##### 权限设置

SDK需要手机网络及 摄像头使用权限，请添加对应的权限声明。在主项目info.plist 配置中添加下面key-value值

```xml
<key>Privacy - Camera Usage Description</key>
<string>人脸核身需要开启您的摄像头权限，用于人脸识别</string>
```

#### 3、开启活体检测接口

```objective-c
#import <HuiYanSDK/HuiYanOsApi.h>
#import <HuiYanSDK/HuiYanOSKit.h>

  //获取token
    NSString *faceToken = self.tokenTextField.text;
    // 配置SDK
    HuiYanOsConfig *config = [[HuiYanOsConfig alloc] init];
    //设置lic
    config.authLicense = [[NSBundle mainBundle] pathForResource:@"xxx.lic" ofType:@""];
    //准备阶段超时配置
    config.prepareTimeoutMs = 20000;
    //检测阶段超时配置
    config.authTimeOutMs = 20000;
    config.isDeleteVideoCache = YES;
    config.languageType = EN;
    //    config.userLanguageFileName = @"ko";
    //    config.userLanguageBundleName = @"UseLanguageBundle";
    config.iShowTipsPage = YES;
    config.isGetBestImg = YES;

    [[HuiYanOSKit sharedInstance] startHuiYaneKYC:faceToken withConfig:config
                                  witSuccCallback:^(HuiYanOsAuthResult * _Nonnull authResult, id  _Nullable reserved) {
        NSString *bestImg = authResult.bestImage;
        NSString *token = authResult.faceToken;
        
    } withFailCallback:^(int errCode, NSString * _Nonnull errMsg, id  _Nullable reserved) {
        NSString *showMsg = [NSString stringWithFormat:@"err:%d:%@",errCode,errMsg];
        NSLog(@"err:%@",showMsg);
    }];
```
[HuiYanOsAuthResult](https://iwiki.woa.com/pages/viewpage.action?pageId=4007948264)为活体成功的返回结果, 最终的核身结果可以通过token，访问[GetFaceldResultIntl](//todo:正式的官网API接口)获取。

**注意：**当前的 **"xxx.lic"**文件是需要您主动申请的，暂时您可以联系客服人员进行license申请

#### 4、SDK资源释放

在您APP退出使用的时候，可以调用SDK资源释放接口

```objective-c
// 退出时做资源释放
- (void)dealloc {
    [HuiYanOsApi release];
}
```