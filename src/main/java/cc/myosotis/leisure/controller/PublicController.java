package cc.myosotis.leisure.controller;

import cc.myosotis.leisure.model.User;
import cc.myosotis.leisure.service.PublicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PublicController {

    private PublicService service;

    @Autowired
    public PublicController(PublicService service) {
        this.service = service;
    }

    // 获取随机验证码
    @GetMapping("/verification")
    public Object verification() {
        return service.verification("0123456789", 4);
    }

    // 创建用户
    @PostMapping("/create")
    public Object createUser(User user, String email) {
        return service.createUser(user, email);
    }

    // 激活用户
    @GetMapping("/activation")
    public Object activationUser(String token) {
        return service.activationUser(token);
    }

    // 登陆用户
    @PostMapping("/login")
    public Object loginUser(User user, String verification) {
        return service.loginUser(user, verification);
    }

    // 用户信息
    @GetMapping("/user/{username}")
    public Object userProfile(@PathVariable String username) {
        return service.userProfile(username);
    }

    // 获取商品分类
    @GetMapping("/sort/list")
    public Object getSorts() {
        return service.sorts();
    }

    // 获取商品
    @PostMapping("/commodities/search")
    public Object getCommodities(
            String commodityName,
            String searchMethod,
            String order,
            Integer page,
            Integer size,
            Double min,
            Double max) {
        return service.getCommodities(
                commodityName,
                searchMethod,
                order,
                page,
                size,
                min,
                max
        );
    }

    // 修改密码（密码找回）

    // 修改绑定邮件

    // 修改保密问题

}
