package com.ohyoung.generator.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollectionUtil;
import com.ohyoung.generator.util.GeneratorUtil;
import com.ohyoung.generator.domain.*;
import com.ohyoung.generator.repository.GeneratorColumnRepository;
import com.ohyoung.generator.service.GeneratorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author vince
 * @date 2019/12/30 20:13
 */
@Slf4j
@Service
public class GeneratorServiceImpl implements GeneratorService {

    @PersistenceContext
    private EntityManager entityManager;

    @Resource
    private GeneratorColumnRepository columnRepository;



    @Override
    public Boolean generateCode(List<String> tables, GeneratorSetting setting) {
        try {
            for (String table : tables) {
                List<GeneratorColumn> columns = listColumns(table);
                GeneratorUtil.generateCode(columns, setting);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public String download() {
        return null;
    }

    @Override
    public List<String> listTables() {
        String sql = "select table_name from information_schema.tables where table_schema = (select database()) order by create_time desc";
        Query nativeQuery = entityManager.createNativeQuery(sql);
        return nativeQuery.getResultList();
    }

    @Override
    public Boolean sync(String tableName) {
        return sync(listOriginColumns(tableName), listColumns(tableName));
    }

    @Override
    public List<GeneratorColumn> listOriginColumns(String tableName) {
        String sql = "select column_name, is_nullable, column_type, column_comment " +
                "from information_schema.columns where table_schema = (select database()) and table_name = ? order by ordinal_position";
        Query nativeQuery = entityManager.createNativeQuery(sql);
        nativeQuery.setParameter(1, tableName);
        List resultList = nativeQuery.getResultList();
        List<GeneratorColumn> columns = new ArrayList<>();
        for (Object object : resultList) {
            Object[] arr = (Object[]) object;
            columns.add(
                    new GeneratorColumn(tableName,
                    arr[0].toString(),
                    "YES".equals(arr[1].toString()),
                    arr[2].toString(),
                    arr[3].toString())
            );
        }
        return columns;
    }

    @Override
    public List<GeneratorColumn> listColumns(String tableName) {
        List<GeneratorColumn> columns = columnRepository.findAllByTableName(tableName);
        if (CollectionUtil.isEmpty(columns)) {
            List<GeneratorColumn> originColumns = listOriginColumns(tableName);
            return columnRepository.saveAll(originColumns);
        }
        return columns;
    }

    @Override
    public List<GeneratorSetting> listSettings(String tableName) {

        return null;
    }

    /**
     *  创建了一个字段表来存储字段的配置信息用于在生成代码时作为的依据,
     *  由于字段可能会修改, 所以使用这个方法让该表与数据库同步
     *  1. 字段表中不存在原始表的字段, 需要新增
     *  2. 字段表中字段信息呵原始表不同, 需要修改
     *  3. 字段表中存在的字段在原始表中不存在, 需要删除
     */
    private Boolean sync(List<GeneratorColumn> originColumns, List<GeneratorColumn> generatorColumns) {
        // 字段新增或者修改
        for (GeneratorColumn originColumn : originColumns) {
            List<GeneratorColumn> collect = generatorColumns.stream()
                    .filter(single -> single.getColumnName().equals(originColumn.getColumnName())).collect(Collectors.toList());
            // 字段存在
            if (CollectionUtil.isNotEmpty(collect)) {
                GeneratorColumn singleOriginColumn = collect.get(0);
                // 字段信息和原字段信息不一致则更新
                if (!singleOriginColumn.equals(originColumn)) {
                    CopyOptions copyOptions = new CopyOptions();
                    copyOptions.setIgnoreProperties("id");
                    BeanUtil.copyProperties(singleOriginColumn, originColumn, copyOptions);
                    columnRepository.save(originColumn);
                }
            } else {
                // 字段不存在则新增
                columnRepository.save(originColumn);
            }
        }

        // 字段删除
        for (GeneratorColumn generatorColumn : generatorColumns) {
            List<GeneratorColumn> collect = generatorColumns.stream()
                    .filter(single -> single.getColumnName().equals(generatorColumn.getColumnName())).collect(Collectors.toList());
            // 如果字段在原表不存在则删除
            if (CollectionUtil.isEmpty(collect)) {
                columnRepository.delete(generatorColumn);
            }
        }
        return true;
    }

}
