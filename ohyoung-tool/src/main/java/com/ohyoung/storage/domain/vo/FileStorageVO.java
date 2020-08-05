package com.ohyoung.storage.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Hey4Ace
 * @date 2020/05/09 10:26:53
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileStorageVO {

    /**
     * 主键id
     */
    private Long id;
    /**
     * 文件类型id
     */
    private Long classificationId;
    /**
     * 文件名
     */
    private String name;
    /**
     *  文件类型
     */
    private String type;
    /**
     * 存储地址
     */
    private String originFileName;
    /**
     * 文件大小
     */
    private String size;
    /**
     * 创建人id
     */
    private Long createUserId;
    /**
     * 更新人id
     */
    private Long updaeUserId;
    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;
    /**
     * 更新时间
     */
    private LocalDateTime gmtModified;

}
