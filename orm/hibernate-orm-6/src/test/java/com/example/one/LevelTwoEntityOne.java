package com.example.one;

// Import

import java.io.Serializable;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

/**
 * Generated Hibernate Entity Class for businessmodel MessagePerfomanceModel.
 * <p>
 * For framework use only!
 */
@Entity(name="com.example.one.LevelTwoEntityOne")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name="Level_Two_Entity_One")
public class LevelTwoEntityOne extends BaseEntity
        implements Serializable
{
    @Basic
    @Column(name="LevelTwoEntityOneAttribute")
    String LevelTwoEntityOneAttribute = null;

    public String getLevelTwoEntityOneAttribute() {
        return LevelTwoEntityOneAttribute;
    }

    public void setLevelTwoEntityOneAttribute(String levelTwoEntityOneAttribute) {
        LevelTwoEntityOneAttribute = levelTwoEntityOneAttribute;
    }
}