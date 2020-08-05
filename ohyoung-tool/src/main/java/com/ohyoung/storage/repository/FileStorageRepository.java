package com.ohyoung.storage.repository;

import com.ohyoung.storage.domain.FileStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author Hey4Ace
* @date 2020/05/09 10:26:53
*/
public interface FileStorageRepository extends JpaRepository<FileStorage, Long>, JpaSpecificationExecutor {


}
