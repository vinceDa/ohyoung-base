package com.ohyoung.storage.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author vince
 * @date 2020/05/15 02:21:57
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileClassificationVO {

/**
 *  主键id
 */
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
}
