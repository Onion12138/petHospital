package com.ecnu.six.pethospital.oauth.entity;

/**
 * @author LEO D PEN
 * @date 2021
 * @desc
 * @since
 */
@Deprecated
public class SLA {

    private Integer id;

    private Integer adminId;

    private Integer socialUId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public Integer getSocialUId() {
        return socialUId;
    }

    public void setSocialUId(Integer socialUId) {
        this.socialUId = socialUId;
    }
}
