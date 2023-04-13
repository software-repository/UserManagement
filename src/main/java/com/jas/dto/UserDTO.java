package com.jas.dto;


import com.jas.entity.Department;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDTO {

    private Integer userId;
    private String firstName;
    private String lastName;
    private Department department;
    private String emailId;
    private Date dob;
}
