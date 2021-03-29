package com.ecnu.six.pethospital.oauth.controller;

import com.ecnu.six.pethospital.common.ResponseData;
import com.ecnu.six.pethospital.oauth.annotation.LoginRequired;
import com.ecnu.six.pethospital.oauth.entity.LocalUser;
import com.ecnu.six.pethospital.oauth.enums.Role;
import com.ecnu.six.pethospital.oauth.mapper.LocalUserMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author LEO D PEN
 * @date 2021
 * @desc
 * @since
 */
@RestController
@RequestMapping("/usr/info")
public class UsrInfoController {

    @Resource
    private LocalUserMapper userMapper;


    @GetMapping("/loadByStuId")
    @LoginRequired(role = Role.USER)
    public ResponseData getUsrInfo(@RequestParam("stuId") String stuId) {
        LocalUser user =  userMapper.selectByStuId(stuId);
        if (user == null) {
            return ResponseData.fail("无效信息");
        }
        user.setPassword(null);
        return ResponseData.success(user);
    }
}