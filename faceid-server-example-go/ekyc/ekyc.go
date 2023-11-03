package ekyc

import (
	"encoding/json"
	cloud "github.com/tencentcloud/tencentcloud-sdk-go-intl-en/tencentcloud/common"
	"github.com/tencentcloud/tencentcloud-sdk-go-intl-en/tencentcloud/common/profile"
	faceid "github.com/tencentcloud/tencentcloud-sdk-go-intl-en/tencentcloud/faceid/v20180301"
	"log"
	"net/http"
)

var FaceIdClient *faceid.Client

func init() {
	// Instantiate a client configuration object. You can specify the timeout period and other configuration items
	prof := profile.NewClientProfile()
	prof.HttpProfile.ReqTimeout = 60
	// TODO replace the SecretId and SecretKey string with the API SecretId and SecretKey
	credential := cloud.NewCredential("SecretId", "SecretKey")
	var err error
	// Instantiate the client object of the requested faceid
	FaceIdClient, err = faceid.NewClient(credential, "ap-singapore", prof)
	if nil != err {
		log.Fatal("FaceIdClient init error: ", err)
	}
}

// ApplySdkVerificationToken get token
func ApplySdkVerificationToken(w http.ResponseWriter, r *http.Request) {
	log.Println("get face id token")
	// Step 1: ... parse parameters
	_ = r.ParseForm()
	var IdCardType = r.FormValue("IdCardType")
	var NeedVerifyIdCard = false

	// Step 2: instantiate the request object and provide necessary parameters
	request := faceid.NewApplySdkVerificationTokenRequest()
	request.IdCardType = &IdCardType
	request.NeedVerifyIdCard = &NeedVerifyIdCard
	// Step 3: call the Tencent Cloud API through FaceIdClient
	response, err := FaceIdClient.ApplySdkVerificationToken(request)

	// Step 4: process the Tencent Cloud API response and construct the return object
	if nil != err {
		log.Println("error: ", err)
		_, _ = w.Write([]byte("error"))
		return
	}
	SdkToken := response.Response.SdkToken
	apiResp := struct {
		SdkToken *string
	}{SdkToken: SdkToken}
	b, _ := json.Marshal(apiResp)

	// ... more codes are omitted

	//Step 5: return the service response
	_, _ = w.Write(b)
}

// GetSdkVerificationResult get result
func GetSdkVerificationResult(w http.ResponseWriter, r *http.Request) {
	// Step 1: ... parse parameters
	_ = r.ParseForm()
	SdkToken := r.FormValue("SdkToken")
	// Step 2: instantiate the request object and provide necessary parameters
	request := faceid.NewGetSdkVerificationResultRequest()
	request.SdkToken = &SdkToken
	// Step 3: call the Tencent Cloud API through FaceIdClient
	response, err := FaceIdClient.GetSdkVerificationResult(request)

	// Step 4: process the Tencent Cloud API response and construct the return object
	if nil != err {
		_, _ = w.Write([]byte("error"))
		return
	}
	result := response.Response.Result
	apiResp := struct {
		Result *string
	}{Result: result}
	b, _ := json.Marshal(apiResp)

	// ... more codes are omitted

	//Step 5: return the service response
	_, _ = w.Write(b)
}
