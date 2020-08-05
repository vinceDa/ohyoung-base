package com.ohyoung.storage.repository;

import com.ohyoung.storage.domain.FileClassification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author vince
* @date 2020/05/15 02:21:57
*/
public interface FileClassificationRepository extends JpaRepository<FileClassification, Long>, JpaSpecificationExecutor {


}
