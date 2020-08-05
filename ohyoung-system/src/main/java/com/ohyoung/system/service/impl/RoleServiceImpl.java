package com.ohyoung.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.ImmutableMap;
import com.ohyoung.exception.RequestValidationFailedException;
import com.ohyoung.system.domain.Menu;
import com.ohyoung.system.domain.Role;
import com.ohyoung.system.domain.dto.RoleDTO;
import com.ohyoung.system.domain.query.RoleQueryCriteria;
import com.ohyoung.system.repository.RoleRepository;
import com.ohyoung.system.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author vince
 * @date 2019/10/11 15:29
 */
@Slf4j
@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleRepository roleRepository;

    @Override
    public List<RoleDTO> listAll() {
        List<Role> all = roleRepository.findAll();
        log.info("listAll, size: {}", all.size());
        return Convert.convert(new TypeReference<List<RoleDTO>>() {}, all);
    }

    @Override
    public List<RoleDTO> listAll(Specification specification) {
        List<Role> all = roleRepository.findAll(specification);
        log.info("listAllWithSpecification, size: {}", all.size());
        return Convert.convert(new TypeReference<List<RoleDTO>>() {}, all);
    }

    @Override
    public Page<Role> listPageable(Pageable pageable) {
        Page<Role> all = roleRepository.findAll(pageable);
        List<Role> content = all.getContent();
        log.info("listAllWithPageable, size: {}", content.size());
        return all;
    }

    @Override
    public Page<Role> listPageable(Specification<RoleQueryCriteria> specification, Pageable pageable) {
        Page<Role> all = roleRepository.findAll(specification, pageable);
        List content = all.getContent();
        log.info("listAllWithSpecificationAndPageable, size: {}", content.size());
        return all;
    }

    @Override
    public RoleDTO getById(Long id) {
        Role one = roleRepository.getOne(id);
        log.info("getById, id: {}, one: {}", id, one.toString());
        return Convert.convert(RoleDTO.class, one);
    }

    @Override
    public RoleDTO getByName(String name) {
        Role byName = roleRepository.getByName(name);
        log.info("getByName, name: {}, byName: {}", name, byName.toString());
        return Convert.convert(RoleDTO.class, byName);
    }

    @Override
    public RoleDTO insert(RoleDTO roleDTO) {
        Role byName = roleRepository.getByName(roleDTO.getName());
        if (byName != null) {
            log.error("insert convert error: roleName is exist");
            throw new RequestValidationFailedException(ImmutableMap.of("errorMessage", "角色名已存在"));
        }
        Role convert = Convert.convert(Role.class, roleDTO);
        Role save = roleRepository.save(convert);
        log.info("insert, res: {}", save.toString());
        return Convert.convert(RoleDTO.class, save);
    }

    @Override
    public void delete(Long id) {
        if (!roleRepository.existsById(id)) {
            log.error("delete role error: unknown id");
            throw new RequestValidationFailedException(ImmutableMap.of("errorMessage", "请选择角色进行删除"));
        }
        roleRepository.deleteById(id);
    }

    @Override
    public RoleDTO update(RoleDTO roleDTO) {
        Optional<Role> byId = roleRepository.findById(roleDTO.getId());
        if (!byId.isPresent()) {
            log.error("update convert error: unknown id");
            throw new RequestValidationFailedException(ImmutableMap.of("errorMessage", "请选择角色进行编辑"));
        }
        if (StrUtil.isNotBlank(roleDTO.getName())) {
            Role byName = roleRepository.getByName(roleDTO.getName());
            if (byName != null && !byName.getId().equals(roleDTO.getId())) {
                log.error("update convert error: roleName is exist");
                throw new RequestValidationFailedException(ImmutableMap.of("errorMessage", "角色名已存在"));
            }
        }
        Role one = byId.get();
        Role convert = Convert.convert(Role.class, roleDTO);
        CopyOptions copyOptions = new CopyOptions();
        copyOptions.ignoreNullValue();
        BeanUtil.copyProperties(convert, one, copyOptions);
        Role save = roleRepository.save(one);
        log.info("update, res: {}", save.toString());
        return Convert.convert(RoleDTO.class, save);
    }

    @Override
    public RoleDTO updateRoleMenus(RoleDTO roleDTO) {
        Role one = roleRepository.getOne(roleDTO.getId());
        one.setMenus(Convert.convert(new TypeReference<Set<Menu>>() {}, roleDTO.getMenus()));
        Role save = roleRepository.save(one);
        log.info("updateRoleMenus, res: {}", save.toString());
        return Convert.convert(RoleDTO.class, save);
    }
}
