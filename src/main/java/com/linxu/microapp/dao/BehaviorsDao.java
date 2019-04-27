package com.linxu.microapp.dao;

import com.linxu.microapp.models.Behaviors;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

/**
 * @author linxu
 * @date 2019/4/27
 */
@Mapper
public interface BehaviorsDao {
    /**
     * 返回ID
     *
     * @param behaviors b
     */
    @Insert("INSERT INTO pro_behaviors (behaviors) VALUE (#{behavior.behaviors})")
    @Options(
            useGeneratedKeys = true, keyProperty = "id"
    )
    void addBehaviors(@Param("behavior") Behaviors behaviors);
}
