package com.ecnu.six.pethospital.oauth.entity;

import com.ecnu.six.pethospital.oauth.form.AppSocialUsrForm;
import lombok.Builder;
import org.springframework.beans.BeanUtils;

@Builder
public class SocialUser {
    private Integer id;

    private String uuid;

    private String source;

    private String accessToken;

    private Integer expirein;

    private String nickName;

    private String avatar;

    private String location;

    private String gender;

    private String email;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken == null ? null : accessToken.trim();
    }

    public Integer getExpirein() {
        return expirein;
    }

    public void setExpirein(Integer expirein) {
        this.expirein = expirein;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public SocialUser() {
    }

    public SocialUser(AppSocialUsrForm form) {
        this.accessToken = form.getAccessToken();
        this.avatar = form.getAvatar();
        this.email = form.getEmail();
        this.expirein = form.getExpirein();
        this.gender = form.getGender();
        this.location = form.getLocation();
        this.source = form.getSource();
        this.uuid = form.getUuid();
        this.nickName = form.getNickName();
    }
}
