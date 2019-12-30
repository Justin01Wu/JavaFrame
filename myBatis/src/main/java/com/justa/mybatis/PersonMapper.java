package com.justa.mybatis;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

public interface PersonMapper {
	@Insert("Insert into person(personId, name) values (#{personId},#{name})")
    public Integer save(Person person);
 
    // ...
 
    @Select(
      "Select personId, name from Person where personId=#{personId}")
    @Results(value = {
      @Result(property = "personId", column = "personId"),
      @Result(property="name", column = "name")
//      , @Result(property = "addresses", javaType = List.class,
//        column = "personId", many=@Many(select = "getAddresses"))
    })
    public Person getPersonById(Integer personId);
}
