package ${package}.domain.vo;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

/**
 * @author ${author}
 * @date ${date}
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ${entityName}VO {

     <#list fields as field>
     /**
      *  ${field['columnComment']}
      */
     private ${field['javaDataType']} ${field['columnHumpName']};

     </#list>
}
