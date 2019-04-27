package com.linxu.microapp.controllers;

import com.linxu.microapp.dtos.ResponseData;
import com.linxu.microapp.models.Robot;
import com.linxu.microapp.models.User;
import com.linxu.microapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author linxu
 * @date 2019/4/27
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseData login(@RequestBody User user) {
        return userService.login(user);
    }

    @PostMapping("/register")
    public ResponseData register(@RequestBody User user) {
        return userService.register(user);
    }

    @PostMapping("/modify")
    public ResponseData modifyPsw(@RequestBody User user) {
        return userService.modifyPsw(user);
    }

    /************robot********************/
    @PostMapping("/addrobot")
    public ResponseData addRobot(@RequestBody Robot robot) {
        try {
            return userService.relateUserAndRobot(robot.getId(), robot.getRobotNumber(), robot.getRobotType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
