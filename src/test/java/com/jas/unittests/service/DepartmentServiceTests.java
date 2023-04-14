package com.jas.unittests.service;

import com.jas.entity.Department;
import com.jas.exceptions.DepartmentAlreadyExistsException;
import com.jas.repository.DepartmentRepository;
import com.jas.service.impl.DepartmentServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DepartmentServiceTests {

    @Autowired
    private DepartmentServiceImpl departmentService;

    @MockBean
    private DepartmentRepository repository;

    @Test
    public void getAllDepartmentsTest_success()
    {

        when(repository.findAll()).thenReturn(
                Stream.of(new Department(1, "Travel"),
                        new Department(2, "Banking")).collect(Collectors.toList())
        );
        assertEquals(2, departmentService.getAllDepartments().size());
    }

    @Test
    public void getDepartmentByIdTest_success()
    {
        when(repository.findById(1)).thenReturn(Optional.of(new Department(1, "Travel")));
        assertEquals(new Department(1, "Travel"), repository.findById(1).get());

    }

    @Test
    public void createDepartmentTest_success() throws DepartmentAlreadyExistsException {
        Department department = Department.builder()
                .departmentId(1)
                .departmentName("Fitness")
                .build();

        when(repository.save(department)).thenReturn(department);

    }

    @Test
    public void deleteDepartmentTest_success()
    {
        Department department = Department.builder()
                .departmentId(1)
                .departmentName("Fitness")
                .build();
        departmentService.deleteDepartment(department.getDepartmentId());
        verify(repository, times(1)).deleteById(department.getDepartmentId());
    }

}
