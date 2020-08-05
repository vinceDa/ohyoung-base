package com.ohyoung.generator.repository;

import com.ohyoung.generator.domain.GeneratorColumn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author ohYoung
 * @date 2020/7/27 22:38
 */
public interface GeneratorColumnRepository extends JpaRepository<GeneratorColumn, Long>, JpaSpecificationExecutor {


    /**
     *  根据表名获取字段信息
     * @param tableName 表名
     * @return 字段信息
     */
    List<GeneratorColumn> findAllByTableName(String tableName);
}
