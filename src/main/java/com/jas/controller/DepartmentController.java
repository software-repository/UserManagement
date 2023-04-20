package com.jas.controller;


import com.jas.dto.DepartmentDTO;
import com.jas.service.impl.DepartmentServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping(value = "api/v1/department")
public class DepartmentController {

    @Autowired
    DepartmentServiceImpl departmentService;

    @PostMapping()
    public ResponseEntity<DepartmentDTO> createNewDepartment(@Valid @RequestBody DepartmentDTO department)
    {
        departmentService.createDepartment(department);
        return ResponseEntity.status(HttpStatus.CREATED).body(department);
    }

    @GetMapping()
    public ResponseEntity<List<DepartmentDTO>> getAllDepartments()
    {
        return ResponseEntity.status(HttpStatus.OK).body(departmentService.getAllDepartments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDTO> getDepartmentById(@PathVariable Integer id)
    {
        return ResponseEntity.status(HttpStatus.OK).body(departmentService.getDepartmentById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity updateDepartment(@Valid @RequestBody DepartmentDTO department, @PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(departmentService.updateDepartment(department,id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteDepartment(@PathVariable Integer id)
    {
        departmentService.deleteDepartment(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(id);
    }
}
