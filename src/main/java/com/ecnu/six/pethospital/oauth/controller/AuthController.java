package com.ecnu.six.pethospital.oauth.controller;

import com.alibaba.fastjson.JSON;
import com.ecnu.six.pethospital.common.ResponseData;
import com.ecnu.six.pethospital.oauth.entity.LocalUser;
import com.ecnu.six.pethospital.oauth.form.LoginForm;
import com.ecnu.six.pethospital.oauth.service.OauthService;
import com.xkcoding.justauth.AuthRequestFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    // 测试使用
    @GetMapping
    public List<String> list() {
        return factory.oauthList();
    }

    // 直接登录
    @PostMapping("/login/normal")
    public ResponseData login(@RequestBody LoginForm loginForm) {
        if (loginForm == null) return ResponseData.fail("请正确传参");
        LocalUser user = null;
        if ((user = oauthService.loginByForm(loginForm)) != null) {
            return ResponseData.success(user);
        }
        return ResponseData.fail("用户名或密码错误");
    }

    // 注册通用
    @PostMapping("/register/all")
    public ResponseData register(@RequestBody LoginForm loginForm) {
        if (loginForm == null) return ResponseData.fail("请正确传参");
        return ResponseData.success(oauthService.saveOne(loginForm));
    }

    // 三方登录入口接口
    @GetMapping("/login/{type}")
    public void login(@PathVariable String type, HttpServletResponse response) throws IOException {
        AuthRequest authRequest = factory.get(type);
        response.sendRedirect(authRequest.authorize(AuthStateUtils.createState()));
    }


    // 前端别接这个接口，不暴露它
    @RequestMapping("/{type}/callback")
    public ResponseData login(@PathVariable String type, AuthCallback callback) {
        return ResponseData.success(oauthService.loginByThirdParty(factory.get(type), callback));
    }



}
