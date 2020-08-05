package com.ohyoung.generator.service;

import com.ohyoung.generator.domain.GeneratorSetting;

/**
 * @author ohYoung
 * @date 2020/7/29 0:23
 */
public interface GeneratorSettingService {

    /**
     *  新增配置
     * @param setting 配置信息
     * @return 操作结果
     */
    GeneratorSetting insert(GeneratorSetting setting);
    /**
     *  更新配置
     * @param setting 配置信息
     * @return 操作结果
     */
    GeneratorSetting update(GeneratorSetting setting);

    /**
     *  根据表名获取配置信息
     * @param tableName 表名
     * @return 配置信息
     */
    GeneratorSetting getByTableName(String tableName);

}
