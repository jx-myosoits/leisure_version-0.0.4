package cc.myosotis.leisure.model;

import javax.persistence.Entity;
import java.util.Date;

/**
 * 用户信息
 */
@Entity
public class Profile extends Operator {

    public static final Integer GENDER_MALE = 1;

    public static final Integer GENDER_OTHER = 2;

    public static final Integer GENDER_FEMALE = 0;

    // 名字
    private String name;

    // 生日
    private Date birthday;

    // 性别，默认（女）
    private Integer gender = GENDER_FEMALE;

    // 位置
    private String location;

    // 家乡
    private String hometown;

    // 介绍
    private String biography;

    // 手机
    private String cellphone;

    // 行业
    private String industry;

    // 专业
    private String major;

    // 邮箱
    private String email;

    // 爱好
    private String hobby[] = new String[0];

    // 站点
    private String site;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String[] getHobby() {
        return hobby;
    }

    public void setHobby(String[] hobby) {
        this.hobby = hobby;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }
}
