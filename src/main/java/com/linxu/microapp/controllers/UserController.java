package com.linxu.microapp.controllers;

import com.linxu.microapp.dtos.RequestData;
import com.linxu.microapp.dtos.ResponseData;
import com.linxu.microapp.enums.Code;
import com.linxu.microapp.enums.Message;
import com.linxu.microapp.exceptions.IllegalDataException;
import com.linxu.microapp.models.Model2Python;
import com.linxu.microapp.models.Robot;
import com.linxu.microapp.models.User;
import com.linxu.microapp.service.UserService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;

import static com.linxu.microapp.models.Model2Python.resolve;

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
        return userService.relateUserAndRobot(robot.getId(), robot.getRobotNumber(), robot.getRobotType());
    }

    /*********program***************************/
    @PostMapping("/program")
    public ResponseData commitProgram(@RequestBody RequestData requestData) {
        String data;
        try {
            data = Model2Python.resolve(requestData.getProgram());
        } catch (IllegalDataException e) {
            e.printStackTrace();
            System.err.println("前端数据格式错误！");
            return new ResponseData.Builder()
                    .setData(null)
                    .setCode(Code.OK.getCode())
                    .setMsg(Message.OPERATION_FAIL.getMessage())
                    .build();

        }
        return userService.commitProgram(requestData.getId(), data);
    }
}
