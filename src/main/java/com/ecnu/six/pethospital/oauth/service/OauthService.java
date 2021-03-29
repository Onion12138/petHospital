package com.ecnu.six.pethospital.oauth.service;

import com.alibaba.fastjson.JSON;
import com.ecnu.six.pethospital.oauth.VO.AdminLogVO;
import com.ecnu.six.pethospital.oauth.VO.UserLogVO;
import com.ecnu.six.pethospital.oauth.config.CacheConfig;
import com.ecnu.six.pethospital.oauth.entity.*;
import com.ecnu.six.pethospital.oauth.enums.UserStatusEnum;
import com.ecnu.six.pethospital.oauth.form.AdminLoginForm;
import com.ecnu.six.pethospital.oauth.form.UserLoginForm;
import com.ecnu.six.pethospital.oauth.mapper.AdmMapper;
import com.ecnu.six.pethospital.oauth.mapper.LocalUserMapper;
import com.ecnu.six.pethospital.oauth.mapper.SLUMapper;
import com.ecnu.six.pethospital.oauth.mapper.SocialUserMapper;
import com.ecnu.six.pethospital.oauth.utils.MD5Utils;
import com.ecnu.six.pethospital.oauth.utils.Pair;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author LEO D PEN
 * @date 2021
 * @desc
 * @since
 */
@Service
@Slf4j
public class OauthService {

    @Resource
    private LocalUserMapper localUserMapper;

    @Resource
    private SLUMapper sluMapper;

    @Resource
    private SocialUserMapper socialUserMapper;

    @Resource
    private AdmMapper admMapper;

    @Resource
    private CacheConfig cache;


    /**
     * 检测用户是否存在【普通登录】
     * @param stuId
     * @param pwd
     * @return
     */
    private LocalUser judgeIfLoginSuccess(String stuId, String pwd) {
        LocalUser user = localUserMapper.selectByStuId(stuId);
        if (user != null && user.getPassword().equals(MD5Utils.pwdMd5(pwd))) {
            return user;
        }
        return null;
    }

    /**
     * 普通用户普通登录入口【学号+密码】
     * @param form
     * @return
     */
    public UserLogVO loginByForm(UserLoginForm form) {
        LocalUser user = judgeIfLoginSuccess(form.getStuId(), form.getPwd());
        if (user == null) {
            return null;
        }
        UserLogVO userLogVO = new UserLogVO();
        userLogVO.setUser(user);
        // 搞token
        Pair<String, Timestamp> pair = MD5Utils.TokenUtil(user.getStuId());
        cache.userTokenCache.putIfAbsent(pair.getLeft(), pair.getRight());
        // 传回
        userLogVO.setToken(pair.getLeft());
        return userLogVO;
    }

    /**
     * save User
     * @param form
     * @return
     */
    public boolean saveOne(UserLoginForm form) {
        try {
            LocalUser user = new LocalUser();
            user.setStuId(form.getStuId());
            user.setPassword(MD5Utils.pwdMd5(form.getPwd()));
            user.setStatus(UserStatusEnum.ACTIVE.getCode()); // 枚举
            if (StringUtils.hasText(form.getSocialUsrId())) {
                SocialUser socialUser = socialUserMapper.selectByPrimaryKey(Integer.valueOf(form.getSocialUsrId()));
                if (socialUser != null) {
                    user.setAvatar(socialUser.getAvatar());
                    user.setGender(socialUser.getGender());
                    user.setLocation(socialUser.getLocation());
                    user.setNickName(socialUser.getNickName());
                    user.setUserMail(socialUser.getEmail());
                    localUserMapper.insertSelective(user);
                    int l_id = user.getId();
                    SLU slu = new SLU();
                    slu.setLocalUId(l_id);
                    slu.setSocialUId(Integer.valueOf(form.getSocialUsrId()));
                    sluMapper.insert(slu);
                }
            }else {
                localUserMapper.insertSelective(user);
            }
        }catch (Exception e) {
            log.error("OauthService -> saveOne", e);
            return false;
        }
        return true;
    }

    /**
     * 普通用户三方登录入口
     * @param request
     * @param callback
     * @return
     */
    public UserLogVO loginByThirdParty(AuthRequest request, AuthCallback callback) {
        AuthResponse response = request.login(callback);
        AuthUser authUser = (AuthUser) response.getData();
        System.out.println(JSON.toJSONString(authUser));
        String uuid = authUser.getUuid();
        String source = authUser.getSource();
        SocialUser  socialUser = null;
        UserLogVO userLogVO = new UserLogVO();
        if ((socialUser = socialUserMapper.selectByUuidAndSource(uuid, source)) == null) {
            // 存入
            socialUser = SocialUser.builder()
                    .avatar(authUser.getAvatar())
                    .email(authUser.getEmail())
                    .gender(authUser.getGender().getDesc())
                    .location(authUser.getLocation())
                    .nickName(authUser.getNickname())
                    .uuid(uuid)
                    .source(source)
                    .accessToken(authUser.getToken().getAccessToken())
                    .build();
            socialUserMapper.insert(socialUser);
            userLogVO.setSocialUsrId(socialUser.getId());
        } else {
            // 存在则查一下是否已经绑定
            SLU slu = sluMapper.selectBySID(socialUser.getId());
            if (slu != null) {
                // 已经绑定
                userLogVO.setUser(localUserMapper.selectByPrimaryKey(slu.getLocalUId()));
                // 存cache信息
                Pair<String, Timestamp> pair = MD5Utils.TokenUtil(userLogVO.getUser().getStuId());
                cache.userTokenCache.putIfAbsent(pair.getLeft(), pair.getRight());
                // 传回
                userLogVO.setToken(pair.getLeft());
            } else {
                // 未绑定
                userLogVO.setSocialUsrId(socialUser.getId());
            }
        }
        return userLogVO;
    }


    public AdminLogVO AmdLogin(AdminLoginForm form) {
        AdminLogVO result = new AdminLogVO();
        if (form == null) return result;
        AdmExample example = new AdmExample();
        example.createCriteria().andAdmNameEqualTo(form.getAdmName());
        List<Adm> res = admMapper.selectByExample(example);
        if (res.size() > 1) {
            log.error("OauthService -> AmdLogin, more than one adm, name : {}", form.getAdmName());
        }
        Adm adm = res.get(0);
        if (!adm.getPassword().equals(form.getPwd())) {
            return result;
        }
        Pair<String, Timestamp> pair = MD5Utils.TokenUtil(adm.getAdmName());
        cache.adminToken.add(pair.getLeft());

        result.setName(adm.getAdmName());
        result.setToken(pair.getLeft());
        return result;
    }

}