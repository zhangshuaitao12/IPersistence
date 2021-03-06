package com.lagou.dao;

import com.lagou.pojo.User;

import java.util.List;

public interface IUserDao {

    //查询所有用户
    public List<User> findAll() throws Exception;


    //根据条件进行用户查询
    public User selectOne(User user) throws Exception;

    public User insertUser(User user)throws Exception;
    public User updateUser(User user)throws Exception;

    public int deleteOne(User user)throws Exception;


}
