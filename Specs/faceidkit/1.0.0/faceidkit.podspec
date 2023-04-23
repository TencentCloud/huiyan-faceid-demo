
#
# MIT License
#
# Copyright (c) 2020 FACEID
#
# Permission is hereby granted, free of charge, to any person obtaining a copy
# of this software and associated documentation files (the "Software"), to deal
# in the Software without restriction, including without limitation the rights
# to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
# copies of the Software, and to permit persons to whom the Software is
# furnished to do so, subject to the following conditions:
#
# The above copyright notice and this permission notice shall be included in all
# copies or substantial portions of the Software.
#
# THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
# IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
# FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
# AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
# LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
# OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
# SOFTWARE.
#

Pod::Spec.new do |spec|
  spec.name         = "faceidkit"
  spec.version      = "1.0.0"
  spec.summary      = "faceidkit"
  spec.description  = "tencent faceid SDK"
  spec.homepage     = "https://github.com/orzzh/testDemo"
  spec.author       = { "webertzhang" => "webertzhang@tencent.com" }
  spec.license      = "MIT"
  spec.platform     = :ios, '9.0'
  spec.ios.deployment_target = '9.0'
  spec.compiler_flags = "-ObjC"
  spec.pod_target_xcconfig = { 'OTHER_LDFLAGS' => '-ObjC' }
  spec.ios.framework    = ["Accelerate"]
  spec.frameworks = "AVFoundation", "SystemConfiguration", "Accelerate"
  spec.libraries = 'c++', 'z'
  spec.source = { :http => 'https://ai-sdk-release-1254418846.cos.ap-guangzhou.myqcloud.com/huiyan/github/faceidkit_1.0.0.0.zip' }
  spec.ios.vendored_frameworks = '1/*.framework'
  spec.resources  = '1/*.bundle'
end
