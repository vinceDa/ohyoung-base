package com.ohyoung.system.rest;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.ohyoung.system.domain.Role;
import com.ohyoung.system.domain.dto.RoleDTO;
import com.ohyoung.system.domain.query.RoleQueryCriteria;
import com.ohyoung.system.domain.vo.RoleVO;
import com.ohyoung.system.service.RoleService;
import com.ohyoung.util.SecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * @author vince
 */
@Slf4j
@Api(tags = "系统管理: 角色管理")
@Controller
@RequestMapping("api/v1/system")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "分页查询角色")
    @GetMapping(value = "/roles/paging")
    @PreAuthorize("hasAuthority('role_list')")
    public ResponseEntity<Object> listRoleForPage(RoleQueryCriteria roleQueryCriteria, Pageable pageable) {
        Long userId = SecurityUtil.getJwtUserId();
        roleQueryCriteria.setCreateBy(userId);
        Page<Role> roles;
        if (BeanUtil.isEmpty(roleQueryCriteria)) {
            roles = roleService.listPageable(pageable);
        } else {
            roles = roleService.listPageable(roleQueryCriteria.toSpecification(), pageable);
        }
        List<Role> content = roles.getContent();
        List<RoleVO> convert = Convert.convert(new TypeReference<List<RoleVO>>() {
        }, content);
        Page<RoleVO> roleVOPage = new PageImpl<>(convert, PageRequest.of(roles.getNumber(), roles.getSize()), roles.getTotalElements());
        return ResponseEntity.ok(roleVOPage);
    }

    @ApiOperation(value = "查询所有角色")
    @GetMapping(value = "/roles/all")
    @PreAuthorize("hasAuthority('role_all')")
    public ResponseEntity<Object> listAll(RoleQueryCriteria roleQueryCriteria) {
        Long userId = SecurityUtil.getJwtUserId();
        roleQueryCriteria.setCreateBy(userId);
        List<RoleDTO> roleDTOS;
        if (BeanUtil.isEmpty(roleQueryCriteria)) {
            roleDTOS = roleService.listAll();
        } else {
            roleDTOS = roleService.listAll(roleQueryCriteria.toSpecification());
        }
        List<RoleVO> convert = Convert.convert(new TypeReference<List<RoleVO>>() {
        }, roleDTOS);
        return ResponseEntity.ok(convert);
    }

    @ApiOperation(value = "新增角色")
    @PostMapping(value = "/roles")
    @PreAuthorize("hasAuthority('role_add')")
    public ResponseEntity<Object> insert(@Validated(RoleDTO.Insert.class) @RequestBody RoleDTO roleDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roleService.insert(roleDTO));
    }

    @ApiOperation(value = "修改角色")
    @PutMapping(value = "/roles")
    @PreAuthorize("hasAuthority('role_update')")
    public ResponseEntity<Object> put(@Validated(RoleDTO.Update.class) @RequestBody RoleDTO roleDTO) {
        roleService.update(roleDTO);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "删除角色")
    @DeleteMapping(value = "/roles/{id}")
    @PreAuthorize("hasAuthority('role_delete')")
    public ResponseEntity<Object> delete(@NotNull @PathVariable Long id) {
        roleService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ApiOperation(value = "更新角色与菜单的关联关系")
    @PutMapping(value = "/roles/menus")
    @PreAuthorize("hasAuthority('role_menu_update')")
    public ResponseEntity<Object> updateRoleMenus(@Validated(RoleDTO.Update.class) @RequestBody RoleDTO roleDTO) {
        roleService.updateRoleMenus(roleDTO);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
