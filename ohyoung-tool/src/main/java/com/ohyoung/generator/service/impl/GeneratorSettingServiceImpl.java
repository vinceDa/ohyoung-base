package com.ohyoung.generator.service.impl;

import com.ohyoung.generator.domain.GeneratorSetting;
import com.ohyoung.generator.repository.GeneratorSettingRepository;
import com.ohyoung.generator.service.GeneratorSettingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author ohYoung
 * @date 2020/7/29 0:25
 */
@Slf4j
@Service
public class GeneratorSettingServiceImpl implements GeneratorSettingService {

    @Resource
    private GeneratorSettingRepository settingRepository;

    @Override
    public GeneratorSetting insert(GeneratorSetting setting) {
        return settingRepository.save(setting);
    }

    @Override
    public GeneratorSetting update(GeneratorSetting setting) {
        return settingRepository.save(setting);
    }

    @Override
    public GeneratorSetting getByTableName(String tableName) {
        return settingRepository.getByTableName(tableName);
    }
}
