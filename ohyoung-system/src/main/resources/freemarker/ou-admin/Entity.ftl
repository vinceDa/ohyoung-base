package ${package}.domain;

import javax.persistence.*;
import com.ohyoung.base.BaseEntity;
import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
* @author ${author}
* @date ${date}
*/
@Entity
@Getter
@Setter
@Table(name = "${tableName}")
public class ${entityName} extends BaseEntity implements Serializable{

    /**
    *  主键id
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

<#list fields as field>
    <#if field['columnHumpName'] != 'id' && field['columnHumpName'] != 'createBy' &&  field['columnHumpName'] != 'modifyBy' &&
    field['columnHumpName'] != 'gmtCreate' &&  field['columnHumpName'] != 'gmtModified'>
    /**
     *  ${field['columnComment']}
     */
    @Column(name = "${field['columnName']}")
    private ${field['javaDataType']} ${field['columnHumpName']};

    </#if>
</#list>
}
