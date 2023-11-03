package com.tencent.could.codedemo.permission;

/**
 * 权限申请的结果回调
 *
 * @author jerrydong
 * @since 2020/8/12
 */
public interface IPermissionsResult {

    /**
     * 权限申请成功
     */
    void passPermissions();

    /**
     * 权限申请失败
     */
    void forbidPermissions();
}
