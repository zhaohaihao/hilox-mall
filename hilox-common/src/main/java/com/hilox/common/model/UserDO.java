package com.hilox.common.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhh
 * @description 用户信息
 * @date 2019-04-25 02:38
 */
@Data
@ToString
public class UserDO implements Serializable {

    private static final long serialVersionUID = 4189645664816607628L;

    /**
     * 用户ID
     */
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 密码
     */
    private String password;

    /**
     * 性别，0未知 1男性 2女性
     */
    private Integer sex;

    /**
     * 出生日期
     */
    private String birth;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * QQ号码
     */
    private String qq;

    /**
     * 微信号
     */
    private String wechat;

    /**
     * 用户状态，0正常，1禁止
     */
    private Integer state;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除，0未删除 1已删除
     */
    private Integer deleted;
}
