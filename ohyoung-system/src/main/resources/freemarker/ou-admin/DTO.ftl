package ${package}.domain.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * @author ${author}
 * @date ${date}
 */
@Data
public class ${entityName}DTO {

    <#list fields as field>
    /**
     *  ${field['columnComment']}
     */
    <#if field['columnName'] = 'id'>
    @NotNull(groups = Update.class, message = "id不能为空")
    </#if>
    private ${field['javaDataType']} ${field['columnHumpName']};
    </#list>

    public interface Insert{};

    public interface Update{};
}
