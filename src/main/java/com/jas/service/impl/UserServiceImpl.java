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
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@CacheConfig(cacheNames = {"UserCache"})
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

    @Cacheable(cacheNames = "users")
    public List<UserDTO> getAllUsers()
    {
        return userRepository.findAll().stream()
                .map(u -> autoUserMapper.userEntityToDTO(u))
                .collect(Collectors.toList());
    }

    @Cacheable(cacheNames = "user", key = "#id", unless = "#result.userId > 3")
    public UserDTO getUserById(Integer id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty())
            throw new ResourceNotFoundException("User not found");
        return autoUserMapper.userEntityToDTO(user.get());
    }

    @Caching(evict = { @CacheEvict(cacheNames = "user", key = "#id"),
            @CacheEvict(cacheNames = "users", allEntries = true) })
    public void updateUser(UserDTO userDTO , Integer id) {
        if(id!=userDTO.getUserId())
            throw new IllegalArgumentException("Ids do not match");
        if(userRepository.findById(userDTO.getUserId()).isEmpty())
            throw new ResourceNotFoundException("userId not found");

        userRepository.save(autoUserMapper.userDTOtoEntity(userDTO));
    }


  @Caching(evict = { @CacheEvict(cacheNames = "user", key = "#id"),
            @CacheEvict(cacheNames = "users", allEntries = true) })
    public void deleteUser(Integer id) {
        if(userRepository.findById(id).isEmpty())
            throw new ResourceNotFoundException("User not found");
        userRepository.deleteById(id);
    }

}
