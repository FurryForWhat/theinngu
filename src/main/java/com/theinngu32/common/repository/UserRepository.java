package com.theinngu32.common.repository;

import java.util.Optional;

import com.theinngu32.common.entity.MissionaryGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.theinngu32.common.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    boolean existsByEmail(String email);

	Optional<User> findByEmail(String email);
}
