package com.linxu.microapp.service;

import com.linxu.microapp.dtos.ResponseData;
import com.linxu.microapp.models.User;

import java.util.List;

/**
 * @author linxu
 * @date 2019/4/26
 */
public interface UserService {
    ResponseData login(User user);

    ResponseData register(User user);

    ResponseData modifyPsw(User user);

    ResponseData relateUserAndRobot(int userId, String robotNumber, String robotType) ;

    ResponseData commitProgram(int userId,String programData,String moduleDataStorage);

    String flushProgramData(String robotNumber);

    String queryAdvice(int userId);

    public static void main(String[] args) {
        //test
        String a="\\"+"\"type";
        System.err.println(a);
        System.out.println(a.replaceAll("\\\\\"","123"));
    }

}
