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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

    @MockBean
    DepartmentRepository departmentRepository;
    @MockBean
    UserRepository userRepository;

    @Autowired
    UserService userService;


    @Test
    public void createUser_useralreadyexists_error()
    {
        when(userRepository.findByEmail("abc@gmail.com")).thenThrow(new UserAlreadyExistsException("User already exists."));
        DepartmentDTO departmentDTO = DepartmentDTO.builder()
                .departmentId(1)
                .departmentName("Test").build();
        UserDTO userDTO = UserDTO.builder()
                .department(departmentDTO)
                .userId(1)
                .dob(new Date("30/12/1992"))
                .email("abc@gmail.com")
                .firstName("TestUser")
                .lastName("TestUser")
                .build();

        assertThrows(UserAlreadyExistsException.class, ()->userService.createUser(userDTO));
        verify(userRepository, times(1)).findByEmail("abc@gmail.com");
    }

    @Test
    public void createuser_departmentNotFoundException()
    {
        when(userRepository.findByEmail("abc@gmail.com")).thenReturn(Optional.empty());
        when(departmentRepository.findById(1)).thenThrow(new ResourceNotFoundException("Department does not exist"));

        DepartmentDTO departmentDTO = DepartmentDTO.builder()
                .departmentId(1)
                .departmentName("Test").build();
        UserDTO userDTO = UserDTO.builder()
                .department(departmentDTO)
                .userId(1)
                .dob(new Date("30/12/1992"))
                .email("abc@gmail.com")
                .firstName("TestUser")
                .lastName("TestUser")
                .build();

        assertThrows(ResourceNotFoundException.class, ()->userService.createUser(userDTO));
        verify(userRepository, times(1)).findByEmail("abc@gmail.com");
        verify(departmentRepository, times(1)).findById(1);

    }

    @Test
    public void createUser_success()
    {
        User user = User.builder()
                .lastName("Test")
                .firstName("Test")
                .dob(new Date("30/12/1992"))
                .department(new Department(1, "Test"))
                .email("abc@gmail.com")
                .build();

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

        when(userRepository.findByEmail("abc@gmail.com")).thenReturn(Optional.empty());
        when(departmentRepository.findById(1)).thenReturn(Optional.of(new Department(1, "Test")));
        when(userRepository.save(user)).thenReturn(user);
        userService.createUser(userDTO);
        verify(userRepository, times(1)).findByEmail("abc@gmail.com");
        verify(userRepository, times(1)).save(user);
        verify(departmentRepository, times(1)).findById(1);

    }

    @Test
    public void getAllUsers_success()
    {
        User user1 = User.builder()
                .userId(1)
                .lastName("Test")
                .firstName("Test")
                .dob(new Date("30/12/1992"))
                .department(new Department(1, "Test"))
                .email("abc1@gmail.com")
                .build();

        User user2 = User.builder()
                .userId(2)
                .lastName("Test")
                .firstName("Test")
                .dob(new Date("30/12/1992"))
                .department(new Department(1, "Test"))
                .email("abc2@gmail.com")
                .build();
        when(userRepository.findAll()).thenReturn(List.of(user1, user2));
        List<UserDTO> userDTOS = userService.getAllUsers();
        assertTrue(userDTOS.size()==2);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void getUserById_success()
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
        UserDTO userDTO = userService.getUserById(1);
        assertTrue(userDTO.getUserId()==1);
        verify(userRepository, times(1)).findById(1);
    }

    @Test
    public void getUserById_failure()
    {
        when(userRepository.findById(1)).thenThrow(new ResourceNotFoundException("User not found"));
        assertThrows(ResourceNotFoundException.class, ()->{userService.getUserById(1);});
    }


    @Test
    public void deleteUser_failure()
    {
        when(userRepository.findById(1)).thenThrow(new ResourceNotFoundException("Resource Not Found"));
        assertThrows(ResourceNotFoundException.class,()->userService.deleteUser(1));
    }

    @Test
    public void deleteUser_success()
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
        doNothing().when(userRepository).deleteById(1);
        userService.deleteUser(1);
        verify(userRepository, times(1)).findById(1);
        verify(userRepository,times(1)).deleteById(1);
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

        assertThrows(ResourceNotFoundException.class, ()->userService.updateUser(userDTO));
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

        userService.updateUser(userDTO);
        verify(userRepository, times(1)).findById(1);
        verify(userRepository, times(1)).save(user1);
    }
}
