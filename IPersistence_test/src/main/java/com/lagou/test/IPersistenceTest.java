package com.lagou.test;

import com.lagou.dao.IUserDao;
import com.lagou.io.Resources;
import com.lagou.pojo.User;
import com.lagou.sqlSession.SqlSession;
import com.lagou.sqlSession.SqlSessionFactory;
import com.lagou.sqlSession.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.InputStream;
import java.util.Date;

public class IPersistenceTest {

    @Test
    public void test() throws Exception {
        InputStream resourceAsSteam = Resources.getResourceAsSteam("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsSteam);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //调用
        User user = new User();
//        user.setId(1);
        user.setUsername("toms");
        user.setPassword("123");
        user.setBirthday(new Date());
//        User user2 = sqlSession.selectOne("com.lagou.dao.IUserDao.selectOne", user);
//
//        System.out.println(user2);

//       List<User> users = sqlSession.selectList("user.selectList",user);
//        for (User user1 : users) {
//            System.out.println(user1);
//        }

        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
//        userDao.selectOne(user);

//        List<User> all = userDao.findAll();
//        for (User user1 : all) {
//            System.out.println(user1);
//        }
        int user1 = userDao.deleteOne(user);
        System.out.println(user1);
    }



}
