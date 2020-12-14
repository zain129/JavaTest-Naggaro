package com.zainimtiaz.nagarro.repository;

import com.zainimtiaz.nagarro.model.UserPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserPermissionRepository extends JpaRepository<UserPermission, Long> {

    List<UserPermission> findAllByUser_id (Long userId);
    void deleteAllByUser_Id(Long id);
}
