package org.hibernate.bugs;

import jakarta.data.repository.BasicRepository;
import jakarta.data.repository.Repository;

@Repository
public interface Cities extends BasicRepository<City, String> {
}
