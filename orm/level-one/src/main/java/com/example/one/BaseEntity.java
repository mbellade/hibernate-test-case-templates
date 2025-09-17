package com.example.one;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

/**
 * Superclass for all Hibernate entity classes.
 */
@MappedSuperclass
public abstract class BaseEntity {
    @GeneratedValue
    @Id
    private Long id;

    @Basic
    @Column(name = "baseAttribute")
    private String baseAttribute = null;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBaseAttribute() {
        return baseAttribute;
    }

    public void setBaseAttribute(String baseAttribute) {
        this.baseAttribute = baseAttribute;
    }
}
