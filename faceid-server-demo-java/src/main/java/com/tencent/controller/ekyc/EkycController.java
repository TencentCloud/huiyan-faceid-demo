package com.tencent.controller.ekyc;

import com.tencent.service.ekyc.EkycService;
import com.tencentcloudapi.faceid.v20180301.models.ApplySdkVerificationTokenResponse;
import com.tencentcloudapi.faceid.v20180301.models.GetSdkVerificationResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author easonlzhang
 */
@RestController
@RequestMapping("/api/v1")
public class EkycController {
    @Autowired
    private EkycService ekycService;

    @GetMapping("/apply-sdk-token")
    public ApplySdkVerificationTokenResponse applySdkVerificationToken(@RequestParam String idCardType) throws Exception {
        return ekycService.applySdkVerificationToken(idCardType);
    }

    @GetMapping("/get-sdk-result")
    public GetSdkVerificationResultResponse getSdkVerificationResult(@RequestParam String sdkToken) throws Exception {
        return ekycService.getSdkVerificationResult(sdkToken);
    }
}
