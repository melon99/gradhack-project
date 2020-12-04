package com.hsbc.gradhack.dao;

import com.hsbc.gradhack.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CloudSQLDAO {

    @Select("select name from user_credit")
    List<String> testSQL();

    @Select("select * from user_credit where name = #{name}")
    User getUser(@Param("name") String name);

    @Update("update user_credit set credit = #{credit} where name = #{name}")
    void updateCredit(@Param("credit") Double credit, @Param("name") String name);

}
