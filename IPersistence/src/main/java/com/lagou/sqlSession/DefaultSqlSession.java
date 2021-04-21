package com.lagou.sqlSession;

import com.lagou.pojo.Configuration;
import com.lagou.pojo.MappedStatement;

import java.lang.reflect.*;
import java.util.List;

public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <E> List<E> selectList(String statementid, Object... params) throws Exception {

        //将要去完成对simpleExecutor里的query方法的调用
        simpleExecutor simpleExecutor = new simpleExecutor();
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementid);
        List<Object> list = simpleExecutor.query(configuration, mappedStatement, params);

        return (List<E>) list;
    }

    @Override
    public <T> T selectOne(String statementid, Object... params) throws Exception {
        List<Object> objects = selectList(statementid, params);
        if(objects.size()==1){
            return (T) objects.get(0);
        }else {
            throw new RuntimeException("查询结果为空或者返回结果过多");
        }


    }

    @Override
    public <T> T insertOne(String statementId, Object... params) throws Exception {
        //将要去完成对simpleExecutor里的query方法的调用
        simpleExecutor simpleExecutor = new simpleExecutor();
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        List<Object> list = simpleExecutor.insert(configuration, mappedStatement, params);

        if(list.size()==1){
            return (T) list.get(0);
        }else {
            throw new RuntimeException("插入数据失败");
        }
    }

    @Override
    public <T> T updateOne(String statementid, Object... params) throws Exception {
        //将要去完成对simpleExecutor里的query方法的调用
        simpleExecutor simpleExecutor = new simpleExecutor();
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementid);
        List<Object> list = simpleExecutor.update(configuration, mappedStatement, params);

        if(list.size()==1){
            return (T) list.get(0);
        }else {
            throw new RuntimeException("更新数据失败");
        }
    }

    @Override
    public <T> T deleteOne(String statementid, Object... params) throws Exception {
        //将要去完成对simpleExecutor里的query方法的调用
        simpleExecutor simpleExecutor = new simpleExecutor();
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementid);
        Integer list = simpleExecutor.delete(configuration, mappedStatement, params);
        return (T) list;
    }

    @Override
    public <T> T getMapper(Class<?> mapperClass) {
        // 使用JDK动态代理来为Dao接口生成代理对象，并返回

        Object proxyInstance = Proxy.newProxyInstance(DefaultSqlSession.class.getClassLoader(), new Class[]{mapperClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // 底层都还是去执行JDBC代码 //根据不同情况，来调用selctList或者selectOne
                // 准备参数 1：statmentid :sql语句的唯一标识：namespace.id= 接口全限定名.方法名
                // 方法名：findAll
                String methodName = method.getName();
                if(methodName.startsWith("find")){

                    String className = method.getDeclaringClass().getName();

                    String statementId = className+"."+methodName;

                    // 准备参数2：params:args
                    // 获取被调用方法的返回值类型
                    Type genericReturnType = method.getGenericReturnType();
                    // 判断是否进行了 泛型类型参数化
                    if(genericReturnType instanceof ParameterizedType){
                        List<Object> objects = selectList(statementId, args);
                        return objects;
                    }

                    return selectOne(statementId,args);

                }else if(methodName.startsWith("insert")){
                    String className = method.getDeclaringClass().getName();

                    String statementId = className+"."+methodName;

                    // 准备参数2：params:args
                    // 获取被调用方法的返回值类型
                    Type genericReturnType = method.getGenericReturnType();
                    // 判断是否进行了 泛型类型参数化
                    if(genericReturnType instanceof ParameterizedType){
                        List<Object> objects = insertOne(statementId, args);
                        return objects;
                    }
                    return insertOne(statementId,args);
                }else if(methodName.startsWith("update")){
                    String className = method.getDeclaringClass().getName();

                    String statementId = className+"."+methodName;

                    // 准备参数2：params:args
                    // 获取被调用方法的返回值类型
                    Type genericReturnType = method.getGenericReturnType();
                    // 判断是否进行了 泛型类型参数化
                    if(genericReturnType instanceof ParameterizedType){
                        List<Object> objects = updateOne(statementId, args);
                        return objects;
                    }
                    return updateOne(statementId,args);
                }else{
                    String className = method.getDeclaringClass().getName();

                    String statementId = className+"."+methodName;

                    // 准备参数2：params:args
                    // 获取被调用方法的返回值类型
                    Type genericReturnType = method.getGenericReturnType();
                    // 判断是否进行了 泛型类型参数化
                    if(genericReturnType instanceof ParameterizedType){
                        List<Object> objects = deleteOne(statementId, args);
                        return objects;
                    }
                    return deleteOne(statementId,args);
                }
            }
        });

        return (T) proxyInstance;
    }


}
