package com.jas.service;


import com.jas.dto.UserDTO;
import com.jas.entity.Department;
import com.jas.entity.User;
import com.jas.exceptions.ResourceNotFoundException;
import com.jas.exceptions.UserAlreadyExistsException;
import com.jas.repository.DepartmentRepository;
import com.jas.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    DepartmentService departmentService;

    public void createUser(User user){
        if(userAlreadyExists(user))
            throw new UserAlreadyExistsException("User already exists");
        if(departmentRepository.findById(user.getDepartmentId()).isEmpty())
            throw new ResourceNotFoundException("Department Id does not exist");
        userRepository.save(user);
    }

    public List<UserDTO> getAllUsers()
    {
        List<User> userlist = userRepository.findAll();
        List<UserDTO> userDTOS = new ArrayList<>();
        for(User user : userlist)
        {
            UserDTO userDTO = getUserDTO(user);
            userDTOS.add(userDTO);
        }

        return userDTOS;
    }

    public UserDTO getUserById(Integer id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty())
            throw new ResourceNotFoundException("User not found");
        return getUserDTO(user.get());
    }

    public void updateUser(User user) {
        User currentUser = userRepository.findById(user.getUserId()).get();
        if(currentUser==null)
            throw new ResourceNotFoundException("User not found");
        BeanUtils.copyProperties(user, currentUser);
        userRepository.save(currentUser);
    }


    public void deleteUser(Integer id) {
        if(userRepository.findById(id).isEmpty())
            throw new ResourceNotFoundException("User not found");
        userRepository.deleteById(id);
    }

    private boolean userAlreadyExists(User user)
    {
        return userRepository.findByEmail(user.getEmail())!=null;
    }


    private UserDTO getUserDTO(User user)
    {
        Optional<Department> department = departmentService.getDepartmentById(user.getDepartmentId());
        return UserDTO.builder().userId(user.getUserId())
                .dob(user.getDob())
                .emailId(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .department(department.get())
                .build();
    }



}
