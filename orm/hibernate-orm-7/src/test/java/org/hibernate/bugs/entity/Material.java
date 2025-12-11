// ******************************************************************
//
//  Copyright 2025 PSI Software SE. All rights reserved.
//  PSI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
//
// ******************************************************************

package org.hibernate.bugs.entity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Material
{
    @Id
    private long id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "weight_value"))
    })
    private Weight weight;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "length_value"))
    })
    private Length length;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Weight getWeight() {
        return weight;
    }

    public void setWeight(Weight weight) {
        this.weight = weight;
    }

    public Length getLength() {
        return length;
    }

    public void setLength(Length length) {
        this.length = length;
    }
}
