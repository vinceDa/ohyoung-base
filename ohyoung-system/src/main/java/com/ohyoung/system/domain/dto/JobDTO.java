package com.ohyoung.system.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author vince
 * @date 2019/10/14 23:21
 */
@Data
public class JobDTO {

    /**
     *  主键id
     */
    @NotNull(groups = Update.class, message = "id不能为空")
    private Long id;

    /**
     * 岗位名称
     */
    @NotNull(groups = Insert.class, message = "岗位名称不能为空")
    private String name;


    /**
     *  是否启用: 0否, 1是
     */
    @NotNull(groups = Insert.class, message = "岗位状态不能为空")
    private Boolean enable;

    /**
     *  所属部门id
     */
    private Long departmentId;

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
