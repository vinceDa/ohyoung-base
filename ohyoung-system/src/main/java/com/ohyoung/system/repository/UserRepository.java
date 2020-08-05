package com.ohyoung.system.repository;

import com.ohyoung.system.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author vince
 */
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor {

    /**
     *  根据用户名查询用户信息
     * @param username 用户名
     * @return 用户信息
     */
    User getByUsername(String username);

    /**
     *  列出当前登陆用户下的用户列表
     * @param createBy 当前登录的用户id
     * @return 用户集合
     */
    List<User> findAllByCreateBy(String createBy);

    /**
     *  获取管理员用户
     */
    @Query(value = "select * from system_user where is_admin = 1", nativeQuery = true)
    User getAdmin();
}
