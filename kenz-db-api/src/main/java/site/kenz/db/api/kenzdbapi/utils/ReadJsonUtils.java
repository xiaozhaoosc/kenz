package site.kenz.db.api.kenzdbapi.utils;


import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 *  * User: kenzhao
 *  * Date: 2017/8/3
 *  * Time: 16:41
 *  * PROJECT_NAME: risk-parent_2.1
 *  * PACKAGE_NAME: com.honglu.backend.utils
 *  * DESC: Json 数据无Model获取key值
 *  * Version: v1.0.0
 *  
 */
public class ReadJsonUtils {

    /**
     * 传入关键字获取指定JSON数据中的值
     * 目前支持三种结果 集合的返回个数/数值/字符串
     *
     * @param json    JSON字符串
     * @param keyCode 关键字，如A.B.C[].d
     * @return Object 解析的值 -9999视为异常，未解析到匹配值
     */
    public static Object getValueByJsonKey(String json, String keyCode) {
//        keyCode = "report_data.main_service[].service_details[]";
        Map map = (Map)fromJson(json, Map.class);
        if (!StringUtil.checkNotNull(keyCode)) {
            return map;
        }
        try {

            Object obj = getObject(map, keyCode);
            if (obj == null) {
                return -99999;
            }
            if (obj instanceof List) {
                return ((List) obj).size();
            } else if (obj instanceof Integer) {
                return new Integer(obj.toString());
            } else if (obj instanceof String) {
                return obj;
            }
            return new Integer(obj.toString());
        } catch (Exception ex) {
            return -99999;
        }
    }


    /**
     * 对象转JSON字符串
     *
     * @param obj
     * @return String
     */
    public static String toJson(Object obj) {
        if (obj == null) {
            return null;
        }
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

  


    /**
     * Json字符串转对象
     *
     * @param json     Json字符串
     * @param classOfT 对象类型
     * @return 传入的对象类型
     */
    public static  Object fromJson(String json, Class classOfT) {
        if (!StringUtil.checkNotNull(json)) {
            return null;
        }
        Gson gson = new Gson();
        return gson.fromJson(json, classOfT);
    }

    /**
     * 从Map中获取指定key的集合
     *
     * @param obj        Map对象
     * @param keyCode    需要解析的keyCode(有[]标记，集合; 如B[].C.name)
     * @param resultList 需要追加到的集合对象
     * @param key        本次解析的key （如 B）
     * @return
     */
    private static Object getInnerList(Map obj, String keyCode, List resultList, String key) {
        keyCode = keyCode.replace(key + ".", "");
        key = key.replace("[]", "");
        List list = (List) obj.get(key);
        resultList.addAll(list);
        resultList = getList(resultList, keyCode);
        return resultList;
    }

    /**
     * 非集合递归，获取Map对象的key值
     *
     * @param obj     Map对象
     * @param keyCode Map的key值集(如 A.B.C.name)
     * @return 最后一级key的value
     */
    private static Object getObject(Map obj, String keyCode) {
        if (obj != null && obj.size() > 0) {

            //最后得到的集合
            List resultList = new ArrayList();
            Map map = null;
            Object resultObj = null;
            //判断是否为最后一级
            int keyLength = keyCode.indexOf(".");

            //非最后一级则继续递归处理
            if (keyLength > -1) {
                String key = keyCode.substring(0, keyLength);
                if (key.indexOf("[]") > -1) {
                    return getInnerList(obj, keyCode, resultList, key);
                } else {
                    keyCode = keyCode.replace(key + ".", "");
                    map = (Map) obj.get(key);
                    resultObj = getObject(map, keyCode);
                    return resultObj;
                }
            } else {
                //最后一级，返回结果
                resultObj = obj.get(keyCode);
                return resultObj;
            }
        }
        return null;
    }

    /**
     * JSON集合递归，获取Map对象的key值
     *
     * @param objs    Map对象集合
     * @param keyCode Map的key值集(如 A.B.C[].name)
     * @return 最后一级key的value
     */
    private static List getList(List<Map> objs, String keyCode) {


        if (objs != null && objs.size() > 0) {
            //最后得到的集合
            List resultList = new ArrayList();

            //判断是否为最后一级
            int keyLength = keyCode.indexOf(".");

            //非最后一级则继续递归处理
            if (keyLength > -1) {
                String key = keyCode.substring(0, keyLength);
                for (Map obj : objs
                        ) {
                    if (key.indexOf("[]") > -1) {
                        return (List) getInnerList(obj, keyCode, resultList, key);
                    } else {
                        Map map = (Map) obj.get(key);
                        resultList.add(map);
                        keyCode = keyCode.replace(key + ".", "");
                        resultList = getList(resultList, keyCode);
                        return resultList;
                    }
                }

            } else {
                //最后一级则返回值
                for (Map obj : objs
                        ) {
                    if (keyCode.indexOf("[]") > -1) {
                        keyCode = keyCode.replace("[]", "");
                        List list = (List) obj.get(keyCode);
                        resultList.addAll(list);
                    } else {
                        if (obj != null) {
                            Object object = obj.get(keyCode);
                            resultList.add(object);
                        }
                    }
                }
                return resultList;

            }
        }
        return null;
    }

    public static void main(String[] args) throws Exception {

		System.out.println(new Date().getTime());

//		Gson gson = new Gson();
    }
}
