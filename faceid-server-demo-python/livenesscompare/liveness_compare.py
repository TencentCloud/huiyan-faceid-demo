from flask import request, jsonify, Blueprint
from tencentcloud.faceid.v20180301 import models

from client import client

lc_bp = Blueprint("liveness-compare", __name__)


@lc_bp.route('/api/v1/get-token')
def get_face_id_token():
    print("get face id token")
    # Step 1: ... parse parameters
    secure_level = request.args.get("secureLevel")

    # Step 2: instantiate the request object and provide necessary parameters
    req = models.GetFaceIdTokenIntlRequest()
    req.SecureLevel = secure_level

    # Step 3: call the Tencent Cloud API through FaceIdClient
    response = client.GetFaceIdTokenIntl(req)

    # Step 4: process the Tencent Cloud API response and construct the return object
    print("sdk token: ", response.SdkToken)
    result = {"SdkToken": response.SdkToken, "RequestId": response.RequestId}

    return jsonify(result)


@lc_bp.route('/api/v1/get-result')
def get_face_id_result():
    print("get face id result")
    # Step 1: ... parse parameters
    sdk_token = request.args.get("sdkToken")

    # Step 2: instantiate the request object and provide necessary parameters
    req = models.GetFaceIdResultIntlRequest()
    req.SdkToken = sdk_token

    # Step 3: call the Tencent Cloud API through FaceIdClient
    response = client.GetFaceIdResultIntl(req)

    # Step 4: process the Tencent Cloud API response and construct the return object
    print("sdk token: ", response.Result)
    result = {"Result": response.Result, "RequestId": response.RequestId}

    return jsonify(result)
