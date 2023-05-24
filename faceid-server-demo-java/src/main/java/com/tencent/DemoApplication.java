package com.tencent;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.faceid.v20180301.FaceidClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * DemoApplication
 * @author easonlzhang
 */
@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class);
    }

    @Bean
    public FaceidClient faceidClient() {
        // Instantiate an authentication object. The Tencent Cloud account `secretId` and `secretKey` need to be passed in as the input parameters
        // TODO replace the SecretId and SecretKey string with the API SecretId and SecretKey
        Credential cred = new Credential("secretId", "secretKey");

        // Instantiate the client object of the requested product
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setSignMethod(ClientProfile.SIGN_TC3_256);

        return new FaceidClient(cred, "ap-singapore", clientProfile);
    }
}
