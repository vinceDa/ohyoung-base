package com.ohyoung.util;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.ohyoung.exception.NotLoggedInException;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author vince
 * @date 2019/12/11 20:34
 */
public class SecurityUtil {

    public static JSONObject getJwtUserInfo() {
        try {
            return JSONUtil.parseObj(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        } catch (Exception e) {
            e.printStackTrace();
            throw new NotLoggedInException();
        }
    }

    public static Long getJwtUserId() {
        try {
            return getJwtUserInfo().getLong("id");
        } catch (Exception e) {
            e.printStackTrace();
            throw new NotLoggedInException();
        }
    }

}
