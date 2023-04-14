package com.jas.mapper;

import com.jas.dto.DepartmentDTO;
import com.jas.entity.Department;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AutoDepartmentMapper {

    AutoDepartmentMapper MAPPER = Mappers.getMapper(AutoDepartmentMapper.class);

    public DepartmentDTO departmentEntityToDTO(Department department);
    public Department departmentDTOToEntity(DepartmentDTO departmentDTO);
}
