package com.app.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.persistence.entity.RolesEntity;

public interface RoleRepository extends JpaRepository<RolesEntity,Long> {
    
    List<RolesEntity> findRoleEntityByRoleEnumIn(List<String> roleName);
}
