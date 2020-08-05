package com.ohyoung.quartz.domain;

import com.ohyoung.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author vince
 * @date 2020/02/02 07:24:34
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "quartz_task")
public class QuartzTask extends BaseEntity implements Serializable {

    /**
     *  主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 任务名
     */
    @Column(name = "name")
    private String name;
    /**
     * 类路径, 任务执行时调用哪个类的方法 包名+类名，完全限定名
     */
    @Column(name = "class_path")
    private String classPath;
    /**
     * cron表达式
     */
    @Column(name = "cron_expression")
    private String cronExpression;
    /**
     * 任务调用的方法名
     */
    @Column(name = "method_name")
    private String methodName;
    /**
     * 触发器名称
     */
    @Column(name = "trigger_name")
    private String triggerName;
    /**
     * 描述
     */
    @Column(name = "description")
    private String description;
    /**
     * 任务状态
     */
    @Column(name = "is_enable")
    private Boolean enable;
}
