package com.jas.service.impl;


import com.jas.dto.UserDTO;
import com.jas.entity.User;
import com.jas.exceptions.ResourceNotFoundException;
import com.jas.exceptions.UserAlreadyExistsException;
import com.jas.mapper.AutoUserMapper;
import com.jas.repository.DepartmentRepository;
import com.jas.repository.UserRepository;
import com.jas.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    AutoUserMapper autoUserMapper;

    public UserDTO createUser(UserDTO userDTO){

       User user =  autoUserMapper.userDTOtoEntity(userDTO);
        if(!userRepository.findByEmail(user.getEmail()).isEmpty())
            throw new UserAlreadyExistsException("User already exists");
        if(departmentRepository.findById(user.getDepartment().getDepartmentId()).isEmpty())
            throw new ResourceNotFoundException("Department Id does not exist");
        userRepository.save(user);
        return userDTO;
    }

    public List<UserDTO> getAllUsers()
    {
        return userRepository.findAll().stream()
                .map(u -> autoUserMapper.userEntityToDTO(u))
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(Integer id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty())
            throw new ResourceNotFoundException("User not found");
        return autoUserMapper.userEntityToDTO(user.get());
    }

    public void updateUser(UserDTO userDTO) {
        if(userRepository.findById(userDTO.getUserId()).isEmpty())
            throw new ResourceNotFoundException("userId not found");

        userRepository.save(autoUserMapper.userDTOtoEntity(userDTO));
    }


    public void deleteUser(Integer id) {
        if(userRepository.findById(id).isEmpty())
            throw new ResourceNotFoundException("User not found");
        userRepository.deleteById(id);
    }

}
