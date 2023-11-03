package main

import (
	"github.com/faceid-demo/ekyc"
	"github.com/faceid-demo/liveness-compare"
	"log"
	"net/http"
)

func main() {
	// expose endpoints
	http.HandleFunc("/api/v1/get-token", liveness_compare.GetFaceIdToken)
	http.HandleFunc("/api/v1/get-result", liveness_compare.GetFaceIdResult)

	http.HandleFunc("/api/v1/apply-sdk-token", ekyc.ApplySdkVerificationToken)
	http.HandleFunc("/api/v1/get-sdk-result", ekyc.GetSdkVerificationResult)
	// listening port
	err := http.ListenAndServe(":8080", nil)
	if nil != err {
		log.Fatal("ListenAndServe error: ", err)
	}
}
