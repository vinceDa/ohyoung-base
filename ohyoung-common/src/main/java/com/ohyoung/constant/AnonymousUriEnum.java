package com.ohyoung.constant;

/**
 *  不需要权限认证的接口枚举
 * @author ohYoung
 * @date 2020/7/22 15:47
 */
public enum AnonymousUriEnum {
    /**
     *  登录接口
     */
    LOGIN("/api/v1/auth/login");

    private String value;

    AnonymousUriEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
