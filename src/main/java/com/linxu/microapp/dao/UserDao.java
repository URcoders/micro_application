package com.linxu.microapp.dao;

import com.linxu.microapp.models.Advice;
import com.linxu.microapp.models.Behaviors;
import com.linxu.microapp.models.Counter;
import com.linxu.microapp.models.User;
import org.apache.ibatis.annotations.*;


import java.sql.SQLException;
import java.util.List;

/**
 * @author linxu
 * @date 2019/4/26
 */
@Mapper
public interface UserDao {
    /*********************************user table***********************************/
    @Select("SELECT * FROM user where username=#{user.userName}")
    @Results(
            {
                    @Result(property = "id", column = "id"),
                    @Result(property = "userName", column = "username"),
                    @Result(property = "password", column = "password"),
                    @Result(property = "name", column = "name")
            }
    )
    User queryUserByUserName(@Param("user") User user) throws SQLException;

    @Insert("INSERT INTO user (username,password,name) VALUES (#{user.userName},#{user.password},#{user.name})")
    @Options(
            useGeneratedKeys = true, keyProperty = "id"
    )
    void addUser(@Param("user") User user) throws SQLException;

    @Update("UPDATE user SET password=#{user.password}")
    void updateUserPsw(@Param("user") User user) throws SQLException;

    /************************************user_robot table*************************/
    @Insert("INSERT INTO user_robot (user_id,robot_id)VALUES (#{id},#{robotId})")
    int addUserRobot(@Param("id") int id, @Param("robotId") int robotId);

    @Select("select robot_id from user_robot where user_id=#{id}")
    int queryRobotIdByUserId(@Param("id") int id);

    /*******************************user_behaviors**********************/
    @Insert("INSERT INTO user_behaviors (user_id,behaviors_id) VALUES(#{user_id},#{b_id})")
    int relateUserAndProgramBehaviors(@Param("user_id") int userId, @Param("b_id") int behaviorsId);

    /******************user_advice****************************/
 /*   @Select("SELECT * FROM pro_advice WHERE id= #{adviceId}")
    Advice queryAdviceByAdviceId(@Param("adviceId") int adviceId);*/
    @Select("SELECT pro_behaviors.*, user_advice.weight  FROM pro_behaviors, user_advice WHERE user_advice.user_id=#{uid} AND pro_behaviors.id=user_advice.advice_id ORDER BY weight ;")
    List<Behaviors> queryAdviceIdByUserId(@Param("uid") int userId);

    @Insert("insert into user_action (id,ahead,behind,move_left,move_right,take_photo,show_photo,open_arm,close_arm,move_high_up," +
            "move_high_down,move_low_up,move_low_down,move_arm_left,move_arm_right,check_thing)VALUES(#{c.id},#{c.ahead},#{c.behind},#{c.left},#{c.right},#{c.take_photo}" +
            ",#{c.show_photo},#{c.open_arm},#{c.close_arm},#{c.move_high_up},#{c.move_high_down},#{c.move_low_up},#{c.move_low_down}" +
            ",#{c.move_arm_left},#{c.move_arm_right},#{c.check_thing}) ")
    void addActionData(@Param("c") Counter counter);

    /****************user_action_data****************************/
    @Insert("insert into user_action_data (user_id,action_id)VALUES(#{u},#{a})")
    void addRelation(@Param("u") int userId, @Param("a") int actionId);

}
