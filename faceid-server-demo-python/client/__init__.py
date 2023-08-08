from tencentcloud.faceid.v20180301.faceid_client import FaceidClient
from tencentcloud.common.credential import Credential
from tencentcloud.common.profile.client_profile import ClientProfile

# Instantiate a client configuration object. You can specify the timeout period and other configuration items
prof = ClientProfile()
prof.httpProfile.reqTimeout = 60

# TODO replace the SecretId and SecretKey string with the API SecretId and SecretKey
credential = Credential(secret_id="secret_id", secret_key="secret_key")

# Instantiate the client object of the requested client
client = FaceidClient(credential=credential, region="ap-singapore", profile=prof)