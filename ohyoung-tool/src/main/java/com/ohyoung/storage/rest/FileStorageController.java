package com.ohyoung.storage.rest;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.ImmutableMap;
import com.ohyoung.exception.RequestValidationFailedException;
import com.ohyoung.storage.domain.FileStorage;
import com.ohyoung.storage.domain.dto.FileStorageDTO;
import com.ohyoung.storage.domain.query.FileStorageQueryCriteria;
import com.ohyoung.storage.domain.vo.FileStorageVO;
import com.ohyoung.storage.service.FileStorageService;
import com.ohyoung.util.MinioUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * @author Hey4Ace
 * @date 2020/05/09 10:26:53
 */
@Slf4j
@ApiOperation("文件存储: 文件管理")
@Controller
@RequestMapping("/api/v1/tool")
public class FileStorageController {

    @Autowired
    private FileStorageService fileStorageService;

    @ApiOperation(value = "获取文件下载链接")
    @GetMapping(value = "/fileStorage/download/{id}")
    public ResponseEntity<Object> download(@PathVariable Long id) {
        FileStorageDTO byId = fileStorageService.getById(id);
        String downloadUrl = MinioUtil.getPreSignedUrl(byId.getOriginFileName());
        if (StrUtil.isEmpty(downloadUrl)) {
            throw new RequestValidationFailedException(ImmutableMap.of("errorMessage", "获取下载链接失败"));
        }
        return ResponseEntity.ok(downloadUrl);
    }

    @ApiOperation(value = "查询所有文件")
    @GetMapping(value = "/fileStorage")
    public ResponseEntity<Object> listAll(FileStorageQueryCriteria fileStorageQueryCriteria) {
        List<FileStorageDTO> fileStorageDTOS;
        if (BeanUtil.isEmpty(fileStorageQueryCriteria)) {
            fileStorageDTOS = fileStorageService.listAll();
        } else {
            fileStorageDTOS = fileStorageService.listAll(fileStorageQueryCriteria.toSpecification());
        }
        List<FileStorageVO> convert = Convert.convert(new TypeReference<List<FileStorageVO>>() {
        }, fileStorageDTOS);
        return ResponseEntity.ok(convert);
    }

    @ApiOperation(value = "分页查询文件")
    @GetMapping(value = "/fileStorage/paging")
    public ResponseEntity<Object> listForPage(FileStorageQueryCriteria fileStorageQueryCriteria, Pageable pageable) {
        List<FileStorage> fileStorageList;
        if (BeanUtil.isEmpty(fileStorageQueryCriteria)) {
            fileStorageList = fileStorageService.listPageable(pageable).getContent();
        } else {
            fileStorageList = fileStorageService.listPageable(fileStorageQueryCriteria.toSpecification(), pageable).getContent();
        }
        List<FileStorageVO> convert = Convert.toList(FileStorageVO.class, fileStorageList);
        return ResponseEntity.ok(convert);
    }

    @ApiOperation(value = "上传文件")
    @PostMapping(value = "/fileStorage")
    public ResponseEntity<Object> insert(MultipartFile file) {
        if (file == null) {
            throw new RequestValidationFailedException(ImmutableMap.of("errorMessage", "文件不能为空"));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(fileStorageService.insert(file));
    }

    @ApiOperation(value = "更新文件")
    @PutMapping(value = "/fileStorage")
    public ResponseEntity<Object> put(@Validated(FileStorageDTO.Update.class) @RequestBody FileStorageDTO fileStorageDTO) {
        fileStorageService.update(fileStorageDTO);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "删除文件")
    @DeleteMapping(value = "/fileStorage/{id}")
    public ResponseEntity<Object> delete(@NotNull @PathVariable Long id) {
        fileStorageService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
