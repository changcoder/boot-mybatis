package com.chang.mapper;

import com.chang.model.Person;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonMapper {

    @Select("select * from person")
    @Results({
            @Result(property = "name", column = "NAME"),
            @Result(property = "address", column = "ADDRESS")
    })
    List<Person> getAll();

    @Select("select * from person where id = #{id}")
    Person getOne(Long id);

    @Insert("insert into person(id,name,age,address) values(#{id},#{name},#{age},#{address})")
    void insertOne(Person person);

    @Update("update person set name=#{name} where id =#{id}")
    void updateOne(Person person);

    @Delete("delete from person where id=#{id}")
    void deleteOne(Long id);
}
