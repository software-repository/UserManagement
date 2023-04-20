package com.jas.service;

import com.jas.dto.DepartmentDTO;
import com.jas.dto.UserDTO;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {

    public DepartmentDTO createDepartment(DepartmentDTO departmentDTO);
    public List<DepartmentDTO> getAllDepartments();
    public DepartmentDTO getDepartmentById(Integer id);

    public DepartmentDTO updateDepartment(DepartmentDTO departmentDTO, Integer id);

    public void deleteDepartment(Integer id);

}
