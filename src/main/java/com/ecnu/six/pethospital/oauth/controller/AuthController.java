package com.ecnu.six.pethospital.oauth.controller;

import com.ecnu.six.pethospital.common.ResponseData;
import com.ecnu.six.pethospital.oauth.VO.AdminLogVO;
import com.ecnu.six.pethospital.oauth.VO.UserLogVO;
import com.ecnu.six.pethospital.oauth.entity.SocialUser;
import com.ecnu.six.pethospital.oauth.form.AdminLoginForm;
import com.ecnu.six.pethospital.oauth.form.AppSocialUsrForm;
import com.ecnu.six.pethospital.oauth.form.UserLoginForm;
import com.ecnu.six.pethospital.oauth.service.OauthService;
import com.xkcoding.justauth.AuthRequestFactory;
import lombok.RequiredArgsConstructor;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author LEO D PEN
 * @date 2021
 * @desc 当前只支持google，后续加其他的也很方便，改一下type传入即可
 * @since
 */
@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AuthController {

    private final AuthRequestFactory factory;

    private final OauthService oauthService;

    private static final String FIRST = "http://localhost:9527/#/register?socialUsrId=";

    private static final String NOT_FIRST = "http://localhost:9527/#/login?stuId=";

    // 测试使用
    @GetMapping
    public List<String> list() {
        return factory.oauthList();
    }

    // 直接登录
    @PostMapping("/login/normal")
    public ResponseData login(@RequestBody UserLoginForm userLoginForm) {
        if (userLoginForm == null) return ResponseData.fail("请正确传参");
        UserLogVO userVO = null;
        if ((userVO = oauthService.loginByForm(userLoginForm)) != null) {
            return ResponseData.success(userVO);
        }
        return ResponseData.fail("用户名或密码错误");
    }

    // 学号是否已有
    @GetMapping("/login/check")
    public ResponseData check(@RequestParam("stuId") String stuId) {
        if (oauthService.checkIfAvailable(stuId)) {
            return ResponseData.success(true);
        }
        return ResponseData.fail("已有改学号信息");
    }

    // 注册通用
    @PostMapping("/register/all")
    public ResponseData register(@RequestBody UserLoginForm userLoginForm) {
        if (userLoginForm == null) return ResponseData.fail("请正确传参");
        return ResponseData.success(oauthService.saveOne(userLoginForm));
    }

    @PostMapping("/login/app/{type}")
    public ResponseData login(@PathVariable String type, @RequestBody AppSocialUsrForm appSocialUsrForm) {
        if (appSocialUsrForm == null) {
            return ResponseData.fail("请正确传参");
        }
        SocialUser user = oauthService.saveSocialUser(appSocialUsrForm);
        if (user == null) {
            return ResponseData.fail("登录失败，请稍后重试");
        }
        return ResponseData.success(user.getId());
    }


    /**
     * web端三方登录入口
     * @param type
     * @param response
     * @return
     * @throws IOException
     */
    // 三方登录入口接口
    @GetMapping("/login/{type}")
    public ResponseData login(@PathVariable String type, HttpServletResponse response) throws IOException {
        AuthRequest authRequest = factory.get(type);
        return ResponseData.success(authRequest.authorize(AuthStateUtils.createState()));
//        response.sendRedirect(authRequest.authorize(AuthStateUtils.createState()));
    }


    @RequestMapping("/{type}/callback")
    public void login(@PathVariable String type, AuthCallback callback, HttpServletResponse response) throws IOException {
        UserLogVO userLogVO = oauthService.loginByThirdParty(factory.get(type), callback);
        StringBuilder redUrl = new StringBuilder();
        if (userLogVO.getSocialUsrId() != null) {
            redUrl.append(FIRST).append(userLogVO.getSocialUsrId());
        }else {
            redUrl.append(NOT_FIRST)
                    .append(userLogVO.getUser().getStuId())
                    .append("&")
                    .append("token=")
                    .append(userLogVO.getToken());
        }
        response.sendRedirect(redUrl.toString());
    }

    @PostMapping("/admin/login/normal")
    public ResponseData admLogin(@RequestBody AdminLoginForm adminLoginForm) {
        if (adminLoginForm == null) return ResponseData.fail("请正确传参");
        AdminLogVO adminVO = null;
        if ((adminVO = oauthService.AmdLogin(adminLoginForm)) != null) {
            return ResponseData.success(adminVO);
        }
        return ResponseData.fail("用户名或密码错误");
    }


}
