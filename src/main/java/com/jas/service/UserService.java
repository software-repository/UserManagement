package com.jas.service;

import com.jas.dto.UserDTO;

import java.util.List;

public interface UserService {

    public UserDTO createUser(UserDTO userDTO);
    public List<UserDTO> getAllUsers();
    public UserDTO getUserById(Integer id);
    public void updateUser(UserDTO userDTO, Integer id);
    public void deleteUser(Integer id);

}
