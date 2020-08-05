package com.ohyoung.quartz.repository;

import com.ohyoung.quartz.domain.QuartzTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author vince
* @date 2020/02/02 07:24:34
*/
public interface QuartzTaskRepository extends JpaRepository<QuartzTask, Long>, JpaSpecificationExecutor {


}
