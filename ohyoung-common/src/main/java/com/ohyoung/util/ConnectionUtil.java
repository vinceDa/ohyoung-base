package com.ohyoung.util;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 获取数据库连接的工具类, 暂时只支持Mysql
 * 
 * @author vince
 * @date 2019/12/20 11:14
 */
@Slf4j
public class ConnectionUtil {

    public static Connection getMysqlConnection(String host, String port, String username, String password) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://" + host + ":" + port + "?serverTimezone=CST";
            return DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            log.error("getMysqlConnection error: {}", e.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
