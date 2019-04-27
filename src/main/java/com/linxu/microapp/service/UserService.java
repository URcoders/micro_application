package com.linxu.microapp.service;

import com.linxu.microapp.dtos.ResponseData;
import com.linxu.microapp.models.User;

/**
 * @author linxu
 * @date 2019/4/26
 */
public interface UserService {
    ResponseData login(User user);

    ResponseData register(User user);

    ResponseData modifyPsw(User user);

    ResponseData relateUserAndRobot(int userId, String robotNumber, String robotType) ;

    ResponseData commitProgram(int userId,String programData);


}
