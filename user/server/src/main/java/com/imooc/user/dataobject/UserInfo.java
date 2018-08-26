package com.imooc.user.dataobject;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.Entity;


@Data
@Entity
public class UserInfo {

    @Id
    private String id;

    private String username;

    private String password;

    private String openid;

    private Integer role;
}
