package com.ohyoung.system.repository;

import com.ohyoung.system.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author vince
 */
public interface RoleRepository extends JpaRepository<Role, Long>, JpaSpecificationExecutor {

    /**
     *  根据角色名查询角色信息
     * @param name 角色名
     * @return 角色信息
     */
    Role getByName(String name);

}
