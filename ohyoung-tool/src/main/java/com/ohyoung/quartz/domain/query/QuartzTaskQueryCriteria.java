package com.ohyoung.quartz.domain.query;

import com.ohyoung.base.BaseQuery;
import com.ohyoung.retention.QueryField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.Specification;

/**
 * @author vince
 * @date 2020/02/02 07:24:34
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QuartzTaskQueryCriteria extends BaseQuery<QuartzTaskQueryCriteria> {

    /**
     * 模糊查询条件
     */
    @QueryField(blurry = {"name"})
    private String blurry;

    /**
     * 查询条件, example: entity中对应的key(key对应到具体的某个字段)
     */
    @QueryField(column = "example")
    private Boolean queryField;

    @Override
    public Specification<QuartzTaskQueryCriteria> toSpecification() {
        return super.toSpecificationWithAnd();
    }
}
