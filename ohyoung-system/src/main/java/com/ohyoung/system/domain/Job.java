package com.ohyoung.system.domain;

import com.ohyoung.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author vince
 */
@Entity
@Getter
@Setter
@Table(name = "system_job")
public class Job extends BaseEntity implements Serializable {

    /**
     *  主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     *  岗位名称
     */
    @Column(name = "name")
    private String name;

    /**
     *  是否启用: 0否, 1是
     */
    @Column(name = "is_enable")
    private Boolean enable;

    /**
     *  所属部门id
     */
    @Column(name = "department_id")
    private Long departmentId;

}
