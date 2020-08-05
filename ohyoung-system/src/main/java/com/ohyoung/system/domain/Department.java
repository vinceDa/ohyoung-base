package com.ohyoung.system.domain;

import com.ohyoung.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author vince
 * @date 2019/10/11 23:46
 */
@Entity
@Getter
@Setter
@Table(name = "system_department")
public class Department extends BaseEntity implements Serializable {

    /**
     *  主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     *  父级部门id
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 部门名称
     */
    @Column(name = "name")
    private String name;

    /**
     *  部门描述
     */
    @Column(name = "description")
    private String description;

    /**
     *  是否启用: 0否, 1是
     */
    @Column(name = "is_enable")
    private Boolean enable;
}
