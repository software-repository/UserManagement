package com.jas.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DepartmentDTO implements Serializable {

    private static final long SerialVersionUID=42l;

    @Range(min = 1, max=10, message = "Department Id must be between 1 and 10")
    @NotNull(message = "departmentId cannot be null")
    private Integer departmentId;

    @Size.List({
            @Size(min = 2, message = "departmentName must be 2 characters long"),
            @Size(max = 25, message = "departmentName cannot be more than 25 characters")
    })
    @NotNull(message = "departmentName cannot be null")
    private String departmentName;

}
