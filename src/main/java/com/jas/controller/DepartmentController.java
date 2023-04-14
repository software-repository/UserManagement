package com.jas.controller;


import com.jas.entity.Department;
import com.jas.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/v1/department")
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @PostMapping("/create")
    public ResponseEntity<Department> createNewDepartment(@Valid @RequestBody Department department)
    {
        departmentService.createDepartment(department);
        return ResponseEntity.status(HttpStatus.CREATED).body(department);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Department>> getAllDepartments()
    {
        List<Department> departmentList = new ArrayList<>();
        departmentList = departmentService.getAllDepartments();
        return ResponseEntity.status(HttpStatus.OK).body(departmentList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Department>> getDepartmentById(@PathVariable Integer id)
    {
        Optional<Department> department = departmentService.getDepartmentById(id);
        return ResponseEntity.status(HttpStatus.OK).body(department);
    }

    @PatchMapping("/update")
    public ResponseEntity updateDepartment(@Valid @RequestBody Department department) {
        departmentService.updateDepartment(department);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(department);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteDepartment(@PathVariable Integer id)
    {
        departmentService.deleteDepartment(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(id);
    }
}
