package com.tencent.service.livenesscompare;

import com.tencentcloudapi.faceid.v20180301.FaceidClient;
import com.tencentcloudapi.faceid.v20180301.models.GetFaceIdResultIntlRequest;
import com.tencentcloudapi.faceid.v20180301.models.GetFaceIdResultIntlResponse;
import com.tencentcloudapi.faceid.v20180301.models.GetFaceIdTokenIntlRequest;
import com.tencentcloudapi.faceid.v20180301.models.GetFaceIdTokenIntlResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author easonlzhang
 */
@Service
public class LivenessCompareService {
    @Autowired
    private FaceidClient client;

    /**
     * get faceid token
     * @param secureLevel secureLevel
     * @return response
     * @throws Exception TencentCloudSDKException
     */
    public GetFaceIdTokenIntlResponse getFaceIdToken(String secureLevel) throws Exception {
        // Step 1: instantiate the request object and provide necessary parameters
        GetFaceIdTokenIntlRequest req = new GetFaceIdTokenIntlRequest();
        req.setSecureLevel(secureLevel);

        // Step 2: call the Tencent Cloud API through FaceIdClient
        GetFaceIdTokenIntlResponse response = client.GetFaceIdTokenIntl(req);

        // Step 3: process the Tencent Cloud API response and construct the return object
        System.out.println(response.getSdkToken());
        return response;
    }

    /**
     * get faceid result
     * @param sdkToken sdkToken
     * @return response
     * @throws Exception TencentCloudSDKException
     */
    public GetFaceIdResultIntlResponse getFaceIdResult(String sdkToken) throws Exception {
        // Step 1: instantiate the request object and provide necessary parameters
        GetFaceIdResultIntlRequest req = new GetFaceIdResultIntlRequest();
        req.setSdkToken(sdkToken);

        // Step 2: call the Tencent Cloud API through FaceIdClient
        GetFaceIdResultIntlResponse response = client.GetFaceIdResultIntl(req);

        // Step 3: process the Tencent Cloud API response and construct the return object
        System.out.println(response.getResult());
        return response;
    }

}
