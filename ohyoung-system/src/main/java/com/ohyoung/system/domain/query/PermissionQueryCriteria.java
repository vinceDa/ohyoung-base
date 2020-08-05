package com.ohyoung.system.domain.query;

import com.ohyoung.base.BaseQuery;
import com.ohyoung.retention.QueryField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.Specification;

/**
 * @author vince
 * @date 2019/10/15 11:29
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PermissionQueryCriteria extends BaseQuery<PermissionQueryCriteria> {

    /**
     * 模糊查询条件: 权限名
     */
    @QueryField(blurry = {"name"})
    private String blurry;

    /**
     *  创建人id
     */
    @QueryField(column = "createBy")
    private Long createBy;

    @Override
    public Specification<PermissionQueryCriteria> toSpecification() {
        return super.toSpecificationWithAnd();
    }
}
