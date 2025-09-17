package com.example.two;

// Import

import java.io.Serializable;

import com.example.one.BaseEntity;
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
@Entity(name="com.example.one.LevelTwoEntityTwo")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name="Level_Two_Entity_Two")
public class LevelTwoEntityTwo extends BaseEntity
        implements Serializable
{
    @Basic
    @Column(name="LevelTwoEntityTwoAttribute")
    String LevelTwoEntityTwottribute = null;

    public String getLevelTwoEntityTwottribute() {
        return LevelTwoEntityTwottribute;
    }

    public void setLevelTwoEntityTwottribute(String levelTwoEntityTwottribute) {
        LevelTwoEntityTwottribute = levelTwoEntityTwottribute;
    }
}