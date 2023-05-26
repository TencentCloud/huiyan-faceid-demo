from flask import request, jsonify, Blueprint
from tencentcloud.faceid.v20180301 import models

from client import client

ekyc_bp = Blueprint("ekyc", __name__)


@ekyc_bp.route('/api/v1/apply-sdk-token')
def apply_sdk_verification_token():
    print("get sdk verification token")
    # Step 1: ... parse parameters
    id_card_type = request.args.get("idCardType")

    # Step 2: instantiate the request object and provide necessary parameters
    req = models.ApplySdkVerificationTokenRequest()
    req.IdCardType = id_card_type
    req.NeedVerifyIdCard = False

    # Step 3: call the Tencent Cloud API through FaceIdClient
    response = client.ApplySdkVerificationToken(req)

    # Step 4: process the Tencent Cloud API response and construct the return object
    print("sdk token: ", response.SdkToken)
    result = {"SdkToken": response.SdkToken, "RequestId": response.RequestId}

    return jsonify(result)


@ekyc_bp.route('/api/v1/get-sdk-result')
def get_sdk_verification_result():
    print("get sdk verification result")
    # Step 1: ... parse parameters
    sdk_token = request.args.get("sdkToken")

    # Step 2: instantiate the request object and provide necessary parameters
    req = models.GetSdkVerificationResultRequest()
    req.SdkToken = sdk_token

    # Step 3: call the Tencent Cloud API through FaceIdClient
    response = client.GetSdkVerificationResult(req)

    # Step 4: process the Tencent Cloud API response and construct the return object
    print("sdk token: ", response.Result)
    result = {"Result": response.Result, "RequestId": response.RequestId}

    return jsonify(result)
