package com.jas.repository;

import com.jas.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select u  from #{#entityName} u where u.email= :email")
    public User findByEmail(@Param("email") String email);

    @Modifying
    @Transactional
    @Query("update #{#entityName} u set u.firstName = :firstName , u.lastName = :lastName , u.departmentId = :departmentId , u.dob = :dob , u.email= :email where u.userId = :userId")
    public void updateUser(@Param("firstName") String firstName ,
                           @Param("lastName") String lastName ,
                           @Param("departmentId") Integer departmentId,
                           @Param("dob") Date dob,
                           @Param("email") String email,
                           @Param("userId") Integer userId);


}
