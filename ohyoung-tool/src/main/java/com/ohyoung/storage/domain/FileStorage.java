package com.ohyoung.storage.domain;

import com.ohyoung.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Hey4Ace
 * @date 2020/05/09 10:26:53
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "file_storage")
public class FileStorage extends BaseEntity implements Serializable {

    /**
     *  主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 文件类型id
     */
    @Column(name = "classification_id")
    private Long classificationId;
    /**
     * 文件名
     */
    @Column(name = "name")
    private String name;
    /**
     *  文件类型
     */
    @Column(name = "type")
    private String type;
    /**
     * 存储地址
     */
    @Column(name = "origin_file_name")
    private String originFileName;
    /**
     * 文件大小
     */
    @Column(name = "size")
    private String size;

}
