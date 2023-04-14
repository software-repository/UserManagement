package com.jas.entity;


import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.Range;

@Entity
@Table(name="`Department`")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department {

    @NotNull
    @Id
    @Column(name = "department_id")
    private Integer departmentId;

    @Column(name = "department_name")
    @NotNull
    private String departmentName;

}
