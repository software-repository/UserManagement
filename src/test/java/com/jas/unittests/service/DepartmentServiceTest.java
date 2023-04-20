package com.jas.unittests.service;

import com.jas.UserManagementApplication;
import com.jas.dto.DepartmentDTO;
import com.jas.entity.Department;
import com.jas.exceptions.DepartmentAlreadyExistsException;
import com.jas.exceptions.ResourceNotFoundException;
import com.jas.mapper.AutoDepartmentMapper;
import com.jas.repository.DepartmentRepository;
import com.jas.service.DepartmentService;
import com.jas.service.impl.DepartmentServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserManagementApplication.class)
public class DepartmentServiceTest {

    @Autowired
    ApplicationContext context;

    @MockBean
    DepartmentRepository departmentRepository;
    @Autowired
    DepartmentServiceImpl departmentService;

    Department department;
    DepartmentDTO departmentDTO;

    @Test
    public void createDepartment_success()
    {
        department =
                Department.builder()
                        .departmentId(1)
                        .departmentName("Test").build();

        departmentDTO = DepartmentDTO.builder()
                .departmentId(1)
                .departmentName("Test").build();

        when(departmentRepository.save(department)).thenReturn(new Department(1,"Test"));
        when(departmentRepository.findById(1)).thenReturn(Optional.empty());

        DepartmentDTO dto = departmentService.createDepartment(departmentDTO);
        assertEquals(Integer.valueOf(1), dto.getDepartmentId());
        assertEquals("Test", dto.getDepartmentName());
        verify(departmentRepository, times(1)).findById(1);
        verify(departmentRepository, times(1)).save(department);
    }

    @Test
    public void createDepartment_alreadyExistsException()
    {
        when(departmentRepository.findById(1)).
                thenThrow(new DepartmentAlreadyExistsException("Department already exists"));

        departmentDTO = DepartmentDTO.builder()
                .departmentId(1)
                .departmentName("Test").build();
        assertThrows(DepartmentAlreadyExistsException.class, ()-> {
            departmentService.createDepartment(departmentDTO);
        });
    }

    @Test
    public void getAllDepartments_success()
    {
        when(departmentRepository.findAll()).thenReturn(
                List.of(new Department(1, "Test1"),
                        new Department(1, "Test1") )
        );
        assertEquals(2, departmentService.getAllDepartments().size());
        verify(departmentRepository, times(1)).findAll();
    }

    @Test
    public void deleteDepartment_error()
    {
        when(departmentRepository.findById(1)).thenThrow(new ResourceNotFoundException("Department not found"));
        departmentDTO = DepartmentDTO.builder()
                .departmentId(1)
                .departmentName("Test").build();
        assertThrows(ResourceNotFoundException.class, ()->{
            departmentService.deleteDepartment(1);
        });
    }

    @Test
    public void deleteDepartment_success()
    {
        when(departmentRepository.findById(1)).thenReturn(Optional.of(new Department(1, "Test")));
       doNothing().when(departmentRepository).deleteById(1);

        departmentService.deleteDepartment(1);
        verify(departmentRepository,times(1)).findById(1);
        verify(departmentRepository,times(1)).deleteById(1);
    }

    @Test
    public void updateDepartment_error()
    {
        when(departmentRepository.findById(1)).thenThrow(new ResourceNotFoundException("Department not found"));
        departmentDTO = DepartmentDTO.builder()
                .departmentId(1)
                .departmentName("Test").build();
        assertThrows(ResourceNotFoundException.class, ()->{departmentService.updateDepartment(departmentDTO,1);});

    }

    @Test
    public void updateDepartment_success()
    {
        when(departmentRepository.findById(1)).thenReturn(Optional.of(new Department(1, "Test")));
        departmentDTO = DepartmentDTO.builder()
                .departmentId(1)
                .departmentName("Test").build();
        departmentService.updateDepartment(departmentDTO,1);
        verify(departmentRepository, times(1)).setDepartmentNameById(departmentDTO.getDepartmentName(), departmentDTO.getDepartmentId());
    }


}
