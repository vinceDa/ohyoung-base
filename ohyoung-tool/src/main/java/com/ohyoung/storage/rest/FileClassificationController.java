package com.ohyoung.storage.rest;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.ohyoung.storage.domain.FileClassification;
import com.ohyoung.storage.domain.dto.FileClassificationDTO;
import com.ohyoung.storage.domain.query.FileClassificationQueryCriteria;
import com.ohyoung.storage.domain.vo.FileClassificationVO;
import com.ohyoung.storage.service.FileClassificationService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * @author vince
 * @date 2020/05/15 02:21:57
 */
@Slf4j
@ApiOperation("文件存储: 文件类型管理")
@Controller
@RequestMapping("/api/v1/tool")
public class FileClassificationController {

    @Autowired
    private FileClassificationService fileClassificationService;

    @ApiOperation(value = "查询所有文件类型")
    @GetMapping(value = "/fileClassifications")
    public ResponseEntity<Object> listAll(FileClassificationQueryCriteria fileClassificationQueryCriteria) {
        List<FileClassificationDTO> fileClassificationDTOS;
        if (BeanUtil.isEmpty(fileClassificationQueryCriteria)) {
            fileClassificationDTOS = fileClassificationService.listAll();
        } else {
            fileClassificationDTOS = fileClassificationService.listAll(fileClassificationQueryCriteria.toSpecification());
        }
        List<FileClassificationVO> convert = Convert.convert(new TypeReference<List<FileClassificationVO>>() {
        }, fileClassificationDTOS);
        return ResponseEntity.ok(convert);
    }

    @ApiOperation(value = "分页查询文件类型")
    @GetMapping(value = "/fileClassifications/paging")
    public ResponseEntity<Object> listForPage(FileClassificationQueryCriteria fileClassificationQueryCriteria, Pageable pageable) {
        List<FileClassification> classifications;
        if (BeanUtil.isEmpty(fileClassificationQueryCriteria)) {
            classifications = fileClassificationService.listPageable(pageable).getContent();
        } else {
            classifications = fileClassificationService.listPageable(fileClassificationQueryCriteria.toSpecification(), pageable).getContent();
        }
        List<FileClassificationVO> convert = Convert.toList(FileClassificationVO.class, classifications);
        return ResponseEntity.ok(convert);
    }

    @ApiOperation(value = "新增文件类型")
    @PostMapping(value = "/fileClassifications")
    public ResponseEntity<Object> insert(@Validated(FileClassificationDTO.Insert.class) @RequestBody FileClassificationDTO fileClassificationDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(fileClassificationService.insert(fileClassificationDTO));
    }

    @ApiOperation(value = "更新文件类型")
    @PutMapping(value = "/fileClassifications")
    public ResponseEntity<Object> put(@Validated(FileClassificationDTO.Update.class) @RequestBody FileClassificationDTO fileClassificationDTO) {
        fileClassificationService.update(fileClassificationDTO);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "删除文件类型")
    @DeleteMapping(value = "/fileClassifications/{id}")
    public ResponseEntity<Object> delete(@NotNull @PathVariable Long id) {
        fileClassificationService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
