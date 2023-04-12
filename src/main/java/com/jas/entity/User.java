package com.jas.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.jas.validators.BirthDate;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import java.util.Date;

@Entity
@Table(name="User")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="user_id")
    private Integer userId;

    @Column(name = "first_name")
    @Size.List({
            @Size(min = 2, message = "firstName must be 2 characters long"),
            @Size(max = 250, message = "firstName cannot be more than 250 characters")
    })
    private String firstName;

    @Column(name = "last_name")
    @Size.List({
            @Size(min = 2, message = "lastName must be 2 characters long"),
            @Size(max = 250, message = "lastName cannot be more than 250 characters")
    })
    private String lastName;

    @JsonFormat(pattern = "MM/dd/yyyy")
    @Past(message = "The date of birth must be in the past.")
    @BirthDate(message = "Age should be between 18years - 60 years")
    @Column(name = "dob")
    private Date dob;

    @Nonnull
    @Column(name = "department_id")
    @Range(min = 1, max = 10, message = "Department Id should be between 1 to 10")
    private Integer departmentId;
}
