package com.linxu.microapp.service.impl;

import com.linxu.microapp.core.OSConfig;
import com.linxu.microapp.core.OSUtil;
import com.linxu.microapp.dao.RobotDao;
import com.linxu.microapp.dao.UserDao;
import com.linxu.microapp.dtos.ResponseData;
import com.linxu.microapp.dtos.WrapData;
import com.linxu.microapp.enums.Code;
import com.linxu.microapp.enums.Message;
import com.linxu.microapp.exceptions.DaoException;
import com.linxu.microapp.models.Robot;
import com.linxu.microapp.models.User;
import com.linxu.microapp.service.UserService;
import com.linxu.microapp.utils.FileUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author linxu
 * @date 2019/4/26
 */
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON, proxyMode = ScopedProxyMode.DEFAULT)
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;
    @Resource
    private RobotDao robotDao;

    @Override
    public ResponseData login(User user) {
        User retUser;
        ResponseData data = null;
        WrapData wrapData = new WrapData();
        try {
            retUser = userDao.queryUserByUserName(user);
            wrapData.setUser(retUser);
        } catch (SQLException e) {
            e.printStackTrace();
            //出现异常
            data = new ResponseData.Builder()
                    .setMsg(Message.SEVER_ERROR.getMessage())
                    .setCode(Code.UNKONW.getCode())
                    .setData(null)
                    .build();
            return data;
        }
        //登录成功
        if (user.getPassword() != null && user.getPassword().equals(retUser.getPassword())) {
            retUser.setPassword("*");
            wrapData.setUser(retUser);
            data = new ResponseData.Builder()
                    .setMsg(Message.LOGIN_SUCCESS.getMessage())
                    .setCode(Code.OK.getCode())
                    .setData(wrapData)
                    .build();
        }
        //登录失败
        else {
            data = new ResponseData.Builder()
                    .setMsg(Message.LOGIN_FAIL.getMessage())
                    .setCode(Code.OK.getCode())
                    .setData(null)
                    .build();
        }
        return data;
    }

    @Override
    public ResponseData register(User user) {
        int retPrimaryKey;
        ResponseData data;
        WrapData wrapData = new WrapData();
        try {
            if (userDao.queryUserByUserName(user) != null) {
                data = new ResponseData.Builder()
                        .setMsg(Message.ISEXIST.getMessage())
                        .setCode(Code.OK.getCode())
                        .setData(null)
                        .build();
                return data;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            userDao.addUser(user);
            retPrimaryKey = user.getId();
            wrapData.setId(retPrimaryKey);
        } catch (SQLException e) {
            e.printStackTrace();
            data = new ResponseData.Builder()
                    .setMsg(Message.REGISTER_FAIL.getMessage())
                    .setCode(Code.UNKONW.getCode())
                    .setData(wrapData)
                    .build();
            return data;
        }
        data = new ResponseData.Builder()
                .setMsg(Message.REGISTER_SUCCESS.getMessage())
                .setCode(Code.OK.getCode())
                .setData(wrapData)
                .build();
        return data;
    }

    @Override
    public ResponseData modifyPsw(User user) {
        ResponseData data;
        WrapData wrapData = new WrapData();
        try {
            userDao.updateUserPsw(user);
        } catch (SQLException e) {
            e.printStackTrace();
            data = new ResponseData.Builder()
                    .setMsg(Message.SEVER_ERROR.getMessage())
                    .setCode(Code.UNKONW.getCode())
                    .setData(wrapData)
                    .build();
            return data;
        }
        data = new ResponseData.Builder()
                .setMsg(Message.OPERATION_SUCCESS.getMessage())
                .setCode(Code.OK.getCode())
                .setData(wrapData)
                .build();
        return data;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, SQLException.class, DaoException.class})
    public ResponseData relateUserAndRobot(int userId, String robotNumber, String robotType) {
        ResponseData data;
        WrapData wrapData = new WrapData();
        Robot robot = new Robot();
        robot.setRobotNumber(robotNumber);
        robot.setRobotType(robotType);
        try {
            int robotIdIfExist;
            try {
                robotIdIfExist = userDao.queryRobotIdByUserId(userId);
            } catch (Exception e) {
                //返回空，赋空
                robotIdIfExist = 0;
            }
            if (robotIdIfExist != 0) {
                //原本存在，即更新
                robotDao.updateRobot(robot, robotIdIfExist);
            } else {
                //不存在则添加
                robotDao.addRobot(robot);
                userDao.addUserRobot(userId, robot.getId());
            }
        } catch (Exception e) {
            throw new DaoException("捕捉回滚");
        }

        data = new ResponseData.Builder()
                .setMsg(Message.OPERATION_SUCCESS.getMessage())
                .setCode(Code.OK.getCode())
                .setData(wrapData)
                .build();
        return data;
    }

    @Override
    public ResponseData commitProgram(int userId, String programData) {
        File file = new File("./" + userId + "-temp.py");
        try {
            FileUtils.writeStringToFile(file, programData);
            OSUtil.upload(file, OSConfig.defaultBucketName, OSConfig.openClient(), userId + "-" + FileUtil.getUuid());
            //TODO program data storage
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseData.Builder()
                .setCode(Code.OK.getCode())
                .setData(null)
                .setMsg(Message.OPERATION_SUCCESS.getMessage())
                .build();
    }
}
