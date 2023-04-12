package com.jas.entity;


import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Entity
@Table(name="`Department`")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department {

    @Nonnull
    @Id
    @Range(min = 1, max=10, message = "Department Id must be between 1 and 10")
    @Column(name = "department_id")
    private Integer departmentId;


    @Size.List({
            @Size(min = 2, message = "departmentName must be 2 characters long"),
            @Size(max = 25, message = "departmentName cannot be more than 25 characters")
    })
    @Column(name = "department_name")
    private String departmentName;
}
