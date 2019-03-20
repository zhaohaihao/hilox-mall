package com.hilox.seckill.controller.viewobject;

/**
 * @author Hilox
 * @date 2019-03-17 22:00
 */
public class UserVO {

    /**
     * 用户ID
     */
    private Integer id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别, 1为男性, 2为女性
     */
    private Byte gender;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 号码
     */
    private String telphone;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }
}
