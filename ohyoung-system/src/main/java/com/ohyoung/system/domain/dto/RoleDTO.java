package com.ohyoung.system.domain.dto;

import com.ohyoung.system.domain.User;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

/**
 * @author vince
 */
@Getter
@Setter
public class RoleDTO {

    /**
     *  主键id
     */
    @NotNull(groups = Update.class, message = "id不能为空")

    private Long id;

    /**
     *  用户名
     */
    @NotNull(groups = Insert.class, message = "角色名不能为空")
    private String name;

    /**
     *  描述
     */
    private String description;

    /**
     *  创建时间
     */

    private LocalDateTime gmtCreate;

    /**
     *  更新时间
     */

    private LocalDateTime gmtModified;

    /**
     *  拥有当前角色的用户的集合
     */

    private Set<User> users;

    /**
     *  当前角色拥有的菜单的集合
     */

    private Set<MenuDTO> menus;

    public @interface Insert{}

    public @interface Update{}

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || o.getClass() != this.getClass()) {
            return false;
        }
        RoleDTO role = (RoleDTO) o;
        return Objects.equals(id, role.getId()) && Objects.equals(name, role.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
