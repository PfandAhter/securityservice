package com.bakirwebservice.securityservice.repository;

import com.bakirwebservice.securityservice.model.Path;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PathRepository extends CrudRepository<Path,Long> {

    @Query("SELECT p FROM Path p WHERE p.path = ?1")
    Path findPathByPath(String path);

}
