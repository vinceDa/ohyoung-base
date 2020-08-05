package com.ohyoung.storage.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.ohyoung.storage.domain.FileClassification;
import com.ohyoung.storage.domain.dto.FileClassificationDTO;
import com.ohyoung.storage.domain.query.FileClassificationQueryCriteria;
import com.ohyoung.storage.repository.FileClassificationRepository;
import com.ohyoung.storage.service.FileClassificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @author vince
 * @date 2020/05/15 02:21:57
 */
@Slf4j
@Service
public class FileClassificationServiceImpl implements FileClassificationService {

    @Resource
    private FileClassificationRepository fileClassificationRepository;

    @Override
    public List<FileClassificationDTO> listAll() {
        List<FileClassification> all = fileClassificationRepository.findAll();
        log.info("listAll, size: {}", all.size());
        return Convert.convert(new TypeReference<List<FileClassificationDTO>>() {}, all);
    }

    @Override
    public List<FileClassificationDTO> listAll(Specification<FileClassificationQueryCriteria> specification) {
        List<FileClassificationDTO> all = fileClassificationRepository.findAll(specification);
        log.info("listAllWithSpecification, size: {}", all.size());
        return Convert.convert(new TypeReference<List<FileClassificationDTO>>() {}, all);
    }

    @Override
    public Page<FileClassification> listPageable(Pageable pageable) {
        Page<FileClassification> page = fileClassificationRepository.findAll(pageable);
        List<FileClassification> content = page.getContent();
        log.info("listAllWithPageable, size: {}", content.size());
        return page;
    }

    @Override
    public Page<FileClassification> listPageable(Specification<FileClassificationQueryCriteria> specification, Pageable pageable) {
        Page<FileClassification> page = fileClassificationRepository.findAll(specification, pageable);
        List<FileClassification> content = page.getContent();
        log.info("listAllWithPageable, size: {}", content.size());
        return page;
    }

    @Override
    public FileClassificationDTO getById(Long id) {
        FileClassification one = fileClassificationRepository.getOne(id);
        log.info("getById, id: {}, one: {}", id, one.toString());
        return Convert.convert(FileClassificationDTO.class, one);
    }

    @Override
    public FileClassificationDTO insert(FileClassificationDTO fileClassificationDTO) {
        FileClassification convert = Convert.convert(FileClassification.class, fileClassificationDTO);
        FileClassification save = fileClassificationRepository.save(convert);
        log.info("insert, save: {}", save.toString());
        return Convert.convert(FileClassificationDTO.class, save);
    }

    @Override
    public void delete(Long id) {
        if (!fileClassificationRepository.existsById(id)) {
            log.error("delete fileClassification error: unknown id");
        }
        fileClassificationRepository.deleteById(id);
    }

    @Override
    public FileClassificationDTO update(FileClassificationDTO fileClassificationDTO) {
        Optional<FileClassification> byId = fileClassificationRepository.findById(fileClassificationDTO.getId());
        if (!byId.isPresent()) {
            log.error("update fileClassification error: unknown id");
        }
        FileClassification one = byId.get();
        FileClassification convert = Convert.convert(FileClassification.class, fileClassificationDTO);
        CopyOptions copyOptions = new CopyOptions();
        copyOptions.ignoreNullValue();
        BeanUtil.copyProperties(convert, one, copyOptions);
        FileClassification save = fileClassificationRepository.save(one);
        log.info("update, save: {}", save.toString());
        return Convert.convert(FileClassificationDTO.class, save);
    }

}
