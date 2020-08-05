package ${package}.rest;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

<#if useSwagger>
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
</#if>


import ${package}.domain.dto.${entityName}DTO;
import ${package}.domain.query.${entityName}QueryCriteria;
import ${package}.domain.vo.${entityName}VO;
import ${package}.service.${entityName}Service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import lombok.extern.slf4j.Slf4j;


/**
 * @author ${author}
 * @date ${date}
 */
@Slf4j
@Controller
@RequestMapping("/api")
<#if useSwagger>
@Api(value = "${entityName}", tags = "${entityCnName}")
</#if>
public class ${entityName}Controller {

    @Autowired
    private ${entityName}Service ${entityName?uncap_first}Service;

    @GetMapping(value = "/${entityName?uncap_first}s")
    <#if useSwagger>
    @ApiOperation(value = "查询所有${entityCnName}", notes = "查询所有${entityCnName}")
    </#if>
    public ResponseEntity<Object> listAll(${entityName}QueryCriteria ${entityName?uncap_first}QueryCriteria) {
        List<${entityName}DTO> ${entityName?uncap_first}DTOS;
        if (BeanUtil.isEmpty(${entityName?uncap_first}QueryCriteria)) {
            ${entityName?uncap_first}DTOS = ${entityName?uncap_first}Service.listAll();
        } else {
            ${entityName?uncap_first}DTOS = ${entityName?uncap_first}Service.listAll(${entityName?uncap_first}QueryCriteria.toSpecification());
        }
        List<${entityName}VO> convert = Convert.convert(new TypeReference<List<${entityName}VO>>() {
        }, ${entityName?uncap_first}DTOS);
        return ResponseEntity.ok(convert);
    }

    @PostMapping(value = "/${entityName?uncap_first}s")
    <#if useSwagger>
    @ApiOperation(value = "新增${entityCnName}", notes = "新增${entityCnName}")
    </#if>
    public ResponseEntity<Object> insert(@Validated(${entityName}DTO.Insert.class) @RequestBody ${entityName}DTO ${entityName?uncap_first}DTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(${entityName?uncap_first}Service.insert(${entityName?uncap_first}DTO));
    }

    @PutMapping(value = "/${entityName?uncap_first}s")
    <#if useSwagger>
    @ApiOperation(value = "编辑${entityCnName}", notes = "编辑${entityCnName}")
    </#if>
    public ResponseEntity<Object> put(@Validated(${entityName}DTO.Update.class) @RequestBody ${entityName}DTO ${entityName?uncap_first}DTO) {
        ${entityName?uncap_first}Service.update(${entityName?uncap_first}DTO);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/${entityName?uncap_first}s/{id}")
    <#if useSwagger>
    @ApiOperation(value = "删除${entityCnName}", notes = "删除${entityCnName}")
    </#if>
    public ResponseEntity<Object> delete(@NotNull @PathVariable <#if useSwagger>@ApiParam("id")</#if> Long id) {
        ${entityName?uncap_first}Service.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
