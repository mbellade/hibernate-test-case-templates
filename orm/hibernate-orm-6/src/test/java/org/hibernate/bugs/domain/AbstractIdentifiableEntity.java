package org.hibernate.bugs.domain;

public abstract class AbstractIdentifiableEntity {

    public abstract Long getId();

    public abstract long getVersion();

}
