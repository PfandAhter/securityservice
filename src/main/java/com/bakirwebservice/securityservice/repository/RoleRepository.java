package com.bakirwebservice.securityservice.repository;

import com.bakirwebservice.securityservice.model.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role,Integer> {

    @Query("SELECT r FROM Role r WHERE r.name = ?1")
    Role findRoleByName(String name);

}
