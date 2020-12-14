package com.zainimtiaz.nagarro.repository;

import com.zainimtiaz.nagarro.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    List<Role> findById(Long id);

    Role findByName(String name);

    List<Role> findAllByActiveTrue();

    List<Role> findAllByIdIn(List<Long> ids);

}
