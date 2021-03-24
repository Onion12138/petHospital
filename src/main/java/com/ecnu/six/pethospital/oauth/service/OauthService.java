package com.ecnu.six.pethospital.oauth.service;

import com.ecnu.six.pethospital.oauth.VO.LogVO;
import com.ecnu.six.pethospital.oauth.config.CacheConfig;
import com.ecnu.six.pethospital.oauth.entity.LocalUser;
import com.ecnu.six.pethospital.oauth.entity.SLU;
import com.ecnu.six.pethospital.oauth.entity.SocialUser;
import com.ecnu.six.pethospital.oauth.enums.UserStatusEnum;
import com.ecnu.six.pethospital.oauth.form.LoginForm;
import com.ecnu.six.pethospital.oauth.mapper.LocalUserMapper;
import com.ecnu.six.pethospital.oauth.mapper.SLUMapper;
import com.ecnu.six.pethospital.oauth.mapper.SocialUserMapper;
import com.ecnu.six.pethospital.oauth.utils.MD5Utils;
import com.sun.tools.javac.util.Pair;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.sql.Timestamp;

/**
 * @author LEO D PEN
 * @date 2021
 * @desc
 * @since
 */
@Service
public class OauthService {

    @Resource
    private LocalUserMapper localUserMapper;

    @Resource
    private SLUMapper sluMapper;

    @Resource
    private SocialUserMapper socialUserMapper;

    @Resource
    private CacheConfig cache;


    // 我不管了我透传了
    private LocalUser judgeIfLoginSuccess(String stuId, String pwd) {
        LocalUser user = localUserMapper.selectByStuId(stuId);
        if (user != null && user.getPassword().equals(MD5Utils.pwdMd5(pwd))) {
            return user;
        }
        return null;
    }

    public LogVO loginByForm(LoginForm form) {
        LocalUser user = judgeIfLoginSuccess(form.stuId(), form.pwd());
        if (user == null) {
            return null;
        }
        LogVO logVO = new LogVO();
        logVO.setUser(user);
        // 搞token
        Pair<String, Timestamp> pair = MD5Utils.TokenUtil(user.getStuId());
        cache.userTokenCache.putIfAbsent(pair.fst, pair.snd);
        // 传回
        logVO.setToken(pair.fst);
        return logVO;
    }

    public boolean saveOne(LoginForm form) {
        try {
            LocalUser user = new LocalUser();
            user.setStuId(form.stuId());
            user.setPassword(MD5Utils.pwdMd5(form.pwd()));
            user.setStatus(UserStatusEnum.ACTIVE.getCode()); // 枚举
            if (StringUtils.hasText(form.socialUsrId())) {
                SocialUser socialUser = socialUserMapper.selectByPrimaryKey(Integer.valueOf(form.socialUsrId()));
                if (socialUser != null) {
                    user.setAvatar(socialUser.getAvatar());
                    user.setGender(socialUser.getGender());
                    user.setLocation(socialUser.getLocation());
                    user.setNickName(socialUser.getNickName());
                    user.setUserMail(socialUser.getEmail());
                    int l_id = localUserMapper.insert(user);
                    SLU slu = new SLU();
                    slu.setLocalUId(l_id);
                    slu.setSocialUId(Integer.valueOf(form.socialUsrId()));
                    sluMapper.insert(slu);
                }
            }else {
                localUserMapper.insert(user);
            }
        }catch (Exception e) {
            return false;
        }
        return true;
    }

    public LogVO loginByThirdParty(AuthRequest request, AuthCallback callback) {
        AuthResponse response = request.login(callback);
        AuthUser authUser = (AuthUser) response.getData();
        String uuid = authUser.getUuid();
        String source = authUser.getSource();
        SocialUser  socialUser = null;
        LogVO logVO = new LogVO();
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
            logVO.setSocialUsrId(socialUserMapper.insert(socialUser));
        } else {
            // 存在则查一下是否已经绑定
            SLU slu = sluMapper.selectBySID(socialUser.getId());
            if (slu != null) {
                // 已经绑定
                logVO.setUser(localUserMapper.selectByPrimaryKey(slu.getLocalUId()));
                // 存cache信息
                Pair<String, Timestamp> pair = MD5Utils.TokenUtil(logVO.getUser().getStuId());
                cache.userTokenCache.putIfAbsent(pair.fst, pair.snd);
                // 传回
                logVO.setToken(pair.fst);
            } else {
                // 未绑定
                logVO.setSocialUsrId(socialUser.getId());
            }
        }
        return logVO;
    }






}
