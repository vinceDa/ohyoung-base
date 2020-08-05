package ${package}.entity;

import javax.validation.constraints.NotNull;

<#if useSwagger>
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
</#if>

/**
* @author ${author}
* @date ${date}
*/
<#if useSwagger>
@ApiModel(value="${entityCnName}", description="${entityCnName}")
</#if>
public class ${entityName} {

<#list fields as field>
    /**
     *  ${field['columnComment']}
     */
    <#if field['nullable']>
        <#if field['columnName'] == 'id'>
    @NotNull(groups = Update.class, message = "${field['columnComment']}不能为空")
            <#else>
    @NotNull(groups = Insert.class, message = "${field['columnComment']}不能为空")
        </#if>
    </#if>
    <#if useSwagger>
    @ApiModelProperty(value = "${field['columnComment']}")
    </#if>
    private ${field['javaDataType']} ${field['columnHumpName']};
</#list>

    public interface Insert{};

    public interface Update{};

<#list fields as field>

    public String get${field['columnHumpName']?cap_first}() {
        return ${field['columnHumpName']};
    }

    public void set${field['columnHumpName']?cap_first}(String ${field['columnHumpName']}) {
        this.${field['columnHumpName']} = ${field['columnHumpName']} == null ? null : ${field['columnHumpName']}.trim();
    }
</#list>
}
