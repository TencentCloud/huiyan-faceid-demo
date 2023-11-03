package com.tencent;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.faceid.v20180301.FaceidClient;
import com.tencentcloudapi.faceid.v20180301.models.CompareFaceLivenessRequest;
import com.tencentcloudapi.faceid.v20180301.models.CompareFaceLivenessResponse;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

/**
 * Example of Compare Face Liveness
 * @author easonlzhang
 */
public class CompareFaceLiveness {

    public static void main(String[] args) {
        // Create Options object
        Options options = new Options();
        options.addOption(new Option("i", true, "The content of tencent cloud secret id"){{
            setRequired(true);
        }});
        options.addOption(new Option("k", true, "The content of tencent cloud secret key"){{
            setRequired(true);
        }});
        options.addOption(new Option("a", true, "The path of face image to be compared"){{
            setRequired(true);
        }});
        options.addOption(new Option("b", true, "The path of video to be detected") {{
            setRequired(true);
        }});

        try {
            CommandLine cmd = new DefaultParser().parse(options, args);

            // Instantiate an authentication object. The Tencent Cloud account `secretId` and `secretKey` need to be passed in as the input parameters
            Credential cred = new Credential(cmd.getOptionValue("i"), cmd.getOptionValue("k"));

            // Instantiate the client object of the requested product
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setSignMethod(ClientProfile.SIGN_TC3_256);

            FaceidClient client = new FaceidClient(cred, "ap-singapore", clientProfile);

            // Instantiate the request object and provide necessary parameters
            CompareFaceLivenessRequest request = new CompareFaceLivenessRequest();
            request.setLivenessType("SILENT");
            request.setImageBase64(getBase64(cmd.getOptionValue("a")));
            request.setVideoBase64(getBase64(cmd.getOptionValue("b")));

            // Call the Tencent Cloud API through FaceIdClient
            CompareFaceLivenessResponse response = client.CompareFaceLiveness(request);

            // Process the Tencent Cloud API response and construct the return object
            System.out.println(CompareFaceLivenessResponse.toJsonString(response));
        } catch (ParseException ex) {
            // automatically generate the help statement
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("CompareFaceLiveness " +
                            "-i <secret_id> " +
                            "-k <secret_key> " +
                            "-a <image_path> " +
                            "-b <video_path> ",
                    options);
            System.exit(-1);
        } catch (IOException | TencentCloudSDKException ex) {
            System.err.println(ex.getMessage());
            System.exit(-2);
        }
    }

    protected static String getBase64(String filePath) throws IOException {
        byte[] content = FileUtils.readFileToByteArray(new File(filePath));
        return Base64.getEncoder().encodeToString(content);
    }
}
