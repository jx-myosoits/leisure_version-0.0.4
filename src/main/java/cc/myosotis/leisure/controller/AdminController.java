package cc.myosotis.leisure.controller;

import cc.myosotis.leisure.service.AdminService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private AdminService service;

    @Autowired
    public AdminController(AdminService service) {
        this.service = service;
    }

    // 获取用户
    @GetMapping("/users")
    public Object getUsers(
            String username,
            String method,
            Integer page,
            Integer size,
            Date begin,
            Date over) {
        return null;
    }

    @GetMapping("/freeze/{username}")
    public Object freezeUsers(@PathVariable String username) {
        return null;
    }

    @GetMapping("/delete/{username}")
    public Object deleteUsers(@PathVariable String username) {
        return null;
    }



}
