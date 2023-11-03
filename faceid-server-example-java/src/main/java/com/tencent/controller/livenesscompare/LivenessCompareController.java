package com.tencent.controller.livenesscompare;

import com.tencent.service.livenesscompare.LivenessCompareService;
import com.tencentcloudapi.faceid.v20180301.models.GetFaceIdResultIntlResponse;
import com.tencentcloudapi.faceid.v20180301.models.GetFaceIdTokenIntlResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * liveness compare controller expose endpoint.
 * @author easonlzhang
 */
@RestController
@RequestMapping("/api/v1")
public class LivenessCompareController {
    @Autowired
    private LivenessCompareService livenessCompareService;

    @GetMapping("/get-token")
    public GetFaceIdTokenIntlResponse getFaceIdToken(@RequestParam String secureLevel) throws Exception {
        return livenessCompareService.getFaceIdToken(secureLevel);
    }

    @GetMapping("/get-result")
    public GetFaceIdResultIntlResponse getFaceIdResult(@RequestParam String sdkToken) throws Exception {
        return livenessCompareService.getFaceIdResult(sdkToken);
    }
}
