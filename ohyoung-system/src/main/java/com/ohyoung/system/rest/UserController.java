package com.ohyoung.system.rest;

import cn.hutool.core.bean.BeanUtil;
import com.ohyoung.system.domain.dto.UserDTO;
import com.ohyoung.system.domain.query.UserQueryCriteria;
import com.ohyoung.system.domain.vo.UserVO;
import com.ohyoung.system.service.UserService;
import com.ohyoung.util.SecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;


/**
 * @author vince
 */
@Controller
@RequestMapping("api/v1/system")
@Api(tags = "系统管理: 用户管理")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "分页查询用户")
    @GetMapping(value = "/users/paging")
    @PreAuthorize("hasAuthority('user_list')")
    public ResponseEntity<Object> listForPage(UserQueryCriteria userQueryCriteria, Pageable pageable) {
        Long userId = SecurityUtil.getJwtUserId();
        userQueryCriteria.setCreateBy(userId);
        boolean empty = BeanUtil.isEmpty(userQueryCriteria);
        Page<UserVO> users;
        if (empty) {
            users = userService.listPageable(pageable);
        } else {
            users = userService.listPageable(userQueryCriteria.toSpecification(), pageable);
        }
        return ResponseEntity.ok(users);
    }

    @ApiOperation(value = "查看当前用户信息")
    @GetMapping(value = "/user/current")
    public ResponseEntity<Object> getCurrentUser() {
        Long userId = SecurityUtil.getJwtUserId();
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.getById(userId));
    }

    @ApiOperation(value = "新增用户")
    @PostMapping(value = "/users")
    @PreAuthorize("hasAuthority('user_add')")
    public ResponseEntity<Object> insert(@Validated(UserDTO.Insert.class) @RequestBody UserDTO userDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.insert(userDTO));
    }

    @ApiOperation(value = "修改用户")
    @PutMapping(value = "/users")
    @PreAuthorize("hasAuthority('user_update')")
    public ResponseEntity<Object> put(@Validated(UserDTO.Update.class) @RequestBody UserDTO userDTO) {
        userService.update(userDTO);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "删除用户")
    @DeleteMapping(value = "/users/{id}")
    @PreAuthorize("hasAuthority('user_delete')")
    public ResponseEntity<Object> delete(@NotNull @PathVariable Long id) {
        userService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ApiOperation(value = "根据用户名获取数据")
    @GetMapping(value = "/users/getByUsername")
    @ResponseBody
    public ResponseEntity<Object> getByUsername(@RequestParam(value = "username") String username) {
        return ResponseEntity.ok(userService.getByUsername(username));
    }

}
