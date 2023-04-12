package com.jas.repository;

import com.jas.entity.Department;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface DepartmentRepository extends JpaRepository<Department,Integer> {

    @Modifying
    @Transactional
    @Query("update #{#entityName} d set d.departmentName = :name where d.departmentId = :id")
    void setDepartmentNameById(@Param("name") String name,
                               @Param("id") Integer id);

}
