package com.linxu.microapp.dao;

import com.linxu.microapp.models.Robot;
import org.apache.ibatis.annotations.*;

import java.sql.SQLException;

/**
 * @author linxu
 * @date 2019/4/27
 */
@Mapper
public interface RobotDao {
    /**********************************robot table*****************************************************/
    @Insert("INSERT INTO robot (robot_no,robot_type) VALUES (#{robot.robotNumber},#{robot.robotType})")
    @Options(
            useGeneratedKeys = true, keyProperty = "id"
    )
    void addRobot(@Param("robot") Robot robot) throws SQLException;

    @Select("SELECT * FROM robot WHERE robot_no = #{no}")
    Robot queryRobotByNumber(@Param("no") String robotNumber);

    @Select("SELECT * FROM robot WHERE id = #{id}")
    Robot queryRobotById(@Param("id") int id);

    @Update("update robot set robot_type=#{r.robotType},robot_no=#{r.robotNumber} where robot_id=#{rid}")
    void updateRobot(@Param("r") Robot robot,@Param("rid") int id);
    /**********************************user_robot*****************/

}
