package com.hilox.seckill.dataobject;

/**
 * 用户密码
 * @author Hilox
 * @date 2019-03-17 16:22
 */
public class UserPasswordDO {

    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 密码
     */
    private String encrptPassword;

    /**
     * 用户ID
     */
    private Integer userId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEncrptPassword() {
        return encrptPassword;
    }

    public void setEncrptPassword(String encrptPassword) {
        this.encrptPassword = encrptPassword == null ? null : encrptPassword.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}