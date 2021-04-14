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
        return ResponseData.success(false);
    }

    // 注册通用
    // now only for normal user
    @PostMapping("/register/all")
    public ResponseData register(@RequestBody UserLoginForm userLoginForm) {
        if (userLoginForm == null) return ResponseData.fail("请正确传参");
        return ResponseData.success(oauthService.saveOne(userLoginForm, false));
    }

    @PostMapping("/register/adm")
    public ResponseData registerForAdm(@RequestBody UserLoginForm userLoginForm) {
        if (userLoginForm == null) return ResponseData.fail("请正确传参");
        return ResponseData.success(oauthService.saveOne(userLoginForm, true));
    }

    @PostMapping("/login/app/{type}")
    public ResponseData login(@PathVariable String type, @RequestBody AppSocialUsrForm appSocialUsrForm) {
        if (appSocialUsrForm == null) {
            return ResponseData.fail("请正确传参");
        }
        if (!type.equals("google")) {
            return ResponseData.fail("暂不支持");
        }
        return ResponseData.success(oauthService.loginAppByThirdParty(appSocialUsrForm));
    }



    /**
     * web端三方登录入口;web端用normal
     * @param type
     * @param response
     * @return
     * @throws IOException
     */
    // 三方登录入口接口
    @GetMapping("/login/{type}")
    @Deprecated
    public ResponseData login(@PathVariable String type, HttpServletResponse response) throws IOException {
        AuthRequest authRequest = factory.get(type);
        return ResponseData.success(authRequest.authorize(AuthStateUtils.createState()));
//        response.sendRedirect(authRequest.authorize(AuthStateUtils.createState()));
    }


    /**
     * admin 三方登录，用login/normal
     * @param type
     * @param callback
     * @param response
     * @throws IOException
     */
    @RequestMapping("/{type}/callback")
    @Deprecated
    public void login(@PathVariable String type, AuthCallback callback, HttpServletResponse response) throws IOException {
        AdminLogVO userLogVO = oauthService.loginByThirdParty(factory.get(type), callback);
        StringBuilder redUrl = new StringBuilder();
        if (userLogVO.getSocialUsrId() != null) {
            redUrl.append(FIRST).append(userLogVO.getSocialUsrId());
        }else {
            redUrl.append(NOT_FIRST)
                    .append(userLogVO.getAdm().getStuId()) // 返回学生号
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
        if ((adminVO = oauthService.AmdLoginNew(adminLoginForm)) != null) {
            return ResponseData.success(adminVO);
        }
        return ResponseData.fail("用户名或密码错误");
    }

    @PostMapping("/admin/register/normal")
    public ResponseData register(@RequestBody AdminLoginForm adminLoginForm) {
        if (adminLoginForm == null) return ResponseData.fail("请正确传参");
        try {
            return ResponseData.success(oauthService.saveOneAdmin(adminLoginForm));
        } catch (Exception e) {
            return ResponseData.fail("管理员创建失败");
        }
    }
}
