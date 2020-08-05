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
 * @date 2019/10/11 21:28
 */
@Entity
@Getter
@Setter
@Table(name = "system_menu")
public class Menu extends BaseEntity implements Serializable {

    @JsonIgnore
    @ManyToMany(mappedBy = "menus")
    private Set<Role> roles;

    /**
     *  主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     *  上级菜单id
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     *  菜单名
     */
    @Column(name = "name")
    private String name;

    /**
     *  菜单类型: 0: 目录, 1: 菜单, 2: 按钮
     */
    @Column(name = "type")
    private String type;

    /**
     *  菜单描述
     */
    @Column(name = "description")
    private String description;

    /**
     *  路由
     */
    @Column(name = "path")
    private String path;

    /**
     *  菜单图标代码
     */
    @Column(name = "icon")
    private String icon;

    /**
     *  权限标识
     */
    @Column(name = "permission_tag")
    private String permissionTag;

    /**
     *  是否显示: 0否, 1是
     */
    @Column(name = "is_show")
    private Boolean show;

    /**
     *  菜单排列序号
     */
    @Column(name = "sort")
    private Integer sort;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || o.getClass() != this.getClass()) {
            return false;
        }
        Menu menu = (Menu) o;
        return Objects.equals(id, menu.getId()) && Objects.equals(name, menu.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
