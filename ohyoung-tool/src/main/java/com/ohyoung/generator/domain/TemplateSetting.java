package com.ohyoung.generator.domain;

import lombok.Data;

/**
 *  模板默认配置, 对应Config.json
 * @author ohYoung
 */
@Data
public class TemplateSetting {

    /**
     *  模板名称
     */
    private String templateName;

    /**
     *  是否为前端文件
     */
    private Boolean view;

    /**
     *  文件名, 例如前端页面生成的名字固定为index
     */
    private String filename;

    /**
     *  生成文件所在包名
     */
    private String packageName;

    /**
     *  文件名后缀
     */
    private String suffix;

    /**
     *  文件类型
     */
    private String fileType;

}
