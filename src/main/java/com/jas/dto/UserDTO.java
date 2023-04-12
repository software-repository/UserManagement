package com.jas.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDTO {

    private String userId;
    private String firstName;
    private String lastName;
    private DepartmentDTO department;
    private String emailId;
    private Date dob;
}
