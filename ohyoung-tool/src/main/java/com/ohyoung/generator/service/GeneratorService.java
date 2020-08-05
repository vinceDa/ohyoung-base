package com.ohyoung.generator.service;


import com.ohyoung.generator.domain.GeneratorColumn;
import com.ohyoung.generator.domain.GeneratorSetting;

import java.util.List;

/**
 * @author vince
 */
public interface GeneratorService {

    /**
     *  生成代码, 包含某个表的数据
     * @param tables 表名集合
     * @param setting 配置项
     * @return 生成结果
     */
    Boolean generateCode(List<String> tables, GeneratorSetting setting);

    /**
     *   代码生成后打包压缩并返回下载地址
     * @return 下载的地址
     */
    String download();

    /**
     *  获取当前数据库所有的表信息
     * @return 表名列表
     */
    List<String> listTables();

    /**
     *  同步对应表的字段信息
     * @param tableName 表名
     * @return 同步结果
     */
    Boolean sync(String tableName);

    /**
     *  根据表名获取information_schema中表字段的信息, 用于同步字段信息
     * @param tableName 表名
     * @return 字段信息
     */
    List<GeneratorColumn> listOriginColumns(String tableName);

    /**
     *  根据表名获取所有的字段信息
     * @param tableName 表名
     * @return 字段信息
     */
    List<GeneratorColumn> listColumns(String tableName);

    /**
     *  根据表名获取配置信息
     * @param tableName 表名
     * @return 配置信息
     */
    List<GeneratorSetting> listSettings(String tableName);

}
