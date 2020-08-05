package com.ohyoung.system.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ohyoung.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * @author vince
 */
@Entity
@Getter
@Setter
@Table(name = "system_role")
public class Role extends BaseEntity implements Serializable {

    /**
     *  主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    /**
     *  角色名
     */
    @Column(name = "name")
    private String name;

    /**
     *  描述
     */
    @Column(name = "description")
    private String description;

    /**
     *  当前角色拥有的菜单的集合
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "system_role_menu", joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))},
            inverseJoinColumns = {@JoinColumn(name = "menu_id", referencedColumnName = "id", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))},
            foreignKey = @ForeignKey(name = "null", value = ConstraintMode.NO_CONSTRAINT))
    private Set<Menu> menus;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || o.getClass() != this.getClass()) {
            return false;
        }
        Role role = (Role) o;
        return Objects.equals(id, role.getId()) && Objects.equals(name, role.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
