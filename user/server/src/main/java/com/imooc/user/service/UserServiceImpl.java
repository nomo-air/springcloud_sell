package com.imooc.user.service;

import com.imooc.user.dataobject.UserInfo;
import com.imooc.user.repostory.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceImpl implements  UserService{

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public UserInfo fingByOpenid(String openid) {
        return userInfoRepository.findByOpenid(openid);
    }
}
