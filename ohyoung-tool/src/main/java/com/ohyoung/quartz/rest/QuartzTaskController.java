package com.ohyoung.quartz.rest;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.ohyoung.quartz.domain.dto.QuartzTaskDTO;
import com.ohyoung.quartz.domain.query.QuartzTaskQueryCriteria;
import com.ohyoung.quartz.domain.vo.QuartzTaskVO;
import com.ohyoung.quartz.service.QuartzTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author vince
 * @date 2020/02/02 07:24:34
 */
@Slf4j
@Controller
@Api(tags = "工具: 定时任务")
@RequestMapping("/api/v1/tool/quartz")
public class QuartzTaskController {

    @Autowired
    private QuartzTaskService quartzTaskService;

    @ApiOperation(value = "查询所有定时任务")
    @GetMapping(value = "/tasks")
    public ResponseEntity<Object> listAll(QuartzTaskQueryCriteria quartzTaskQueryCriteria) {
        List<QuartzTaskDTO> quartzTaskDTOS;
        if (BeanUtil.isEmpty(quartzTaskQueryCriteria)) {
            quartzTaskDTOS = quartzTaskService.listAll();
        } else {
            quartzTaskDTOS = quartzTaskService.listAll(quartzTaskQueryCriteria.toSpecification());
        }
        List<QuartzTaskVO> convert = Convert.convert(new TypeReference<List<QuartzTaskVO>>() {}, quartzTaskDTOS);
        return ResponseEntity.ok(convert);
    }

    @ApiOperation(value = "新增定时任务")
    @PostMapping(value = "/tasks")
    public ResponseEntity<Object> insert(HttpServletRequest request,
                                         @Validated(QuartzTaskDTO.Insert.class) @RequestBody QuartzTaskDTO quartzTaskDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(quartzTaskService.insert(quartzTaskDTO));
    }

    @ApiOperation(value = "更新定时任务")
    @PutMapping(value = "/tasks")
    public ResponseEntity<Object> put(HttpServletRequest request,
                                      @Validated(QuartzTaskDTO.Update.class) @RequestBody QuartzTaskDTO quartzTaskDTO) {
        quartzTaskService.update(quartzTaskDTO);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "删除定时任务")
    @DeleteMapping(value = "/tasks/{id}")
    public ResponseEntity<Object> delete(@NotNull @PathVariable Long id) {
        quartzTaskService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ApiOperation(value = "开启定时任务")
    @PostMapping(value = "/tasks/start/{id}")
    public ResponseEntity<Object> start(@NotNull @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(quartzTaskService.startQuartzTask(id));
    }

    @ApiOperation(value = "暂停定时任务")
    @PostMapping(value = "/tasks/pause/{id}")
    public ResponseEntity<Object> pause(@NotNull @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(quartzTaskService.pauseQuartzTask(id));
    }

    @ApiOperation(value = "重启定时任务")
    @PostMapping(value = "/tasks/resume/{id}")
    public ResponseEntity<Object> resume(@NotNull @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(quartzTaskService.resumeQuartzTask(id));
    }
}
