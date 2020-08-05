package com.ohyoung.base;

import com.ohyoung.util.SecurityUtil;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

/**
 * 自定义审计功能, 自动插入创建人id、更新人id、创建时间和更新时间
 *
 * @author ohYoung
 * @date 2020/7/21 17:02
 */
public class CustomEntityListener {

    /**
     *  开始保存数据时调用
     */
    @PrePersist
    public void prePersist(BaseEntity entity){
        entity.setCreateBy(SecurityUtil.getJwtUserId());
        entity.setModifyBy(SecurityUtil.getJwtUserId());
        entity.setGmtCreate(LocalDateTime.now());
        entity.setGmtModified(LocalDateTime.now());
    }
    /**
     *  开始更新数据时调用
     */
    @PreUpdate
    public void preUpdate(BaseEntity entity){
        entity.setCreateBy(SecurityUtil.getJwtUserId());
        entity.setGmtModified(LocalDateTime.now());
    }
    /**
     *  结束保存数据时调用
     */
    @PostPersist
    public void postPersist(BaseEntity entity){
    }
    /**
     *  结束更新数据时调用
     */
    @PostUpdate
    public void postUpdate(BaseEntity entity){
    }

}
