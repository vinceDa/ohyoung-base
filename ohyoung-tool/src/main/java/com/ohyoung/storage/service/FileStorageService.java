package com.ohyoung.storage.service;

import com.ohyoung.storage.domain.FileStorage;
import com.ohyoung.storage.domain.dto.FileStorageDTO;
import com.ohyoung.storage.domain.query.FileStorageQueryCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Hey4Ace
 * @date 2020/05/09 10:26:53
 */
public interface FileStorageService {

    /**
     *  返回所有的FileStorage信息
     * @return  返回所有的FileStorage信息
     */
    List<FileStorageDTO> listAll();

    /**
     *  根据条件查询FileStorage信息
     * @param specification 查询条件实体类
     * @return 符合条件的FileStorage信息集合
     */
    List<FileStorageDTO> listAll(Specification<FileStorageQueryCriteria> specification);

    /**
     *  分页查询FileStorage信息
     * @param pageable 分页参数实体类
     * @return 符合条件的FileStorage信息集合
     */
    Page<FileStorage> listPageable(Pageable pageable);

    /**
     *  分页查询FileStorage信息
     * @param specification 查询条件
     * @param pageable 分页参数实体类
     * @return 符合条件的FileStorage信息集合
     */
    Page<FileStorage> listPageable(Specification<FileStorageQueryCriteria> specification, Pageable pageable);

    /**
     *  根据FileStorageid获取FileStorage信息
     * @param id  FileStorageid
     * @return 传入id对应的FileStorage信息
     */
    FileStorageDTO getById(Long id);

    /**
     *  新增FileStorage
     * @param file 文件
     * @return 新增成功实体类
     */
    FileStorageDTO insert(MultipartFile file);

    /**
     *  删除FileStorage
     * @param id FileStorageid
     */
    void delete(Long id);

    /**
     *  更新FileStorage
     * @param fileStorageDTO 需要更新的FileStorage信息
     * @return 更新成功的实体类
     */
    FileStorageDTO update(FileStorageDTO fileStorageDTO);
}