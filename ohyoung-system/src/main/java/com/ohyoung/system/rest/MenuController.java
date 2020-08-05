package com.ohyoung.system.rest;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.StrUtil;
import com.ohyoung.system.domain.dto.MenuDTO;
import com.ohyoung.system.domain.dto.RoleDTO;
import com.ohyoung.system.domain.dto.UserDTO;
import com.ohyoung.system.domain.query.MenuQueryCriteria;
import com.ohyoung.system.domain.vo.MenuVO;
import com.ohyoung.system.domain.vo.RoleVO;
import com.ohyoung.system.domain.vo.UserVO;
import com.ohyoung.system.service.MenuService;
import com.ohyoung.system.service.UserService;
import com.ohyoung.util.SecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * @author vince
 */
@Slf4j
@Api(tags = "系统管理: 菜单管理")
@Controller
@RequestMapping("api/v1/system")
public class MenuController {

    @Autowired
    private UserService userService;

    @Autowired
    private MenuService menuService;

    @ApiOperation(value = "查询所有菜单")
    @GetMapping(value = "/menus")
    @PreAuthorize("hasAuthority('menu_all')")
    public ResponseEntity<Object> listAll(MenuQueryCriteria menuQueryCriteria) {
        Long userId = SecurityUtil.getJwtUserId();
        menuQueryCriteria.setCreateBy(userId);
        UserVO byId = userService.getById(userId);
        Set<RoleVO> roles = byId.getRoles();
        Iterator<RoleVO> iterator = roles.iterator();
        Set<MenuVO> totalMenu = new HashSet<>();
        while (iterator.hasNext()) {
            RoleVO next = iterator.next();
            totalMenu.addAll(next.getMenus());
        }
        // 根据筛选条件剔除不符合条件的菜单
        String blurry = menuQueryCriteria.getBlurry();
        Boolean show = menuQueryCriteria.getShow();
        if (StrUtil.isNotEmpty(blurry)) {
            totalMenu = totalMenu.stream().filter(single -> single.getName().contains(blurry)).collect(Collectors.toSet());
        }
        if (show != null) {
            totalMenu = totalMenu.stream().filter(single -> single.getShow().equals(show)).collect(Collectors.toSet());
        }
        List<MenuDTO> menuDTOS = Convert.convert(new TypeReference<List<MenuDTO>>() {
        }, totalMenu);
        List<MenuVO> convert = Convert.convert(new TypeReference<List<MenuVO>>() {
        }, menuService.buildMenuTree(menuDTOS));
        return ResponseEntity.ok(convert);
    }

    @ApiOperation(value = "生成菜单树")
    @GetMapping(value = "/menus/tree")
    @PreAuthorize("hasAuthority('menu_tree')")
    public ResponseEntity<Object> buildMenuTree(MenuQueryCriteria menuQueryCriteria) {
        Long userId = SecurityUtil.getJwtUserId();
        menuQueryCriteria.setCreateBy(userId);
        UserVO byId = userService.getById(userId);
        Set<RoleVO> roles = byId.getRoles();
        Iterator<RoleVO> iterator = roles.iterator();
        Set<MenuVO> totalMenu = new HashSet<>();
        while (iterator.hasNext()) {
            RoleVO next = iterator.next();
            totalMenu.addAll(next.getMenus());
        }
        List<MenuDTO> menuDTOS = Convert.convert(new TypeReference<List<MenuDTO>>() {
        }, totalMenu);
        // 过滤掉所有按钮级别的菜单
        menuDTOS = menuDTOS.stream().filter(single -> !"2".equals(single.getType())).collect(Collectors.toList());
        List<MenuVO> convert = Convert.convert(new TypeReference<List<MenuVO>>() {
        }, menuService.buildMenuTree(menuDTOS));
        return ResponseEntity.ok(convert);
    }

    @ApiOperation(value = "新增菜单")
    @PostMapping(value = "/menus")
    @PreAuthorize("hasAuthority('menu_add')")
    public ResponseEntity<Object> insert(@Validated(MenuDTO.Insert.class) @RequestBody MenuDTO menuDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(menuService.insert(menuDTO));
    }

    @ApiOperation(value = "修改菜单")
    @PutMapping(value = "/menus")
    @PreAuthorize("hasAuthority('menu_update')")
    public ResponseEntity<Object> put(@Validated(MenuDTO.Update.class) @RequestBody MenuDTO menuDTO) {
        menuService.update(menuDTO);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "删除菜单")
    @DeleteMapping(value = "/menus/{id}")
    @PreAuthorize("hasAuthority('menu_delete')")
    public ResponseEntity<Object> delete(@NotNull @PathVariable Long id) {
        menuService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}

