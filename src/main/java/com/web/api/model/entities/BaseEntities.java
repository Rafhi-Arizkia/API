package com.web.api.model.entities;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntities<T> {
    @CreatedBy
    protected T createdBy;
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    protected Date createDate;
    @LastModifiedBy
    protected T updateBy;
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    protected Date updateData;

    public T getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(T createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public T getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(T updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateData() {
        return updateData;
    }

    public void setUpdateData(Date updateData) {
        this.updateData = updateData;
    }
}
