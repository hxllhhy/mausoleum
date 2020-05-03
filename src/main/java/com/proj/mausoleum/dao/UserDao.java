package com.proj.mausoleum.dao;

import com.proj.mausoleum.pojo.MInfo;
import com.proj.mausoleum.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import javax.jws.soap.SOAPBinding;
import java.util.List;

@Mapper
public interface UserDao {

    User selectUser(User user);

    Integer insertUser(User user);

    Integer updateUser(User user);

    List<User> selectUserList();

    Integer countUserList();

    Integer deleteUserByUserId(@Param("userId") Integer userId);
}
