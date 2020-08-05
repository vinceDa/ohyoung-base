package com.ohyoung.generator.repository;

import com.ohyoung.generator.domain.GeneratorSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author ohYoung
 * @date 2020/7/27 22:38
 */
public interface GeneratorSettingRepository extends JpaRepository<GeneratorSetting, Long>, JpaSpecificationExecutor {


    /**
     *  根据表名获取配置信息
     * @param tableName 表名
     * @return 配置信息
     */
    GeneratorSetting getByTableName(String tableName);
}
