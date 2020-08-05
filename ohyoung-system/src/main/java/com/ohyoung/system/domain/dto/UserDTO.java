package com.ohyoung.system.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author vince
 */
@Getter
@Setter
@ToString
public class UserDTO {

    /**
     *  主键id
     */
    @NotNull(groups = Update.class, message = "id不能为空")
    private Long id;

    /**
     *  用户名
     */
    @NotNull(groups = Insert.class, message = "用户名不能为空")
    private String username;

    /**
     *  密码
     */
    private String password;

    /**
     *  盐
     */

    private String salt;

    /**
     *  手机号码
     */
    private String phone;

    /**
     *  邮箱
     */
    private String email;

    /**
     *  是否为管理员
     */
    private Boolean admin;

    /**
     *  是否启用: 0否, 1是
     */
    private Boolean enable;

    /**
     *  职位
     */
    private JobDTO job;

    /**
     *  所属部门
     */
    private DepartmentDTO department;


    /**
     *  创建时间
     */
    private LocalDateTime gmtCreate;

    /**
     *  更新时间
     */
    private LocalDateTime gmtModified;

    /**
     *  上一次密码更新时间
     */
    private LocalDateTime lastPasswordResetTime;

    /**
     *  当前用户拥有的角色的集合
     */
    private Set<RoleDTO> roles;

    public @interface Insert{}

    public @interface Update{}
}
