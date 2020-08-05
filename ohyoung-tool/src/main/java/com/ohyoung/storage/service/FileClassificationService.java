package com.ohyoung.storage.service;

import com.ohyoung.storage.domain.FileClassification;
import com.ohyoung.storage.domain.dto.FileClassificationDTO;
import com.ohyoung.storage.domain.query.FileClassificationQueryCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * @author vince
 * @date 2020/05/15 02:21:57
 */
public interface FileClassificationService {

    /**
     *  返回所有的FileClassification信息
     * @return  返回所有的FileClassification信息
     */
    List<FileClassificationDTO> listAll();

    /**
     *  根据条件查询FileClassification信息
     * @param specification 查询条件实体类
     * @return 符合条件的FileClassification信息集合
     */
    List<FileClassificationDTO> listAll(Specification<FileClassificationQueryCriteria> specification);

    /**
     *  分页查询FileClassification信息
     * @param pageable 分页参数实体类
     * @return 符合条件的FileClassification信息集合
     */
    Page<FileClassification> listPageable(Pageable pageable);

    /**
     *  分页查询FileClassification信息
     * @param specification 查询条件
     * @param pageable 分页参数实体类
     * @return 符合条件的FileClassification信息集合
     */
    Page<FileClassification> listPageable(Specification<FileClassificationQueryCriteria> specification, Pageable pageable);

    /**
     *  根据FileClassificationid获取FileClassification信息
     * @param id  FileClassificationid
     * @return 传入id对应的FileClassification信息
     */
    FileClassificationDTO getById(Long id);

    /**
     *  新增FileClassification
     * @param fileClassificationDTO FileClassification信息实体
     * @return 新增成功实体类
     */
    FileClassificationDTO insert(FileClassificationDTO fileClassificationDTO);

    /**
     *  删除FileClassification
     * @param id FileClassificationid
     */
    void delete(Long id);

    /**
     *  更新FileClassification
     * @param fileClassificationDTO 需要更新的FileClassification信息
     * @return 更新成功的实体类
     */
    FileClassificationDTO update(FileClassificationDTO fileClassificationDTO);
}