package com.linxu.microapp.dao;

import com.linxu.microapp.models.Advice;
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
    int addUserRobot(@Param("id") int id, @Param("robotId") int robotId) ;

    @Select("select robot_id from user_robot where user_id=#{id}")
    int queryRobotIdByUserId(@Param("id") int id);
    /*******************************user_behaviors**********************/
    @Insert("INSERT INTO user_behaviors (user_id,behaviors_id) VALUES(#{user_id},#{b_id})")
    int relateUserAndProgramBehaviors(@Param("user_id")int userId,@Param("b_id")int behaviorsId);
    /******************user_advice****************************/
    @Select("SELECT * FROM pro_advice WHERE id= #{adviceId}")
    Advice queryAdviceByAdviceId(@Param("adviceId") int adviceId);
    @Select("SELECT advice_id FROM user_advice WHERE user_id=#{uid};")
    List<Integer> queryAdviceIdByUserId(@Param("uid") int userId);

}
