package com.simpleprj.project.repository;

import com.simpleprj.project.entity.InvalidedJwtToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvalidedJwtTokenRepository extends JpaRepository<InvalidedJwtToken, String> {

}
