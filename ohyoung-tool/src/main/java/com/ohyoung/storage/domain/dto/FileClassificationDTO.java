package com.ohyoung.storage.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author vince
 * @date 2020/05/15 02:21:57
 */
@Data
public class FileClassificationDTO {

    /**
     *  主键id
     */
    @NotNull(groups = Update.class, message = "id不能为空")
    private Long id;
    /**
     *  文件类型名称
     */
    private String name;
    /**
     *  创建时间
     */
    private LocalDateTime gmtCreate;
    /**
     *  创建人
     */
    private Long createBy;
    /**
     *  更新时间
     */
    private LocalDateTime gmtModified;
    /**
     *  更新人
     */
    private Long updateBy;

    public interface Insert{};

    public interface Update{};
}
