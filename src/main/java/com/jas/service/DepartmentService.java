package com.jas.service;

import com.jas.entity.Department;
import com.jas.exceptions.DepartmentAlreadyExistsException;
import com.jas.repository.DepartmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class DepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;

    public void createDepartment(Department department) {
        if(!departmentRepository.findById(department.getDepartmentId()).isEmpty())
            throw new DepartmentAlreadyExistsException("Department already exists");
       departmentRepository.save(department);
    }

    public List<Department> getAllDepartments()
    {
        return departmentRepository.findAll();
    }

    public Optional<Department> getDepartmentById(Integer id)
    {
        return departmentRepository.findById(id);
    }

    public void updateDepartment(Department department) {
        Optional<Department> departmentId = departmentRepository.findById(department.getDepartmentId());
        if(departmentId.isEmpty())
            throw new DepartmentNotFoundException("Department not found");

           departmentRepository.
                    setDepartmentNameById(department.getDepartmentName(), department.getDepartmentId());

    }

    public void deleteDepartment(Integer id)
    {
        departmentRepository.deleteById(id);
    }





}
