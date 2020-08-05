package ${package}.controller;

import ${package}.common.result.BaseResult;
import ${package}.common.result.FailureResult;
import ${package}.common.result.SuccessResult;
import ${package}.entity.${entityName};
import ${package}.entity.query.${entityName}QueryCriteria;
import ${package}.service.${entityName}Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
<#if useSwagger>
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
</#if>
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author ${author}
 * @date ${date}
 */
@RestController
@RequestMapping("/api/v1")
<#if useSwagger>
@Api(value = "${entityName}", tags = "${entityCnName}")
</#if>
public class ${entityName}Controller {

    @Autowired
    private ${entityName}Service ${entityName?uncap_first}Service;

    @GetMapping(value = "/${entityName?uncap_first}s/page")
    <#if useSwagger>
    @ApiOperation(value = "分页查询${entityCnName}", notes = "分页查询${entityCnName}")
    </#if>
    public BaseResult listForPage(${entityName}QueryCriteria queryCriteria) {
        PageHelper.startPage(queryCriteria.getPageNum(), queryCriteria.getPageSize());
        List<${entityName}> list = ${entityName?uncap_first}Service.listForPage(queryCriteria);
        PageInfo<${entityName}> pageInfo = new PageInfo<>(list);
        Map<String, Object> result = new HashMap<>(3);
        result.put("totalCount", pageInfo.getTotal());
        result.put("pageNum", pageInfo.getPageNum());
        result.put("list", list);
        return new SuccessResult(result);
    }

    @GetMapping(value = "/${entityName?uncap_first}s/{id}")
    <#if useSwagger>
    @ApiOperation(value = "获取单个${entityCnName}", notes = "根据id获取单个${entityCnName}")
    </#if>
    public BaseResult getById(@NotNull @PathVariable <#if useSwagger>@ApiParam("${entityName?uncap_first}Id")</#if> Long id) {
        return new SuccessResult(${entityName?uncap_first}Service.selectByPrimaryKey(id));
    }

    @PostMapping(value = "/${entityName?uncap_first}s")
    <#if useSwagger>
    @ApiOperation(value = "新增${entityCnName}", notes = "新增${entityCnName}")
    </#if>
    public BaseResult insert(@Validated(${entityName}.Insert.class) @RequestBody ${entityName} ${entityName?uncap_first}) {
        int resultCount = ${entityName?uncap_first}Service.insertSelective(${entityName?uncap_first});
        if (resultCount == 1) {
            return new SuccessResult();
        }
        return new FailureResult();
    }

    @PutMapping(value = "/${entityName?uncap_first}s")
    <#if useSwagger>
    @ApiOperation(value = "编辑${entityCnName}", notes = "编辑${entityCnName}")
    </#if>
    public BaseResult put(@Validated(${entityName}.Update.class) @RequestBody ${entityName} ${entityName?uncap_first}) {
        int resultCount = ${entityName?uncap_first}Service.updateByPrimaryKeySelective(${entityName?uncap_first});
        if (resultCount == 1) {
            return new SuccessResult();
        }
        return new FailureResult();
    }

    @DeleteMapping(value = "/${entityName?uncap_first}s/{id}")
    <#if useSwagger>
    @ApiOperation(value = "删除${entityCnName}", notes = "删除${entityCnName}")
    </#if>
    public BaseResult delete(@NotNull @PathVariable <#if useSwagger>@ApiParam("${entityName?uncap_first}Id")</#if> Long id) {
        int resultCount = ${entityName?uncap_first}Service.deleteByPrimaryKey(id);
        if (resultCount == 1) {
            return new SuccessResult();
        }
        return new FailureResult();
    }

}
