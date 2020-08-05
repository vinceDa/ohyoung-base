package com.ohyoung.util;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

/**
 * 给对象的公共参数设置值, createTime、updateTime、createUserId、updateUserId
 *
 * @author vince
 */
public class CommonParamUtil {

    private static final String COMMON_PARAM_CREATE_TIME = "createTime";
    private static final String COMMON_PARAM_UPDATE_TIME = "updateTime";
    private static final String COMMON_PARAM_CREATE_USER_ID = "createUserId";
    private static final String COMMON_PARAM_UPDATE_USER_ID = "updateUserId";


    public static void setAll(Object o) {
        Class<?> aClass = o.getClass();
        Field[] fields = aClass.getFields();
        try {

            for (Field field : fields) {
                String fieldName = field.getName();
                if (COMMON_PARAM_CREATE_TIME.equals(fieldName)) {
                    field.set(o, LocalDateTime.now());
                } else if (COMMON_PARAM_UPDATE_TIME.equals(fieldName)) {
                    field.set(o, LocalDateTime.now());
                } else if (COMMON_PARAM_CREATE_USER_ID.equals(fieldName)) {
                    field.set(o, LocalDateTime.now());
                } else if (COMMON_PARAM_UPDATE_USER_ID.equals(fieldName)) {
                    field.set(o, LocalDateTime.now());
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void setCreateParam(Object o) {

    }

    public static void setUpdateParam(Object o) {

    }

}
