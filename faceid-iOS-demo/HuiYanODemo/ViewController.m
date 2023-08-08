//
//  ViewController.m
//  HuiYanODemo
//
//  Created by clv on 2022/6/13.
//

#import "ViewController.h"
#import <AVFoundation/AVMediaFormat.h>
#import <AVFoundation/AVCaptureDevice.h>

#import <HuiYanSDK/HuiYanOsApi.h>
#import <HuiYanSDK/HuiYanOSKit.h>

@interface ViewController (){
    BOOL isHasCameraPermissions;
}

@end

@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];

    NSLog(@"version:%@",[HuiYanOsApi sdkVersion]);
}


-(void) viewWillAppear:(BOOL)animated {
    NSString *mediaType = AVMediaTypeVideo;//读取媒体类型
    [AVCaptureDevice requestAccessForMediaType:mediaType completionHandler:^(BOOL granted) {
       if (granted) {
           NSLog(@"允许访问相机");
           self->isHasCameraPermissions = YES;
       } else {
           NSLog(@"用户拒绝访问相机~");
           self->isHasCameraPermissions = NO;
           dispatch_async(dispatch_get_main_queue(), ^{
               NSLog(@"请开启访问相机权限!");
           });
       }
    }];
}

- (IBAction)startHuiYanSDKEvent:(id)sender {

    [self startNetHuiYanAuth];
}

- (void)startNetHuiYanAuth {

    // 业务token，推荐从后台拉取
    NSString *faceToken = self.tokenTextField.text;
    
    HuiYanOsConfig *config = [[HuiYanOsConfig alloc] init];
    // 这里需要传入对应的申请的lic文件
    config.authLicense = [[NSBundle mainBundle] pathForResource:@".ls" ofType:@""];
    config.authTimeOutMs = 20000;
    config.isDeleteVideoCache = YES;
    config.iShowTipsPage = YES;
    // 主动启动核身检测的SDK代码
    [[HuiYanOSKit sharedInstance] startHuiYaneKYC:faceToken withConfig:config
                                  witSuccCallback:^(HuiYanOsAuthResult * _Nonnull authResult, id  _Nullable reserved) {
        NSString *bestImg = authResult.bestImage;
        NSString *token = authResult.faceToken;
        
    } withFailCallback:^(int errCode, NSString * _Nonnull errMsg, id  _Nullable reserved) {
        NSString *showMsg = [NSString stringWithFormat:@"err:%d:%@",errCode,errMsg];
        NSLog(@"err:%@",showMsg);
    }];
}

@end
