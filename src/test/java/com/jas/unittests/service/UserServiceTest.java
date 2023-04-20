package com.jas.unittests.service;


import com.jas.dto.DepartmentDTO;
import com.jas.dto.UserDTO;
import com.jas.entity.Department;
import com.jas.entity.User;
import com.jas.exceptions.ResourceNotFoundException;
import com.jas.exceptions.UserAlreadyExistsException;
import com.jas.repository.DepartmentRepository;
import com.jas.repository.UserRepository;
import com.jas.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {

    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;


    @Test
    public void verifyUserCreationErrorWhenEmailAlreadyExists()
    {
        //GIVEN
        Department department = Department.builder()
                        .departmentName("Test")
                                .departmentId(1).build();
        userRepository.save(User.builder().firstName("x").email("x@gmail.com").lastName("y")
                .dob(new Date("30/12/1992")).department(department).build());
        // when
        DepartmentDTO departmentDTO = DepartmentDTO.builder()
                .departmentId(1)
                .departmentName("Test").build();
        UserDTO userDTO = UserDTO.builder()
                .userId(1)
                .dob(new Date("30/12/1992"))
                .email("x@gmail.com")
                .department(departmentDTO)
                .firstName("TestUser")
                .lastName("TestUser")
                .build();
         // THEN
        assertThrows(UserAlreadyExistsException.class, ()->userService.createUser(userDTO));
    }

    @Test
    public void verifyUserCreationErrorWhenDepartmentDoesNotExist()
    {
        //GIVEN
        Department department = Department.builder()
                .departmentName("Test")
                .departmentId(1).build();
        userRepository.save(User.builder().firstName("x").email("x@gmail.com").lastName("y")
                .dob(new Date("30/12/1992")).department(department).build());

        //WHEN
        DepartmentDTO departmentDTO = DepartmentDTO.builder()
                .departmentId(2)
                .departmentName("Test").build();
        UserDTO userDTO = UserDTO.builder()
                .department(departmentDTO)
                .userId(1)
                .dob(new Date("30/12/1992"))
                .email("abc@gmail.com")
                .firstName("TestUser")
                .lastName("TestUser")
                .build();

        //THEN
        assertThrows(ResourceNotFoundException.class, ()->userService.createUser(userDTO));

    }

    @Test
    public void verifyUserCreationSuccess()
    {
        //GIVEN
        DepartmentDTO departmentDTO = DepartmentDTO.builder()
                .departmentId(1)
                .departmentName("Test").build();
        UserDTO userDTO = UserDTO.builder()
                .lastName("Test")
                .firstName("Test")
                .dob(new Date("30/12/1992"))
                .department(departmentDTO)
                .email("pqrxyzw1@gmail.com")
                .build();
        //WHEN
        userService.createUser(userDTO);

        //THEN
        assertTrue(!userRepository.findByEmail("pqrxyzw1@gmail.com").isEmpty());

    }

    @Test
    public void getAllUsers_success()
    {
        //GIVEN
        User user1 = User.builder()
                .userId(1)
                .lastName("Test")
                .firstName("Test")
                .dob(new Date("30/12/1992"))
                .department(new Department(1, "Test"))
                .email("abc5@gmail.com")
                .build();

        User user2 = User.builder()
                .userId(2)
                .lastName("Test")
                .firstName("Test")
                .dob(new Date("30/12/1992"))
                .department(new Department(1, "Test"))
                .email("abc6@gmail.com")
                .build();
        userRepository.save(user1);
        userRepository.save(user2);
        //WHEN
        List<UserDTO> userDTOS = userService.getAllUsers();
        //THEN
        assertTrue(userDTOS.size()==10);
    }


    @Test
    public void getUserById_failure()
    {
        assertThrows(ResourceNotFoundException.class, ()->{userService.getUserById(81);});
    }


    @Test
    public void deleteUser_failure()
    {
        assertThrows(ResourceNotFoundException.class,()->userService.deleteUser(91));
    }

    @Test
    public void updateUser_error()
    {
        when(userRepository.findById(1)).thenThrow(new ResourceNotFoundException("Not Found"));
        DepartmentDTO departmentDTO = DepartmentDTO.builder()
                .departmentId(1)
                .departmentName("Test").build();
        UserDTO userDTO = UserDTO.builder()
                .lastName("Test")
                .firstName("Test")
                .dob(new Date("30/12/1992"))
                .department(departmentDTO)
                .email("abc@gmail.com")
                .build();

        assertThrows(ResourceNotFoundException.class, ()->userService.updateUser(userDTO,1));
    }

    @Test
    public void updateUser_success()
    {
        User user1 = User.builder()
                .userId(1)
                .lastName("Test")
                .firstName("Test")
                .dob(new Date("30/12/1992"))
                .department(new Department(1, "Test"))
                .email("abc1@gmail.com")
                .build();
        when(userRepository.findById(1)).thenReturn(Optional.ofNullable(user1));

        DepartmentDTO departmentDTO = DepartmentDTO.builder()
                .departmentId(1)
                .departmentName("Test").build();
        UserDTO userDTO = UserDTO.builder()
                .userId(1)
                .lastName("Test")
                .firstName("Test")
                .dob(new Date("30/12/1992"))
                .department(departmentDTO)
                .email("abc1@gmail.com")
                .build();

        userService.updateUser(userDTO,1);
        verify(userRepository, times(1)).findById(1);
        verify(userRepository, times(1)).save(user1);
    }
}
