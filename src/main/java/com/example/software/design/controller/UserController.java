package com.example.software.design.controller;

import com.example.software.design.dto.ReadUser;
import com.example.software.design.entity.User;
import com.example.software.design.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger logger = Logger.getLogger(UserController.class.getName());
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        logger.info("bean UserController is created.");
        this.userService = userService;
    }

    @PostMapping("/save")
    public void saveUser (@RequestBody User saveUser) {
        userService.save(saveUser);
    }

    @GetMapping("/all")
    public List<ReadUser> findAllUsers() {
        return userService.findAll().stream().map(userService::map
        ).toList();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ReadUser findUserById(@RequestParam("id") int id) {
        return userService.map(userService.findById(id));
    }

}
