package com.jas.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.jas.validators.BirthDate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDTO implements Serializable {

    private Integer userId;

    @Size.List({
            @Size(min = 2, message = "firstName must be 2 characters long"),
            @Size(max = 250, message = "firstName cannot be more than 250 characters")
    })
    private String firstName;

    @Size.List({
            @Size(min = 2, message = "lastName must be 2 characters long"),
            @Size(max = 250, message = "lastName cannot be more than 250 characters")
    })
    private String lastName;

    private DepartmentDTO department;

    @Email
    @NotNull(message = "email cannot be null")
    private String email;


    @JsonFormat(pattern = "MM/dd/yyyy")
    @NotNull(message = "dob cannot be null")
    @BirthDate(message = "Age should be between 18years - 60 years")
    private Date dob;
}
