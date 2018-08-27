package com.imooc.user.controller;

import com.imooc.user.constant.CookieConst;
import com.imooc.user.VO.ResultVO;
import com.imooc.user.constant.RedisConstant;
import com.imooc.user.dataobject.UserInfo;
import com.imooc.user.enums.ResultEnum;
import com.imooc.user.enums.RoleEnum;
import com.imooc.user.service.UserService;
import com.imooc.user.utils.CookieUtil;
import com.imooc.user.utils.ResultVOUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/buyer")
    public ResultVO buyer(@RequestParam("openid") String openid,
                          HttpServletResponse response) {
        // 1.openid和数据库里的数据匹配
        UserInfo userInfo = userService.fingByOpenid(openid);
        if (userInfo == null) {
            return ResultVOUtil.error(ResultEnum.LOGIN_FAIL);
        }
        // 2.判断角色
        if (RoleEnum.BUYER.getCode() != userInfo.getRole()) {
            return ResultVOUtil.error(ResultEnum.ROLE_ERROR);
        }
        // 3.cookie里设置openid=abc
        CookieUtil.set(response, CookieConst.OPENID, openid, CookieConst.expire);
        return ResultVOUtil.success();
    }

    @GetMapping("/seller")
    public ResultVO seller(@RequestParam("openid") String openid,
                           HttpServletResponse response,
                           HttpServletRequest request) {
        // 判断是否登陆
        Cookie cookie = CookieUtil.get(request, CookieConst.TOKEN);
        String cookieValue = null;
        if (cookie != null) {
            cookieValue = stringRedisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_TEMPLATE, cookie.getValue()));
        }
        if (cookie != null && !StringUtils.isEmpty(cookieValue)) {
            return ResultVOUtil.success();
        }

        // 1.openid和数据库里的数据匹配
        UserInfo userInfo = userService.fingByOpenid(openid);
        if (userInfo == null) {
            return ResultVOUtil.error(ResultEnum.LOGIN_FAIL);
        }
        // 2.判断角色
        if (RoleEnum.SELLER.getCode() != userInfo.getRole()) {
            return ResultVOUtil.error(ResultEnum.ROLE_ERROR);
        }
        // 3.redis设置key=UUID，value=xyz
        String token = UUID.randomUUID().toString();
        String key = String.format(RedisConstant.TOKEN_TEMPLATE, token);
        Integer expire = CookieConst.expire;
        stringRedisTemplate.opsForValue().set(key, openid, expire, TimeUnit.SECONDS);
        // 4.cookie里设置openid=xyz
        CookieUtil.set(response, CookieConst.TOKEN, token, CookieConst.expire);
        return ResultVOUtil.success();
    }
}
