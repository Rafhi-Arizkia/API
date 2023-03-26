package com.web.api.model.entities;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
@Getter
@Setter
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

}
