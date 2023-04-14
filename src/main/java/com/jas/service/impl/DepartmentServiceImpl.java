package com.jas.service.impl;

import com.jas.dto.DepartmentDTO;
import com.jas.entity.Department;
import com.jas.exceptions.DepartmentAlreadyExistsException;
import com.jas.exceptions.ResourceNotFoundException;
import com.jas.mapper.AutoDepartmentMapper;
import com.jas.repository.DepartmentRepository;
import com.jas.service.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    private AutoDepartmentMapper departmentMapper;

    public DepartmentDTO createDepartment(DepartmentDTO department) {
        if(!departmentRepository.findById(department.getDepartmentId()).isEmpty())
            throw new DepartmentAlreadyExistsException("Department already exists");
        departmentRepository.save(departmentMapper.departmentDTOToEntity(department));
        return department;
    }

    public List<DepartmentDTO> getAllDepartments()
    {
        List<Department> departmentList = departmentRepository.findAll();
        return departmentList.stream()
                .map(d-> departmentMapper.departmentEntityToDTO(d))
                .collect(Collectors.toList());
    }

    public DepartmentDTO getDepartmentById(Integer id)
    {
        if (departmentRepository.findById(id).isEmpty())
            throw new ResourceNotFoundException("Department does not exist");
        return departmentMapper.departmentEntityToDTO(departmentRepository.findById(id).get());
    }

    public DepartmentDTO updateDepartment(DepartmentDTO departmentDTO) {
        Department department = departmentMapper.departmentDTOToEntity(departmentDTO);
        Optional<Department> departmentId = departmentRepository.findById(department.getDepartmentId());
        if(departmentId.isEmpty())
            throw new ResourceNotFoundException("Department not found");
           departmentRepository.
                    setDepartmentNameById(department.getDepartmentName(), department.getDepartmentId());
           return departmentDTO;
    }

    public void deleteDepartment(Integer id)
    {
        departmentRepository.deleteById(id);
    }





}
