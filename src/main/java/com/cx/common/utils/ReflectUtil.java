package com.cx.common.utils;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 反射工具类
 */
@Slf4j
public class ReflectUtil {

    /**
     * 执行方法 针对多个参数
     * @param beanName  类名
     * @param methodName  方法名
     * @param params  参数Map {参数类型：参数值}
     * @return
     */
    public static Object execByParams(String beanName, String methodName, String params){
        try {
            Object service = SpringContextUtil.getBean(beanName);

            Map<String, String> map = JSON.parseObject(params, LinkedHashMap.class);

            Object[] objs = new Object[map.size()];
            Class[] classes = new Class[map.size()];
            int i = 0;
            for (Map.Entry<String, String> entry : map.entrySet()) {
                Class clazz = getClass(entry.getKey());
                classes[i] = clazz;
                objs[i] = JSON.parseObject(JSON.toJSONString(entry.getValue()), clazz);
                i++;
            }
            Method method = service.getClass().getDeclaredMethod(methodName, classes);

            return method.invoke(service, objs);
        }catch (NoSuchMethodException|IllegalAccessException|InvocationTargetException e){
            log.error("",e);
        }
       return null;
    }

    /**
     * 执行 针对单个参数
     * @param beanName  类名
     * @param methodName  方法名
     * @param param  参数值
     * @param paramType  参数类型
     * @return
     */
    public static Object execByParam(String beanName,String  methodName,Object param,String  paramType){
        try {
            Object service = SpringContextUtil.getBean(beanName);
            Method method = service.getClass().getDeclaredMethod(methodName, Class.forName(paramType));
            return method.invoke(service, JSON.parseObject(JSON.toJSONString(param),Class.forName(paramType)));
        }catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
            log.error("",e);
        }
        return null;
    }

    /**
     * 执行 针对单个参数
     *
     * @param beanName   类名
     * @param methodName 方法名
     * @param param      参数值
     * @param paramType  参数类型
     * @return
     */
    public static Object execByParam(String beanName, String methodName, Object[] param, Class[] paramType) {
        try {
            Object service = SpringContextUtil.getBean(beanName);
            Method method = service.getClass().getDeclaredMethod(methodName, paramType);
            return method.invoke(service, param);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            log.error("", e);
        }
        return null;
    }

    /**
     * 执行 针对单个参数
     *
     * @param beanName   类名
     * @param methodName 方法名
     * @param param      参数值
     * @param paramType  参数类型
     * @return
     */
    public static Object execByParam(String beanName, String methodName, Object param, Class<?> paramType) {
        try {
            Object service = SpringContextUtil.getBean(beanName);
            Method method = service.getClass().getDeclaredMethod(methodName, paramType);
            return method.invoke(service, param);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            log.error("", e);
        }
        return null;
    }

    /**
     * 执行 针对单个参数
     * @param param  参数值
     * @param paramType  参数类型
     * @return
     */
    public static Object  transform(Object param, String paramType){
        try {
            return JSON.parseObject(JSON.toJSONString(param), (Type) Class.forName(paramType));
        }catch (ClassNotFoundException e) {
            log.error("",e);
        }
        return null;
    }


    /**
     * 执行方法 针对多个参数
     *
     * @param params 参数Map {参数类型：参数值}
     * @return
     */
    public static Map<String, Object> transform(String params) {
            Map<String, Object> paramsObj = new HashMap<>();
            Map<String, String> map = JSON.parseObject(params, LinkedHashMap.class);
            Object[] objs = new Object[map.size()];
            Class[] classes = new Class[map.size()];
            int i = 0;
            for (Map.Entry<String, String> entry : map.entrySet()) {
                Class clazz = getClass(entry.getKey());
                classes[i] = clazz;
                objs[i] = JSON.parseObject(JSON.toJSONString(entry.getValue()), clazz);
                i++;
            }
            paramsObj.put("params", objs);
            paramsObj.put("class", classes);
            return paramsObj;
    }


    private static Class  getClass(String className) {
        Class clazz = null;
       try{
           if (className.contains("##")) {
               clazz = Class.forName(className.split("##")[0]);
           } else {
               clazz = Class.forName(className);
           }
       }catch(ClassNotFoundException e){
           log.error(""+e);
       }
        return clazz;
    }
}
