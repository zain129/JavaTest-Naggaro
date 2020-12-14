package com.zainimtiaz.nagarro.repository;

import com.zainimtiaz.nagarro.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    List<Permission> findById(Long id);
    List<Permission> findAllByActiveTrueOrderBySortOrderAsc();
    List<Permission> findAllByActiveTrueAndPermissionForIndEqualsOrderBySortOrder(char permFor);

    @Query("SELECT DISTINCT p FROM Permission p INNER JOIN p.rolePermissions rp INNER JOIN rp.role r INNER JOIN r.userRoles ur INNER JOIN ur.user u WHERE u.id=:userId")
    List<Permission> findUserPermissionByUserId(@Param("userId") Long userId);
    Permission findByName(String name);
    @Query("SELECT p FROM Permission p INNER JOIN p.rolePermissions pr  WHERE pr.role.id = :roleId")
    List<Permission> findByRoles(@Param("roleId") long roleId);
    List<Permission> findByIdInAndActiveTrue(List<Long> ids);
    List<Permission> findByIdInAndActiveTrueAndPermissionForIndEquals(List<Long> permIds, char permFor);

}
