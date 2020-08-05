package com.ohyoung.generator.rest;

import com.ohyoung.generator.domain.GeneratorSetting;
import com.ohyoung.generator.service.GeneratorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;


/**
 *  代码生成
 * @author vince
 */
@Slf4j
@Controller
@RequestMapping("api/v1/tool/generate")
public class GeneratorController {

    @Autowired
    private GeneratorService generatorService;

    @GetMapping("/tables")
    public ResponseEntity<Object> listTable() {
        return ResponseEntity.ok(generatorService.listTables());
    }

    @GetMapping("/table/{tableName}/fields")
    public ResponseEntity<Object> listFieldByTableName(@NotNull @PathVariable String tableName) {
        return ResponseEntity.ok(generatorService.listColumns(tableName));
    }

    @PostMapping("/code")
    // @PreAuthorize("hasAuthority('connection_list')")
    public ResponseEntity<Object> generateCode(@RequestBody List<String> tables) {
        GeneratorSetting setting = new GeneratorSetting();
        setting.setAuthorName("ohYoung");
        setting.setModuleName("ohyoung-common");
        setting.setPackageName("com.jxnx.app");
        setting.setDefaultSetting(true);
        setting.setInterfacePrefix("/api/v1/test");
        setting.setInterfaceName("tests");
        setting.setUseSwagger(true);
        setting.setWebRootPath("D:\\Work\\WebProjects\\Git\\ououou-web");
        setting.setWebApiPath("tool.test");
        return ResponseEntity.ok(generatorService.generateCode(tables, setting));
    }

}
