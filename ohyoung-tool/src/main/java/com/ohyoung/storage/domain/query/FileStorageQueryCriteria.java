package com.ohyoung.storage.domain.query;

import com.ohyoung.base.BaseQuery;
import com.ohyoung.constant.sql.ConnectConditionEnum;
import com.ohyoung.retention.QueryField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.Specification;

/**
 * @author Hey4Ace
 * @date 2020/05/09 10:26:53
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FileStorageQueryCriteria extends BaseQuery<FileStorageQueryCriteria> {

    /**
     * 模糊查询条件
     */
    @QueryField(blurry = {"name"})
    private String blurry;

    /**
     * 查询条件, example: entity中对应的key(key对应到具体的某个字段)
     */
    @QueryField(column = "classificationId", connect = ConnectConditionEnum.EQUAL)
    private Long classificationId;

    @Override
    public Specification<FileStorageQueryCriteria> toSpecification() {
        return super.toSpecificationWithAnd();
    }
}
