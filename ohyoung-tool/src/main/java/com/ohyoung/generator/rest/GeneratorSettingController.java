package com.ohyoung.generator.rest;

import com.ohyoung.generator.domain.GeneratorSetting;
import com.ohyoung.generator.service.GeneratorSettingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;


/**
 *  配置信息
 * @author vince
 */
@Slf4j
@Controller
@RequestMapping("api/v1/tool/generate")
public class GeneratorSettingController {

    @Autowired
    private GeneratorSettingService settingService;

    @PostMapping("/settings")
    public ResponseEntity<Object> insert(@RequestBody GeneratorSetting generatorSetting) {
        return ResponseEntity.ok(settingService.insert(generatorSetting));
    }

    @PutMapping("/settings")
    public ResponseEntity<Object> update(@RequestBody GeneratorSetting generatorSetting) {
        return ResponseEntity.ok(settingService.update(generatorSetting));
    }

    @GetMapping("/table/{tableName}/settings")
    public ResponseEntity<Object> getSettingByTableName(@NotNull @PathVariable String tableName) {
        return ResponseEntity.ok(settingService.getByTableName(tableName));
    }
}
