package com.ohyoung.storage.domain;

import com.ohyoung.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;

/**
* @author vince
* @date 2020/05/15 02:21:57
*/
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "file_classification")
public class FileClassification extends BaseEntity implements Serializable {

        /**
         *  主键id
         */
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private Long id;

        /**
         *  文件类型名称
         */
        @Column(name = "name")
        private String name;
        /**
         *  创建人
         */
        @Column(name = "create_by")
        private Long createBy;
        /**
         *  更新人
         */
        @Column(name = "update_by")
        private Long updateBy;
}
