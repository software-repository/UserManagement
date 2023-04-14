package com.jas.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.jas.validators.BirthDate;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Integer userId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @NotNull
    @Column(name = "dob")
    private Date dob;

    @OneToOne
    @NotNull
    @JoinColumn(name= "department_id")
    private Department department;
    @Email
    @Column(name = "email")
    private String email;
}
