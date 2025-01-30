package com.bakirwebservice.securityservice.repository;

import com.bakirwebservice.securityservice.model.ErrorCodes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ErrorCodeRepository extends JpaRepository<ErrorCodes,String> {
}
