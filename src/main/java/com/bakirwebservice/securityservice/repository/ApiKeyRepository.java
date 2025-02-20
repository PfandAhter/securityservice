package com.bakirwebservice.securityservice.repository;

import com.bakirwebservice.securityservice.model.ApiKeyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApiKeyRepository extends JpaRepository<ApiKeyEntity,Long> {

    @Query("SELECT A FROM ApiKeyEntity A WHERE A.apiKey = ?1")
    Optional<ApiKeyEntity> findByApiKey(String apiKey);

}