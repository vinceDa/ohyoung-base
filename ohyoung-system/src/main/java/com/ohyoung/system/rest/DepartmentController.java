package com.ohyoung.system.rest;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import com.ohyoung.system.domain.dto.DepartmentDTO;
import com.ohyoung.system.domain.query.DepartmentQueryCriteria;
import com.ohyoung.system.domain.vo.DepartmentVO;
import com.ohyoung.system.service.DepartmentService;
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
import java.util.List;


/**
 * @author vince
 */
@Slf4j
@Api(tags = "系统管理: 部门管理")
@Controller
@RequestMapping("api/v1/system")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @ApiOperation(value = "查询所有部门")
    @GetMapping(value = "/departments")
    @PreAuthorize("hasAuthority('department_all')")
    public ResponseEntity<Object> listAll(DepartmentQueryCriteria departmentQueryCriteria) {
        Long userId = SecurityUtil.getJwtUserId();
        departmentQueryCriteria.setCreateBy(userId);
        List<DepartmentDTO> departmentDTOS;
        if (BeanUtil.isEmpty(departmentQueryCriteria)) {
            departmentDTOS = departmentService.listAll();
        } else {
            departmentDTOS = departmentService.listAll(departmentQueryCriteria.toSpecification());
        }
        List<DepartmentVO> convert = Convert.toList(DepartmentVO.class, departmentService.buildMenuTree(departmentDTOS));
        return ResponseEntity.ok(convert);
    }

    @ApiOperation(value = "新增部门")
    @PostMapping(value = "/departments")
    @PreAuthorize("hasAuthority('department_add')")
    public ResponseEntity<Object> insert(@Validated(DepartmentDTO.Insert.class) @RequestBody DepartmentDTO departmentDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(departmentService.insert(departmentDTO));
    }

    @ApiOperation(value = "修改部门")
    @PutMapping(value = "/departments")
    @PreAuthorize("hasAuthority('department_update')")
    public ResponseEntity<Object> put(@Validated(DepartmentDTO.Update.class) @RequestBody DepartmentDTO departmentDTO) {
        departmentService.update(departmentDTO);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "删除部门")
    @DeleteMapping(value = "/departments/{id}")
    @PreAuthorize("hasAuthority('department_delete')")
    public ResponseEntity<Object> delete(@NotNull @PathVariable Long id) {
        departmentService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
