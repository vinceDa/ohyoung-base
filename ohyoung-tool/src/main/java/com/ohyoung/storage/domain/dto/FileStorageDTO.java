package com.ohyoung.storage.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author Hey4Ace
 * @date 2020/05/09 10:26:53
 */
@Data
public class FileStorageDTO {

    /**
     *  主键id
     */
    @NotNull(groups = Update.class, message = "id不能为空")
    private Long id;
    /**
     *  文件类型id
     */
    private Long classificationId;
    /**
     *  文件名
     */
    private String name;
    /**
     *  文件类型
     */
    private String type;
    /**
     *  存储地址
     */
    private String originFileName;
    /**
     *  文件大小
     */
    private String size;
    /**
     *  创建人id
     */
    private Long createUserId;
    /**
     *  更新人id
     */
    private Long updateUserId;
    /**
     *  创建时间
     */
    private LocalDateTime gmtCreate;
    /**
     *  更新时间
     */
    private LocalDateTime gmtModified;

    public interface Insert{};

    public interface Update{};
}
