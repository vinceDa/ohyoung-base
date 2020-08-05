package com.ohyoung.system.repository;

import com.ohyoung.system.domain.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author vince
 */
public interface DepartmentRepository extends JpaRepository<Department, Long>, JpaSpecificationExecutor {

    /**
     *  根据部门名查询部门信息
     * @param name 部门名
     * @return 部门信息
     */
    Department getByName(String name);

}
