package com.ohyoung.storage.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.google.common.collect.ImmutableMap;
import com.ohyoung.exception.RequestValidationFailedException;
import com.ohyoung.storage.domain.FileStorage;
import com.ohyoung.storage.domain.dto.FileStorageDTO;
import com.ohyoung.storage.domain.query.FileStorageQueryCriteria;
import com.ohyoung.storage.repository.FileStorageRepository;
import com.ohyoung.storage.service.FileStorageService;
import com.ohyoung.util.MinioUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author Hey4Ace
 * @date 2020/05/09 10:26:53
 */
@Slf4j
@Service
public class FileStorageServiceImpl implements FileStorageService {

    @Resource
    private FileStorageRepository fileStorageRepository;

    @Override
    public List<FileStorageDTO> listAll() {
        List<FileStorage> all = fileStorageRepository.findAll();
        log.info("listAll, size: {}", all.size());
        return Convert.convert(new TypeReference<List<FileStorageDTO>>() {}, all);
    }

    @Override
    public List<FileStorageDTO> listAll(Specification<FileStorageQueryCriteria> specification) {
        List<FileStorageDTO> all = fileStorageRepository.findAll(specification);
        log.info("listAllWithSpecification, size: {}", all.size());
        return Convert.convert(new TypeReference<List<FileStorageDTO>>() {}, all);
    }

    @Override
    public Page<FileStorage> listPageable(Pageable pageable) {
        Page<FileStorage> page = fileStorageRepository.findAll(pageable);
        List<FileStorage> content = page.getContent();
        log.info("listAllWithPageable, size: {}", content.size());
        return page;
    }

    @Override
    public Page<FileStorage> listPageable(Specification<FileStorageQueryCriteria> specification, Pageable pageable) {
        Page<FileStorage> page = fileStorageRepository.findAll(specification, pageable);
        List<FileStorage> content = page.getContent();
        log.info("listAllWithPageable, size: {}", content.size());
        return page;
    }

    @Override
    public FileStorageDTO getById(Long id) {
        FileStorage one = fileStorageRepository.getOne(id);
        log.info("getById, id: {}, one: {}", id, one.toString());
        return Convert.convert(FileStorageDTO.class, one);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public FileStorageDTO insert(MultipartFile file) {
        FileStorage fileStorage = new FileStorage();
        String originalFilename = file.getOriginalFilename();
        String[] fileArr = originalFilename.split("\\.");
        fileStorage.setName(fileArr[0]);
        fileStorage.setType(fileArr[1]);
        fileStorage.setOriginFileName(originalFilename);
        fileStorage.setSize(String.valueOf(file.getSize()));
        fileStorage.setGmtCreate(LocalDateTime.now());
        fileStorage.setGmtModified(LocalDateTime.now());
        FileStorage save = fileStorageRepository.save(fileStorage);
        MinioUtil.upload(file);
        log.info("insert, save: {}", save.toString());
        return Convert.convert(FileStorageDTO.class, save);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        Optional<FileStorage> byId = fileStorageRepository.findById(id);
        if (!byId.isPresent()) {
            log.error("delete fileStorage error: unknown id");
            throw new RequestValidationFailedException(ImmutableMap.of("errorMessage", "请选择文件删除"));
        }
        fileStorageRepository.deleteById(id);
        MinioUtil.delete(byId.get().getOriginFileName());
    }

    @Override
    public FileStorageDTO update(FileStorageDTO fileStorageDTO) {
        Optional<FileStorage> byId = fileStorageRepository.findById(fileStorageDTO.getId());
        if (!byId.isPresent()) {
            log.error("update fileStorage error: unknown id");
            throw new RequestValidationFailedException(ImmutableMap.of("errorMessage", "请选择文件编辑"));
        }
        fileStorageDTO.setGmtModified(LocalDateTime.now());
        FileStorage one = byId.get();
        FileStorage convert = Convert.convert(FileStorage.class, fileStorageDTO);
        CopyOptions copyOptions = new CopyOptions();
        copyOptions.ignoreNullValue();
        BeanUtil.copyProperties(convert, one, copyOptions);
        one.setClassificationId(fileStorageDTO.getClassificationId());
        FileStorage save = fileStorageRepository.save(one);
        log.info("update, save: {}", save.toString());
        return Convert.convert(FileStorageDTO.class, save);
    }

}
