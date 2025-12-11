// ******************************************************************
//
//  Copyright 2025 PSI Software SE. All rights reserved.
//  PSI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
//
// ******************************************************************

package org.hibernate.bugs.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class Length
{
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
