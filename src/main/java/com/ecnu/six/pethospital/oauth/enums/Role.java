package com.ecnu.six.pethospital.oauth.enums;

/**
 * @author LEO D PEN
 * @date 2021
 * @desc
 * @since
 */
public enum Role {
    USER(0),
    ADMIN(1),
    ;
    private final Integer roleCode;

    Role(Integer roleCode) {
        this.roleCode = roleCode;
    }

    public Integer getRoleCode() {
        return roleCode;
    }
}
