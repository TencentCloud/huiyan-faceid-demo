package api

import (
	"encoding/base64"
	"fmt"
	cloud "github.com/tencentcloud/tencentcloud-sdk-go-intl-en/tencentcloud/common"
	"github.com/tencentcloud/tencentcloud-sdk-go-intl-en/tencentcloud/common/profile"
	faceid "github.com/tencentcloud/tencentcloud-sdk-go-intl-en/tencentcloud/faceid/v20180301"
	"log"
	"os"
)

var FaceIdClient *faceid.Client

func init() {
	// Instantiate a client configuration object. You can specify the timeout period and other configuration items
	prof := profile.NewClientProfile()
	prof.HttpProfile.ReqTimeout = 60
	// TODO replace the SecretId and SecretKey string with the API SecretId and SecretKey
	credential := cloud.NewCredential("SecretId", "SecretKey")
	var err error
	// Instantiate the client object of the requested faceId
	FaceIdClient, err = faceid.NewClient(credential, "ap-singapore", prof)
	if nil != err {
		log.Fatal("FaceIdClient init error: ", err)
	}
}

func CompareFaceLiveness() error {
	// Step 1: Instantiate the request object and provide necessary parameters
	request := faceid.NewCompareFaceLivenessRequest()
	request.VideoBase64 = getBase64("./api/video.mp4")
	request.ImageBase64 = getBase64("./api/image.png")
	request.LivenessType = getPointer("ACTION")
	request.ValidateData = getPointer("1")
	// Step 2: call the Tencent Cloud API through FaceIdClient
	response, err := FaceIdClient.CompareFaceLiveness(request)

	// Step 3: process the Tencent Cloud API response and construct the return object
	if nil != err {
		return err
	}
	fmt.Printf("result: %s", response.ToJsonString())
	return nil
}

func getBase64(path string) *string {
	file, err := os.ReadFile(path)
	if nil != err {
		fmt.Printf("read file error: %+v", err)
		return nil
	}

	encodeToString := base64.StdEncoding.EncodeToString(file)
	return &encodeToString
}

func getPointer(s string) *string {
	return &s
}
