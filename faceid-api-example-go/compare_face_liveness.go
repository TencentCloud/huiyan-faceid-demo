package main

import (
	"encoding/base64"
	"flag"
	"fmt"
	cloud "github.com/tencentcloud/tencentcloud-sdk-go-intl-en/tencentcloud/common"
	"github.com/tencentcloud/tencentcloud-sdk-go-intl-en/tencentcloud/common/profile"
	faceid "github.com/tencentcloud/tencentcloud-sdk-go-intl-en/tencentcloud/faceid/v20180301"
	"log"
	"os"
)

func CompareFaceLiveness(secretId, secretKey, imagePath, videoPath string) error {
	// Instantiate a client configuration object. You can specify the timeout period and other configuration items
	prof := profile.NewClientProfile()
	prof.HttpProfile.ReqTimeout = 60
	// replace the SecretId and SecretKey string with the API SecretId and SecretKey
	credential := cloud.NewCredential(secretId, secretKey)
	var err error
	// Instantiate the client object of the requested faceId
	client, err := faceid.NewClient(credential, "ap-singapore", prof)
	if nil != err {
		log.Fatal("FaceIdClient init error: ", err)
	}
	// Instantiate the request object and provide necessary parameters
	request := faceid.NewCompareFaceLivenessRequest()
	request.ImageBase64 = getBase64(imagePath)
	request.VideoBase64 = getBase64(videoPath)
	request.LivenessType = getPointer("SILENT")
	// Call the Tencent Cloud API through FaceIdClient
	response, err := client.CompareFaceLiveness(request)

	// Process the Tencent Cloud API response and construct the return object
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
		os.Exit(-2)
	}

	encodeToString := base64.StdEncoding.EncodeToString(file)
	return &encodeToString
}

func getPointer(s string) *string {
	return &s
}

func main() {
	// Parse command-line arguments
	var secretId string
	var secretKey string
	var imagePath string
	var videoPath string
	flag.StringVar(&secretId, "i", "", "The content of tencent cloud secret id")
	flag.StringVar(&secretKey, "k", "", "The content of tencent cloud secret key")
	flag.StringVar(&imagePath, "a", "", "The path of face image to be compared")
	flag.StringVar(&videoPath, "b", "", "The path of video to be detected")
	flag.Parse()
	if "" == secretId || "" == secretKey || "" == imagePath || "" == videoPath {
		flag.Usage()
		os.Exit(-1)
	}
	// invoke function
	err := CompareFaceLiveness(secretId, secretKey, imagePath, videoPath)
	if nil != err {
		fmt.Println(err)
	}
}
