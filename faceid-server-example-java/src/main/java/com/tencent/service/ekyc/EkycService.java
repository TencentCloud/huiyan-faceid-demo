package com.tencent.service.ekyc;

import com.tencentcloudapi.faceid.v20180301.FaceidClient;
import com.tencentcloudapi.faceid.v20180301.models.ApplySdkVerificationTokenRequest;
import com.tencentcloudapi.faceid.v20180301.models.ApplySdkVerificationTokenResponse;
import com.tencentcloudapi.faceid.v20180301.models.GetSdkVerificationResultRequest;
import com.tencentcloudapi.faceid.v20180301.models.GetSdkVerificationResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author easonlzhang
 */
@Service
public class EkycService {
    @Autowired
    private FaceidClient client;

    /**
     * apply sdk verification token
     * @param idCardType idCardType
     * @return response
     * @throws Exception TencentCloudSDKException
     */
    public ApplySdkVerificationTokenResponse applySdkVerificationToken(String idCardType) throws Exception {
        // Step 1: instantiate the request object and provide necessary parameters
        ApplySdkVerificationTokenRequest req = new ApplySdkVerificationTokenRequest();
        req.setNeedVerifyIdCard(false);
        req.setIdCardType(idCardType);

        // Step 2: call the Tencent Cloud API through FaceIdClient
        ApplySdkVerificationTokenResponse response = client.ApplySdkVerificationToken(req);

        // Step 3: process the Tencent Cloud API response and construct the return object
        System.out.println(response.getSdkToken());
        return response;
    }

    /**
     * get sdk verification result
     * @param sdkToken sdkToken
     * @return response
     * @throws Exception TencentCloudSDKException
     */
    public GetSdkVerificationResultResponse getSdkVerificationResult(String sdkToken) throws Exception {
        // Step 1: instantiate the request object and provide necessary parameters
        GetSdkVerificationResultRequest req = new GetSdkVerificationResultRequest();
        req.setSdkToken(sdkToken);

        // Step 2: call the Tencent Cloud API through FaceIdClient
        GetSdkVerificationResultResponse response = client.GetSdkVerificationResult(req);

        // Step 3: process the Tencent Cloud API response and construct the return object
        System.out.println(response.getResult());
        return response;
    }
}
