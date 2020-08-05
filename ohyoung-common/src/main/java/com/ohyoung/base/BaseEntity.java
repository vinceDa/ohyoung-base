package com.ohyoung.base;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * @author vince
 * @date 2019/10/10 23:32
 */
@Data
@MappedSuperclass
@EntityListeners(CustomEntityListener.class)
public class BaseEntity {

    /**
     *  创建人id
     */
    @Column(name = "create_by")
    private Long createBy;

    /**
     *  更新人id
     */
    @Column(name = "modify_by")
    private Long modifyBy;

    /**
     *  创建时间
     */
    @Column(name = "gmt_create")
    private LocalDateTime gmtCreate;

    /**
     *  更新时间
     */
    @Column(name = "gmt_modified")
    private LocalDateTime gmtModified;



}
