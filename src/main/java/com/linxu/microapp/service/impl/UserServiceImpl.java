package com.linxu.microapp.service.impl;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.linxu.microapp.core.OSConfig;
import com.linxu.microapp.core.OSUtil;
import com.linxu.microapp.dao.BehaviorsDao;
import com.linxu.microapp.dao.RobotDao;
import com.linxu.microapp.dao.UserDao;
import com.linxu.microapp.dtos.ResponseData;
import com.linxu.microapp.dtos.WrapData;
import com.linxu.microapp.enums.Code;
import com.linxu.microapp.enums.Message;
import com.linxu.microapp.exceptions.DaoException;
import com.linxu.microapp.models.*;
import com.linxu.microapp.service.UserService;
import com.linxu.microapp.utils.FileUtil;
import com.linxu.microapp.utils.RequestUtil;
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
import java.util.LinkedList;
import java.util.List;

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
    @Resource
    private BehaviorsDao behaviorsDao;

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
    public ResponseData commitProgram(int userId, String programData, String moduleDataStorage, Counter counter) {
        File file = new File("./" + userId + "-temp.py");
        int robotIdIfExist;
        try {
            robotIdIfExist = userDao.queryRobotIdByUserId(userId);
        } catch (Exception e) {
            //由于不存在
            robotIdIfExist = 0;
        }
        Robot robot;
        if (robotIdIfExist != 0) {
            //已经存在关联的机器人
            robot = robotDao.queryRobotById(robotIdIfExist);
            try {
                FileUtils.writeStringToFile(file, programData);
                //采用机器人编号+.py构成编程文件
                OSUtil.upload(file, OSConfig.defaultBucketName, OSConfig.openClient(), robot.getRobotNumber());
                //存储编程数据
                Behaviors behaviors = new Behaviors();
                behaviors.setBehaviors(moduleDataStorage);
                //会返回ID
                behaviorsDao.addBehaviors(behaviors);
                userDao.relateUserAndProgramBehaviors(userId, behaviors.getId());
                counter.setId(behaviors.getId());
                userDao.addActionData(counter);
                userDao.addRelation(userId, counter.getId());
                //请求数据挖掘，尽早推荐
                System.err.println(RequestUtil.requestPredicate(userId,counter.getId()));
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                //help GC
                counter=null;
            }
            return new ResponseData.Builder()
                    .setCode(Code.OK.getCode())
                    .setData(null)
                    .setMsg(Message.PROGRAM_SUCCESS.getMessage())
                    .build();
        } else {
            //不存在机器人
            return new ResponseData.Builder()
                    .setCode(Code.OK.getCode())
                    .setData(null)
                    .setMsg(Message.ROBOT_IS_NOT_EXIST.getMessage())
                    .build();

        }
    }

    //响应嵌入式
    @Override
    public String flushProgramData(String robotNumber) {
        if (robotNumber == null) {
            return "robot number is null , please check your number from setting.";
        }
        Robot robot = robotDao.queryRobotByNumber(robotNumber);
        if (robot != null) {
            OSSClient client = null;
            try {
                client = OSConfig.openClient();
                //删除
                client.deleteObject(OSConfig.defaultBucketName, robotNumber + ".py");
            } catch (OSSException e) {
                e.printStackTrace();
            } catch (ClientException e) {
                e.printStackTrace();
            } finally {
                if (client != null)
                    client.shutdown();
            }
            return "flush data successfully!";
        }
        return "robot number is not true!";
    }

    @Override
    public synchronized String queryAdvice(int userId) {
        List<Behaviors> adviceList;
        try {
            adviceList = userDao.queryAdviceIdByUserId(userId);
        } catch (Exception e) {
            System.err.println("获取推荐方案出错！");
            e.printStackTrace();
            return null;
        }
        for (Behaviors adv : adviceList) {
            String advice;
            advice = adv.getBehaviors().replaceAll("\\n", "");
            advice = advice.replace("\\", "");
            adv.setBehaviors(advice.trim());
        }
        return Model2Python.buildProgramingAdviceData(adviceList);
    }
}
