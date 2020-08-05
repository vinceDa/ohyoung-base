package com.ohyoung.generator.domain;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 *  生成配置, 用于配置生成位置, 生成注释等信息
 * @author ohYoung
 */
@Getter
@Setter
@Entity
@Table(name = "generator_setting")
public class GeneratorSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "table_name")
    private String tableName;

    /**
     *  作者名
     */
    @Column(name = "author_name")
    private String authorName;

    /**
     *  模块名
     */
    @Column(name = "module_name")
    private String moduleName;

    /**
     *  包名
     */
    @Column(name = "package_name")
    private String packageName;

    /**
     *  接口名前缀, 例如/api/v1/system
     */
    @Column(name = "interface_prefix")
    private String interfacePrefix;

    /**
     *  接口名, 用于controller以及接口文档
     */
    @Column(name = "interface_name")
    private String interfaceName;

    /**
     *  前端项目位置
     */
    @Column(name = "web_root_path")
    private String webRootPath;

    /**
     *  前端代码生成位置
     */
    @Column(name = "web_api_path")
    private String webApiPath;

    /**
     *  是否启用swagger注释
     */
    @Column(name = "use_swagger")
    private Boolean useSwagger;

    /**
     * 是否默认配置, 每个表都要配置很繁琐, 如果没有配置的话就启用默认配置
     */
    @Column(name = "is_default")
    private Boolean defaultSetting;

}
