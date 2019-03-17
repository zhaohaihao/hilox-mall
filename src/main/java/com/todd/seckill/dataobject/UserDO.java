package com.todd.seckill.dataobject;

/**
 * 用户信息
 * @author Todd
 * @date 2019-03-17 16:22
 */
public class UserDO {
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

    /**
     * 注册方式, byphone,bywechat,byalipay
     */
    private String registerMode;

    /**
     * 第三方账号ID
     */
    private String thirdPartId;

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
        this.name = name == null ? null : name.trim();
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
        this.telphone = telphone == null ? null : telphone.trim();
    }

    public String getRegisterMode() {
        return registerMode;
    }

    public void setRegisterMode(String registerMode) {
        this.registerMode = registerMode == null ? null : registerMode.trim();
    }

    public String getThirdPartId() {
        return thirdPartId;
    }

    public void setThirdPartId(String thirdPartId) {
        this.thirdPartId = thirdPartId == null ? null : thirdPartId.trim();
    }
}