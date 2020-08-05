package com.ohyoung.system.domain;

import com.ohyoung.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

/**
 * @author vince
 */
@Entity
@Getter
@Setter
@Table(name = "system_user")
public class User extends BaseEntity implements Serializable {

    /**
     *  主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     *  用户名
     */
    @Column(name = "username")
    private String username;

    /**
     *  密码
     */
    @Column(name = "password")
    private String password;

    /**
     *  手机号码
     */
    @Column(name = "phone")
    private String phone;

    /**
     *  邮箱
     */
    @Column(name = "email")
    private String email;

    /**
     *  是否为管理员
     */
    @Column(name = "is_admin")
    private Boolean admin;

    /**
     *  是否启用: 0否, 1是
     */
    @Column(name = "is_enable")
    private Boolean enable;

    /**
     *  职位id
     */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "job_id")
    private Job job;

    /**
     *  所属部门id
     */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id")
    private Department department;

    /**
     *  上一次密码更新时间
     */
    @Column(name = "last_password_reset_time")
    private LocalDateTime lastPasswordResetTime;

    /**
     *  当前用户拥有的角色的集合
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "system_user_role", joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))})
    private Set<Role> roles;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(id, user.getId()) && Objects.equals(username, user.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }
}
