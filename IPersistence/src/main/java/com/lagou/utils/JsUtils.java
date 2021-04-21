package com.lagou.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.script.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class JsUtils {
    public static void main(String[] args) throws Exception{
        /*mimeType为传输的文件类型,如 application/javascript*/
        /*获取执行JavaScript的执行引擎*/
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");
        /*为文件注入全局变量*/
        Bindings bindings = engine.createBindings();
        bindings.put("factor", 2);
        /*设置绑定参数的作用域*/
        engine.setBindings(bindings, ScriptContext.ENGINE_SCOPE);
        engine.put("document",BareBonesBrowserLaunch("http://www.baidu.com/"));
//        Scanner scanner = new Scanner(System.in);
        /*开始等待用户输入->体现出不需要重复运行JVM就可以实现脚本文件的更换*/
//        while(scanner.hasNext()){
            /*只有当用户输入的是整数型时才会被执行*/
            String first = "{\"B\":\"A\"}";
//            int second = scanner.nextInt();
            System.out.println("当前输入的参数为: "+first);
            /*执行js文件代码*/
            File testJs = new File("D:\\ZHT\\mybatis-2021\\mybatis-2021\\mybatis相关代码\\IPersistence\\src\\main\\resources\\script_engine.js");
            String path;
            if (testJs!=null){
                path = testJs.getPath();
                engine.eval(new FileReader(path));
                /*查看是否可以调用方法*/
                if (engine instanceof Invocable){
                    Invocable in = (Invocable) engine;
                    String result = (String)in.invokeFunction("jsonUtil",first);
                    System.out.println("输出结果为"+result);
                }
            }

//        }
    }
    public static Document BareBonesBrowserLaunch(String url) throws MalformedURLException, IOException {
        Document mydoc = Jsoup.parse(new URL(url),30000);//利用Jsoup实现document树
       return  mydoc;
    }
}
