package cc.myosotis.leisure.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 用户实体
 */
@Entity
public class User extends Operator {

    public static final Integer STATUS_NORMAL = 0;

    public static final Integer STATUS_FREEZE = 1;

    public static final Integer STATUS_DELETE = 2;

    public static final String ROLE_VIP = "vip";

    public static final String ROLE_ADMIN = "admin";

    public static final String ROLE_GENERAL = "general";

    // 用户
    private String username;

    // 密码
    private String password;

    // 角色
    private String role = ROLE_GENERAL;

    // 状态
    private Integer status = STATUS_NORMAL;

    // 简介
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Profile profile = new Profile();

    // 消闲应用
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Leisure leisure = new Leisure();

    // 好友
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<User> friends = new HashSet<>();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Leisure getLeisure() {
        return leisure;
    }

    public void setLeisure(Leisure leisure) {
        this.leisure = leisure;
    }

    public Set<User> getFriends() {
        return friends;
    }

    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }

    public void create(String who, String email) {
        super.create(who);
        profile.create(who);
        leisure.create(who);
        profile.setEmail(email);
    }

    public void addFriend(User user) {
        this.friends.add(user);
    }
}
