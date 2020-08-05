package com.ohyoung.system.domain.query;

import com.ohyoung.base.BaseQuery;
import com.ohyoung.retention.QueryField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.Specification;

/**
 *  角色查询参数实体类
 * @author vince
 * @date 2019/10/12 16:47
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RoleQueryCriteria extends BaseQuery<RoleQueryCriteria> {

    /**
     *  模糊查询条件: 角色名
     */
     @QueryField(blurry = {"name"})
    private String blurry;

    /**
     *  创建人id
     */
    @QueryField(column = "createBy")
    private Long createBy;

    @Override
    public Specification<RoleQueryCriteria> toSpecification() {
        return super.toSpecificationWithAnd();
    }
}
