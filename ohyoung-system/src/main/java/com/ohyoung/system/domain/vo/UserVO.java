package com.ohyoung.system.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author vince
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserVO {

    /**
     *  主键id
     */
    private Long id;

    /**
     *  用户名
     */
    private String username;

    /**
     *  手机号码
     */
    private String phone;

    /**
     *  邮箱
     */
    private String email;

    /**
     *  是否启用: 0否, 1是
     */
    private Boolean enable;

    /**
     *  职位
     */
    private JobVO job;

    /**
     *  所属部门
     */
    private DepartmentVO department;

    /**
     *  创建时间
     */
    private LocalDateTime gmtCreate;

    /**
     *  上一次密码更新时间
     */
    private LocalDateTime lastPasswordResetTime;

    /**
     *  当前用户拥有的角色的集合
     */
    private Set<RoleVO> roles;

    /**
     *  当前用户拥有的菜单的集合
     */
    private Set<MenuVO> menus;

}
