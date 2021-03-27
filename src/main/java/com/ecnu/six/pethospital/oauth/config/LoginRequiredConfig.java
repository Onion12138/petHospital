package com.ecnu.six.pethospital.oauth.config;

import cn.hutool.core.collection.ConcurrentHashSet;
import com.ecnu.six.pethospital.common.ResponseData;
import com.ecnu.six.pethospital.oauth.annotation.LoginRequired;
import com.ecnu.six.pethospital.oauth.enums.Role;
import com.ecnu.six.pethospital.oauth.utils.MD5Utils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @author LEO D PEN
 * @date 2021
 * @desc
 * @since
 */
@Aspect
@Component
public class LoginRequiredConfig {

    @Resource
    private CacheConfig cacheConfig;

    @Pointcut(value = "@annotation(loginRequired)")
    public void doAuth(LoginRequired loginRequired) {}


    @Around(value = "doAuth(loginRequired)", argNames = "pjp,loginRequired")
    public Object doAuthJudge(ProceedingJoinPoint pjp, LoginRequired loginRequired) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        String token = request.getHeader("token");
        String stuId = request.getParameter("stuId");
        if (!MD5Utils.pwdMd5(stuId).equals(token)) {
            // 大概率是一些用户改个人信息的操作，或者获取用户具体信息的操作
            return ResponseData.fail("token 无效");
        }
        Role role = loginRequired.role();
        if (role == Role.ADMIN) {
            if (!cacheConfig.adminToken.contains(token)) {
                return ResponseData.fail("登录信息失效");
            }
        }else {
            if (!cacheConfig.adminToken.contains(token)) {
                try {
                    Timestamp value = cacheConfig.userTokenCache.getOrDefault(token, null);
                    if (value == null) {
                        return ResponseData.fail("登录已过期");
                    } else {
                        LocalDateTime localDateTime = value.toLocalDateTime();
                        if (localDateTime.isBefore(LocalDateTime.now())) {
                            cacheConfig.userTokenCache.remove(token);
                            return ResponseData.fail("登录已过期");
                        }else {
                            cacheConfig.userTokenCache.put(token, Timestamp.valueOf(LocalDateTime.now().plusDays(1L)));
                        }
                    }
                } catch (Exception e) {
                    return ResponseData.fail("信息认证错误");
                }
            }
        }
        return pjp.proceed();
    }
}
