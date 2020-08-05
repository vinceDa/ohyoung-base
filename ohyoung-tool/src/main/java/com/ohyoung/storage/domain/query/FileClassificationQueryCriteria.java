package com.ohyoung.storage.domain.query;

import com.ohyoung.base.BaseQuery;
import com.ohyoung.retention.QueryField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.Specification;

/**
 * @author vince
 * @date 2020/05/15 02:21:57
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FileClassificationQueryCriteria extends BaseQuery<FileClassificationQueryCriteria> {

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
    public Specification<FileClassificationQueryCriteria> toSpecification() {
        return super.toSpecificationWithAnd();
    }
}
